# Variables
FILE_NAME = Lexeur

# Chemin vers le fichier source et le fichier compilé
SRC = src/$(FILE_NAME).java
CLASS = build/$(FILE_NAME).class

# Règle par défaut
all: $(CLASS)

# Compiler le fichier .java en .class dans le dossier build/
compil: $(SRC)
	@mkdir -p build
	javac -d build $(SRC)

# Exécuter le programme Java
run: $(CLASS)
	java -cp build $(FILE_NAME)

# Supprimer les fichiers compilés dans build/
clean:
	rm -rf build
