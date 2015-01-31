(ns d20.core.roller
  (:require [clojure.edn :as edn]))

(def valid-dice (set '(4 6 8 10 12 20)))

;stolen from http://stackoverflow.com/questions/3249334/test-whether-a-list-contains-a-specific-value-in-clojure
(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

(defn valid-roll? [input]
  (if (true? (in? valid-dice input))
    true
    false))

(defn parse-int [s]
  (if (empty? s)
    0
    (clojure.edn/read-string s)))

(defn random-num [n]
  (+ (rand-int n) 1))

(defn build-roll [rolled denomination]
  (assoc {} :number (str rolled) :denomination (str denomination)))

(defn do-roll [denomination]
  (build-roll (random-num denomination)
              denomination))

(defn do-multi-roll [denomination number]
  (repeatedly number #(do-roll denomination)))
