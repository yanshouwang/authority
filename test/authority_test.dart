import 'package:authority/src/messenger.dart' as messenger;
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:authority/authority.dart';

void main() {
  final method = MethodChannel(messenger.method.name);

  TestWidgetsFlutterBinding.ensureInitialized();

  final calls = <MethodCall>[];

  setUp(() {
    method.setMockMethodCallHandler((call) async {
      calls.add(call);
      return false;
    });
  });

  tearDown(() {
    method.setMockMethodCallHandler(null);
    calls.clear();
  });

  for (var authority in Authority.values) {
    test('check $authority.', () async {
      final actual = await authority.checkAsync();
      expect(actual, false);
      expect(calls, [isMethodCall('check', arguments: authority.index)]);
    });
    test('request $authority.', () async {
      final actual = await authority.requestAsync();
      expect(actual, false);
      expect(calls, [isMethodCall('request', arguments: authority.index)]);
    });
  }
}
