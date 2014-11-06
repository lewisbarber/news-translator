package com.news.translator.service;

import com.news.translator.model.NewsSource;
import com.news.translator.response.FetchArticleResponse;
import com.news.translator.response.FetchArticlesResponse;

public interface ArticleService {

	FetchArticleResponse fetchArticleByArticleId(Long articleId);

	void updateArticles() throws Exception;

	void saveArticleContent(String articleUrl, NewsSource newsSource) throws Exception;

	FetchArticlesResponse fetchAllArticles();

}