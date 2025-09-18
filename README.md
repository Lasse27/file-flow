# file-flow

File-flow is a tool for defining procedures (workflows) that allow users to manage files on their system. It supports basic file operations, cleaning directories, and provides both CLI and GUI interfaces.

[![Java CI with Maven](https://github.com/Lasse27/file-flow/actions/workflows/maven.yml/badge.svg)](https://github.com/Lasse27/file-flow/actions/workflows/maven.yml)
[![CodeQL Advanced](https://github.com/Lasse27/file-flow/actions/workflows/codeql.yml/badge.svg)](https://github.com/Lasse27/file-flow/actions/workflows/codeql.yml)

---

## Features

- **Move** files between folders
- **Delete** files or directories
- **Clean** (i.e. tidy up) directory structures
- Minimalistic CLI built using a CLI framework
- Interactive CLI modes
- GUI using JavaFX
- (Threaded / parallel processing planned, for better performance in the background)

---

## Disclaimer & Requirements

- Use at your own risk. Some operations (especially with regex or delete) can be destructive.
- Knowledge of regex may be required for filtering or matching files.

---

## Technology Stack

- **Java 21**
- **Maven** for build and dependency management

### Key dependencies & tools

- **JUnit** — for testing. See [JUnit](https://github.com/junit-team/junit5)
- **Jimfs** — in-memory filesystem for testing, which helps mocking file system operations.
See [Jimfs](https://github.com/google/jimfs)
- **Lombok** — for reducing boilerplate (getters, setters, logging, etc.)

---

## Current Status

This is the present, working state of the project:

- Core features (Move, Delete) are implemented.
- CLI interface exists in a minimal form.
- GUI (JavaFX) has not been started.
- Commands are under progress; no CLI commands fully implemented yet.
- No roadmap published yet beyond the current state.

---

## Building & Running

To build the project:

```bash
mvn clean install
```
(This builds all modules: core, cli, gui.)

Usage via CLI is under development; once you build, you will find the CLI-artifact under cli/target/, for example:

```bash
java -jar cli/target/file-flow.jar [options]
```

---

## License

This project is licensed under the MIT License.

---

## Contribution

- Contributions are welcome. If you want to contribute:

- Open issues to report bugs or propose improvements.

- Fork the repo and submit pull requests.

---

## Acknowledgments / Related Projects

- JUnit — for test-framework on the JVM
- Jimfs by Google — in-memory file system used in tests
- Project Lombok — reduces boilerplate through annotations
