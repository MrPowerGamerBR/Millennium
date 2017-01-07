package com.mrpowergamerbr.millennium.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.jooby.Request;
import org.jooby.Response;
import org.jooby.Session;

import com.mitchellbosecke.pebble.template.PebbleTemplate;
import com.mrpowergamerbr.millennium.Millennium;
import com.mrpowergamerbr.millennium.utils.RenderWrapper;
import com.mrpowergamerbr.millennium.utils.blog.Post;

public class AdminPanelView {
	public static Object render(Request req, Response res) {
		return render(req, res, null);
	}

	public static Object render(Request req, Response res, String message) {
		try {
			HashMap<String, Object> context = new HashMap<String, Object>();

			context.put("message", message);

			Session session = req.session();

			if (session.get("loggedInAs").isSet()) {
				context.put("loggedInAs", session.get("loggedInAs").value());
			} else {
				return LoginPanelView.render(req, res);
			}

			if (message == null) { // Se a mensagem é diferente de null, ai nós redirecionamos para qualquer lugar necessário
				// Se não verificar isso, vai dar StackOverflowException
				if (req.path().startsWith("/admin/login")) { // Debug
					return LoginPanelView.render(req, res);
				} else if (req.path().startsWith("/admin/createpost")) {
					return PostCreateView.render(req, res);
				} else if (req.path().startsWith("/admin/editpost")) {
					return PostEditView.render(req, res);
				} else if (req.path().startsWith("/admin/createpage")) {
					return PageCreateView.render(req, res);
				} else if (req.path().startsWith("/admin/editpage")) {
					return PageEditView.render(req, res);
				}
			}

			long count = Millennium.client.getDatabase("millennium").getCollection("posts").count();

			context.put("postsPublicados", count);

			ArrayList<Post> posts = Millennium.getAllPosts();

			context.put("posts", posts);

			PebbleTemplate template = Millennium.engine.getTemplate("panel.html");

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
