# Authority

An authority plugin for flutter, support check, request and openAppSettings on Android an iOS.

## Features

- checkAsync(Authority authority)
- requestAsync(Authority authority)
- openAppSettingsAsync()

## Getting Started

Add `authority` as a [dependency in your pubspec.yaml file](https://flutter.dev/using-packages/).

```
dependencies:
  authority: ^<latest-version>
```

### Android

Add the authority's [corresponding permissions](example/android/app/src/main/AndroidManifest.xml) to the `AndroidManifest.xml`.

### iOS

Add the authority's [corresponding descriptions](example/ios/Runner/Info.plist) to the `Info.plist`.