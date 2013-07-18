package com.simplicityitself

import groovy.util.logging.Slf4j

@Slf4j
class DroneService {

   Drone drone

    // TODO Expose these methods to JMX for 'integration' purposes
    // TODO Attempt to send an email if status indicates an emergency has occurred
    // TODO Code to unpack the status' will be rife in here.
    // TODO Add logging of commands such that they can be replayed 'in the future'

   def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffSeconds) {
     log.info("Taking off for [$takeOffSeconds] and hovering for [$hoverSeconds]")
       drone.takeOff(takeOffSeconds)
       drone.hover(hoverSeconds)
       drone.getCurrentStatus()
    }

    def climbForSecondsAtSpecifiedRate(float rate, int seconds) {
      log.info "Climbing for $seconds at $rate"
        drone.climb(seconds, rate)
    }

    def closeSession() {
      log.warn "Drone is instructed to land"
        drone.land()
    }

    // TODO Implement
    def flyInACircle() {
        drone.getCurrentStatus()
    }
}
