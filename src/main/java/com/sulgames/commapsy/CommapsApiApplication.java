package com.sulgames.commapsy;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

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

}
