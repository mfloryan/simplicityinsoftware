package com.simplicityitself.commands

import com.simplicityitself.Drone

class Land extends BaseCommand {

  Land(Drone drone) {
    super(drone)
  }

  def logEntry = "Land"

  def action() {
    drone.land()
  }

}
