---
title: Feature Overview
---

# Feature Overview

## Cross-compilation

Minart can target **JVM**, **JS** and **Native** backends.

The library includes abstractions to make sure that the same code can run on
all backends with no changes, while also making backend-specific changes easy
to implement.

## Small footprint

External dependencies are kept to a minimum, to keep the resulting binaries small.

For extreme cases, it's also possible to only import a subset of features.

## Out of the box graphic features

Minart comes out of the box with some basic graphic features, such as:
  - Double buffered canvas
  - Integer scaling
  - Surface blitting (with a mask)

It also includes **Surface views** and **Planes** which makes it possible to manipulate
(possibly unbounded) images with familiar operations such as `map` and `flatMap`.

## Input handling

Minart comes with some helpers to handle Keyboard and pointer input.

Not only is mouse input supported, but touch screen input also comes for free.

### Resource loading

A `Resource` abstraction provides a backend-agnostic way to load and store resources.

Codecs for some image formats (PPM, BMP and QOI) is also included.
