---
title: Feature Overview
---

# Feature overview

## Cross-compilation

Minart can target **JVM**, **JS** and **Native** backends.

The library includes abstractions to make sure that the same code can run on
all backends with no changes, while also making backend-specific changes easy
to implement.

## Small footprint

External dependencies are kept to a minimum, to keep the resulting binaries small.

For extreme cases, it's also possible to only import a subset of features.

## Graphics

Minart comes out of the box with some basic graphic features, such as:
  - Double buffered canvas
  - Integer scaling
  - Surface blitting (with multiple blending modes)

It also includes **Surface views** and **Planes** which makes it possible to manipulate
(possibly unbounded) images with familiar operations such as `map` and `flatMap`.

## Audio

Minart has support for multi-channel mono audio playback.

It also includes multiple abstractions, such as oscillators and audio waves, to
simplify procedural audio generation.

## Input handling

Minart comes with some helpers to handle Keyboard and pointer input.

Not only is mouse input supported, but touch screen input also comes for free.

## Resource loading

A `Resource` abstraction provides a backend-agnostic way to load and store resources.

Codecs for some image formats (PPM, BMP and QOI) is also included.

## Minart runtime and other runtimes

Minart comes with it's own runtime that abstracts away the application loop logic (so that applications can be cross-compiled).

However, you are free to use your own runtime (such as Tyrian or Cats Effect). The basic structure of an application is:

- Create low level versions of the subsystems you want (e.g. `val canvas = LowLevelCanvas.create()`)
- Initialize the subsystems (e.g. `canvas.init(settings)`)
- Run your application loop
- Close the subsystems (e.g. `canvas.close`)
