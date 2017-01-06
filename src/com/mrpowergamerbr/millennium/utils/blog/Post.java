package com.mrpowergamerbr.millennium.utils.blog;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Transient;

import lombok.*;

@Getter
@Setter
@Entity(value = "posts", noClassnameStored = true)
public class Post {
	@Id
	public ObjectId id; // ID do post
	
	public ObjectId authorId; // ID do autor
	
	public String content; // Conte�do
	
	public String title; // T�tulo do post
	
	public String slug; // Slug
	
	public long date = System.currentTimeMillis(); // Data do post
	
	@Transient
	public transient Author author;
	
	@Transient
	public transient String htmlContent;
	
	@Transient
	public transient String fancyDate;
}
