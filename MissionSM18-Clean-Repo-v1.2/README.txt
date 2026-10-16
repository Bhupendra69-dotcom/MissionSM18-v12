MISSION SM18 WIDGET REFINEMENT PATCH

This patch updates only the widget layout and CountdownWidgetProvider.java. It keeps the existing app/build files and day/night background assets.

Features:
- Title at the top-left.
- Current weekday/date directly below the title.
- Existing target countdown below the date.
- Compact daily remaining bar immediately to the right of the HH/MM/SS counter.
- Daily bar changes green/yellow/orange/red as the day runs out.
- Percentage labels to the right of both bars.
- Existing mission progress and day/night background behavior retained.

Upload into the nested project used by the successful build:
MissionSM18-Clean-Repo-v1.2/app/src/main/java/com/missionsm18/CountdownWidgetProvider.java
MissionSM18-Clean-Repo-v1.2/app/src/main/res/layout/countdown_widget.xml

Keep all other project files unchanged. Commit to main, wait for GitHub Actions, download the new APK artifact, install, and re-add the widget.
