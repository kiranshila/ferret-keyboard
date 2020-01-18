(require '[ferret.arduino :as gpio]
         '[deps.keyboard :as keyboard])

(def rows (list 6 7 8 9))
(def cols (list 10 11 12 15 16 17 18 19 20 21 22 23 ))

(defn setup [rows cols]
  (do
    (doseq [row rows]
      (gpio/pin-mode row :input))
    (doseq [col cols]
      (gpio/pin-mode col :input_pullup))))

(defn get-matrix [rows cols]
  (doseq [col cols]
    (do ; Set each col low
      (gpio/pin-mode col :output)
      (gpio/digital-write col 0)
      (doseq [row rows]
        (do
          (gpio/pin-mode row :input_pullup)
          (if (gpio/digital-read row) (keyboard/print "Hi"))
          (gpio/pin-mode row :input)))
      (gpio/pin-mode col :input)))) ; Disable col

(do
  (setup rows cols)
  (forever
   (get-matrix rows cols)))
