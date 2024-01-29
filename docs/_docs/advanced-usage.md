---
title: Advanced Usage
---

# Advanced usage

If for some reason you want to make your binary as small as possible, you can include individual packages instead of
the full `minart` bundle.

## Minimal builds

The Minart project is divided in multiple small packages:

- `minart-core`: This package is always required.
- `minart-backend`: Contains the default implementations for each backend (AWT, HTML and SDL).
  While usually required, you can skip it if you plan to implement your own backend.
- `minart-image`: Contains logic to load and store images in PPM, BMP or QOI format.
  You can skip it if you don't plan to read or write any images.
- `minart-sound`: Contains logic to load and store sound files in WAV, AIFF, RTTTL or QOA format.
  You can skip it if you don't plan to read or write any sound files.
