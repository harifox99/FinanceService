
package org.bear.util;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.*;


public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body)
	{
		try
	    {
		    MimeMessage msg = new MimeMessage(session);
		    //set message headers
		    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		    msg.addHeader("format", "flowed");
		    msg.addHeader("Content-Transfer-Encoding", "8bit");
		    msg.setFrom(new InternetAddress("aluba0504@gmail.com", "聶爺"));
		    msg.setReplyTo(InternetAddress.parse("aluba0504@gmail.com", false));
		    msg.setSubject("每日籌碼報表", "UTF-8");
		    msg.setText(body, "UTF-8");
		    msg.setSentDate(new Date());
		    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		    System.out.println("Message is ready");
	    	Transport.send(msg);  
		    System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) 
		{
	    	e.printStackTrace();
	    }
	}
	public static void main(String args[])
	{
		System.out.println("SimpleEmail Start");
	    String smtpHostServer = "msa.hinet.net";
	    String emailID = "bear@cht.com.tw";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", smtpHostServer);
	    Session session = Session.getInstance(props, null);
	    EmailUtil.sendEmail(session, emailID, "每日籌碼報表", "SimpleEmail Testing Body");
	}
}
