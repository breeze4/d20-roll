(ns d20.core.roll-handler-test
  (:require [clojure.test :refer :all]
            [d20.core.roll-handler :refer :all]))

(deftest test-random-number-generator
  (testing "random number generator is sort of very difficult"
    (is (not (= (random-num 20) 0)))))
