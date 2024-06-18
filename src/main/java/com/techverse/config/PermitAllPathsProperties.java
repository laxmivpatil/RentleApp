package com.techverse.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
class PermitAllPathsProperties {
    private List<String> permitAllPaths;

	public List<String> getPermitAllPaths() {
		return permitAllPaths;
	}

	public void setPermitAllPaths(List<String> permitAllPaths) {
		this.permitAllPaths = permitAllPaths;
	}

    
}