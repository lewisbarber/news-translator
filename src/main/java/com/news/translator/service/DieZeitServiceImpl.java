package com.news.translator.service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class DieZeitServiceImpl implements DieZeitService {

	@Override
	public Boolean isLinkValid(String link) {
		return link.contains("http://www") && !link.contains("-live");
	}

	@Override
	public List<String> getArticleLinks(Document doc) {

		Elements imageLinks = doc.getElementsByAttributeValue("class", "teaser-image-wrap");

		List<String> articleLinks = new ArrayList<String>();

		for (Element link : imageLinks) {

			String linkContent = link.attr("href");

			articleLinks.add(linkContent);

		}

		return articleLinks;

	}

	@Override
	public List<String> getArticleParagraphs(Document doc) {

		Elements bodyElements = doc.getElementsByAttributeValue("class", "article-body");
		List<String> finalParagraphs = new ArrayList<String>();
		if (bodyElements.size() > 0) {
			Element bodyHolder = bodyElements.get(0);
			Elements paragraphs = bodyHolder.getElementsByTag("p");
			for (Element paragraph : paragraphs) {
				if (!paragraph.hasAttr("class")) {
					finalParagraphs.add(paragraph.text());
				}
			}
		}

		return finalParagraphs;

	}

	@Override
	public String getArticleTitle(Document doc) {

		Elements titleElements = doc.getElementsByAttributeValue("class", "title");
		String articleTitle = null;
		for (Element element : titleElements) {
			if (element.tagName() == "span") {
				articleTitle = element.text();
			}
		}

		return articleTitle;

	}

	@Override
	public String getArticleImage(Document doc) {

		Elements imageElements = doc.getElementsByAttributeValue("rel", "image_src");
		String articleImage = null;
		for (Element element : imageElements) {
			if (element.tagName() == "link") {
				articleImage = element.attr("href");
			}
		}

		return articleImage;

	}

}