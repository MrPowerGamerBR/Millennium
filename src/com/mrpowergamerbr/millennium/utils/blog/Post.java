package com.mrpowergamerbr.millennium.utils.blog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.jooby.Request;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Transient;

import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.StrUtils;

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
	
	@Indexed
	public String slug; // Slug
	
	@Indexed
	public HashSet<String> tags = new HashSet<String>();
	
	public long date = System.currentTimeMillis(); // Data do post
		
	public HashMap<String, Long> views = new HashMap<String, Long>();
	
	public HashMap<String, Long> viewCache = new HashMap<String, Long>();
	
	@Transient
	public transient Author author;
	
	@Transient
	public transient String htmlContent;
	
	@Transient
	public transient String fancyDate;
	
	@Transient
	public transient String smallDate;
	
	@Transient
	public transient long viewCount = 0;
	
	public long getTotalViewCount() {
		long viewCount = 0;
		for (Long l : views.values()) {
			viewCount += l;
		}
		return viewCount;
	}
	
	public void addOneMoreView(Request req) {
		if (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - viewCache.getOrDefault(StrUtils.ip2mongo(req.header("X-Forwarded-For").value()), 0L)) > 60) {
			viewCache.put(StrUtils.ip2mongo(req.header("X-Forwarded-For").value()), System.currentTimeMillis());
			
			Calendar cal = Calendar.getInstance();
			views.put(cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR), views.getOrDefault(cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR), 0L) + 1L);
			
			Millennium.datastore.save(this);
		}
	}
}
