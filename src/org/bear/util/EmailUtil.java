package org.bear.util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
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
	public static void sendEmail(Session session, String toEmail, String subject, String body, String user, String pass, 
			Set <String> KdGolden , Set<String> vcpSets, Set<String> orchidSets)
	{
		try
	    {
			String[] to = 
			{
				"aluba0504@gmail.com",
				"Kuoxingying@gmail.com",
				"3hf.unds@gmail.com",
				"mosquitoboy513@gmail.com",
				"squidcar@gmail.com",
				"zxcpkpkjcjc@gmail.com",
				"boss4310@hotmail.com",
				"chen18850@hotmail.com",
				"lichain7@gmail.com",
				"yinliying3836@gmail.com",
				"peter198729@gmail.com",
				"netflix.loveabby@gmail.com",
				"a531626@gmail.com"
			};
		    MimeMessage msg = new MimeMessage(session);
		    //set message headers
		    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		    msg.addHeader("format", "flowed");
		    msg.addHeader("Content-Transfer-Encoding", "8bit");
		    msg.setFrom(new InternetAddress("love.wine@msa.hinet.net"));
		    
		    InternetAddress[] sendTo = new InternetAddress[to.length];
			for (int i = 0; i < to.length; i++) 
			{
				System.out.println("發送到:" + to[i]);
				sendTo[i] = new InternetAddress(to[i]);
			}
		    //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			msg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
		    msg.setReplyTo(InternetAddress.parse("aluba0504@gmail.com", false));
		    msg.setSubject("每日籌碼報表", "UTF-8");

		    //Body Content
		    body = "VCP選股:";
		    List<String> list;
		    list = new ArrayList<String>();
		    list.addAll(vcpSets);
		    for (int i = 0; i < list.size(); i++)
		    	body = body + "," + list.get(i);
		    body = body + "\n";
		    body = body + "蘭弦選股:";
		    list = new ArrayList<String>();
		    list.addAll(orchidSets);
		    for (int i = 0; i < list.size(); i++)
		    	body = body + "," + list.get(i);
		    body = body + "\n";
		    body = body + "VCP + 蘭弦選股:";
		    vcpSets.retainAll(orchidSets);
		    list = new ArrayList<String>();
		    list.addAll(vcpSets);
		    for (int i = 0; i < list.size(); i++)
		    	body = body + "," + list.get(i);
		    body = body + "\n";
		    body = body + "日週KD黃金交叉:";		    
		    list = new ArrayList<String>();
		    list.addAll(KdGolden);
		    for (int i = 0; i < list.size(); i++)
		    	body = body + "," + list.get(i);		    
		    body = body + "\n";
		    body = body + "日週KD黃金交叉+VCP+蘭弦:";
		    vcpSets.retainAll(KdGolden);
		    list = new ArrayList<String>();
		    for (int i = 0; i < list.size(); i++)
		    	body = body + "," + list.get(i);
		    //End Body
		    msg.setText(body, "UTF-8");
		    msg.setSentDate(new Date());
		    System.out.println("Message is ready");
	    	Transport.send(msg, user, pass);  
		    System.out.println("EMail Sent Successfully!!");
	    }
	    catch (Exception e) 
		{
	    	e.printStackTrace();
	    }
	}
	public static void main(String args[])
	{
		final String user = "love.wine@msa.hinet.net";
	    final String pass = "chtl@9191";   
		System.out.println("SimpleEmail Start");
	    String smtpHostServer = "msa.hinet.net";
	    String emailID = "aluba0504@gmail.com";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", smtpHostServer);
	    Session session = Session.getInstance(props, null);
	    //EmailUtil.sendEmail(session, emailID, "每日籌碼報表", "SimpleEmail Testing Body", user, pass);
	}
}
