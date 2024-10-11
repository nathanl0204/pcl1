FILE_NAME = Lexeur
SRC = $(wildcard src/*.java)
CLASS = $(patsubst src/%.java, build/%.class, $(SRC))
all: compil

compil: $(CLASS)

build: 
	@mkdir -p build

build/%.class: src/%.java | build
	javac -d build $<

run: compil
	java -cp build $(FILE_NAME)

test: compil run
	cat output.txt

clean:
	rm -rf build

.PHONY: compil run test clean