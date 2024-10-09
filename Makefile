FILE_NAME = Lexeur
SRC = src/$(FILE_NAME).java
CLASS = build/$(FILE_NAME).class

compil: $(SRC)
	@mkdir -p build
	javac -d build $(SRC)

run: $(CLASS)
	java -cp build $(FILE_NAME)

test: compil run
	cat output.txt

clean:
	rm -rf build