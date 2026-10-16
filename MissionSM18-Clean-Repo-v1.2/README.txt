MISSION SM18 — LAYOUT ADJUSTMENT PATCH

This patch only changes the widget layout. It keeps the current title, countdown, main progress bar, day/night background, icon assets, percentage labels, and daily progress color logic.

Layout changes:
- The weekday/date is moved below the main mission progress bar.
- The daily remaining-time progress bar is placed to the right of the weekday/date and extends toward the right edge.
- The hours/minutes/seconds row now contains only the time, leaving it less congested.

Upload the included app/src/main/res/layout/countdown_widget.xml to the matching path inside the nested project that your successful build uses:
MissionSM18-Clean-Repo-v1.2/app/src/main/res/layout/countdown_widget.xml

Replace that file only. Do not replace the Java provider or other resources. Commit to main, wait for GitHub Actions, then install the APK from the successful run and re-add the widget if needed.
