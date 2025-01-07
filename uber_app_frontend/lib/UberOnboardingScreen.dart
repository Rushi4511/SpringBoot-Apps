import "package:flutter/material.dart";

class UberSplashScreen extends StatefulWidget {
  const UberSplashScreen({super.key});

  @override
  State<UberSplashScreen> createState() => _UberonboardingscreenState();
}

class _UberonboardingscreenState extends State<UberSplashScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: Colors.black,
        body: Center(
          child: Image.network(
              height: 250,
              width: 250,
              "https://helios-i.mashable.com/imagery/articles/03y6VwlrZqnsuvnwR8CtGAL/hero-image.fill.size_1248x702.v1623372852.jpg"),
        ));
  }
}
