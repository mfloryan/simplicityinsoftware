package com.simplicityitself.core.commands

import com.simplicityitself.logging.LoggingService

class Executor {

  def emailService
  def flightLog
  LoggingService loggingService
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
      loggingService.LogExecution(command)
      command.Execute()
    } else {
      loggingService.LogUnauthorised(command);
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
