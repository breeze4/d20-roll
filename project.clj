(defproject d20 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.1"]
                 [ring/ring-json "0.3.1"]
                 [c3p0/c3p0 "0.9.1.2"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [com.h2database/h2 "1.4.185"]
                 [cheshire "5.4.0"]]
  :plugins [[lein-ring "0.8.13"]
            [com.jakemccrary/lein-test-refresh "0.5.2"]]
  :ring {:handler d20.core.controller/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
