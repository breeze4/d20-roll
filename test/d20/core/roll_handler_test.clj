(ns d20.core.roll-handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [d20.core.roll-handler :refer :all]))

(deftest test-random-number-generator
  (testing "random number generator is sort of very difficult"
    (is (not (= (random-num 20) 0)))))

(deftest test-valid-denomination
  (testing "valid numbers are found"
    (is (= true (valid-roll? 4)))
    (is (= true (valid-roll? 6)))
    (is (= true (valid-roll? 8)))
    (is (= true (valid-roll? 10)))
    (is (= true (valid-roll? 12)))
    (is (= true (valid-roll? 20))))
  (testing "invalid numbers are not found"
    (is (= false (valid-roll? 1)))
    (is (= false (valid-roll? 2)))
    (is (= false (valid-roll? 3)))
    (is (= false (valid-roll? 0)))
    (is (= false (valid-roll? nil)))))

(deftest test-roll-200
  (testing "get single roll with denomination"
    (let [response (app (mock/request :get "/roll/6"))]
      (is (= (:status response) 200)))))

(deftest test-roll-400
  (testing "get single roll with invalid denomination"
    (let [response (app (mock/request :get "/roll/5"))]
      (is (= (:status response) 400))
      (is (= (:body response) "Invalid input, must be a number corresponding to a valid dice roll")))))
