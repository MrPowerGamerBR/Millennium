package com.mrpowergamerbr.millennium;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.github.slugify.Slugify;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Sorts;
import com.mrpowergamerbr.millennium.utils.DateUtils;
import com.mrpowergamerbr.millennium.utils.blog.Author;
import com.mrpowergamerbr.millennium.utils.blog.Post;
import com.mrpowergamerbr.millennium.views.GlobalHandler;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import spark.Spark;

public class Millennium {
	public static final String rootFolder = "C:\\Users\\Whistler\\Documents\\Millennium\\";
	public static final String websiteUrl = "http://127.0.0.1:4567/";

	public static PebbleEngine engine;
	public static MongoClient client;
	public static Morphia morphia;
	public static Datastore datastore;
	public static Slugify slg;
	
	public static void main(String[] args) {
		FileLoader fl = new FileLoader();
		fl.setPrefix(rootFolder);
		engine = new PebbleEngine.Builder().cacheActive(false).strictVariables(true).templateCache(null).loader(fl).build();

		client = new MongoClient();
		morphia = new Morphia();
		datastore = morphia.createDatastore(client, "millennium");

		slg = new Slugify();
		Spark.externalStaticFileLocation(rootFolder + "static");
		get("*", (req, res) -> GlobalHandler.render(req, res));
		post("*", (req, res) -> GlobalHandler.render(req, res));

		Scanner scanner = new Scanner(System.in);

		while (true) {
			String line = scanner.nextLine();
			String[] cmds = line.split(" ");

			if (cmds[0].equals("createauthor")) {
				if (cmds.length == 3) {
					Author author = new Author();
					author.setAuthorName(cmds[1]);
					author.setPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(cmds[2]));

					System.out.println("Login: " + author.authorName);
					System.out.println("Password: " + author.password);
					
					datastore.save(author);
					
					System.out.println("Done!");
				} else {
					System.out.println("Author Creator");
					System.out.println("createauthor username pass");
				}
			}
		}
	}
	
	public static Post fillPost(Post post) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(post.content);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		post.setHtmlContent(renderer.render(document));
		
		Author author = Millennium.datastore.get(Author.class, post.getAuthorId());
		
		post.setAuthor(author);
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(post.date);

		String fancy = DateUtils.getFancyDay(cal.get(Calendar.DAY_OF_WEEK)) + ", " + DateUtils.addZeroIfNeeded(cal.get(Calendar.DAY_OF_MONTH)) + "/" + DateUtils.addZeroIfNeeded((cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR);
		post.setFancyDate(fancy);
		
		String smallDate = DateUtils.addZeroIfNeeded(cal.get(Calendar.DAY_OF_MONTH)) + "/" + DateUtils.addZeroIfNeeded((cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR);
		post.setSmallDate(smallDate);
		
		return post;
	}
	
	public static ArrayList<Post> getAllPosts() {
		ArrayList<Post> posts = new ArrayList<Post>();
		
		FindIterable<Document> docs = Millennium.client.getDatabase("millennium").getCollection("posts").find();
		
		docs.sort(Sorts.descending("date"));
		
		for (Document doc : docs) {
			Post post = Millennium.datastore.get(Post.class, doc.get("_id"));
			
			Parser parser = Parser.builder().build();
			Node document = parser.parse(post.content);
			HtmlRenderer renderer = HtmlRenderer.builder().build();
			post.setHtmlContent(renderer.render(document));
			
			Author author = Millennium.datastore.get(Author.class, post.getAuthorId());
			
			post.setAuthor(author);
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(post.date);

			String fancy = DateUtils.getFancyDay(cal.get(Calendar.DAY_OF_WEEK)) + ", " + DateUtils.addZeroIfNeeded(cal.get(Calendar.DAY_OF_MONTH)) + "/" + DateUtils.addZeroIfNeeded((cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR);
			post.setFancyDate(fancy);
			
			String smallDate = DateUtils.addZeroIfNeeded(cal.get(Calendar.DAY_OF_MONTH)) + "/" + DateUtils.addZeroIfNeeded((cal.get(Calendar.MONTH) + 1)) + "/" + cal.get(Calendar.YEAR);
			post.setSmallDate(smallDate);
			
			posts.add(post);
		}
		return posts;
	}
}