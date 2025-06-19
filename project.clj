(defproject categorease "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [environ "1.2.0"]
                 [net.clojars.wkok/openai-clojure "0.21.1"]
                 [hawk "0.2.11"]
                 [net.java.dev.jna/jna "5.12.1"]]
  :main ^:skip-aot categorease.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
