package com.simplicityitself

import clojure.lang.Keyword
import spock.lang.Specification

class ExerciseSystemSpecification extends Specification {

    class DroneService {

        def facade = new com.simplicityitself.DroneFacade()

        def takeOffForTimeAndAltitudeAndGetStatus(seconds, altitude) {
            facade.takeOffForWithAltitude(seconds, altitude)
            facade.getCurrentStatus()
        }

        String land() {
            facade.land()
        }

    }

    def "exercise the system"() {
        given:
        def uut = new DroneService()

        when:
        def status = uut.takeOffForTimeAndAltitudeAndGetStatus(10, 4)

        uut.land()
        status?.keySet.each() {
            println it.class
        }

        then:
        status?.get(Keyword.intern("altitude")) < 2
    }

}