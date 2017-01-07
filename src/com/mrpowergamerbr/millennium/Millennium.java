package com.mrpowergamerbr.millennium;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;
import java.util.SplittableRandom;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.jooby.Jooby;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.github.slugify.Slugify;
import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Sorts;
import com.mrpowergamerbr.millennium.utils.DateUtils;
import com.mrpowergamerbr.millennium.utils.JoobyTest;
import com.mrpowergamerbr.millennium.utils.blog.Author;
import com.mrpowergamerbr.millennium.utils.blog.Post;
import com.mrpowergamerbr.millennium.views.GlobalHandler;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

public class Millennium extends Jooby {
	public static String rootFolder = "/home/servers/millennium/root/";
	public static String websiteUrl = "http://mrpowergamerbr.com/";

	public static boolean development = false;

	public static PebbleEngine engine;
	public static MongoClient client;
	public static Morphia morphia;
	public static Datastore datastore;
	public static Slugify slg;

	public static final SplittableRandom rand = new SplittableRandom();

	{		
		port(4568);
		assets("/**", Paths.get(rootFolder + "static/"));
		get("/**", (req, res) -> res.send(GlobalHandler.render(req, res)));
		post("/**", (req, res) -> res.send(GlobalHandler.render(req, res)));
	}
	
	public static void main(String[] args) {
		if (development) {
			rootFolder = "D:\\workspace\\Millennium\\Millennium\\";
			websiteUrl = "http://127.0.0.1:4568/";
		}
		
		FileLoader fl = new FileLoader();
		fl.setPrefix(rootFolder);
		engine = new PebbleEngine.Builder().cacheActive(false).strictVariables(true).templateCache(null).loader(fl).build();

		client = new MongoClient();
		morphia = new Morphia();
		datastore = morphia.createDatastore(client, "millennium");

		slg = new Slugify();

		/* get("*", (req, res) -> GlobalHandler.render(req, res));
		post("*", (req, res) -> GlobalHandler.render(req, res));
		Spark.exception(RuntimeException.class, (e, request, response) -> {
			if(!request.pathInfo().contains("wsebchat")) {
				response.status(404);
				GlobalHandler.render(request, response);
			}

		}); */

		run(Millennium::new, args);
		
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
		return getAllPosts(Sorts.descending("date"));
	}

	public static ArrayList<Post> getAllPosts(Bson sort) {
		ArrayList<Post> posts = new ArrayList<Post>();

		FindIterable<Document> docs = Millennium.client.getDatabase("millennium").getCollection("posts").find();

		docs.sort(sort);

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

	public static HashSet<String> getAllTags() {
		HashSet<String> tags = new HashSet<String>();
		ArrayList<Post> posts = getAllPosts();

		for (Post post : posts) {
			tags.addAll(post.tags);
		}

		return tags;
	}
}