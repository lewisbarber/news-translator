package com.news.translator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.news.translator.model.Translation;

public interface TranslationRepository extends JpaRepository<Translation, Long> {

	Translation findByToWordAndToLanguageAndFromLanguage(String toWord, String toLanguage, String fromLanguage);

	Translation findByFromWordAndToLanguageAndFromLanguage(String fromWord, String toLanguage, String fromLanguage);

	Translation findByToWordAndFromWordAndToLanguageAndFromLanguage(String toWord, String fromWord, String toLanguage, String fromLanguage);

}