(ns d20.core.roller-test
  (:require [clojure.test :refer :all]
            [d20.core.roller :refer :all]))

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

(deftest test-roller
  (testing "single rolls"
    (let [roll (do-roll 6)]
     (is (= 2 (count roll))) ;2 entries in roll
     (is (<= (parse-int (:number roll)) (parse-int (:denomination roll))))
     (is (>= 1 (parse-int (:number roll))))))
  (testing "multiple rolls"
    (let [rolls (do-multi-roll 6 4)]
     (is (= 4 (count rolls)))))) ;4 separate rolls
