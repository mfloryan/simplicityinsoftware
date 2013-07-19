package com.simplicityitself.commands

import com.simplicityitself.Drone

class FlyShape extends BaseCommand {

  FlyShape(Drone drone) {
    super(drone)
  }

  def action() {
    tiltAndSpin()

    def status = drone.currentStatus

    if (status.altitude < 1) {
      drone.climb(3, 0.7f)
    }

    3.times {
      tiltAndSpin()
    }
  }

  private tiltAndSpin() {
    drone.tiltFront(3, 0.6f)
    drone.spinRight(2, 0.7f)
  }


}
