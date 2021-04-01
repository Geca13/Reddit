package com.example.reddit.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;




@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JWTProvider provider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	String jwt =	getJwtFromRequest(request);
		provider.validateToken(jwt);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer") ) {
			return bearerToken.substring(7);
		}
		return bearerToken;
	}

	

}
