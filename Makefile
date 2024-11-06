
FILE_NAME = AST
PACKAGE_NAME = AST

compil:
	@mkdir -p build/$(PACKAGE_NAME)  # Créer le dossier pour le package
	javac -d build/ -cp src/ src/$(PACKAGE_NAME)/*.java

run:
	java -cp build/ $(PACKAGE_NAME).$(FILE_NAME)

clean:
	rm -rf build/*
