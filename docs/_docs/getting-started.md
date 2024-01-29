---
title: Getting Started
---

# Getting started

To include Minart, simply add the `minart` library to your project:

```scala
// JVM Only
libraryDependencies += "eu.joaocosta" %% "minart" % "{{ projectVersion }}"
// For JVM/JS/Native cross-compilation
libraryDependencies += "eu.joaocosta" %%% "minart" % "{{ projectVersion }}"
```

Or just create a new project using the provided [giter8](http://www.foundweekends.org/giter8/) template, with:

```
g8 https://github.com/JD557/minart
```

## Tutorial

The easiest way to start using the library is to follow the tutorials in the [`examples`](https://github.com/JD557/minart/tree/master/examples) directory.

The examples in [`examples/release`](https://github.com/JD557/minart/tree/master/examples/release) target the latest released version,
while the examples in [`examples/snapshot`](https://github.com/JD557/minart/tree/master/examples/snapshot) target the code in the repository.

All the examples are `.sc` files that can be executed via [scala-cli](https://scala-cli.virtuslab.org/).

## General recommendations

It is strongly recommended to use `fullLinkJs` for Scala.js releases and
`release-full` for Scala native, as those can lead to huge speedups.

Since Scala Native `release-full` can be quite slow, it might be useful to use the
JVM version during development and only building the native version once in a while.
