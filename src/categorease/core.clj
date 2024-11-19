(ns categorease.core
  (:require [categorease.ollama :as ollama]
            [categorease.utils :as utils]
            [clojure.string :as str]
            [hawk.core :as hawk])
  (:gen-class))

(def categories
  #{"music" "documents" "installations" "data"
    "images" "video" "archives"})

(def default-category "others")
(def model-name "llama3.2")

(defn get-download-path []
  (str (System/getProperty "user.home") "/Downloads"))

(defn get-category [category]
  (let [normalized (-> category str/trim str/lower-case)]
    (if (contains? categories normalized)
      normalized
      default-category)))

(defn process-file [download-path file-name]
  (try
    (let [category (-> (ollama/classify-file file-name model-name)
                       get-category)
          source-path (utils/join-paths download-path file-name)
          dest-dir (utils/join-paths download-path category)
          dest-path (utils/join-paths dest-dir file-name)]
      (println (utils/move-file source-path dest-path)))
    (catch Exception e
      (println (str "Błąd podczas przetwarzania pliku "
                    file-name ": " (.getMessage e))))))

(defn valid-file? [file]
  (let [file-name (.getName file)]
    (and (not (.isHidden file))
         (not (str/ends-with? file-name ".rcdownload")))))

(defn handle-event [download-path ctx {:keys [kind file]}]
  (let [file-name (.getName file)
        parent-dir (.getParent file)]
    (println "Wykryte" kind "zdarzenie na" file-name)
    (when (and (= kind :create)
               (= parent-dir download-path)
               (valid-file? file))
      (process-file download-path file-name))
    ctx))

(defn -main []
  (let [download-path (get-download-path)
        all-categories (conj categories default-category)]
    (utils/create-directories download-path all-categories)
    (doseq [file-name (utils/get-file-names download-path)]
      (process-file download-path file-name))
    (hawk/watch! [{:paths   [download-path]
                   :filter  hawk/file?
                   :handler (partial handle-event download-path)}])
    (println "Monitoruję:" download-path)
    @(promise)))