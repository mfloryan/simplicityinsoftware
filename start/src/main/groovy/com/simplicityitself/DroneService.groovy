package com.simplicityitself

import com.simplicityitself.commands.*

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
    def takeOff = new TakeOff(drone, takeOffHeight);
    def hover = new Hover(drone, hoverSeconds);

    execute(takeOff, hover)
  }

  def climbForSecondsAtSpecifiedRate(int rate, int seconds) {
    execute(new Climb(drone, rate, seconds))
  }

  def landIfAuthorised() {
    execute(new Land(drone))
  }

  def flyShape() {
    def takeOff = new TakeOff(drone)
    def flyShape = new FlyShape(drone)
    def land = new Land(drone)

    execute( takeOff, flyShape, land)
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

  def execute(Command... commands) {
    def status
    commands.each { c ->
      status = execute(c)
    }
    status
  }

  private NotifyForEmergency(Map droneStatus) {
    if (droneStatus.get("emergency") == "detected") {
      emailService.sendMail("Emergency Detected", "We detected an emergency condition on the drone.")
    }
  }
}
