                                        ;(native-header "Keyboard.h")
(require '[ferret.matrix :as m])
                                        ;(defn begin[] "Keyboard.begin();")

;(defn write [^byte c]
;  "__result = obj<size_t>(Keyboard.write(c));")

(defn print [^c_str s]
  "__result = obj<number>(Keyboard.print(s));")

(def layout
  (matrix
   [[-1 \q \w \e \r \t \y \u \i \o \p -1]
    [-1 \a \s \d \f \g \h \j \k \l \; -1]
    [-1 \z \x \c \v \b \n \m \< \> \/ -1]
    [-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1]]))
