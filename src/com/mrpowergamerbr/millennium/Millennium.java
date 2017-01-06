package com.mrpowergamerbr.millennium;

import static spark.Spark.*;

import java.util.Scanner;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.FileLoader;
import com.mongodb.MongoClient;
import com.mrpowergamerbr.millennium.utils.blog.Author;
import com.mrpowergamerbr.millennium.views.GlobalHandler;

import spark.Spark;

public class Millennium {
	public static final String rootFolder = "C:\\Users\\Whistler\\Documents\\Millennium\\";
	public static final String websiteUrl = "http://127.0.0.1:4567/";

	public static PebbleEngine engine;
	public static MongoClient client;
	public static Morphia morphia;
	public static Datastore datastore;

	public static void main(String[] args) {
		FileLoader fl = new FileLoader();
		fl.setPrefix(rootFolder);
		engine = new PebbleEngine.Builder().cacheActive(false).strictVariables(true).templateCache(null).loader(fl).build();

		client = new MongoClient();
		morphia = new Morphia();
		datastore = morphia.createDatastore(client, "millennium");

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
}