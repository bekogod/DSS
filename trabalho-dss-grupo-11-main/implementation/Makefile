JAVAC = javac
CLASS_FILES = ./*.class
# view/*.class model/Businesses/*.class model/Reviews/*.class model/Users/*.class loaders/*.class

#Default: $(CLASS_FILES)
.DEFAULT_GOAL = build

build: 
	$(JAVAC) -cp sd23.jar -d out src/*.java

.PHONY: dao # Run DAO
dao: 
	java -cp mariadb-java-client-2.7.1.jar:out UtilizadorDAO

.PHONY: menu # Run App
menu: 
	java -cp mariadb-java-client-2.7.1.jar:out Menu

.PHONY: sgo # Run sgo debug
sgo: 
	java -cp mariadb-java-client-2.7.1.jar:out SGOficina


.PHONY: clean # Clean java class files
clean:
	@rm -r out

.PHONY: man, help # Generate list of targets with descriptions
man help:
	@echo -------------------------------------------------------------------+
	@grep '^.PHONY: .* #' Makefile | sed 's/\.PHONY: \(.*\) # \(.*\)/    \1 \t: \2\n-------------------------------------------------------------------+/'