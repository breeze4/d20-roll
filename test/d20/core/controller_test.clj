(ns d20.core.controller-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [d20.core.controller :refer :all]))

(deftest test-roll-injection
  (testing "test injection"
    (let [response (app (mock/request :get "/roll/#=(println \"Hello, vulnerability!\")"))]
      (is (= (:status response) 200)))))

(deftest test-roll-200
  (testing "get single roll with denomination"
    (let [response (app (mock/request :get "/roll/6"))]
      (is (= (:status response) 200)))))

(deftest test-multi-roll-200
  (testing "get multiple rolls with denomination"
    (let [response (app (mock/request :get "/roll/5/num/2"))]
      (is (= (:status response) 200))
      (is (= (count (:body response)))))))

(deftest test-roll-400
  (testing "get single roll with invalid denomination"
    (let [response (app (mock/request :get "/roll/5"))]
      (is (= (:status response) 400))
      (is (= (:body response) "Invalid input, must be a number corresponding to a valid dice roll")))))
