package com.simplicityitself.commands

import com.simplicityitself.Drone

class Hover extends BaseCommand {
  def hoverSeconds

  Hover(Drone drone, int hoverSeconds) {
    super(drone)
    this.hoverSeconds = hoverSeconds
  }

  def action() {
    drone.hover(hoverSeconds)
  }

}
