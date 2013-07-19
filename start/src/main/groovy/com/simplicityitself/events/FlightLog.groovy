package com.simplicityitself.events

import groovy.util.logging.Slf4j

@Slf4j
class FlightLog {

  public void Log(logObject) {
    log.debug "REPLY: " + logObject.toString();
  }

}
