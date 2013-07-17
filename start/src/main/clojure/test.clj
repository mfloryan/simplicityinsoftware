(ns test
   (:require [clj-drone.core :refer :all]
             [clj-drone.navdata :refer :all])
	(:gen-class
    :name com.simplicityitself.DroneFacade))

(defn -testDrone [this]
  (drone-initialize)

  (drone-init-navdata)

  (end-navstream))

(defn -main [& args]
	
	 (println "Hello World"))
