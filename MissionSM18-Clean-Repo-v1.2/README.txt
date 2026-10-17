Mission SM18 — Full App Layout Correction

This archive contains the complete app/ directory from the existing Mission SM18 project.
Only app/src/main/res/layout/countdown_widget.xml was adjusted.

Layout changes:
- Countdown block shifted down to sit approximately at the vertical center of the card.
- Main progress row moved down slightly relative to the countdown block.
- Bottom padding removed so the date row has room to render fully.
- Date row widened and daily progress track shortened so the year can fit.
- Java source, countdown calculations, progress-color logic, backgrounds, icons, and other app resources remain unchanged.

Installation:
1. Extract this ZIP.
2. In the repository, open MissionSM18-Clean-Repo-v1.2/app.
3. Replace the contents of that existing app folder with the contents of this archive's app folder.
4. Do not replace project-level Gradle files or .github/workflows/build-apk.yml.
5. Commit the files, build a new APK, and reinstall it. Remove and re-add the home-screen widget if needed.
