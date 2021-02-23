import 'enum.dart';
import 'messenger.dart';

/// Check the authority state. Returns ture if authorized.
Future<bool> checkAsync(Authority authority) =>
    method.invokeMethod('check', authority.index);

/// Request the authority. Returns ture if authorized.
Future<bool> requestAsync(Authority authority) =>
    method.invokeMethod('request', authority.index);

/// Open App Settings.
Future<void> openAppSettingsAsync() => method.invokeMethod('openAppSettings');
