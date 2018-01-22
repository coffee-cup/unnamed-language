# The Unnamed Language

Programming language for the UVic CSC 435 Compiler Construction class.

## Compiling

The language is built with `make`.

- `make grammar` runs Antlr to generate lexer and parser classes
- `make compiler` compiles the compiler
- `make clean` removes compile and build files

To do a full clean and build run,

```sh
make clean; make
```

## Running

The compiler can be run against language files to produce a pretty printed version of the file. Options can be specified to change the default behaviour.

The built compiler is located in the `bin/` directory.

```sh
cd bin/
java Compiler path/to/file.ul
```

### Options

- `-o outfile` Specify a file to output the pretty printed language.
- `-s 1|0` Silent mode. If `1` then no output will be produced. Use this no just compile a file and check for errors.

## Testing

For the first assignment there are a bunch of .ul language files that can be tested against the parser. Files in tests/accept should all pass and be in the language. Files in tests/reject should all fail and not be in the language. Use the script test.sh to run on the tests.

```sh
# Run the tests
./test.sh
```

## Licenses

All third party code is referenced in the LICENSES file.

## TODO

- [x] Lexer
- [x] Parser and AST generation
- [x] Pretty printing
- [ ] Syntax analysis
- [ ] Type checking
- [ ] Intermediate code generation
- [ ] Register allocation
- [ ] Machine code generation
- [ ] Assembly and linking
