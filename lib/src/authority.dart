import 'package:authority/src/messenger.dart';

enum Authority {
  camera,
  locationAlways,
  locationWhenUse,
}

extension AuthorityExtension on Authority {
  Future<bool> checkAsync() => method.invokeMethod('check', index);
  Future<bool> requestAsync() => method.invokeMethod('request', index);
}
