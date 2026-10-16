Mission SM18 — Daily progress bar patch

This patch adds a small horizontal bar to the RIGHT of the hours/minutes/seconds counter.
The daily bar starts full at midnight India time and decreases toward empty by the next midnight.
The existing main countdown progress bar and its color thresholds remain unchanged.
It also includes the four day/night background assets and provider used by the previous day/night patch.

Apply this patch to the Android project that the workflow actually builds:
MissionSM18-Clean-Repo-v1.2/app/

Copy/replace the contents of this patch's app/ folder into that project's app/ folder.
Do not replace build.gradle, settings.gradle, gradlew, or the workflow.
