package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.DateUtils;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Author;
import com.mrpowergamerbr.millennium.utils.blog.Post;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import spark.Request;
import spark.Response;

public class HomeView {

	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			ArrayList<Post> posts = new ArrayList<Post>();

			Post post = new Post();

			post.setContent("**Ol� Mundo!**");
			post.setTitle("Hello World!");
			Parser parser = Parser.builder().build();
			Node document = parser.parse(post.content);
			HtmlRenderer renderer = HtmlRenderer.builder().build();

			post.setHtmlContent(renderer.render(document));
			
			Author author = new Author();
			author.setAuthorName("MrPowerGamerBR");
			
			post.setAuthor(author);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(post.date);

			String fancy = DateUtils.getFancyDay(cal.get(Calendar.DAY_OF_WEEK)) + ", " + DateUtils.addZeroIfNeeded(cal.get(Calendar.DAY_OF_MONTH)) + "/" + DateUtils.addZeroIfNeeded((cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR);
			post.setFancyDate(fancy);
			
			posts.add(post);
			posts.add(post);
			posts.add(post);
			
			context.put("posts", posts);
			
			PebbleTemplate template = Millennium.engine.getTemplate("home.html");

			return new RenderWrapper(template, context);
		} catch (PebbleException e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return e.toString();
		}

	}
}
