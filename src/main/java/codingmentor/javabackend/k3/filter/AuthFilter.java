package codingmentor.javabackend.k3.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import codingmentor.javabackend.k3.Utils.UrlUtils;
import codingmentor.javabackend.k3.model.User;

@WebFilter(urlPatterns = {UrlUtils.ALL_PATH})
public class AuthFilter implements Filter{
	
	/*
    process before the request get in servlet
    chain.doFilter(request,response)
    process response from servlet
   */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");
        }

        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
        	
        HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse resp = (HttpServletResponse) response;
	    
	    if (hasSlashEnd(req)) {
	    	String path = req.getServletPath();
	    	path = path.substring(0, path.length() - 1);
	    	req.getRequestDispatcher(path).forward(req, resp);
	    	return;
	    }
	    
	    if (isInSession(req) && isLoginPage(req)) {
	    	resp.sendRedirect(req.getContextPath() + UrlUtils.ROOT_PATH + "/");
			return;
		}
	    
	    if (isInSession(req) && isUnauthorized(req)) {
	    	resp.sendError(HttpServletResponse.SC_FORBIDDEN);
	    	return;
	    }
	    

		if (isInSession(req) || isLoginPage(req) || isResourceRequest(req) || isSetUpPage(req))  {
			if (!isResourceRequest(req)) { // Prevent restricted pages from being cached.
				resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
				resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
				resp.setDateHeader("Expires", 0); // Proxies.
			}
			
			chain.doFilter(request, response);
		} else {
			// If not at home page nor logged in, send to /login
			resp.sendRedirect(req.getContextPath() + UrlUtils.LOGIN_PATH);
			// we return here so the original servlet is not processed
			return;
		}
	}	

	private boolean isLoginPage(HttpServletRequest request) { // if url = /login
		String servletPathpath = request.getServletPath(); 
		return servletPathpath.startsWith(UrlUtils.LOGIN_PATH) || servletPathpath.startsWith(UrlUtils.SHOW_REQUEST_PASSWORD_RESET_PATH) || servletPathpath.startsWith(UrlUtils.SHOW_USER_PASSWORD_RESET_PATH);
    }
	
	private boolean isSetUpPage(HttpServletRequest request) { // if url = /login
		String requestUrl = request.getRequestURL().toString();
		return  requestUrl.endsWith("set_up") || requestUrl.endsWith("accept_invite") || requestUrl.endsWith("use_password_reset");
    }
	
	private boolean isInSession (HttpServletRequest req) { // if no user logged in yet
		return req.getSession().getAttribute("current_user") != null;
	}
	
	private boolean isResourceRequest(HttpServletRequest request) { // if request to /assets
        String path = request.getRequestURI();
        return path.startsWith(request.getContextPath() + "/assets");
    }
	
	private boolean hasSlashEnd(HttpServletRequest request) {
		String path = request.getServletPath();
		return path.endsWith("/");
	}
	
	private boolean isUnauthorized(HttpServletRequest request) {
		User current_user = (User) request.getSession(false).getAttribute("current_user");
		String path =  request.getServletPath();
		return  (current_user != null && !current_user.isAdmin() && path.startsWith(UrlUtils.USERS_PATH));
	}
}
