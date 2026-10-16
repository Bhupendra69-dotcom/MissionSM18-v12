Mission SM18 layout fix

This patch changes only countdown_widget.xml. It inserts a flexible spacer above the bottom row so the title, countdown, time, and main progress bar remain at their existing positions while the date and daily progress bar sit near the bottom edge.

Upload this file to the same project folder used by your successful build:
MissionSM18-Clean-Repo-v1.2/app/src/main/res/layout/countdown_widget.xml

Do not replace other files. Commit, wait for GitHub Actions to succeed, install the new APK, and re-add the home-screen widget if necessary.
