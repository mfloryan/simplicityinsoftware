package com.simplicityitself

public interface Drone {
  void initializeDrone()

  void takeOff(int height)

  void hover(int time)

  void climb(int seconds, float rateOfClimb)

  Map getCurrentStatus()

  void land()

  void spinRight(int seconds, float powerPercentage)
  void spinLeft(int seconds, float powerPercentage)
  void up(int seconds, float powerPercentage)
  void down(int seconds, float powerPercentage)
  void tiltFront(int seconds, float powerPercentage)
  void tiltBack(int seconds, float powerPercentage)
  void tiltLeft(int seconds, float powerPercentage)
  void tiltRight(int seconds, float powerPercentage)

}
