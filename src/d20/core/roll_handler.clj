(ns d20.core.roll-handler
  (:require [compojure.handler :as handler]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer :all]
            [cheshire.core :refer :all]
            [d20.core.db :as repository]))

(repository/create-table)
(def valid-dice (set '(4 6 8 10 12 20)))

(defn parse-int [s]
  (if (empty? s)
    0
    (read-string s)))

(defn random-num [n]
  (+ (rand-int n) 1))

(defn build-roll [rolled denomination]
  (assoc {} :number (str rolled) :denomination (str denomination)))

;stolen from http://stackoverflow.com/questions/3249334/test-whether-a-list-contains-a-specific-value-in-clojure
(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

(defn valid-roll? [input]
  (if (true? (in? valid-dice input))
    true
    false))

(defn handle-single-roll [denomination]
  (if (valid-roll? denomination)
    (first (repository/insert-new-roll
            (build-roll (random-num denomination)
                        denomination)))
    "Invalid input, must be a number corresponding to a valid dice roll"))

(defroutes app-routes
  (GET  "/roll/:denomination" [denomination]
        {:status (if (valid-roll? (parse-int denomination)) 200 400) :body (handle-single-roll (parse-int denomination))})
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
