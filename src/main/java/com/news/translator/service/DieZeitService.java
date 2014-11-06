package com.news.translator.service;

import java.util.List;

import org.jsoup.nodes.Document;

public interface DieZeitService {

	List<String> getArticleLinks(Document doc);

	Boolean isLinkValid(String link);

	List<String> getArticleParagraphs(Document doc);

	String getArticleTitle(Document doc);

	String getArticleImage(Document doc);

}