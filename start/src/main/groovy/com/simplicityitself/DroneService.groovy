package com.simplicityitself

import groovy.jmx.builder.JmxBuilder
import groovy.util.logging.Slf4j


@Slf4j
class DroneService {

  def emailService
  def eventLog

  Drone drone

  DroneService(emailService, eventLog) {
    this.emailService = emailService
    this.eventLog = eventLog
    //expose this on JMX
    new JmxBuilder().export {
      bean(this)
    }
  }

  def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffSeconds) {
    eventLog.logTakeOff hoverSeconds, takeOffSeconds
    drone.initializeDrone()
    drone.takeOff(takeOffSeconds)
    drone.hover(hoverSeconds)
    def status = drone.getCurrentStatus()
    if (status.get("emergency") == "detected") {
      emailService.sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }
    return status
  }


  def climbForSecondsAtSpecifiedRate(int rate, int seconds) {
    eventLog.logClimb(rate, seconds)
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
      emailService.sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }

    return status
  }

}
