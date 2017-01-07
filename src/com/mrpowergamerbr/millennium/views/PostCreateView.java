package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bson.types.ObjectId;
import org.jooby.Request;
import org.jooby.Response;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mongodb.client.model.Filters;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

public class PostCreateView {
	public static Object render(Request req, Response res) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			if (req.param("title").isSet()) {
				if (req.param("postContent").isSet()) {
					Post post = new Post();
					post.authorId = new ObjectId(req.session().get("loggedInId").value());
					post.content = req.param("postContent").value();
					post.slug = Millennium.slg.slugify(req.param("title").value());
					boolean sameSlug = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("slug", post.getSlug())).first() != null;

					int idx = 0;

					while (sameSlug) {
						if (idx > 100) {
							post.setSlug(post.getSlug() + "-" + UUID.randomUUID().toString());
						}
						post.setSlug(post.getSlug() + "-" + Millennium.rand.nextInt(1, 10000));
						sameSlug = Millennium.client.getDatabase("millennium").getCollection("posts").find(Filters.eq("slug", post.getSlug())).first() != null;
						idx++;
					}
					post.title = req.param("title").value();

					String strTags = req.param("tags").value();
					String[] split = strTags.split(", ");

					HashSet<String> tags = new HashSet<String>();

					for (String tag : split) {
						if (!tag.trim().isEmpty()) {
							tags.add(tag.trim());
						}
					}

					post.setTags(tags);

					Millennium.datastore.save(post);
					
					return AdminPanelView.render(req, res, "Post criado com sucesso!");
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
