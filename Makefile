FERRET = ../ferret
ORGFILE = core.org
INPUT  = src/core.clj
OUTPUT = src/core.cpp
RM = rm -rd

.PHONY: build upload clean

default: build

$(INPUT): $(ORGFILE)
	@echo [tangle] $(ORGFILE)
	@emacs --batch -nw -Q --eval 									\
	"(progn                                                     	\
           (require 'org)                                           \
           (require 'ob)                                            \
           (setq org-babel-use-quick-and-dirty-noweb-expansion t)   \
           (setq org-confirm-babel-evaluate nil)                    \
	   (when (locate-library \"ob-sh\")                         	\
            (org-babel-do-load-languages                            \
              'org-babel-load-languages '((sh . t))))               \
	   (when (locate-library \"ob-shell\")                      	\
            (org-babel-do-load-languages                            \
              'org-babel-load-languages '((shell . t))))            \
           (find-file \"$(ORGFILE)\")                               \
           (org-babel-tangle))" 2>&1 >/dev/null | grep 'Tangle'

$(OUTPUT): $(INPUT)
	@echo [ferret] $(INPUT)
	$(FERRET) -i $(INPUT) -o $(OUTPUT)

build: $(OUTPUT)
	@echo [platfomio-build]
	platformio run

upload: $(OUTPUT)
	@echo [platfomio-upload]
	platformio run -t upload

clean:
	@echo [clean]
	$(RM) src
	platformio run --target clean
