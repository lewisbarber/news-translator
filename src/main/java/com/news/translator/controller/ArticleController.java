package com.news.translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.news.translator.response.FetchArticleResponse;
import com.news.translator.response.FetchArticlesResponse;
import com.news.translator.service.ArticleService;

@Controller
@RequestMapping(value = "/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	@RequestMapping("/article")
	public String index() {
		return "article";
	}

	@ResponseBody
	@RequestMapping(value = "/fetch/{articleId}", method = RequestMethod.GET)
	public FetchArticleResponse fetchArticle(@PathVariable("articleId") Long articleId) {
		return articleService.fetchArticleByArticleId(articleId);
	}

	@ResponseBody
	@RequestMapping(value = "/fetch-all", method = RequestMethod.GET)
	public FetchArticlesResponse fetchAll() {
		return articleService.fetchAllArticles();
	}

	@RequestMapping(value = "/fetch-test", method = RequestMethod.GET)
	public void fetchTest() throws Exception {
		articleService.updateArticles();
	}

}