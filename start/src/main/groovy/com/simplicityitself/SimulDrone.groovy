package com.simplicityitself

import groovy.util.logging.Slf4j

/**
 * Overly simplistic helicopter simulation.
 * Crudely emulates the drone behaviour, including some dleayed application of state (due to the helo flying)
 *
 * Open to concurrency issues galore.
 */
@Slf4j
class SimulDrone implements Drone {

  boolean landed = true
  boolean initialised = false

  final Map data = [height: 0,
              pitch:0, //in degrees
              roll:0,  // in degrees
              yaw:0,  // in degrees
              altitude: 0,// ;=> in meters
              "velocity-x":0,
              "velocity-y":0,
              "velocity-z":0]

  @Override
  void initializeDrone() {
    initialised=true
  }

  void checkInit() {
    log.error "Drone is not initialised"
    if (!initialised) throw new IllegalStateException("SimulDron is not initialied")
  }

  @Override
  void takeOff(int height) {
    checkInit()
    landed = false
    doAfter(2) {
      data.height = height
    }
  }

  @Override
  void hover(int time) {
    checkInit()


  }

  @Override
  void climb(int seconds, float rateOfClimb) {
    checkInit()

    doAfter(seconds) {
      data.height = rateOfClimb * seconds
    }
  }

  @Override
  Map getCurrentStatus() {
    checkInit()
    return data
  }

  @Override
  void land() {
    checkInit()
    doAfter(1) {
      landed = true
    }
  }

  @Override
  void spinRight(int seconds, float power) {
    checkInit()
    doAfter(seconds) {
      data.yaw += seconds * power
    }
  }

  @Override
  void spinLeft(int seconds, float power) {
    checkInit()

    doAfter(seconds) {
      data.yaw += seconds * power
    }
  }

  @Override
  void up(int seconds, float power) {
    checkInit()
  }

  @Override
  void down(int seconds, float power) {
    checkInit()

    doAfter(seconds) {
      data.height = 0
    }
  }

  @Override
  void tiltFront(int seconds, float power) {
    checkInit()
    data.roll = 30
    doAfter(seconds) {
      data.roll = 0
    }
  }

  @Override
  void tiltBack(int seconds, float power) {
    checkInit()
    data.tilt = -30
    doAfter(seconds) {
      data.tilt = 30
    }
  }

  @Override
  void tiltLeft(int seconds, float power) {
    checkInit()
    data.roll = -30
    doAfter(seconds) {
      data.roll = -30
    }
  }

  @Override
  void tiltRight(int seconds, float power) {
    checkInit()
    data.roll = 30
    doAfter(seconds) {
      data.roll = 0
    }
  }

  void doAfter(int seconds, Closure exec) {
    Thread.start {
      synchronized (data) {
        data.wait(seconds * 1000)
        exec()
      }
    }
  }
}
