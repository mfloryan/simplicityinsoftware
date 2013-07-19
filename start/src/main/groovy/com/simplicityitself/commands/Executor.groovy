package com.simplicityitself.commands

class Executor {

  def emailService
  def flightLog
  def loggingService
  def authorisationService

  Executor(emailService, flightLog, loggingService, authorisationService) {
    this.emailService = emailService
    this.flightLog = flightLog
    this.loggingService = loggingService
    this.authorisationService = authorisationService
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
