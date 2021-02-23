import 'package:authority/authority.dart';
import 'package:flutter/material.dart';

class HomeView extends StatefulWidget {
  @override
  _HomeViewState createState() => _HomeViewState();
}

class _HomeViewState extends State<HomeView> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Authority'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            FlatButton(child: Text('CHECK'), onPressed: check),
            FlatButton(child: Text('REQUEST'), onPressed: request),
            FlatButton(child: Text('APP SETTINGS'), onPressed: openAppSettings),
          ],
        ),
      ),
    );
  }

  void check() async {
    final value = await checkAsync(Authority.camera);
    await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('CHECK RESULT'),
        content: Text('$value'),
      ),
    );
  }

  void request() async {
    final value = await requestAsync(Authority.camera);
    await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('CHECK RESULT'),
        content: Text('$value'),
      ),
    );
  }

  void openAppSettings() async {
    await openAppSettingsAsync();
  }
}
