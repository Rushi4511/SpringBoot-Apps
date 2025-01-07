import 'package:flutter/material.dart';
import 'package:uber_app_frontend/EmailEnterScreen.dart';
import 'package:uber_app_frontend/OtpEmail.dart';
import 'package:uber_app_frontend/OtpMobile.dart';
import 'package:uber_app_frontend/StartingPage.dart';
import 'package:uber_app_frontend/UberOnboardingScreen.dart';

void main() {
  runApp(const MainApp());
}

class MainApp extends StatelessWidget {
  const MainApp({super.key});

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
        debugShowCheckedModeBanner: false, home: StartingPage());
  }
}
