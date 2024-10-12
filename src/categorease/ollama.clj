(ns categorease.ollama
  (:require [wkok.openai-clojure.api :as api]
            [environ.core :refer [env]]))

(def api-config
  {:api-key      (env :ollama-api-key)
   :api-endpoint (env :ollama-api-endpoint)})

(defn prompt
  "Buduje prompt wraz z danymi wejściowymi."
  [file-name]
  (format
    "I will give you the names of the files along with the extensions, your job is to classify these files into categories:

<categories>
music - all music extensions
documents - anything that counts as a text file
data - various types of spreadsheets
images - all image extensions
videos - all video extensions
archives - all extensions for the archive
</categories>

the result only needs to be the category name (THIS IS VERY IMPORTANT!!!)

<input>
%s
</input>" file-name))

(defn get-response
  "Wysyła zapytanie do modelu GPT i zwraca odpowiedź lub błąd."
  [model prompt]
  (try
    (api/create-chat-completion
     {:model    model
      :messages [{:role "user" :content prompt}]}
     api-config)
    (catch Exception e
      {:error (.getMessage e)})))

(defn extract-content
  "Wyciąga zawartość odpowiedzi z struktury zwróconej przez API."
  [response]
  (if-let [content (get-in response [:choices 0 :message :content])]
    content
    "Nie udało się wyciągnąć treści z odpowiedzi."))

(defn classify-file
  "Klasyfikuje pojedynczy plik i zwraca kategorię."
  [file-name model]
  (let [content  (->> (prompt file-name)
                      (get-response model)
                      extract-content)]
    (if (not-empty content)
      content
      "others")))
