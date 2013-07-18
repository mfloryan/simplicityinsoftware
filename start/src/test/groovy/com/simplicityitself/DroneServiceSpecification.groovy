package com.simplicityitself

import clojure.lang.Keyword
import spock.lang.Specification

class DroneServiceSpecification extends Specification {


  // TODO Add before to load the application context to bring the components to life.

  def "exercise the drone service"() {
    given:
    def uut = new DroneService()
    uut.drone = DroneBuilder.buildDrone()

    when:
    def status = uut.takeOffAndHoverForSeconds(4, 4)

    status = uut.climbForSecondsAtSpecifiedRate(0.8f, 3)

    uut.closeSession()

    then:
    status?.get(Keyword.intern("altitude")) < 2
  }

  Drone drone() {

  }
}
