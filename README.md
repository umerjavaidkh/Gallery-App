# Gallery App

## Overview
The **Gallery App** is an Android application designed to efficiently display images and videos stored on the device. It leverages modern Android components and libraries to ensure a smooth and optimized experience.

## Prerequisites
Before running the project or executing test cases, ensure you have the following installed:

- **Android Studio** <img width="495" alt="image" src="https://github.com/user-attachments/assets/cc6e5c26-923a-4413-8c3b-cc8747ee847f" />

- **Java 17+**
- **Gradle** (Bundled with Android Studio) <img width="632" alt="image" src="https://github.com/user-attachments/assets/be51252f-7129-4bbd-943d-424a7dadedca" />
<img width="632" alt="image" src="https://github.com/user-attachments/assets/039165cb-6c19-4e8b-8f7e-18c3f04e0a7f" />

- **Android Emulator or Physical Device**

## Running the App
To run the application in **Android Studio**:
1. Open the project in **Android Studio**.
2. Connect a **physical device** or launch an **Android emulator**.
3. Click on **Run ▶** or use the shortcut **Shift + F10**.

## Running Tests
This project includes both **Unit Tests** and **Instrumentation (UI) Tests**.

### Running Unit Tests with Coverage
To execute **unit tests** and generate a **coverage report**, run the following command in the terminal:
```sh
./gradlew testDebugUnitTestCoverage
```
This will run all unit tests and generate a coverage report in:
```
app/build/reports/jacoco/testDebugUnitTestCoverage/html/index.html
```

### Generating a Detailed Jacoco Coverage Report
To generate a full **Jacoco coverage report**, run:
```sh
./gradlew jacocoTestReport
```
The HTML report will be available at:
```
app/build/reports/jacoco/jacocoTestReport/html/index.html
```
Open the `index.html` file in a browser to view the coverage details.

### Running UI Tests (Espresso & Instrumentation Tests)
For **UI tests** (Espresso and instrumentation tests), execute:
```sh
./gradlew connectedCheck
```
This command will:
- Run all **Espresso UI tests** on a connected device or emulator.
- Generate a test coverage report in:
```
app/build/outputs/code-coverage/connected/
```

## Viewing Test Coverage in Android Studio
To view test coverage inside **Android Studio**:
1. **Run Tests with Coverage**:
   - Right-click on the test file/class and select **Run 'Test with Coverage'**.
   - Open **View → Tool Windows → Coverage** to inspect the results.
2. **View Coverage Report in Browser**:
   - Navigate to `app/build/reports/jacoco/jacocoTestReport/html/index.html`.
   - Open the file in a web browser to see detailed coverage.
3. **Enable Code Coverage Highlighting**:
   - Open **View → Tool Windows → Coverage**.
   - Click the ⚡ **(Enable Code Coverage Highlighting)** icon.

## Conclusion
This guide provides the necessary steps to **run tests**, **generate coverage reports**, and **view results** in Android Studio. Following these steps ensures high-quality code with measurable test coverage. 🚀

For any issues, feel free to raise an issue in this repository!

<img width="1230" alt="Pasted Graphic 13" src="https://github.com/user-attachments/assets/a49105b3-a20c-4119-8841-bac16e7ef7cc" />

