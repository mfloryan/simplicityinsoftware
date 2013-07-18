(ns com.simplicityitself.dronedriver
  (:require [clj-drone.core :refer :all]
            [clj-drone.navdata :refer :all])
  (:gen-class
    :name com.simplicityitself.Drone
    :methods [[initializeDrone [] void]
              [takeOff [int] void]
              [hover [int] void]
              [climb [int, float] void]
              [getCurrentStatus [] java.util.Map]
              [land [] void]]))

(defn -initializeDrone [this]
  (drone-initialize)

  (drone-init-navdata))

(defn -takeOff [this seconds]
  (drone-do-for seconds :take-off))

(defn -hover [this seconds]
  (drone-do-for seconds :hover))

(defn -climb [this seconds rateOfClimb]
  (drone-do-for seconds :up rateOfClimb))

(defn -getCurrentStatus [this]

  @nav-data)

; TODO Expose a whole bunch of the rest of the functionality for fun

(defn -land [this]

  (drone :land)

  (end-navstream))


; Miscellaneous experimental commands
(defn -takeOffForWithAltitude [this takeOffSeconds climbSeconds hoverSeconds rateOfClimb]

  (drone-initialize)

  (drone-init-navdata)

  (drone-do-for takeOffSeconds :take-off)

  (drone-do-for hoverSeconds :hover)

  (drone-do-for climbSeconds :up rateOfClimb)

  (drone-do-for hoverSeconds :hover))

(defn -testDrone [this]

  (drone-initialize)

  (drone-init-navdata)

  (drone-do-for 4 :take-off) ;=> take off for 4 seconds
  (drone-do-for 2 :spin-right 0.8) ;=> spin right at 80% for 2 seconds
  (drone-do-for 2 :spin-left 0.8)

  (drone :land)

  (end-navstream))




