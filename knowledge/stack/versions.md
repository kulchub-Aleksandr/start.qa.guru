# Version sources

[build.gradle](../../build.gradle) is authoritative. This generated snapshot is a reading guide, not a second lockfile.
After editing configuration, re-read it and update these notes; do not change dependencies to fit a locally available compiler.

- Required `java` runtime: `21` (match the declared version components).
- Dependency, SDK, compiler and build-tool versions must match their declared pins exactly. A mismatch is BLOCKED, not a verified result for a nearby version.
- Required `gradle` executable: `9.6.0`.
- `java`: `21` - declared in [build.gradle](../../build.gradle).
- `junit6`: `6.1.3` - declared in [build.gradle](../../build.gradle).
- `selenide`: `7.19.0` - declared in [build.gradle](../../build.gradle).
- `allurePlugin`: `4.3.0` - declared in [build.gradle](../../build.gradle).
- `allure`: `3.14.3` - declared in [build.gradle](../../build.gradle).
- `allureBom`: `3.0.0` - declared in [build.gradle](../../build.gradle).
- Read additional plugin and transitive constraints in the same build file; the list is not a copied build configuration.
- No wrapper is supplied. Verify the required Gradle version before compilation; an installation name or a different local version is not sufficient.

CI tool/action/image declarations: [Jenkinsfile](../../Jenkinsfile). Jenkins installation names do not prove installed versions.
Testing on a different library/compiler version is a local compatibility probe, not a PASS for the declared pins.
