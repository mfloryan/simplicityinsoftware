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


  final static String toEmail = "david.dawson@simplicityitself.com"
  Drone drone

  DroneService() {
    //expose this on JMX
    new JmxBuilder().export {
      bean(this)
    }
  }

  def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffSeconds) {
    log.debug "REPLAY: takeOffAndHoverForSeconds($hoverSeconds, $takeOffSeconds)"
    drone.initializeDrone()
    drone.takeOff(takeOffSeconds)
    drone.hover(hoverSeconds)
    def status = drone.getCurrentStatus()
    if (status.get("emergency") == "detected") {
      sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }
    return status
  }

  def climbForSecondsAtSpecifiedRate(int rate, int seconds) {
    log.debug "REPLAY: climbForSecondsAtSpecifiedRate($rate, $seconds)"
    log.info "Climbing for $seconds at $rate"
    drone.climb(seconds, (float) rate/ 100)

   drone.currentStatus
  }

  def closeSession() {
    log.warn "Drone is instructed to land"

    if (userOk()) {
      drone.land()
    } else {
      log.error "Unable to land, user is not authorised."
    }

  }

  boolean userOk() {
    return System.properties["user.name"] == "mfloryan"
  }

  def flyShape() {
    log.info("Drone instructed to fly in a shape")

    drone.initializeDrone()
    drone.takeOff(2)
    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)

    def status = drone.currentStatus

    if (status.altitude < 1) {
     drone.climb(3, 0.7f)
    }

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
