package com.ak.lancamentos.api.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ak.lancamentos.api.config.property.LancamentoApiProperty;

@Profile("oauth-security")
@RestController
@RequestMapping("/tokens")
public class TokenResource {
	
	@Autowired
	private LancamentoApiProperty lancamentoApiProperty;

	@DeleteMapping("/revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null); // Tirar o refreshToken do cookie
		cookie.setHttpOnly(true);
		cookie.setSecure(lancamentoApiProperty.getSeguranca().isEnableHttps()); // true em produção
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
}
