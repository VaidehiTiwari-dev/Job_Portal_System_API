package com.example.demo.Config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component 
public class JwtUtil {

	public final String SECRET = "mysecretkeymysecretkeymysecretkey123";
	
	public Key getSign()
	{
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	
	//generateToken
	public String generateToken(String username)
	{
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getSign(),SignatureAlgorithm.HS256)
				.compact();
	}
	//extractUsername
	public String extractUsername(String token)
	{ 
		return Jwts.parserBuilder()
		.setSigningKey(getSign())
		.build()
		.parseClaimsJws(token)
		.getBody()
		.getSubject();
	}
	//validateToken
	public boolean validateToken(String username,String token)
	{
		return extractUsername(token).equals(username);       
	}
	
}
