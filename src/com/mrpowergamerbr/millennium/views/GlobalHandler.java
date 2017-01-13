package com.mrpowergamerbr.millennium.views;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;

import org.bson.Document;
import org.jooby.Request;
import org.jooby.Response;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.DateAndViews;
import com.mrpowergamerbr.millennium.utils.DateUtils;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.StrUtils;
import com.mrpowergamerbr.millennium.utils.TretaNewsGenerator;
import com.mrpowergamerbr.millennium.utils.blog.Post;
import com.mrpowergamerbr.millennium.utils.blog.ViewCount;
import com.mrpowergamerbr.millennium.utils.locale.LocaleFactory;

public class GlobalHandler {

	public static void render(Request req, Response res) {
		String path = req.path();
		Object render = null;

		if (path.startsWith("/droidtale")) {
			try {
				res.redirect("http://droidtale.mrpowergamerbr.com");
				res.send("Redirecting...");
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (path.startsWith("/tretanews")) {
			render = TretaNewsGenerator.generate(req, res);
			return;
		}
		
		Document doc = Millennium.client.getDatabase("millennium").getCollection("globalviewcount").find().first();
		
		ViewCount vc = null;
		if (doc != null) {
			vc = Millennium.datastore.get(ViewCount.class, doc.get("_id"));
			vc.addOneMoreView(req);
		} else {
			vc = new ViewCount();
			vc.addOneMoreView(req);
		}
		
		ArrayList<DateAndViews> dateAndViews = new ArrayList<DateAndViews>();
		
		for (Entry<String, Long> entry : vc.views.entrySet()) {
			// RegEx!
			Matcher matcher = StrUtils.datePatt.matcher(entry.getKey());
			matcher.find();
			
			Calendar cal = Calendar.getInstance();
			cal.set(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(1)));
			DateAndViews dav = new DateAndViews(entry.getValue(), cal);
			dav.setFancyDate(DateUtils.addZeroIfNeeded(matcher.group(1)) + "/" + DateUtils.addZeroIfNeeded(matcher.group(2)) + "/" + DateUtils.addZeroIfNeeded(matcher.group(3)));
			dateAndViews.add(dav);
		}
		
		Collections.sort(dateAndViews, new Comparator<DateAndViews>() {
			@Override
			public int compare(DateAndViews o1, DateAndViews o2) {
				return o1.getCalendar().compareTo(o2.getCalendar());
			}
		});
		
		HashMap<String, Object> defaultContext = new HashMap<String, Object>();

		ArrayList<Post> posts = Millennium.getAllPosts();

		posts.sort(new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return ((Long) o2.viewCount).compareTo(o1.viewCount);
			}
		});
		
		defaultContext.put("allTimePosts", posts);
		defaultContext.put("viewCounts", dateAndViews);
		defaultContext.put("websiteUrl", Millennium.websiteUrl);
		defaultContext.put("locale", LocaleFactory.getLocale(req.locale().getDisplayLanguage()));
		
		HashSet<String> locales = new HashSet<String>();
		for (Locale locale : Locale.getAvailableLocales()) {
			locales.add(locale.getDisplayLanguage());
		}
		defaultContext.put("locales", locales);
		
		if (req.session().isSet("loggedInAs")) {
			defaultContext.put("loggedInAs", req.session().get("loggedInAs").value());
		} else {
			defaultContext.put("loggedInAs", null);
		}
		
		if (path.equalsIgnoreCase("/")) {
			render = HomeView.render(req, res);
		} else if (path.startsWith("/admin")) {
			render = AdminPanelView.render(req, res);
		} else if (path.startsWith("/posts")) {
			render = PostView.render(req, res);
		} else if (path.startsWith("/pages")) {
			render = PageView.render(req, res);
		}

		PebbleTemplate compiledTemplate;
		StringWriter writer;
		String output;

		if(render instanceof RenderWrapper) {
			try {
				Map<String, Object> context = ((RenderWrapper)render).context;

				defaultContext.putAll(context);

				compiledTemplate = ((RenderWrapper)render).pebble;
				writer = new StringWriter();
				compiledTemplate.evaluate(writer, defaultContext);
				output = writer.toString();
				try {
					res.send(output);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			} catch (Exception var11) {
				StringWriter db1 = new StringWriter();
				PrintWriter coll1 = new PrintWriter(db1);
				var11.printStackTrace(coll1);
				try {
					res.send(db1.toString());
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else if(render == null) {
			HashMap<String, Object> context = new HashMap<String, Object>();

			defaultContext.putAll(context);

			compiledTemplate = null;

			try {
				compiledTemplate = Millennium.engine.getTemplate("404.html");
			} catch (PebbleException var14) {
				var14.printStackTrace();
			}

			writer = new StringWriter();

			try {
				compiledTemplate.evaluate(writer, defaultContext);
			} catch (PebbleException var12) {
				var12.printStackTrace();
			} catch (IOException var13) {
				var13.printStackTrace();
			}

			output = writer.toString();
			try {
				res.send(output);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				res.send(render);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
