package com.simplicityitself.events

import groovy.util.logging.Slf4j

@Slf4j
class FlightLog {

  public void logTakeOff(int hoverSeconds, int takeOffSeconds) {
    log.debug "REPLAY: takeOffAndHoverForSeconds($hoverSeconds, $takeOffSeconds)"
  }

  public void logClimb(int rate, int seconds) {
    log.debug "REPLAY: climbForSecondsAtSpecifiedRate($rate, $seconds)"
  }

}
