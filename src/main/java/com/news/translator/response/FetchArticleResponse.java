package com.news.translator.response;

import com.news.translator.model.Article;

public class FetchArticleResponse {

	private Article article;

	public FetchArticleResponse() {

	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}