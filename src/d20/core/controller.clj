(ns d20.core.controller
  (:require [clojure.edn :as edn]
            [compojure.handler :as handler]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer :all]
            [cheshire.core :refer :all]
            [d20.core.db :as repository]
            [d20.core.roller :as roller]))

(repository/initialize-db)

(defn handle-single-roll [denomination]
  (if (roller/valid-roll? denomination)
    (first (repository/insert-new-roll
            (roller/do-roll denomination)))
    "Invalid input, must be a number corresponding to a valid dice roll"))

(defn handle-roll [denomination]
  (into '() (repository/insert-new-roll
            (roller/do-roll denomination))))

(defn handle-multi-roll [denomination number]
  (repeatedly number #(handle-roll denomination)))

(defroutes app-routes
  (context "/roll/:denomination" [denomination]
           (GET  "/" []
                 {:status (if (roller/valid-roll? (roller/parse-int denomination)) 200 400)
                  :body (handle-roll (roller/parse-int denomination))})
           (context  "/num/:number" [number]
                     (GET  "/" []
                           {:status (if (roller/valid-roll? (roller/parse-int denomination)) 200 400)
                            :body (handle-multi-roll (roller/parse-int denomination) (roller/parse-int number))})))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
