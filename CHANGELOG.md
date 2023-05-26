# Changelog

## [Unreleased]
### ✨ Added

* Port to 1.20.

### 👷 Technical

* Uses Gradle 8.1.1.
* Uses fabric-loom 1.2.
* Uses fabric-loader 0.14.20.
* Updates the API specification.
  - The "master" category now includes its class info and can be sorted by class name,
    and can only be specified once per class.
  - The enum `SoundCategory` generator now includes the modId prefix to reduce the
    possibility of name collisions. The field now contains its modId and `$`, such as
    `EXTRASOUNDS$MASTER`, and also localization keys need to be updated.

## [1.2.4+1.19.4-build.3] - 2023-05-21
### ✨ Added

* Annotation value `tooltip` to allow to display specified tooltip.

## [1.2.4+1.19.4-build.2] - 2023-03-27
### ✨ Added

* Annotation value `toggle` to allow to be displayed as a button widget.
* Displays in two columns on `SoundGroupOptionsScreen`.

## [1.2.4+1.19.4-build.1] - 2023-03-17
### ✨ Added

* Port to 1.19.4.

### 👷 Technical

* Uses Java 17.
* Uses Gradle 8.0.1.
* Uses fabric-loom 1.1.

## [1.2.4+1.19.3-SNAPSHOT]
### ✨ Added

* Port to 1.19.3.

## [1.2.4] - 2022-06-05

### Fixed

- Sound category volume not being saved

## [1.2.3] - 2022-06-05

### Fixed

- Sound categories being null when mods are being initialized
- Default float not being applied if settings are not yet saved

## [1.2.2] - 2022-06-01

### Changed

- Made default level mixin optional - this makes Optifine run with SoundCategories, however the default levels for all
  sounds will always be 100%.

## [1.2.1] - 2022-01-02

### Changed

- Added Optifine/Optifabric as an incompatible mod - causes crash on startup
- Added Fabric API as a required dependency

## [1.2.0] - 2021-12-28

### Added

- Category grouping via `master` attribute
- Configurable default volume levels

### Fixed

- Done button offset

## [1.1.0] - 2021-12-15

### Changed

- Replaced registration function with annotations

### Fixed

- Sound categories not being registered after init

## [1.0.0] - 2021-12-15

Initial release