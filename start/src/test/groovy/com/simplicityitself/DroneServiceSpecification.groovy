package com.simplicityitself

import spock.lang.Specification

class DroneServiceSpecification extends Specification {


  // TODO Add before to load the application context to bring the components to life.

  def "exercise the drone service"() {
    given:
    def uut = new DroneService()
    uut.drone = drone()

    when:
    def status = uut.takeOffAndHoverForSeconds(4, 4)

    status = uut.climbForSecondsAtSpecifiedRate(80, 3)

    uut.closeSession()

    then:
    status?.get("altitude") > 6
  }

  Drone drone() {
    new SimulDrone()
  }
}
