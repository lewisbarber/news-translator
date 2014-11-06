package com.news.translator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memetix.mst.language.Language;
import com.news.translator.response.ExecuteTranslationResponse;
import com.news.translator.service.TranslationService;

@Controller
@RequestMapping(value = "/translation")
public class TranslationController {

	@Autowired
	TranslationService translationService;

	@RequestMapping(value = "/execute", method = RequestMethod.GET)
	@ResponseBody
	public ExecuteTranslationResponse executeTranslation(@RequestParam("fromText") String fromText) throws Exception {

		return translationService.executeTranslation(fromText, Language.ENGLISH, Language.GERMAN);

	}

}