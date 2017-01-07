package com.mrpowergamerbr.millennium.utils.blog;

import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.jooby.Request;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.StrUtils;

@Entity(value = "globalviewcount", noClassnameStored = true)
public class ViewCount {
	@Id
	public ObjectId id;
	public HashMap<String, Long> views = new HashMap<String, Long>();
	
	public HashMap<String, Long> viewCache = new HashMap<String, Long>();
	
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
