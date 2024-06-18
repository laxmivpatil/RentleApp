package com.techverse.config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.api.client.util.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys; 


public class JwtValidator extends OncePerRequestFilter {
	

	  @Autowired
	    private PermitAllPathsProperties permitAllPathsProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 
		 String requestUri = request.getRequestURI();

		 System.out.println(requestUri);
	        // Check if the request URI matches any of the permitAll paths
	        if (isPermitAllPath(requestUri)) {
	        	filterChain.doFilter(request, response);
	            return;
	        }
		String jwt=request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt!=null)
		{
			jwt=jwt.substring(7);
			 try {
				SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
				Claims claims= Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
				
				String email=String.valueOf(claims.get("email"));
				
				String authorities=String.valueOf(claims.get("authorities"));
				
				List<GrantedAuthority> auths=AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication=new UsernamePasswordAuthenticationToken(email,null, auths);
				
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				
			}
			catch(Exception e) {
				// TODO: handle exception
				
				throw new BadCredentialsException("Invalid Token",e);
			} 
				 
		}
		filterChain.doFilter(request, response);
		 
		
	}
	
	
	private boolean isPermitAllPath(String requestUri) {
      //  List<String> permitAllPaths = permitAllPathsProperties.getPermitAllPaths();
		 List<String> permitAllPaths =new ArrayList<>();
		 permitAllPaths.add("/auth/signup");
		 permitAllPaths.add("/auth/signin");
		 permitAllPaths.add("/auth/validateotp");
		 permitAllPaths.add("/auth/generateotp");
        System.out.println(permitAllPaths.size());
        for (String path : permitAllPaths) {
            if (requestUri.startsWith(path)) {
                return true;
            }
        }
        return false;
    }

}