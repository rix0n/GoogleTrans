package xss;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XSSFilter implements Filter {
	private FilterConfig config = null;
	private static boolean no_init = true;
	private String apostrophe = "&#39;";

	public void init(FilterConfig paramFilterConfig) throws ServletException {
		this.config = paramFilterConfig;
		no_init = false;
		String str = paramFilterConfig.getInitParameter("apostrophe");
		if (str != null) {
			this.apostrophe = str.trim();
		}
		System.out.println("XSS filter (c) Coldbeans mailto:info@servletsuite.com ver. 1.3");
	}

	public void destroy() {
		this.config = null;
	}

	public FilterConfig getFilterConfig() {
		return this.config;
	}

	public void setFilterConfig(FilterConfig paramFilterConfig) {
		if (no_init) {
			no_init = false;
			this.config = paramFilterConfig;
			String str = paramFilterConfig.getInitParameter("apostrophe");
			if (str != null) {
				this.apostrophe = str.trim();
			}
		}
	}

	public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse,
			FilterChain paramFilterChain) throws IOException, ServletException {
		paramFilterChain.doFilter(new RequestWrapper((HttpServletRequest) paramServletRequest, this.apostrophe),
				paramServletResponse);
	}
}
