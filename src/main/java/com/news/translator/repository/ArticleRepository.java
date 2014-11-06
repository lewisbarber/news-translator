package com.news.translator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.translator.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}