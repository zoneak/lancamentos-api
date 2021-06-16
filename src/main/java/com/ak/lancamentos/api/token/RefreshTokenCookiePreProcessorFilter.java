package com.ak.lancamentos.api.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		
		if ("/oauth/token".equalsIgnoreCase(req.getRequestURI()) && "refresh_token".equals(req.getParameter("grant_type")) && req.getCookies() != null) {
			for (Cookie c : req.getCookies()) {
				if (c.getName().equals("refreshToken")) {
					String refreshToken = c.getValue();
					req = new MyServletRequestWrapper(req, refreshToken); // sobreescrevendo a requisição pois a requisição original não pode ser alterada
				}
			}
		}
		
		chain.doFilter(req, response);
	}
	
	// Precisa dessa classe pq não é possível alterar o request depois que ele está pronto. o mapa não pode ser mais alterado
	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;
		
		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true);
			return map;
		}
		
	}

}
