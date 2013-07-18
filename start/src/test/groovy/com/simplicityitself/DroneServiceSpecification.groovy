package com.simplicityitself

import clojure.lang.Keyword
import spock.lang.Specification

class DroneServiceSpecification extends Specification {

//    class DroneService {
//
//        def drone = new com.simplicityitself.Drone()
//
//        def takeOffForTimeAndAltitudeAndGetStatus(takeOffSeconds, climbSeconds, hoverSeconds, rateOfAscent) {
//            drone.takeOff(takeOffSeconds)
//            drone.hover(hoverSeconds)
//            drone.climb(climbSeconds, rateOfAscent)
//            drone.getCurrentStatus()
//        }
//
//        String land() {
//            drone.land()
//        }
//
//    }

    // TODO Add before to load the application context to bring the components to life.

    def "exercise the drone service"() {
        given:
        def uut = new DroneService()
        uut.drone = DroneBuilder.buildDrone()

        when:
        //def status = uut.takeOffForTimeAndAltitudeAndGetStatus(10, 4, 4, 0.3)
        def status = uut.takeOffAndHoverForSeconds(4, 4)

        // TODO Finish this code...
        //status = uut.climbForSecondsAtSpecifiedRate(...)

        uut.closeSession()

        then:
        status?.get(Keyword.intern("altitude")) < 2
    }
}
