package com.akn.blog.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.akn.blog.entity.Role;
import com.akn.blog.entity.User;
import com.akn.blog.security.CustomUserDetails;

import io.jsonwebtoken.Claims;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (!hasAuthorizationHeader(request)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Get JWT access token Token from http request
		String accessToken = getAccessToken(request);

		// Validate token
		if (!jwtTokenUtil.validateJwtToken(accessToken)) {
			filterChain.doFilter(request, response);
			return;
		}

		// Setting Authentication
		setAuthenticationContext(accessToken, request);

		filterChain.doFilter(request, response);

	}

	private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
		UserDetails userDetails = getUserDetails(accessToken);

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// Set spring security
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private UserDetails getUserDetails(String accessToken) {

		User user = new User();
		Claims claims = jwtTokenUtil.parseClaims(accessToken);

		String claimRoles = (String) claims.get("roles");
		claimRoles = claimRoles.replace("[", "").replace("]", "");
		
		String[] roleNames = claimRoles.split(",");

		for (String aRoleName : roleNames) {
			user.addRole(new Role(aRoleName));
		}

		String subject = (String) claims.get(Claims.SUBJECT);
		String[] subjectArray = subject.split(",");

		user.setId(Long.parseLong(subjectArray[0]));
		user.setEmail(subjectArray[1]);

		CustomUserDetails customUserDetails = new CustomUserDetails(user);

		return customUserDetails;
	}

	// Check is header present
	private boolean hasAuthorizationHeader(HttpServletRequest request) {
		String header = request.getHeader("Authorization");

		if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
			return false;
		}
		return true;
	}

	// Bearer <accessToken>
	private String getAccessToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");

		if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
			return header.split(" ")[1].trim();
//			return header.substring(7, header.length());
		}
		return null;
	}

}
