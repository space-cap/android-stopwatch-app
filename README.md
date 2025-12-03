# 간단한 스톱워치 앱

Jetpack Compose와 Material 3로 제작된 간단한 안드로이드용 스톱워치 애플리케이션입니다.

## 📸 실행 화면

<!-- 앱의 스크린샷이나 GIF를 여기에 추가하세요. -->

## 🚀 주요 기능

- **시작, 일시정지, 초기화**: 기본적인 스톱워치 기능입니다.
- **랩 타임 기록**: 랩 타임을 기록하고 목록으로 표시합니다.
- **깔끔하고 현대적인 UI**: 최신 Material Design 구성 요소를 사용한 직관적인 사용자 인터페이스를 제공합니다.

## 🛠️ 사용된 기술

- **[Kotlin](https://kotlinlang.org/)**: 안드로이드 공식 개발 언어입니다.
- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: 안드로이드의 최신 선언형 UI 툴킷입니다.
- **[Material 3](https://m3.material.io/)**: 구글의 최신 디자인 시스템입니다.
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)**: 생명주기를 고려하여 UI 관련 데이터를 관리합니다.

## 📂 프로젝트 구조

```
SimpleStopWatch/
├── app/
│   ├── src/main/
│   │   ├── java/com/ezlevup/stopwatch/
│   │   │   └── MainActivity.kt  # 메인 액티비티, ViewModel, Composable UI 포함
│   │   └── res/                 # 리소스 폴더 (아이콘, 테마 등)
│   └── build.gradle.kts       # 앱 모듈의 Gradle 빌드 스크립트
└── build.gradle.kts           # 최상위 프로젝트의 Gradle 빌드 스크립트
```

## ⚙️ 빌드 방법

1.  이 저장소를 복제(Clone)합니다.
2.  최신 버전의 Android Studio에서 프로젝트를 엽니다.
3.  Gradle이 프로젝트 종속성을 동기화하도록 합니다.
4.  안드로이드 에뮬레이터 또는 실제 기기에서 앱을 빌드하고 실행합니다.

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참고하세요.

```
MIT License

Copyright (c) 2023 [Your Name]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
