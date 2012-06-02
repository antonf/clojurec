(ns clojurec.core
  (:require [clojure.inspector :as inspector]
            [clojure.java.io :as io]
            [cljc.compiler :as cljc]))

(binding [*print-meta* true]
  (cljc/reset-namespaces!)
  (let [env {:ns (@cljc/namespaces 'cljc.user) :context :statement :locals {}}
        ast (cljc/analyze env
			  '(def heusler (fn* ([x] x)))

			  )]
    (inspector/inspect-tree ast)
    (let [main-code (with-out-str (cljc/emit ast))
          code (apply str
                   (concat @cljc/declarations
                           ["int main (void) {\n"
                            "environment_t *env = NULL;\n"
                            "GC_INIT ();\n"
                            main-code
                            "return 0;\n}\n"]))
          user-dir (java.lang.System/getProperty "user.dir")
          preamble (slurp (io/file user-dir "src" "c" "preamble.c"))]
      (spit (io/file user-dir "run" "cljc.c") (str preamble code))
      code)))


(comment

(macroexpand-1 '(fn [x] x [x y] y))

{:env {:line 1, :ns {:name cljc.user}, :context :statement, :locals {}},
 :op :defprotocol*,
 :as ^{:line 1} (defprotocol* Beidel ^{:line 1} (kurde [])),
 :p cljc.user.Beidel,
 :methods (^{:line 1} (kurde []))}

{:env {:line 1,
       :ns {:name cljs.user},
       :context :statement,
       :locals {}},
 :op :deftype*,
 :as ^{:line 1} (deftype* MyType [a ^{:raw true} b]),
 :t cljs.user.MyType,
 :fields [a ^{:raw true} b],
 :pmasks nil}

{:ret {:info {:ns cljs.user, :name-sym cljs.user/MyType, :line 1, :file nil, :name cljs.user.MyType},
       :op :var,
       :env {:line 1, :ns {:name cljs.user}, :context :statement, :locals {}},
       :form MyType},
 :statements ({:env {:line 1, :ns {:name cljs.user}, :context :statement, :locals {}},
               :op :deftype*,
               :as (deftype* MyType [a b] nil),
               :t cljs.user.MyType,
               :fields [a b],
               :pmasks nil}
              {:env {:line 1, :ns {:name cljs.user}, :context :statement, :locals {}},
               :op :set!,
               :form (set! (.-cljs$lang$type MyType) true),
               :target {:env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                        :op :dot,
                        :form (. MyType -cljs$lang$type),
                        :target {:info {:ns cljs.user, :name-sym cljs.user/MyType, :line 1, :file nil, :name cljs.user.MyType},
                                 :op :var,
                                 :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                                 :form MyType},
                        :field cljs$lang$type,
                        :tag nil},
               :val {:op :constant,
                     :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                     :form true}}
              {:env {:line 1, :ns {:name cljs.user}, :context :statement, :locals {}},
               :op :set!,
               :form (set! (.-cljs$lang$ctorPrSeq MyType) (cljs.core/fn [this__2301__auto__] (clojure.core/list "cljs.user.MyType"))),
               :target {:env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                        :op :dot,
                        :form (. MyType -cljs$lang$ctorPrSeq),
                        :target {:info {:ns cljs.user, :name-sym cljs.user/MyType, :line 1, :file nil, :name cljs.user.MyType},
                                 :op :var,
                                 :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                                 :form MyType},
                        :field cljs$lang$ctorPrSeq,
                        :tag nil},
               :val {:loop-lets nil,
                     :recur-frames (nil nil),
                     :form (fn* ([this__2301__auto__] (clojure.core/list "cljs.user.MyType"))),
                     :op :fn,
                     :name nil,
                     :max-fixed-arity 1,
                     :methods ({:ret {:env {:line 1, :ns {:name cljs.user}, :context :return, :locals {this__2301__auto__ {:name this__2301__auto__}}},
                                      :op :invoke,
                                      :form (clojure.core/list "cljs.user.MyType"),
                                      :f {:info {:ns cljs.core, :name-sym cljs.core/list, :name cljs.core.list},
                                          :op :var,
                                          :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {this__2301__auto__ {:name this__2301__auto__}}},
                                          :form clojure.core/list},
                                      :args [{:op :constant,
                                              :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {this__2301__auto__ {:name this__2301__auto__}}},
                                              :form "cljs.user.MyType"}],
                                      :tag nil},
                                :statements nil,
                                :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                                :variadic false,
                                :params (this__2301__auto__),
                                :max-fixed-arity 1,
                                :gthis nil,
                                :recurs nil}),
                     :env {:line 1, :ns {:name cljs.user}, :context :expr, :locals {}},
                     :variadic false,
                     :jsdoc [nil]}}),
 :env {:line 1, :ns {:name cljs.user}, :context :statement, :locals {}},
 :op :do,
 :form (do (deftype* MyType [a b] nil)
           (set! (.-cljs$lang$type MyType) true)
           (set! (.-cljs$lang$ctorPrSeq MyType) (cljs.core/fn [this__2301__auto__] (clojure.core/list "cljs.user.MyType")))
           MyType)}

{:children [{:children [{:info {:name x}, :op :var, :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :return, :locals {x {:name x}, bla {:name bla}}}, :form x}], :loop-lets nil, :recur-frames (nil), :form (fn* ([x] x)), :op :fn, :name bla, :max-fixed-arity 1, :methods ({:ret {:info {:name x}, :op :var, :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :return, :locals {x {:name x}, bla {:name bla}}}, :form x}, :statements nil, :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :expr, :locals {}}, :variadic false, :params (x), :max-fixed-arity 1, :gthis nil, :recurs nil}), :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :expr, :locals {}}, :variadic false, :jsdoc [nil]}],
 :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :statement, :locals {}},
 :op :def,
 :form (def bla (fn [x] x)),
 :name cljs.user.bla,
 :doc nil,
 :init {:children [{:info {:name x}, :op :var, :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :return, :locals {x {:name x}, bla {:name bla}}}, :form x}],
        :loop-lets nil,
        :recur-frames (nil),
        :form (fn* ([x] x)),
        :op :fn,
        :name bla,
        :max-fixed-arity 1,
        :methods ({:ret {:info {:name x},
                         :op :var,
                         :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :return, :locals {x {:name x}, bla {:name bla}}},
                         :form x},
                   :statements nil,
                   :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :expr, :locals {}},
                   :variadic false,
                   :params (x),
                   :max-fixed-arity 1,
                   :gthis nil,
                   :recurs nil}),
        :env {:line 1, :ns {:defs {bla {:method-params ((x)), :max-fixed-arity 1, :variadic false, :fn-var true, :line 1, :file nil, :name cljs.user.bla}}, :name cljs.user}, :context :expr, :locals {}},
        :variadic false,
        :jsdoc [nil]}}

)

(defn -main
  "I don't do a whole lot."
  [& args]
  (println "Hello, World!"))