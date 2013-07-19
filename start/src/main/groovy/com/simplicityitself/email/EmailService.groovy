package com.simplicityitself.email

import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailService {

  final static String toEmail = "marcin.floryan@gmail.com"

  def sendMail(String subject, String messageText) {

    final String username = "1dayworkshop@simplicityitself.com";
    final String password = "simplesoftware";

    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
              protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
              }
            });

    try {

      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(username));
      message.setRecipients(Message.RecipientType.TO,
              InternetAddress.parse(toEmail));
      message.setSubject(subject);
      message.setText(messageText);

      Transport.send(message);

      log.debug "Sent message $subject"

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }

}
