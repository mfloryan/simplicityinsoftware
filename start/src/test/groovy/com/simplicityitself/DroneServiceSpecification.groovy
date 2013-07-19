package com.simplicityitself

import com.simplicityitself.authorisation.AuthorisationService
import com.simplicityitself.email.EmailService
import com.simplicityitself.events.FlightLog
import com.simplicityitself.logging.LoggingService
import com.simplicityitself.remoting.RemotingService
import spock.lang.Specification

class DroneServiceSpecification extends Specification {


  // TODO Add before to load the application context to bring the components to life.

  def "exercise the drone service"() {
    given:
    def uut = new DroneService(
            new EmailService(),
            new FlightLog(),
            new LoggingService(),
            new AuthorisationService(),
            drone()
    )

    new RemotingService().registerBean(uut);

    when:
    def status = uut.takeOffAndHoverForSeconds(4, 4)

    status = uut.climbForSecondsAtSpecifiedRate(80, 3)

    uut.landIfAuthorised()

    then:
    status?.get("altitude") > 6
  }

  Drone drone() {
    new SimulDrone()
  }
}
