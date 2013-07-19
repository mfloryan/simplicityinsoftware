package com.simplicityitself.core.commands

import com.simplicityitself.Drone

class TakeOff extends BaseCommand {
  def height

  TakeOff(Drone drone, int height) {
    super(drone)
    this.height = height
  }

  def logEntry = "TakeOff $height"

  def action() {
    drone.initializeDrone()
    drone.takeOff(height)
  }

}
