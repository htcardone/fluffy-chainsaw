# AndroidTechnicalChallenge
[![Build Status](https://app.bitrise.io/app/e2c65fd1f7dd8e85/status.svg?token=9_NIM68p0jtmD2RDSKR5BQ&branch=main)](https://app.bitrise.io/app/e2c65fd1f7dd8e85)

Submitted by: Henrique Cardone (htcardone@gmail.com)

This project was developed based on the [original one](https://github.com/podium/Android-Challenge), and on the [requirements](REQUIREMENTS.md) sent by email.

## Architecture
This app was implemented based on the latest [Google's app architecture guide](https://developer.android.com/topic/architecture), so it contains the following layers:

### UI Layer
The UI was made using **Jetpack Compose**, and all its state was hoisted to a ViewModel. Also, this implementation follows a **unidirectional data flow**, in a way that we have classes to represent the state and classes to map the user's events.

The state object is being exposed using **LiveData**, but the Composables were built in a way that it could be easily changed to StateFlow or any other reactive library.

Each screen has its own Fragment, and all navigation is done by **Jetpack Navigation**.

The ViewModels have unit tests, while the main app's flows are being tested by UI Instrumented Tests configured with a MockWebServer. In the future, it would be good to also test the Composables in isolation, and even add a screenshot comparison tool.

### Data and Network Layer
All data accessed by the ViewModel is provided by two **Repositories**, one for the Movies and the other for the Genres. These repositories depend on Data Source interfaces, in a way that it is easier to test them using [fakes instead of mocks](https://android.googlesource.com/platform/frameworks/support/+/androidx-main/docs/do_not_mock.md). Also, the Dependency Injection modules are ready to support other data sources.

The network calls are made only using Retrofit, since the Apollo client was [removed](https://github.com/podium/Android-Challenge/commit/c4d7e4fff02d751d3a20b59d2139ba997f154ba4) from the project last month. Since this layer is decoupled from other parts of the code, it would be simple to change it back.

## Testing
This project has the following tests
- Unit Tests, using fake data sources, and no mocks:
  - Repositories
  - ViewModels
- UI Tests, using a MockWebServer for the API responses:
  - Home screen content loading
  - Home screen navigation to the Movie screen
  - Movie screen content loading and interactions
  - Movie screen navigation to the Movies screen

All these tests are configured to run a [CI pipeline](https://app.bitrise.io/app/e2c65fd1f7dd8e85).