(require '[ferret.arduino :as gpio]
         '[deps.keyboard :as keyboard]
         '[ferret.matrix :as m])

(def cols (list 10 11 12 15 16 17 18 19 20 21 22 23)) ;outputs
(def rows (list 6 7 8 9)) ;inputs

(defn m-and [m-bool m-target]
  (let [result (m/zeros (m/row-count m-bool) (m/column-count m-bool))]
    (doseq [row (range (m/row-count m-bool))]
      (doseq [col (range (m/column-count m-bool))]
        (if (pos? (m/mget m-bool row col)) (m/mset! result row col (m/mget m-target row col)))))
    result))

(defn enumerate [in]
  (map list (range (count in)) in))

(defn arduino-write-byte [^byte b]
  "__result = obj<number>(Serial.print((char)b));")

(defn setup [rows cols]
  (do
    (doseq [col cols]
      (do
        (gpio/pin-mode col :output)
        (gpio/digital-write col 0)))
    (doseq [row rows]
      (gpio/pin-mode row :input_pulldown))))

(defn read-matrix [rows cols]
  (let [activations (m/zeros (count rows) (count cols))]
    (doseq [[j col] (enumerate cols)]
      (do
        (gpio/digital-write col 1)
        (doseq [[i row] (enumerate rows)]
          (m/mset! activations i j (gpio/digital-read row)))
        (gpio/digital-write col 0)))
    activations))

(defn apply-layout [layout activations]
  (let [result (m-and layout activations)]
    (doseq [keypress (m/seq result)]
      (if (pos? keypress) (arduino-write-byte keypress)))))

(do
  (setup rows cols)
  (forever
   (->> (read-matrix rows cols)
        ( m-and keyboard/layout)
        m/seq
        print)
   (print "\n")))
