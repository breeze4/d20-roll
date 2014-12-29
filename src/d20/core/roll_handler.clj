(ns d20.core.roll-handler
  (:require [compojure.handler :as handler]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer :all]
            [cheshire.core :refer :all]
            [d20.core.db :as repository]))

(defn random-num [n]
  (+ (rand-int n) 1))
