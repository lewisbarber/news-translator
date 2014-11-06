package com.news.translator.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.memetix.mst.language.Language;
import com.news.translator.model.Article;
import com.news.translator.model.NewsSource;
import com.news.translator.repository.ArticleRepository;
import com.news.translator.repository.NewsSourceRepository;
import com.news.translator.response.FetchArticleResponse;
import com.news.translator.response.FetchArticlesResponse;
import com.news.translator.vo.RevertWordVO;

@Service
@EnableScheduling
public class ArticleServiceImpl implements ArticleService {

	private static Logger logger = Logger.getLogger(ArticleServiceImpl.class);

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	TranslationService translationService;

	@Autowired
	NewsSourceRepository newsSourceRepository;

	@Autowired
	DieZeitService dieZeitService;

	@Override
	public FetchArticleResponse fetchArticleByArticleId(Long articleId) {

		FetchArticleResponse response = new FetchArticleResponse();

		Article article = articleRepository.findOne(articleId);

		if (article == null) {
			return response;
		}

		logger.info("Fetched article: " + article.getUrl());

		response.setArticle(article);

		return response;

	}

	@Override
	public void saveArticleContent(String articleUrl, NewsSource newsSource) throws Exception {

		Document doc = null;
		try {

			doc = Jsoup.connect(articleUrl).get();

			logger.info("Fetched URL: " + articleUrl);

		} catch (Exception e) {

			logger.error("Error fetching URL: " + articleUrl);

			e.printStackTrace();

		}

		if (doc != null) {

			String articleTitle = null;
			String articleImage = null;
			List<String> paragraphs = new ArrayList<String>();

			switch (newsSource.getName()) {
			case "Die Zeit":

				articleTitle = dieZeitService.getArticleTitle(doc);
				articleImage = dieZeitService.getArticleImage(doc);
				paragraphs = dieZeitService.getArticleParagraphs(doc);

				break;

			default:
				break;
			}

			String articleBody = this.enrichArticle(paragraphs);

			if (articleBody.length() > 3 && articleBody.substring(0, 3).contains("<p>")) {

				Article article = new Article();

				article.setBody(articleBody);
				article.setImagePath(articleImage);
				article.setNewsSource(newsSource);
				article.setTitle(articleTitle);
				article.setUrl(articleUrl);

				try {

					articleRepository.save(article);

					logger.info("Saved article: " + articleUrl);

				} catch (Exception e) {

					logger.error("Error whilst saving article: " + articleUrl);

					e.printStackTrace();

				}

			}

		}

	}

	private String enrichArticle(List<String> paragraphs) throws Exception {

		String bodyToSave = "";
		Map<String, String> allTranslations = new HashMap<String, String>();
		Set<String> allWords = new HashSet<String>();
		for (String paragraph : paragraphs) {

			String paragraphWithoutPunctuation = this.removePunctionation(paragraph);
			String words[] = paragraphWithoutPunctuation.split("\\s+");

			for (String word : words) {
				allTranslations.put(word, translationService.executeTranslation(word, Language.GERMAN, Language.ENGLISH).getToWord());
				allWords.add(word);
			}

			bodyToSave += " <P>" + paragraph + "</P>";

		}

		List<RevertWordVO> revertWords = new ArrayList<RevertWordVO>();
		for (String word : allWords) {

			String translation = allTranslations.get(word);

			String wrappedWord = "<SPAN DATA-TOOLTIP ARIA-HASPOPUP='TRUE' CLASS='TIP-TOP TRANSLATED_WORD' TITLE='"
					+ translation.toUpperCase() + "'>" + word + "</SPAN>";

			RevertWordVO revertWord = new RevertWordVO();

			revertWord.setRevert(translation);
			revertWord.setUpper(translation.toUpperCase());

			revertWords.add(revertWord);

			bodyToSave = bodyToSave.replaceAll("\\b" + word + "\\b", wrappedWord);

		}

		for (RevertWordVO revertWord : revertWords) {
			bodyToSave = bodyToSave.replaceAll("\\b" + revertWord.getUpper() + "\\b", revertWord.getRevert());
		}

		bodyToSave = bodyToSave.replaceAll("(?i)SPAN", "span");
		bodyToSave = bodyToSave.replaceAll("(?i)DATA-TOOLTIP ARIA-HASPOPUP='TRUE' CLASS='TIP-TOP TRANSLATED_WORD' TITLE",
				"data-tooltip aria-haspopup='true' class='tip-top translated_word' title");
		bodyToSave = bodyToSave.replaceAll("(?i)<P>", "<p>");
		bodyToSave = bodyToSave.replaceAll("(?i)<p> ", "<p>");
		bodyToSave = bodyToSave.replaceAll("(?i)</P>", "</p>");

		return bodyToSave;

	}

	private String removePunctionation(String text) {
		return text.replaceAll("[^\\p{L}\\p{Nd} ]+", "");
	}

	@Override
	@Scheduled(cron = "0 0 1 * * ?")
	public void updateArticles() throws Exception {

		logger.info("Updating news articles from sources...");

		List<NewsSource> newsSources = newsSourceRepository.findAll();

		for (NewsSource newsSource : newsSources) {

			logger.info("Updating news articles for: " + newsSource.getName());

			Document doc = null;
			try {

				doc = Jsoup.connect(newsSource.getUrl()).get();

				logger.info("Fetched URL: " + newsSource.getUrl());

			} catch (Exception e) {

				logger.error("Error fetching URL: " + newsSource.getUrl());

				e.printStackTrace();

			}

			List<String> artileLinks = new ArrayList<String>();

			switch (newsSource.getName()) {
			case "Die Zeit":

				artileLinks = dieZeitService.getArticleLinks(doc);

				break;

			default:
				break;
			}

			for (String link : artileLinks) {

				Boolean validLink = false;

				switch (newsSource.getName()) {
				case "Die Zeit":

					validLink = dieZeitService.isLinkValid(link);

					break;

				default:
					break;
				}

				if (validLink) {
					this.saveArticleContent(link, newsSource);
				}

			}

		}

	}

	@Override
	public FetchArticlesResponse fetchAllArticles() {

		FetchArticlesResponse response = new FetchArticlesResponse();

		List<Article> allArticles = articleRepository.findAll();

		for (Article article : allArticles) {

			String body = article.getBody();

			String newBody = body.replaceAll("\\<.*?\\>", "");

			article.setBody(newBody.substring(0, 300));

		}

		response.setArticles(allArticles);

		return response;

	}

}