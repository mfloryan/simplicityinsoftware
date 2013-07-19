package com.simplicityitself.core.commands

import com.simplicityitself.Drone

class Climb extends BaseCommand {

  def rate
  def seconds

  Climb(Drone drone, int rate, int seconds) {
    super(drone)
    this.rate = rate
    this.seconds = seconds
  }

  def logEntry = "Climb $rate, $seconds"

  def action() {
    drone.climb(seconds, (float) rate/ 100)
  }

}
