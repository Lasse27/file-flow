# file-flow

File-flow is a tool for defining procedures (workflows) that allow users to manage files on their system. It supports basic file operations, cleaning directories, and provides both CLI and GUI interfaces.

[![Java CI with Maven](https://github.com/Lasse27/file-flow/actions/workflows/maven.yml/badge.svg)](https://github.com/Lasse27/file-flow/actions/workflows/maven.yml)
[![CodeQL Advanced](https://github.com/Lasse27/file-flow/actions/workflows/codeql.yml/badge.svg)](https://github.com/Lasse27/file-flow/actions/workflows/codeql.yml)

## Disclaimer & Requirements

- Use at your own risk. Some operations (especially with regex or delete) can be destructive.
- Knowledge of regex may be required for filtering or matching files.

## Current Status

This is the present, not-working state of the project:

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

## License

This project is licensed under the MIT License.
- JUnit — for test-framework on the JVM
- Jimfs by Google — in-memory file system used in tests
- Project Lombok — reduces boilerplate through annotations
