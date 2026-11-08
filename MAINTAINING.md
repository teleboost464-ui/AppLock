# Maintaining AppLock

This fork builds and tests changes in the Android CI workflow. Run
`./gradlew :app:testDebugUnitTest :app:assembleDebug :app:assembleRelease` locally
with JDK 17 or newer and Android SDK Platform 37. On Windows, use `gradlew.bat`.
Test changes to protection and package visibility on Samsung One UI before
promoting a preview to a stable release.

Dependabot checks Gradle dependencies and GitHub Actions weekly. In the fork's
GitHub settings, enable Dependabot version updates under **Advanced Security**
if the setting is off.

## Publishing an APK

1. Increase `versionCode` and set `versionName` in `app/build.gradle.kts`.
2. Merge the version change after Android CI passes.
3. Push a Git tag matching `v<versionName>` on the merged commit. A hyphenated
   version publishes as a prerelease.

The Signed APK Release workflow tests, builds, signs, and verifies the APK before
attaching it to a GitHub release. It uses the repository's
`APPLOCK_KEYSTORE_BASE64` and `APPLOCK_KEYSTORE_PASSWORD` Actions secrets. The
keystore is PKCS#12 with the alias `applock`. Keep a secure backup of the keystore
and password and reuse them so Android can install future releases as updates.
