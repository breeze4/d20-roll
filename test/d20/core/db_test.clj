(ns d20.core.db-test
  (:require [clojure.test :refer :all]
            [d20.core.db :refer :all]))

(deftest test-db-connections
  (testing "can make query to database"
    (is (not (= (get-all-rolls) nil)))))

(deftest test-insert-data
  (testing "can insert data into database"
    (is (not (= (insert-new-roll {:denomination 4 :number 3}) nil)))))

(deftest test-get-all-rolls
  (testing "can get all the rolls"
    (insert-new-roll {:denomination 4 :number 3})
    (insert-new-roll {:denomination 4 :number 2})
    (is (not (= (get-all-rolls) nil)))))

(deftest test-get-single-roll
  (testing "can get a single roll"
    (let [roll (insert-new-roll {:denomination 4 :number 3})]
      (is (not (= :id roll nil))))))

(defn db-setup []
  (create-table))

(defn db-teardown []
  (drop-table))

(defn db-test-fixture [f]
  (db-setup)
  (f)
  (db-teardown))

(use-fixtures :each db-test-fixture)
