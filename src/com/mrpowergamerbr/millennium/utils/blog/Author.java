package com.mrpowergamerbr.millennium.utils.blog;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import lombok.*;

@Getter
@Setter
@Entity(value = "authors", noClassnameStored = true)
public class Author {
	@Id
	public ObjectId id;
	
	@Indexed(options = @IndexOptions(unique = true))
	public String authorName;
	public String avatar;
	
	public String password; // SHA256
}
