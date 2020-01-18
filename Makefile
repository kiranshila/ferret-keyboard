FERRET = java -jar ../ferret.jar
INPUT  = src/core.clj
OUTPUT = src/core.ino
RM = rm -f

.PHONY: build upload clean

default: build

core: $(INPUT)
	$(FERRET) -i $(INPUT) -o $(OUTPUT)

build: core
	platformio run

upload: core
	platformio run -t upload

clean:
	$(RM) $(OUTPUT)
	platformio run --target clean