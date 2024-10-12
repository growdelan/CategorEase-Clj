(ns categorease.core
  (:require [categorease.ollama :as ollama]
            [categorease.utils :as utils]
            [clojure.string :as str])
  (:gen-class))

(def categories
  #{"music" "documents" "installations" "data"
    "images" "videos" "archives"})

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

(defn -main []
  (let [download-path (get-download-path)
        all-categories (conj categories default-category)]
    (utils/create-directories download-path all-categories)
    (doseq [file-name (utils/get-file-names download-path)]
      (process-file download-path file-name))))
