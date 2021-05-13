package com.sulgames.commapsy.utils;

import java.io.StringReader;
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

public class Utils {
	
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

	public static boolean sendMail(String to, String subject, String body) 
	{
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
	        message.addRecipients(Message.RecipientType.TO, to);   //Se podrían añadir varios de la misma manera
	        message.setSubject(subject);
	        message.setText(body);
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
