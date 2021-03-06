package com.example.reddit.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;




@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JWTProvider provider;
	@Autowired UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	String jwt =	getJwtFromRequest(request);
	
	if(StringUtils.hasText(jwt) && provider.validateToken(jwt)) {
		String username = provider.getUsernameFromJwt(jwt);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, userDetails.getAuthorities());
	auth.setDetails(new WebAuthenticationDetails(request));
	
	SecurityContextHolder.getContext().setAuthentication(auth);
	}
	filterChain.doFilter(request, response);
	
		
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer") ) {
			return bearerToken.substring(7);
		}
		
		return bearerToken;
	}

	

}
