package com.news.translator.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "translation")
public class Translation {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "toWord", nullable = false, length = 255)
	private String toWord;

	@Column(name = "fromWord", nullable = false, length = 255)
	private String fromWord;

	@Column(name = "toLanguage", nullable = false, length = 10)
	private String toLanguage;

	@Column(name = "fromLanguage", nullable = false, length = 10)
	private String fromLanguage;

	@Column(name = "dateAdded", nullable = false)
	private Date dateAdded = new Date();

	public Translation() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToWord() {
		return toWord;
	}

	public void setToWord(String toWord) {
		this.toWord = toWord;
	}

	public String getFromWord() {
		return fromWord;
	}

	public void setFromWord(String fromWord) {
		this.fromWord = fromWord;
	}

	public String getToLanguage() {
		return toLanguage;
	}

	public void setToLanguage(String toLanguage) {
		this.toLanguage = toLanguage;
	}

	public String getFromLanguage() {
		return fromLanguage;
	}

	public void setFromLanguage(String fromLanguage) {
		this.fromLanguage = fromLanguage;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}