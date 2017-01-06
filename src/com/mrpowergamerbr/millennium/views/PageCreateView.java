package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Page;
import com.mrpowergamerbr.millennium.utils.blog.Post;

import spark.Request;
import spark.Response;

public class PageCreateView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			if (req.queryParams("title") != null) {
				if (req.queryParams("postContent") != null) {
					Post post = new Page();
					post.authorId = req.session().attribute("loggedInId");
					post.content = req.queryParams("postContent");
					post.slug = Millennium.slg.slugify(req.queryParams("title"));
					boolean sameSlug = Millennium.client.getDatabase("millennium").getCollection("pages").find(Filters.eq("slug", post.getSlug())).first() != null;

					int idx = 0;

					while (sameSlug) {
						if (idx > 100) {
							post.setSlug(post.getSlug() + "-" + UUID.randomUUID().toString());
						}
						post.setSlug(post.getSlug() + "-" + Millennium.rand.nextInt(1, 10000));
						sameSlug = Millennium.client.getDatabase("millennium").getCollection("pages").find(Filters.eq("slug", post.getSlug())).first() != null;
						idx++;
					}
					post.title = req.queryParams("title");

					String strTags = req.queryParams("tags");
					String[] split = strTags.split(", ");

					HashSet<String> tags = new HashSet<String>();

					for (String tag : split) {
						if (!tag.trim().isEmpty()) {
							tags.add(tag.trim());
						}
					}

					post.setTags(tags);

					Millennium.datastore.save(post);
					
					return AdminPanelView.render(req, res, "P�gina criada com sucesso!");
				}
			}

			context.put("tags", Millennium.getAllTags());

			PebbleTemplate template = Millennium.engine.getTemplate("postcreate.html");

			return new RenderWrapper(template, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return e.toString();
		}
	}
}
