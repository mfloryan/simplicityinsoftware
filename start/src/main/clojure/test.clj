(ns test
   (:require [clj-drone.core :refer :all]
             [clj-drone.navdata :refer :all])
	(:gen-class
    :name com.simplicityitself.DroneFacade
    :methods [[testDrone [] void]
              [takeOffForWithAltitude [int int] void]
              [land [] void]
              [getCurrentStatus [] java.util.Map]]))

(defn -testDrone [this]

  (drone-initialize)

  (drone-init-navdata)

  (drone-do-for 4 :take-off) ;=> take off for 4 seconds
  (drone-do-for 2 :spin-right 0.8) ;=> spin right at 80% for 2 seconds
  (drone-do-for 2 :spin-left 0.8)

  (drone :anim-flip-right)

  (drone :land)

  (end-navstream))

(defn -takeOffForWithAltitude [this seconds altitude]

  (drone-initialize)

  (drone-init-navdata)

  (drone-do-for seconds :take-off)

  (drone-do-for 4 :hover)

  (drone-do-for 4 :up 0.4)

  (drone-do-for 4 :hover))

(defn -getCurrentStatus [this]

  @nav-data)


(defn -land [this]

  (drone :land)

  (end-navstream))

