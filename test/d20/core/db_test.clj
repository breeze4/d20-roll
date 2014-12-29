(ns d20.core.db-test
  (:require [clojure.test :refer :all]
            [d20.core.db :refer :all]))

(deftest test-db-connections
  (try
    (create-table)
    (testing "can make query to database"
      (is (not (= (get-all-rolls) nil))))
    (finally (drop-table))))

(deftest test-insert-data
  (try
    (create-table)
    (testing "can insert data into database"
      (is (not (= (insert-new-roll {:denomination 4 :number 5}) nil))))
    (finally (drop-table))))
