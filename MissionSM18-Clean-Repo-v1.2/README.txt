Mission SM18 - Complete app folder with requested layout adjustments

Changes in this package are limited to app/src/main/res/layout/countdown_widget.xml:
- Countdown years/months/days and hours/minutes/seconds block is moved slightly lower (date margin 9dp -> 18dp).
- Main progress bar row is moved lower (top margin 22dp -> 32dp).
- Date/day row is given more width so the year is less likely to be ellipsized; daily bar is correspondingly shorter but still displayed with its percentage.

All Java files, countdown calculations, progress colors, day/night backgrounds, icons, manifest, and other resources are retained from the previous complete app package.

Upload the CONTENTS of the app folder into the existing project's app folder. Do not create app/app. Do not replace project-level Gradle files or the GitHub Actions workflow.
