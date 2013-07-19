package com.simplicityitself.logging

import groovy.util.logging.Slf4j

@Slf4j
class LoggingService {

  public void logInstructedToFlyAShape() {
    log.info("Drone instructed to fly in a shape")
  }

  public void logInstructedToLand() {
    log.warn "Drone is instructed to land"
  }

  public void logClimbing(int seconds, int rate) {
    log.info "Climbing for $seconds at $rate"
  }

  def LogUnauthorised(command) {
    log.error "Not authorised to execute " + command
  }

  def LogExecution(command) {
    log.info "Executing " + command
  }
}
