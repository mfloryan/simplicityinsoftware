package com.simplicityitself

class DroneService {

   Drone drone

    // TODO Expose these methods to JMX for 'integration' purposes
    // TODO Attempt to send an email if status indicates an emergency has occurred
    // TODO Code to unpack the status' will be rife in here.
    // TODO Add regular logging
    // TODO Add logging of commands such that they can be replayed 'in the future'

   def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffSeconds) {
       drone.takeOff(takeOffSeconds)
       drone.hover(hoverSeconds)
       drone.getCurrentStatus()
    }

    def climbForSecondsAtSpecifiedRate(float rate, int seconds) {
        drone.climb(seconds, rate)
    }

    def closeSession() {
        drone.land()
    }

    // TODO Implement
    def flyInACircle() {
        drone.getCurrentStatus()
    }
}
