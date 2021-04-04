package com.api.toolscript;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class ToolScriptApp {

	public static void main(String[] args) {
		SpringApplication.run(ToolScriptApp.class, args);
	}

}
