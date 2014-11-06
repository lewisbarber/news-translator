package com.news.translator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import com.news.translator.model.Translation;
import com.news.translator.repository.TranslationRepository;
import com.news.translator.response.ExecuteTranslationResponse;

@Service
public class TranslationServiceImpl implements TranslationService {

	@Autowired
	TranslationRepository translationRepository;

	@Autowired
	Environment env;

	public TranslationServiceImpl() {
		Translate.setClientId(env.getProperty("microsoft.azure.client.id"));
		Translate.setClientSecret(env.getProperty("microsoft.azure.client.secret"));
	}

	@Override
	public ExecuteTranslationResponse executeTranslation(String fromText, Language fromLanguage, Language toLanguage) throws Exception {

		ExecuteTranslationResponse response = new ExecuteTranslationResponse();

		Translation translation = null;
		try {
			translation = translationRepository.findByFromWordAndToLanguageAndFromLanguage(fromText, toLanguage.toString(),
					fromLanguage.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		String translatedText = fromText;
		if (translation != null) {
			translatedText = translation.getToWord();
		} else {

			translatedText = Translate.execute(fromText, fromLanguage, toLanguage);

			Translation newTranslation = new Translation();

			newTranslation.setFromLanguage(fromLanguage.toString());
			newTranslation.setToLanguage(toLanguage.toString());
			newTranslation.setToWord(translatedText);
			newTranslation.setFromWord(fromText);

			try {
				translationRepository.save(newTranslation);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		response.setFromWord(fromText);
		response.setToWord(translatedText);

		return response;

	}

}