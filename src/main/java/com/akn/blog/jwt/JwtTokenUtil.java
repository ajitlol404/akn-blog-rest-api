package com.akn.blog.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.akn.blog.entity.User;
import com.akn.blog.exception.BlogAPIException;
import com.akn.blog.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpiration;

	@Autowired
	UserRepository userRepository;

	// Generate JWT Token
	public String generateAccessToken(UserDetails userDetails) {

		User user = userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername()).get();

		Date expirationDate = new Date(new Date().getTime() + jwtExpiration);

		String token = Jwts.builder().setSubject(user.getId() + "," + user.getEmail())
				.claim("roles", user.getRoles().toString()).setIssuer("AKN").setIssuedAt(new Date())
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

		return token;
	}

	// Validate JWT access Token
	public Boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid Jwt token");
		} catch (ExpiredJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired Jwt token");
		} catch (UnsupportedJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported Jwt token");
		} catch (IllegalArgumentException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Jwt claims string is empty");
		} catch (SignatureException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Signature validation failed");
		}
	}

	// Get Username/Subject from the token
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}

	public Claims parseClaims(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
	}

}
