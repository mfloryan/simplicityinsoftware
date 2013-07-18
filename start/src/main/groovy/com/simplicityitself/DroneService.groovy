package com.simplicityitself

import groovy.jmx.builder.JmxBuilder
import groovy.util.logging.Slf4j

import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Slf4j
class DroneService {

  Drone drone

  DroneService() {
    //expose this on JMX
    new JmxBuilder().export {
      bean(this)
    }
  }

  // TODO Attempt to send an email if status indicates an emergency has occurred
  // TODO Code to unpack the status' will be rife in here.
  // TODO Add logging of commands such that they can be replayed 'in the future'

  def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffSeconds) {
    log.info("Taking off for [$takeOffSeconds] and hovering for [$hoverSeconds]")
    drone.initializeDrone()
    drone.takeOff(takeOffSeconds)
    drone.hover(hoverSeconds)
    def status = drone.getCurrentStatus()
    if (status.get("emergency") == "detected") {
      sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }
    return status
  }

  def climbForSecondsAtSpecifiedRate(float rate, int seconds) {
    log.info "Climbing for $seconds at $rate"
    drone.climb(seconds, rate)
  }

  def closeSession() {
    log.warn "Drone is instructed to land"
    drone.land()
  }

  def flyShape() {
    log.info("Drone instructed to fly in a shape")

    drone.initializeDrone()
    drone.takeOff(2)
    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)

    def status = drone.currentStatus

    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)

    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)

    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)

    drone.land()

    status = drone.getCurrentStatus()
    if (status.get("emergency") == "detected") {
      sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }

    return status
  }

  def sendMail(String subject, String messageText) {
      final String to = "david.dawson@simplicityitself.com"
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
            InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(messageText);

        Transport.send(message);

        log.debug "Sent message $subject"

      } catch (MessagingException e) {
        throw new RuntimeException(e);
      }
    }
}
