package com.techverse.config;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

 

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys; 

@Service
public class JwtProvider {
	
	SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication auth)
	{
		  Date issuedAt = new Date();
	        // Calculate expiry time as 5 months from the issued date
	        Date expiryDate = new Date(issuedAt.getTime() + (150L * 24L * 60L * 60L * 1000L)); // 150 days in milliseconds
	       
		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.claim("email", auth.getName())
				.signWith(key).compact();

				
		return jwt;
	}
	public String generateToken1(String emailorphone)
	{
		 Date issuedAt = new Date();
	        // Calculate expiry time as 5 months from the issued date
	        Date expiryDate = new Date(issuedAt.getTime() + (150L * 24L * 60L * 60L * 1000L)); // 150 days in milliseconds
	 
		String jwt=Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(expiryDate)
				.claim("email", emailorphone)
				.signWith(key).compact();

				
		return jwt;
	}
	
	public String getEmailfromToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
		
		String email=String.valueOf(claims.get("email"));
		
		return email;
	}
	

}