(ns dist-db.core
 (:require [clojure.java.io :as io])
 (:require [clojure.spec.alpha :as s])
 (:require [clojure.spec.gen.alpha :as sg])
 (:require [clojure.spec.test.alpha :as st]))

;; Private helper functions
;; ------------------------
(defn- java-path [path]
  (java.nio.file.Paths/get path (into-array String [""])))

(defn- java-parent-path [java-path]
  (.getParent java-path))

(defn- java-combine-paths [java-path & java-paths]
  (reduce #(.resolve %1 %2) java-path java-paths))

;; Specs
;; ------------------------
(defn path-exists? [path]
 (.exists (io/file path)))

(defn not-path-exists? [path]
  (not (path-exists? path)))

(defn is-directory? [path]
  (.isDirectory (io/file path)))

(defn can-write? [path]
 (.canWrite (io/file path)))

(s/def
  ::make-db-args
  (s/cat :path (s/and path-exists?
                      is-directory?
                      can-write?)
         :db-name not-path-exists?))

(s/def
	::make-db-ret
	(s/keys :req [::path]))

(s/fdef make-db
  :args ::make-db-args
  :ret ::make-db-ret)

;; Database API|
;; ------------------------
(defn make-db
  "Returns a db defnition"
  [path db-name]
  (s/explain ::make-db-args [path db-name])
  {:path path
   :db-name db-name})
