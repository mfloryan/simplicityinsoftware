package com.simplicityitself

class DroneService {

  def emailService
  def eventLog
  def loggingService
  def authorisationService

  Drone drone

  DroneService(emailService, eventLog, loggingService, authorisationService, drone) {
    this.emailService = emailService
    this.eventLog = eventLog
    this.loggingService = loggingService
    this.authorisationService = authorisationService
    this.drone = drone
  }

  def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffSeconds) {
    eventLog.logTakeOff hoverSeconds, takeOffSeconds

    initialiseAndTakeOff(takeOffSeconds)

    drone.hover(hoverSeconds)

    NotifyForEmergency(drone.getCurrentStatus())
    return drone.getCurrentStatus()
  }

  def climbForSecondsAtSpecifiedRate(int rate, int seconds) {
    eventLog.logClimb(rate, seconds)
    loggingService.logClimbing(seconds, rate)

    drone.climb(seconds, (float) rate/ 100)

    drone.currentStatus
  }

  def landIfAuthorised() {
    loggingService.logInstructedToLand()

    authorisationService.executeWhenAuthorisedToLand(
      { drone.land() },
      { loggingService.logUnauthorisedToLand()})

  }


  def flyShape() {
    loggingService.logInstructedToFlyAShape()

    initialiseAndTakeOff(2)
    tiltAndSpin()

    def status = drone.currentStatus

    if (status.altitude < 1) {
     drone.climb(3, 0.7f)
    }

    3.times {
      tiltAndSpin()
    }

    drone.land()

    NotifyForEmergency(drone.getCurrentStatus())
    drone.getCurrentStatus()
  }

  private initialiseAndTakeOff(int takeoffHeight) {
    drone.initializeDrone()
    drone.takeOff(takeoffHeight)
  }

  private tiltAndSpin() {
    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)
  }

  private NotifyForEmergency(Map droneStatus) {
    if (droneStatus.get("emergency") == "detected") {
      emailService.sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }
  }
}
