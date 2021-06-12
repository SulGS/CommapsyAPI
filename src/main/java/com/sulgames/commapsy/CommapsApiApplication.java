package com.sulgames.commapsy;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.xml.sax.SAXException;

import com.sulgames.commapsy.Security.JWTAuthorizationFilter;
import com.sulgames.commapsy.utils.Utils;

@SpringBootApplication
public class CommapsApiApplication {

	public static void main(String[] args) {
		File file = new File("src/main/resources/plantilla.html");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Utils.document = documentBuilder.parse(file);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.document.normalize();
		SpringApplication.run(CommapsApiApplication.class, args);
		
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/User/login").permitAll()
				.antMatchers(HttpMethod.POST, "/User/register").permitAll()
				.antMatchers(HttpMethod.POST, "/User/validate").permitAll()
				.antMatchers(HttpMethod.POST, "/User/requestPasswordChange").permitAll()
				.antMatchers(HttpMethod.POST, "/User/changePassword").permitAll()
				.antMatchers(HttpMethod.POST, "/Admin").permitAll()
				.anyRequest().authenticated();
		}
	}

}
