---
title: Project structure
---

# Project structure

## `eu.joaocosta.minart.runtime`

This package contains the core abstractions that allow the code to run on all platforms without changes.

The `Loop`/`LoopRunner` abstractions allows to write loops that run with a certain `LoopFrequency`.

`Platform` provites runtime checks to know the current platform.

`Resource`s represent data that can be written or read.
- On JVM and Native it will load local files and embedded resources, while storing local files
- On JS it will load data from the local storage or relative URIs, while storing the data on the local storage

## `eu.joaocosta.minart.graphics`

This package contains all the code required to draw on a screen.

A `Surface` represents an image. It may be a `MutableSurface`, which allows drawing.

A `Canvas` is a special type of `MutableSurface`, which represents a window.

The `RenderLoop` abstraction works on top of the `Loop`/`LoopRunner` to continually write to a `Canvas`.

`SurfaceView`s and `Planes` are abstractions to quickly and efficiently manipulate `Surface`s.

## `eu.joaocosta.minart.input`

Contains the `KeyboardInput` and `PointerInput`, which hold the current input status.

## `eu.joaocosta.minart.backend`

Contains the platform-specific internals.

Most applications will only need to `import eu.joaocosta.minart.backend.defaults._`.
