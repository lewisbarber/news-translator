package com.news.translator.response;

public class ExecuteTranslationResponse {

	private String fromWord;
	private String toWord;

	public ExecuteTranslationResponse() {

	}

	public String getFromWord() {
		return fromWord;
	}

	public void setFromWord(String fromWord) {
		this.fromWord = fromWord;
	}

	public String getToWord() {
		return toWord;
	}

	public void setToWord(String toWord) {
		this.toWord = toWord;
	}

}