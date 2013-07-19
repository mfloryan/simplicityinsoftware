package com.simplicityitself

import com.simplicityitself.commands.Command

class DroneService {

  def emailService
  def flightLog
  def loggingService
  def authorisationService
  Drone drone

  DroneService(emailService, flightLog, loggingService, authorisationService, drone) {
    this.emailService = emailService
    this.flightLog = flightLog
    this.loggingService = loggingService
    this.authorisationService = authorisationService
    this.drone = drone
  }

  def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffHeight) {
    flightLog.logTakeOff hoverSeconds, takeOffHeight

    initialiseAndTakeOff(takeOffHeight)

    drone.hover(hoverSeconds)

    NotifyForEmergency(drone.getCurrentStatus())
    return drone.getCurrentStatus()
  }

  def climbForSecondsAtSpecifiedRate(int rate, int seconds) {
    flightLog.logClimb(rate, seconds)
    loggingService.logClimbing(seconds, rate)

    drone.climb(seconds, (float) rate/ 100)

    drone.currentStatus
  }

  //
  def landIfAuthorised() {
    loggingService.logInstructedToLand()

    authorisationService.executeWhenAuthorisedToLand(
      { drone.land() },
      { loggingService.logUnauthorisedToLand()})

  }

  def execute(Command command) {

    flightLog.Log(command.logEntry)

    if (authorisationService.isAuthorised(command)) {
      command.Execute()
    } else {
      // Log
    }

    def status = command.Execute()
    NotifyForEmergency(status)
    status
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


  private NotifyForEmergency(Map droneStatus) {
    if (droneStatus.get("emergency") == "detected") {
      emailService.sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }
  }
}
