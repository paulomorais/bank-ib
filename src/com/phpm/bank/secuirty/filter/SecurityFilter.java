
package com.phpm.bank.secuirty.filter;

import java.io.IOException;

import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.phpm.bank.client.pojo.User;
import com.phpm.bank.util.ResourceLoader;

@WebFilter("*.xhtml")
public class SecurityFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		Object sessionObject = null;
		User user = null;
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		HttpSession session = httpServletRequest.getSession();

		if (httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER)) {
			chain.doFilter(request, response);
			return;
		}

		sessionObject = session.getAttribute("user");
		if (sessionObject != null && sessionObject instanceof User) {
			user = (User) sessionObject;
		}
		if (user == null && 
				!httpServletRequest.getRequestURI().startsWith(httpServletRequest.getContextPath() + "/login.xhtml")) {
			if (isAjax(httpServletRequest)) {
				httpServletResponse.getWriter().print(xmlPartialRedirectToPage(httpServletRequest, "/login.xhtml"));
				httpServletResponse.flushBuffer();
			} else {
				if (!httpServletResponse.isCommitted()) {
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
				}
			}
			return;
		}
		if (user != null) {
			String link = this.getSimpleHtml(httpServletRequest.getRequestURI());
			
			if (ResourceLoader.permissions.get(link) == null 
					|| !ResourceLoader.permissions.get(link).contains(user.getType())) {
				
				if (!httpServletResponse.isCommitted()) {
					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login.xhtml");
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	private String xmlPartialRedirectToPage(HttpServletRequest request,
			String page) {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<partial-response><redirect url=\"")
				.append(request.getContextPath())
				.append(request.getServletPath()).append(page)
				.append("\"/></partial-response>");
		return sb.toString();
	}

	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	private String getSimpleHtml(String html) {
		String[] partesHtmlUtil = html.split(";");
		String HtmlUtil = partesHtmlUtil[0];
		String[] partesHtml = HtmlUtil.split("/");
		return partesHtml[partesHtml.length - 1];
	}
}