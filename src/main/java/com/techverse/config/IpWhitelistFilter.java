package com.techverse.config;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class IpWhitelistFilter extends OncePerRequestFilter {

    private final List<String> whitelist = Arrays.asList(
        "192.168.1.1",
        "127.0.0.1","localhost","0:0:0:0:0:0:0:1"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String remoteIp = request.getRemoteAddr();
        System.out.println("jgjsdgj"+remoteIp);
        if (whitelist.contains(remoteIp)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
