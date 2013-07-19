package com.simplicityitself

import com.simplicityitself.commands.*

class DroneService {

  Drone drone
  Executor executor

  DroneService(drone, executor) {
    this.drone = drone
    this.executor = executor
  }

  def takeOffAndHoverForSeconds(int hoverSeconds, int takeOffHeight) {
    def takeOff = new TakeOff(drone, takeOffHeight);
    def hover = new Hover(drone, hoverSeconds);

    executor.execute(takeOff, hover)
  }

  def climbForSecondsAtSpecifiedRate(int rate, int seconds) {
    executor.execute(new Climb(drone, rate, seconds))
  }

  def landIfAuthorised() {
    executor.execute(new Land(drone))
  }

  def flyShape() {
    def takeOff = new TakeOff(drone)
    def flyShape = new FlyShape(drone)
    def land = new Land(drone)

    executor.execute(takeOff, flyShape, land)
  }
}
