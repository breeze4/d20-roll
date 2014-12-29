(ns d20.core.db
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:require [cheshire.core :refer :all]
            [clojure.java.jdbc :as sql]))

(def db-spec
  {:classname "org.h2.Driver"
   :subprotocol "h2"
   :subname "mem:rolls"
   :user ""
   :password ""})

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn pool
  [spec]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setDriverClass (:classname spec))
               (.setJdbcUrl (str "jdbc:" (:subprotocol spec) ":" (:subname spec)))
               (.setUser (:user spec))
               (.setPassword (:password spec))
               (.setMaxPoolSize 6)
               (.setMinPoolSize 1)
               (.setInitialPoolSize 1))]
    {:datasource cpds}))

(def pooled-db (delay (pool db-spec)))

(defn db-connection [] @pooled-db)

; sets up database
(defn drop-table []
  (println "dropping table")
  (sql/db-do-commands (db-connection)
                      (sql/drop-table-ddl :rolls)))

(defn create-table []
  (println "creating table")
  (sql/db-do-commands (db-connection)
                      (sql/create-table-ddl :rolls
                                            [:id "varchar(256)" "primary key"]
                                            [:denomination :int]
                                            [:number :int])))

(defn get-all-rolls []
  (println "getting all rolls..")
  (sql/query (db-connection)
             ["select * from rolls"]))

(defn get-roll [id]
  (println "getting rolls with id:" id)
  (sql/query (db-connection)
             ["select * from rolls where id = ?" id]))

(defn insert-new-roll [rolled]
  (println "insert a new roll: " rolled)
  (let [id (uuid) roll (assoc rolled "id" id)]
    (sql/insert! (db-connection) :rolls roll)
    (get-roll id)))
