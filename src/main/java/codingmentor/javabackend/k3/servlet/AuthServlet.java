package codingmentor.javabackend.k3.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import codingmentor.javabackend.k3.DAO.LoginDAO;
import codingmentor.javabackend.k3.DAO.LoginDAOImpl;
import codingmentor.javabackend.k3.Utils.JspUtils;
import codingmentor.javabackend.k3.Utils.UrlUtils;


@WebServlet(name = "authServlet", urlPatterns = {
	UrlUtils.SIGN_IN,
	UrlUtils.SIGN_UP,
	UrlUtils.SIGN_OUT,
})
public class AuthServlet extends HttpServlet{

	private static final long serialVersionUID = -3801412244941307670L;
    private LoginDAOImpl loginDAOImpl = null;
    
    public AuthServlet() {
        loginDAOImpl = new LoginDAOImpl();
    }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch(req.getServletPath()) {
		case UrlUtils.SIGN_IN:
			req.getRequestDispatcher(JspUtils.SIGN_IN)
				.forward(req, resp);
			break;
		case UrlUtils.SIGN_UP:
			req.getRequestDispatcher(JspUtils.SIGN_UP)
				.forward(req,  resp);
			break;
		case UrlUtils.SIGN_OUT:
			req.getSession().invalidate();
			resp.sendRedirect(req.getContextPath() + UrlUtils.SIGN_IN);
			break;
		default:
			resp.sendRedirect(req.getContextPath() + UrlUtils.NOT_FOUND);
		}
	}
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		switch(req.getServletPath()) {
		case UrlUtils.SIGN_IN:
			processLogin(req, resp);
			break;
		}
	}
	
	/***
	 * Implement processLogin method
	 * Date: 31/12/2022
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void processLogin(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException {
		String email = req.getParameter("email");
        String password = req.getParameter("password");
        LoginDAO loginDAO = new LoginDAO(email, password);
       // ERROR = NULL => not empty = false
        try {
            if (loginDAOImpl.validate(loginDAO)) {
                HttpSession session = req.getSession();
                session.setAttribute("LOGIN_USER", email); // LOGINUSER = email we typed in
                req.removeAttribute("ERROR"); 
                resp.sendRedirect(req.getContextPath() + UrlUtils.HOME);
            } else { 	
                req.setAttribute("ERROR", "INVALID USERNAME OR PASSWORD");
            	req.getRequestDispatcher(JspUtils.SIGN_IN).forward(req, resp);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
}