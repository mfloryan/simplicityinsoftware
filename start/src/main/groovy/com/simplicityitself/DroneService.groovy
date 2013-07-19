package com.simplicityitself

import groovy.jmx.builder.JmxBuilder

class DroneService {

  def emailService
  def eventLog
  def loggingService

  Drone drone

  DroneService(emailService, eventLog, loggingService) {
    this.emailService = emailService
    this.eventLog = eventLog
    this.loggingService = loggingService

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
    loggingService.logClimbing(seconds, rate)
    drone.climb(seconds, (float) rate/ 100)

    drone.currentStatus
  }



  def closeSession() {
    loggingService.logInstructedToLand()

    if (userOk()) {
      drone.land()
    } else {
      loggingService.logUnauthorisedToLand()
    }

  }



  boolean userOk() {
    return System.properties["user.name"] == "mfloryan"
  }

  def flyShape() {
    loggingService.logInstructedToFlyAShape()

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
