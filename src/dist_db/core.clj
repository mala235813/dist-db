(ns dist-db.core
 (:require [clojure.java.io :as io])
 (:require [clojure.spec.alpha :as s])
 (:require [clojure.spec.gen.alpha :as sg])
 (:require [clojure.spec.test.alpha :as st]))

;; Private helper functions
;; ------------------------
(defn- java-path [path]
  (java.nio.file.Paths/get path (into-array String [""])))

(defn- java-path-name [java-path]
  (.getFileName java-path))

(defn- java-parent-path [java-path]
  (.getParent java-path))

(defn- java-combine-paths [java-path & java-paths]
  (reduce #(.resolve %1 %2) java-path java-paths))

;; Specs
;; ------------------------
(defn is-java-path? [obj]
  (instance? java.nio.file.Path obj))

(defn path-exists? [path]
 (.exists (io/file path)))

(defn not-path-exists? [path]
  (not (path-exists? path)))

(defn is-directory? [path]
  (.isDirectory (io/file path)))

(defn can-write? [path]
 (.canWrite (io/file path)))

(s/def
  ::make-db-args-1
  #(not (path-exists? %)))

(s/def
  ::make-db-args-2
  (s/cat :path (s/and path-exists?
                      is-directory?
                      can-write?)
         :db-name  string? ))

(s/def
	::make-db-ret
	(s/keys :req [::db-path ::db-name]))

(s/fdef make-db
  :args ::make-db-args
  :ret ::make-db-ret)

(defn s-valid-or-throw [spec & args]
  (let [data (if (= 1 (count args)) (first args) args)]
    (cond (not (s/valid? spec data))
          (throw (AssertionError. (s/explain-str spec data))))))

;; Database API|
;; ------------------------
(defn make-db
  "Returns a db defnition"
  ([path]
   (s-valid-or-throw ::make-db-args-1 path)
   (let [j-path (java-path path)
         parent-path (str (java-parent-path j-path))
         db-name (str (java-path-name j-path))]
     (make-db parent-path db-name)))
  ([path db-name]
   (s-valid-or-throw ::make-db-args-2 [path db-name])
   {:db-path path
    :db-name db-name}))
