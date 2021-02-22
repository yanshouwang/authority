import 'package:authority/src/messenger.dart';

enum Authority {
  calendar,
  camera,
  contacts,
  locationAlways,
  locationWhenUse,
  microphone,
  phone,
  sensors,
  sms,
  storage,
}

extension AuthorityExtension on Authority {
  Future<bool> checkAsync() => method.invokeMethod('check', index);
  Future<bool> requestAsync() => method.invokeMethod('request', index);
}
