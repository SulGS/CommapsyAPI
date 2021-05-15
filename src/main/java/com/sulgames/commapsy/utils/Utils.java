package com.sulgames.commapsy.utils;

import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.UserDataHandler;

import com.sulgames.commapsy.entities.User.User;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

public class Utils {
	
	public static Document document;
	
	public static String generateRandomKey() {
	    // El banco de caracteres
	    String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    // La cadena en donde iremos agregando un carácter aleatorio
	    
	    Random random = new Random();
	    String cadena = "";
	    for (int x = 0; x < 8; x++) {
	        int indiceAleatorio = random.nextInt(banco.length());
	        char caracterAleatorio = banco.charAt(indiceAleatorio);
	        cadena += caracterAleatorio;
	    }
	    return cadena;
	}
	
	public static JsonObject stringToJson(String jsonToParse) 
	{
		JsonReader jReader = Json.createReader(new StringReader(jsonToParse));

		JsonObject jsonValues = jReader.readObject();

		jReader.close();
		
		return jsonValues;
	}
	
	public static String hashString(String text) 
	{
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		byte[] encodedhash = digest.digest(
				text.getBytes(StandardCharsets.UTF_8));
		
		StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
	    for (int i = 0; i < encodedhash.length; i++) {
	        String hex = Integer.toHexString(0xff & encodedhash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

	public static boolean sendMail(User to, String subject, String body) 
	{
		Document copiedDocument = (Document) document.cloneNode(true);
		
		Element element = (Element)copiedDocument.getElementById("message");
		
		Element p = copiedDocument.createElement("p");
		p.setAttribute("style","color:white;");
		p.setTextContent("Hola " + to.getName() + " " + to.getSurname() + ",");
		
		element.appendChild(p);
		
		p = copiedDocument.createElement("p");
		p.setAttribute("style","color:white;");
		p.setTextContent(body);
		
		element.appendChild(p);
		
		StringWriter writer = new StringWriter();
		
		TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = null;
	   
	    try {
			transformer = tf.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
        //transform document to string 
        try {
			transformer.transform(new DOMSource(copiedDocument), new StreamResult(writer));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
        String xmlString = writer.getBuffer().toString();   
		
		
	    String remitente = "commapsy@gmail.com";  //Para la dirección nomcuenta@gmail.com

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    props.put("mail.smtp.clave", "miClaveDeGMail");    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, to.getMail());   //Se podrían añadir varios de la misma manera
	        message.setSubject(subject);
	        message.setContent(xmlString,"text/html");
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, "Commapsy1234");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();
	        return false;
	    }
		
		return true;
	}
}
