---
title: Project structure
---

# Project structure

## `eu.joaocosta.minart.runtime`

This package contains the default minart runtime that allow the code to run on all platforms without changes.
If you plan to use another runtime (e.g. Tyrian or Cats Effect) you'll probably won't need anything from here.

The `AppLoop`/`LoopRunner` abstractions allows to write loops that run with a certain `LoopFrequency`.

The `MinartApp` is a main class helper that uses the `AppLoop`/`LoopRunner` underneath-

`Platform` provides runtime checks to know the current platform.

`Resource`s represent data that can be written or read.
- On JVM and Native it will load local files and embedded resources, while storing local files
- On JS it will load data from the local storage or relative URIs, while storing the data on the local storage

## `eu.joaocosta.minart.graphics`

This package contains all the code required to draw on a screen.

A `Surface` represents an image. It may be a `MutableSurface`, which allows drawing.

A `Canvas` is a special type of `MutableSurface`, which represents a window.

`SurfaceView`s and `Planes` are abstractions to quickly and efficiently manipulate `Surface`s.

## `eu.joaocosta.minart.audio`

This package contains all abstractions related to audio playback.

An `AudioWave` is an infinite wave of audio.
It is usually be generated from an `Oscilator`, but it can also be generated from a sequence of samples (padding with silence)

An `AudioClip` is an `AudioWave` clipped to a certain duration.

As the name implies, an `AudioPlayer` allows to play audio on multiple channels. It can play both `AudioClip`s and `AudioWave`s.

## `eu.joaocosta.minart.input`

Contains the `KeyboardInput` and `PointerInput`, which hold the current input status.

## `eu.joaocosta.minart.backend`

Contains the platform-specific internals.

Most applications will only need to `import eu.joaocosta.minart.backend.defaults._`.
