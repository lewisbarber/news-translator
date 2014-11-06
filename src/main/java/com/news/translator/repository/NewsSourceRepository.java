package com.news.translator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.translator.model.NewsSource;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {

}