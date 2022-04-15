# Minart

![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/eu.joaocosta/minart_3?server=https%3A%2F%2Foss.sonatype.org)
 [![scaladoc (core)](https://javadoc.io/badge2/eu.joaocosta/minart-core_3/scaladoc%20%28core%29.svg)](https://javadoc.io/doc/eu.joaocosta/minart-core_3)
 [![scaladoc (pure)](https://javadoc.io/badge2/eu.joaocosta/minart-pure_3/scaladoc%20%28pure%29.svg)](https://javadoc.io/doc/eu.joaocosta/minart-pure_3)
 [![scaladoc (image)](https://javadoc.io/badge2/eu.joaocosta/minart-image_3/scaladoc%20%28image%29.svg)](https://javadoc.io/doc/eu.joaocosta/minartimage_3)

Minart is a very minimalistic Scala library to put pixels in a canvas.

It's mostly useful for small toy projects or prototypes that deal with generative art or software rendering.

Minart is still in a 0.x version. Quoting the semver specification:
> Major version zero (0.y.z) is for initial development. Anything MAY change at any time. The public API SHOULD NOT be considered stable.

## Features

* JVM, JS and Native support
* Small footprint
* Double buffered canvas
* Integer scaling
* Keyboard and pointer input
* Surface blitting (with a mask)
* Surface views and infinite planes

## Getting Started

To include Minart, simply add `minart` to your project:

```scala
libraryDependencies += "eu.joaocosta" %% "minart" % "0.4.0"
```

Or just create a new project using the provided giter8 template, with:

```
g8 https://github.com/JD557/minart
```

You can follow the tutorials in the `examples` directory.
The examples in `examples/release` target the latest released version, while the examples in `examples/snapshot` target
the code in the repository.


## Advanced Usage

### Minimal builds

The Minart project is divided in multiple small packages:

- `minart-core`: This package is always required.
- `minart-backend`: Contains the default implementations for each backend (AWT, HTML and SDL).
  While usually required, you can skip it if you plan to implement your own backend.
- `minart-pure`: Contains the RIO implementation for pure applications.
  You can skip it if you plan to just write impure code or bring your own IO implementation.
- `minart-image`: Contains logic to load images from PPM, BMP or QOI files.
  You can skip it if you don't plan to load any images.

If for some reason you want to make your binary as small as possible, you can include individual packages instead of
the full `minart` bundle.
