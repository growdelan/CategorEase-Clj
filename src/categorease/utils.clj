(ns categorease.utils
  (:require [clojure.java.io :as io]))

(defn create-directories
  "Tworzy katalogi, jeśli nie istnieją"
  [dir-path dir-list]
  (doseq [directory dir-list]
    (let [dir (io/file dir-path directory)]
      (if (.exists dir)
        (println (str "Katalog " directory " już istnieje"))
        (do
          (.mkdirs dir)
          (println (str "Katalog został utworzony: " directory)))))))

(defn get-file-names
  "Pobiera nazwy plików z katalogu, z wyłączeniem ukrytych plików"
  [directory-path]
  (->> (.listFiles (io/file directory-path))
       (filter #(and (.isFile %)
                     (not (.isHidden %))))
       (map #(.getName %))
       vec))

(defn join-paths
  "Łączy ze sobą ścieżki"
  [& paths]
  (.getPath (apply io/file paths)))

(defn move-file
  "Przenosi plik ze źródła do miejsca docelowego"
  [source destination]
  (let [source-file (io/file source)
        dest-file (io/file destination)]
    (io/make-parents dest-file)
    (java.nio.file.Files/move (.toPath source-file) (.toPath dest-file)
                              (into-array java.nio.file.CopyOption []))
    (str "Plik przeniesiony do: " (.getPath dest-file))))


