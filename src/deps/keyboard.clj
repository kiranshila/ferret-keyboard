;(native-header "Keyboard.h")

;(defn begin[] "Keyboard.begin();")

;(defn write [^byte c]
;  "__result = obj<size_t>(Keyboard.write(c));")

(defn print [^c_str s]
  "__result = obj<number>(Keyboard.print(s));")
