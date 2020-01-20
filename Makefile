FERRET = ../ferret
INPUT  = src/core.clj
OUTPUT = src/core.ino
RM = rm -f

.PHONY: build upload clean

default: build

core: $(INPUT)
	$(FERRET) -i $(INPUT) -o $(OUTPUT)

build: core
	platformio run --verbose

upload: core
	platformio run -t upload --verbose

clean:
	$(RM) $(OUTPUT)
	platformio run --target clean --verbose
