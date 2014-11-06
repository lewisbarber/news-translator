package com.news.translator.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "article")
public class Article {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title", nullable = false, length = 255, unique = true)
	private String title;

	@Column(name = "body", nullable = false, length = 200000)
	private String body;

	@Column(name = "url", nullable = false, length = 4000, unique = true)
	private String url;

	@Column(name = "imagePath", nullable = false, length = 4000)
	private String imagePath;

	@ManyToOne(cascade = CascadeType.DETACH)
	@Fetch(value = FetchMode.JOIN)
	@JoinColumn(name = "newsSourceId", nullable = false)
	private NewsSource newsSource;

	@Column(name = "dateAdded", nullable = false)
	private Date dateAdded = new Date();

	public Article() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public NewsSource getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(NewsSource newsSource) {
		this.newsSource = newsSource;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}