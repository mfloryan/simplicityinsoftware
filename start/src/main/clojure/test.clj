(ns test
   (:require [clj-drone.core :refer :all]
             [clj-drone.navdata :refer :all])
	(:gen-class))

(defn -main [& args]
	 (drone-initialize)

	 (drone-init-navdata)
	
	 (end-navstream)
	
	 (println "Hello World"))
