(ns categorease.ollama
  (:require [wkok.openai-clojure.api :as api]
            [environ.core :refer [env]]))

(def api-config
  {:api-key      (env :ollama-api-key)
   :api-endpoint (env :ollama-api-endpoint)})

(def prompt
  "I will give you the names of the files along with the extensions, your job is to classify these files into categories:

<categories>
music - all music extensions
documents - anything that counts as a text file
data - various types of spreadsheets
images - all image extensions
video - all video extensions
archives - all extensions for the archive
</categories>

The result only needs to be the category name (THIS IS VERY IMPORTANT!!!)")

(defn get-response
  "Wysyła zapytanie do modelu GPT i zwraca odpowiedź lub błąd."
  [model prompt file-name]
  (try
    (api/create-chat-completion
     {:model    model
      :messages [{:role "system" :content prompt}
                 {:role "user"   :content file-name}]}
     api-config)
    (catch Exception e
      {:error (.getMessage e)})))

(defn extract-content
  "Wyciąga zawartość odpowiedzi z struktury zwróconej przez API."
  [response]
  (get-in response [:choices 0 :message :content]
          "Nie udało się wyciągnąć treści z odpowiedzi."))

(defn classify-file
  "Klasyfikuje pojedynczy plik i zwraca kategorię."
  [file-name model]
  (let [response-content (->> file-name
                              (get-response model prompt)
                              extract-content)]
    (or (not-empty response-content) "others")))