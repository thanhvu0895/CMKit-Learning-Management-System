package codingmentor.javabackend.k3.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import codingmentor.javabackend.k3.Utils.JspUtils;
import codingmentor.javabackend.k3.Utils.UrlUtils;

@WebServlet(urlPatterns = {
		UrlUtils.ROOT_PATH,
		UrlUtils.SHOW_REQUEST_PASSWORD_RESET_PATH
})
public class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch(req.getServletPath()) {
		case UrlUtils.ROOT_PATH:
			req.getRequestDispatcher(JspUtils.KLASSES_INDEX)
			.forward(req, resp);
			break;
		case UrlUtils.SHOW_REQUEST_PASSWORD_RESET_PATH:
			req.getRequestDispatcher(JspUtils.PASSWORD_RESET_SHOW_PASSWORD_RESET_REQUEST)
				.forward(req, resp);
		break;
		}		
	}
}
