package com.news.translator.response;

import java.util.List;

import com.news.translator.model.Article;

public class FetchArticlesResponse {

	private List<Article> articles;

	public FetchArticlesResponse() {

	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

}