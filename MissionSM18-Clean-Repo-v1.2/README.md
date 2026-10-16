# Mission SM18 — clean GitHub Actions project (v1.2)

This repository is arranged with the Android Gradle project at the repository root. Do not upload this folder inside another folder in GitHub; upload the **contents** of this folder to the new repository root.

## Build the APK without Android Studio

1. Create a new public or private GitHub repository named `MissionSM18-v12`.
2. Use **Add file → Upload files** and upload all contents of this folder, including the `.github` folder. Make sure `settings.gradle`, `build.gradle`, `gradle.properties`, `app/`, and `.github/` appear directly on the repository's main page.
3. Commit to the `main` branch.
4. Open **Actions → Build Mission SM18 APK** and open the newest successful run.
5. Download artifact `Mission-SM18-v1.2-APK`, extract the ZIP, and install `app-debug.apk`.

## Important install note

The application ID remains `com.missionsm18`. GitHub-hosted builds generally use a new debug signing key for each workflow run, so Android may refuse to update the existing app. If that happens, uninstall the old Mission SM18 app, then install the new APK. This removes its app data and you must add the home-screen widget again.

## Widget refresh note

The app starts a foreground service that attempts to refresh the widget every second and shows an ongoing notification. Android launchers may throttle widget rendering, and Xiaomi battery management may stop background services. No app can guarantee an exact once-per-second launcher refresh on every Android device.
