package com.news.translator.service;

import com.memetix.mst.language.Language;
import com.news.translator.response.ExecuteTranslationResponse;

public interface TranslationService {

	ExecuteTranslationResponse executeTranslation(String fromText, Language fromLanguage, Language toLanguage) throws Exception;

}