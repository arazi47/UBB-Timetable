package com.razi.ubbtt.Utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSystem {
    /**
     *
     * @param fromUsername the scs webmail address of the student/teacher that sent a message
     * @param messageContents the contents of the message
     *
     * Note: the message will be delivered to the developer's e-mail
     *
     * DISABLED (currently saving complaints in the DB)
     */
    public static void sendMail(String fromUsername, String messageContents) {
        String username = "to_email";
        String password = "to_password";

        Properties properties = new Properties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, null);
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(username)
            );
            message.setSubject("title here");
            message.setContent(messageContents, "text/html");

            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
