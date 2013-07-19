package com.simplicityitself.remoting

import com.simplicityitself.DroneService
import groovy.jmx.builder.JmxBuilder

class RemotingService {

  def registerBean(DroneService beanToRegister) {

    //expose this on JMX
    new JmxBuilder().export {
      bean(beanToRegister)
    }
  }

}