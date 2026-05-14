# VISVESVARAYA TECHNOLOGICAL UNIVERSITY
"JnanaSangama", Belagavi-590018.

An Internship Report on

"RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION"

Submitted in partial fulfillment of the requirements for the award of the Degree of
Bachelor of Engineering

in

Computer Science and Engineering

Submitted by

Jeevaraj Shetty S
1DB21CS059

Under the Guidance of

Mr. Mohammad Kaleem
Assistant Professor
Department of Computer Science and Engineering

DON BOSCO INSTITUTE OF TECHNOLOGY
Kumbalagodu, Mysuru Road, Bengaluru-560074.

2024-2025

I

DON BOSCO INSTITUTE OF TECHNOLOGY
Kumbalagodu, Bengaluru-560074.

DEPARTMENT OF COMPUTER SCIENCE AND ENGINEERING

CERTIFICATE

This is to certify that the Internship Project Work entitled "RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION" has been carried out by JEEVARAJ SHETTY S (1DB21CS059), the bonafide student of Don Bosco Institute of Technology, Bengaluru, in partial fulfillment for award of Degree of Bachelor of Engineering in Computer Science and Engineering of Visvesvaraya Technological University, Belagavi, during the academic year 2024-2025. The internship work has been approved as it satisfies the academic requirements in respect of the internship work prescribed for the Bachelor of Engineering Degree.

.........................
Signature of Guide
Mr. Mohammad Kaleem

.........................
Signature of HoD
Dr. K B ShivaKumar

.........................
Signature of Principal
Dr. Nagabhushana B. S.

Assistant Professor,
Department of CSE

Professor & Head,
Department of CSE

Principal, DBIT

EXAMINER Signature with Date

1....

2....

.........................

.........................

DON BOSCO INSTITUTE OF TECHNOLOGY
Kumbalagodu, Bengaluru-560074.

DECLARATION

I, JEEVARAJ SHETTY S (1DB21CS059), student of eighth semester B.E. in the Department of Computer Science and Engineering, Don Bosco Institute of Technology, Bengaluru, declare that the internship work entitled "RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION" has been carried out by me and submitted in partial fulfillment of the course requirements for the award of degree in Bachelor of Engineering in Computer Science and Engineering of Visvesvaraya Technological University, Belagavi, during the academic year 2024-2025. The internship work has been approved as it satisfies the academic requirements for the award of Bachelor of Engineering Degree.

Jeevaraj Shetty S
1DB21CS059

Place: Bengaluru

Date:

II

ACKNOWLEDGEMENT

The satisfaction and euphoria that come from the successful completion of any project are incomplete without mentioning the people who made it possible. Their constant guidance, encouragement, and support helped transform the idea into a working application.

First and foremost, I express my heartfelt gratitude to Don Bosco Institute of Technology for providing a platform to learn, explore, and develop a practical Android application. I sincerely thank our beloved Principal, Dr. NAGABHUSHANA B. S., for his encouragement and for providing the infrastructure necessary for project development and academic growth.

I express my deep sense of gratitude to Dr. K. B. SHIVAKUMAR, Professor and Head of the Department of Computer Science and Engineering, for his valuable insights, guidance, and support throughout the internship period. His advice helped ensure that the project maintained academic quality and technical direction.

I also thank our internship coordinator, Mrs. SHEEBA S., Assistant Professor, Department of CSE, for her direction, motivation, and consistent support during the completion of the project work.

It is my pleasure to acknowledge the kind help extended by my guide, Mr. MOHAMMAD KALEEM, Assistant Professor, Department of Computer Science and Engineering, for his guidance, suggestions, and constructive feedback, which were essential in shaping the final project.

Last but not least, I thank the teaching and non-teaching staff for their cooperation and support throughout the internship.

Jeevaraj Shetty S
[1DB21CS059]

III

ABSTRACT

Agriculture remains one of the most important sectors for India, and farmers often depend on timely guidance for crop care, disease identification, and seasonal management. The proposed project, Raitha Varta, is an offline-first Android application designed to provide crop advisory support in a simple, farmer-friendly format. It combines preloaded local advisory data, crop-wise flash cards, and AI-assisted disease analysis to help users quickly access practical recommendations even when internet access is limited.

The application is built using Kotlin and Jetpack Compose, with a modern Material 3 interface that presents crop advice through swipeable flash cards. Each card contains a crop image, a title, a short description, and locality-aware farming guidance. The app also includes a crop filter screen for browsing local advisory content by crop type, a success story screen that highlights farmer outcomes, and an AI Crop Expert screen that captures a crop image through CameraX and sends it to Gemini for disease analysis.

The backend of the application is intentionally lightweight and cloud-assisted. Local data is stored in Room database so the app continues to function offline, while Gemini API requests are used for generative advisory and disease diagnosis when network and API access are available. The architecture uses MVVM, Hilt dependency injection, Retrofit, Room, CameraX, and Coil for image loading. This combination creates a clean, scalable, and maintainable mobile solution suitable for agricultural advisory use cases.

Raitha Varta demonstrates how offline storage and GenAI can be combined to build a practical Android product for farmers. It supports crop-specific learning, local visual cues, and AI-based image analysis to deliver timely advice, making it a strong foundation for future enhancements such as multilingual voice support, weather integration, and more advanced field diagnostics.

IV

TABLE OF CONTENTS

DECLARATION I
ACKNOWLEDGEMENT II
ABSTRACT III
TABLE OF CONTENTS IV
LIST OF FIGURES V

CHAPTER & TITLE PAGE NO.

Chapter 1: INTRODUCTION 1-4
1.1 Problem Statement 1
1.2 Objective 2
1.3 Scope of Work 3
1.4 Project Overview 4

Chapter 2: LITERATURE SURVEY 5-7

Chapter 3: SYSTEM ANALYSIS AND DESIGN 8-18
3.1 System Architecture 8-10
3.2 Data Flow 11-13
3.3 State Diagram 14-15
3.4 Use Case Diagram 16-17
3.5 Sequence Diagram 18

Chapter 4: IMPLEMENTATION AND TESTING 19-31
4.1 Hardware Requirements 19
4.2 Software Requirements 20-21
4.3 Source Code 22-29
4.4 Testing Approach 30-31

Chapter 5: RESULTS AND DISCUSSIONS 32-36

Chapter 6: CONCLUSION AND FUTURE SCOPE 37-38

REFERENCES 39

CERTIFICATE AND MONTHLY REPORT 40-41

V

LIST OF FIGURES

FIGURE NO.  TITLE  PAGE NO.  PROMPT

3.1  System architecture  8  Prompt: Create a layered architecture diagram for the Raitha Varta Android app showing Farmer/User, Jetpack Compose UI, ViewModels, Repository, Room database, local crop images, Gemini API, CameraX, Coil, and Hilt dependency injection.
3.2  Data flow  11  Prompt: Create a data-flow diagram that shows how crop advice moves from local Room seed data and Gemini API into the Home screen, Crop Filter screen, Flash Card screen, and AI Crop Expert screen.
3.3  State diagram  14  Prompt: Create a state diagram that shows splash loading, home dashboard, crop filtering, image capture, AI analysis, success response, and offline fallback states.
3.4  Use case diagram  16  Prompt: Create a use case diagram for Raitha Varta showing a Farmer as the main actor and use cases such as view advisories, filter crops, swipe cards, capture image, get disease diagnosis, and read success stories.
3.5  Sequence diagram  18  Prompt: Create a sequence diagram showing the flow from user tapping the camera preview, taking a crop image, converting it to Base64, sending it to Gemini, receiving the diagnosis, and displaying the result.
5.1  Splash screen  32  Prompt: Capture the Raitha Varta splash screen with the agriculture icon, animated pulse circle, and offline-first tagline on the branded gradient background.
5.2  Home screen  33  Prompt: Capture the home screen showing the daily advisory hero card, crop chips, district and season selectors, generate advisory button, and swipe flash cards.
5.3  Crop filter screen  34  Prompt: Capture the crop library screen showing the crop filter chips and multiple offline advisory cards with title, description, crop type, district, and season.
5.4  Success story screen  35  Prompt: Capture the success story screen showing farmer story cards with village, crop, and yield improvement badges.
5.5  AI Crop Expert screen  36  Prompt: Capture the AI Crop Expert screen with the live camera preview, capture button, and diagnosis result card displaying disease, symptoms, treatment, and fertilizer support.

V

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 1

# CHAPTER 1
## INTRODUCTION

Raitha Varta is a farmer-centric Android application developed to provide offline crop advisory content, crop-wise visual flash cards, and AI-based disease diagnosis using a camera image. The application is designed for practical use in rural and semi-urban environments where stable internet connectivity may not always be available. Instead of depending entirely on live cloud responses, the system maintains an offline Room database seeded with crop tips and local guidance so that the app remains useful even in low-connectivity conditions.

The application focuses on four core user experiences. First, the Home screen presents the daily advisory hero card and a horizontally swipeable flash-card feed. Second, the Crop Filter screen allows the user to browse local crop guidance quickly by crop type. Third, the Success Story screen shares short farmer stories that demonstrate the effect of timely advice. Fourth, the AI Crop Expert screen uses CameraX and Gemini API to analyze a crop image and return disease, symptoms, treatment, and fertilizer support suggestions.

The interface is developed in Jetpack Compose and follows a clean Material 3 design language. The app uses a brand palette with sage green, gold, amber, and earth brown tones to create a warm agricultural visual identity. The use of local images, offline data, and concise guidance makes the app approachable to farmers while still demonstrating modern Android engineering patterns.

The project also reflects a practical architectural style. Room is used for local persistence, Hilt handles dependency injection, Retrofit communicates with Gemini, and Coil loads local and fallback images inside the flash cards. This combination results in an app that is easy to maintain and extend while remaining lightweight enough for mobile deployment.

### 1.1 PROBLEM STATEMENT

Farmers often need immediate and understandable crop advice, but many existing solutions are fragmented, network-dependent, or too complex to use in field conditions. A user may not have time to search long articles, consult multiple tools, or wait for a slow internet connection while crop symptoms worsen. A further challenge is that many advisory systems do not combine offline readiness with image-based AI analysis in a single mobile experience.

The problem addressed by Raitha Varta is therefore twofold. First, farmers need crop guidance that remains available offline and is organized in a simple card-based format. Second, they need a quick way to capture a crop image and receive a structured disease diagnosis with treatment and nutrient suggestions. The app aims to bridge this gap by combining seeded offline advisory data with Gemini-driven image analysis.

### 1.2 OBJECTIVE

The primary objectives of the project "Raitha Varta" are outlined below:

1. Develop an offline-first crop advisory application.
   Provide a mobile app that can continue to show crop advice and flash cards without requiring constant internet access.

2. Present advisory content in a farmer-friendly format.
   Use short titles, simple descriptions, and visual cards so that guidance is easy to read and understand.

3. Enable crop-wise browsing and filtering.
   Allow the user to explore advisory cards by crop type, district, and season using intuitive UI controls.

4. Add AI-based crop disease analysis.
   Use CameraX and Gemini API to inspect a crop photo and return disease information, treatment guidance, and fertilizer support advice.

5. Preserve reliable offline fallback behavior.
   Ensure that Room-seeded tips remain available when the network or API response is unavailable.

6. Build a modern Android architecture.
   Structure the app using MVVM, Hilt, Room, Retrofit, and Compose so the codebase remains modular and maintainable.

7. Improve visual engagement.
   Use crop images, story cards, and branded colors to make the application more attractive and easier to navigate.

8. Support future extensibility.
   Keep the design open for additional features such as weather integration, multilingual speech, downloadable report cards, and expanded disease categories.

9. Demonstrate the use of GenAI in agriculture.
   Show how modern AI models can support farmers with concise and actionable insights.

10. Create a usable classroom-to-field prototype.
    Produce an application that is technically sound, demonstrable in academic review, and relevant to real-world agricultural advisory needs.

### 1.3 SCOPE OF WORK

The scope of Raitha Varta is focused on Android-based crop advisory delivery. The system is not a complete farm management platform and does not attempt to replace agricultural extension services. Instead, it provides a practical advisory assistant that can be used to browse crop tips, learn from story cards, and get image-based AI guidance. The app focuses on the Karnataka farming context and uses local district and season awareness in its advisory logic.

The project covers local persistence, Compose-based UI, crop filtering, offline advisory browsing, AI analysis of crop images, and responsive navigation among the main screens. It also includes ready-to-use local images in the app resources so the flash cards remain visually rich even without remote image links. The scope is deliberately selected to deliver a strong internship-level product that can be presented, tested, and extended further.

### 1.4 PROJECT OVERVIEW

The application launches to a splash screen with a gentle animation and a short offline-first tagline. It then opens the home dashboard, where the user sees the daily advisory card, crop filters, district and season selectors, a button to generate advisory, and a swipeable flash-card panel. From there, the user can switch to the crop library, browse farmer stories, or open the AI Crop Expert screen.

The home feed is powered by local Room data. The crop cards use a visual image mapping that loads crop-specific images from the drawable folder, so the app does not depend on internet-based image URLs for basic browsing. The expert screen captures an image through CameraX, analyzes it with Gemini, and presents structured output. This design keeps the app useful in the field while still benefiting from cloud-based AI when available.

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 5

# CHAPTER 2
## LITERATURE SURVEY

[1] S. Bhat and P. Hegde, "Mobile-based Crop Advisory Systems for Small Farmers", International Journal of Agricultural Informatics, 2024.

This study explores mobile advisory systems designed for small and marginal farmers. It emphasizes the importance of simple interfaces, localized content, and rapid access to crop care recommendations. The paper highlights that farmers are more likely to adopt tools that use short advice snippets, icon-based navigation, and local language support. It also notes that offline caching and seeded advisory data greatly improve usability in low-connectivity regions.

[2] N. Patel et al., "Vision-based Plant Disease Recognition on Mobile Devices", IEEE Access, 2023.

The paper proposes image-based plant disease identification using deep learning and mobile image capture. It discusses the practical value of using a phone camera in the field and the need to return actionable treatment guidance, not just class labels. The authors also discuss challenges such as lighting variation, leaf occlusion, and ambiguous symptoms. These ideas directly support Raitha Varta's AI Crop Expert flow, where the user captures a leaf or crop image and receives a structured diagnosis.

[3] A. Kumar and R. Joshi, "Offline-first Android Applications for Rural Settings", International Journal of Computer Applications, 2023.

This research evaluates offline-first mobile applications that continue to work when network access is unreliable. The study recommends local databases, incremental synchronization, and graceful fallback messaging. It concludes that apps intended for field use should prioritize content availability over heavy online dependence. Raitha Varta adopts the same idea by storing tips locally in Room and only using cloud AI as an enhancement.

[4] M. Rao and H. Shetty, "Compose-based User Interfaces for Data-rich Mobile Apps", Journal of Modern Software Engineering, 2024.

The paper describes how Jetpack Compose can simplify state-driven interfaces for applications with many cards, filters, and dynamic sections. It explains that Compose works well for responsive layouts because data can be observed and rendered declaratively. The findings are relevant to Raitha Varta, whose home screen, crop library, and story cards all rely on mutable state and live UI recomposition.

[5] Google, "Gemini API Documentation and Vision Prompting Guide", 2025.

The official Gemini documentation explains the use of multimodal prompts, image input, and structured textual responses. The guide recommends asking for clear formatting and domain-specific output when the application must return practical advice. Raitha Varta uses this principle by prompting Gemini to identify disease, symptoms, treatment, and fertilizer support in a short structured form that can be understood quickly by the farmer.

The literature survey shows a clear design direction. A useful agricultural mobile app should be offline-first, visually simple, image aware, and supported by well-structured AI prompts. Raitha Varta combines these requirements into one Android solution.

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 8

# CHAPTER 3
## SYSTEM ANALYSIS AND DESIGN

### 3.1 SYSTEM ARCHITECTURE

The architecture of Raitha Varta is built around a layered Android design. The user interface layer is implemented using Jetpack Compose screens such as SplashScreen, HomeScreen, CropFilterScreen, SuccessStoryScreen, and ExpertAskScreen. These screens interact with view models that store state and coordinate the data flow. The domain and data behavior is handled through repositories, local Room persistence, and Retrofit-based Gemini communication.

The system begins with a farmer using the app on a phone. The splash screen introduces the application and transitions into the home dashboard. The home view model gathers all crop tips from the repository and maintains the selected crop, district, and season. When the user selects a crop, the app updates the flash-card feed accordingly. When the user presses the generate advisory button, the application sends a concise prompt to Gemini and, if required, falls back to offline guidance from Room.

The AI Crop Expert screen forms the multimodal portion of the architecture. It binds CameraX to a preview, lets the user capture a crop image, converts that image into Base64, and sends it to Gemini. The repository then parses the response and returns either a structured diagnosis or an explicit error message. This makes the flow transparent and easier to debug than a silent fallback.

The architecture also includes local images. Flash cards do not depend on remote image URLs; instead, crop-specific images are mapped to local drawable resources through a resource name lookup. This design keeps the browsing experience consistent even without internet connectivity.

**Figure 3.1 Prompt:** Create a layered architecture diagram for the Raitha Varta Android app showing Farmer/User, Jetpack Compose UI, ViewModels, Repository, Room database, local crop images, Gemini API, CameraX, Coil, and Hilt dependency injection.

#### Fig 3.1: System architecture

The system architecture therefore supports three major operating modes:

1. Offline browsing mode.
   The user can open the app, swipe cards, filter crops, and read stories without a network connection.

2. Assisted AI advisory mode.
   The user can request a generated advisory using the selected crop, district, and season.

3. AI disease diagnosis mode.
   The user can capture a crop image and receive disease-oriented guidance when the model and API are available.

These modes are combined in a single mobile interface, making the app practical for daily use.

### 3.2 DATA FLOW

The data flow in Raitha Varta begins from local pre-seeded tips and flows into the UI through reactive Kotlin flows. The repository exposes all tips from the Room database, and the HomeViewModel transforms that data into available crops, districts, and seasons. The UI listens to the view model state and updates automatically when the selected crop changes.

When a user chooses a crop chip, the state changes and the flash-card list switches to the related crop entries. When the user selects a district or season, the advisory generation prompt uses those values to personalize the result. If Gemini responds successfully, the AI advisory card is displayed. If not, the offline advisory is shown instead.

For image analysis, the flow is slightly different. The user taps the camera preview or capture button. CameraX captures the frame, the bitmap is compressed into JPEG, and the byte array is converted to Base64. The repository sends the image and prompt to Gemini. The response is parsed into a final diagnosis string, or an explicit error is shown if the response is empty or the API returns an HTTP error.

**Figure 3.2 Prompt:** Create a data-flow diagram that shows how crop advice moves from local Room seed data and Gemini API into the Home screen, Crop Filter screen, Flash Card screen, and AI Crop Expert screen.

#### Fig 3.2: Data flow

The flow can be summarized as follows:

1. App starts and loads splash screen.
2. Room database provides seeded tips and local crop data.
3. Home screen displays the advisory hero and swipe cards.
4. User filters crop content with chips and selectors.
5. User opens AI Crop Expert and captures image.
6. Image is encoded and sent to Gemini.
7. AI result or offline fallback is displayed.
8. User continues browsing or switches screens using bottom navigation.

This flow ensures that the app remains responsive and understandable across both offline and AI-assisted use cases.

### 3.3 STATE DIAGRAM

The state behavior of the application is centered around screen navigation and analysis progress. The splash screen is the initial state. After a short animated wait, it transitions into the home dashboard. From the home screen, the user may move to crop library, success stories, or expert analysis. The expert screen itself also contains internal states such as permission request, preview ready, image capture in progress, analyzing, diagnosis complete, and analysis failed.

The state diagram for the AI Crop Expert is particularly important because it handles a real-time user interaction. When the camera permission is granted, the preview becomes active. On capture, the state switches to analyzing. If Gemini returns a valid answer, the app enters a success state and shows the diagnosis. If the request fails, the app enters a fallback state and explains the cause, such as a missing API key, quota limitation, or empty response.

**Figure 3.3 Prompt:** Create a state diagram for Raitha Varta showing splash, home, crop library, success stories, camera preview, analyzing, diagnosis complete, and offline fallback states.

#### Fig 3.3: State diagram

This state model improves reliability because it makes the user experience explicit. The user can always see whether the app is loading, waiting for a photo, processing an image, or returning advice.

### 3.4 USE CASE DIAGRAM

The primary actor in Raitha Varta is the farmer or user. The user can view the advisory feed, filter crop tips, open the success stories, and request AI analysis from a crop image. The application itself acts as the advisor and local data manager, while Gemini serves as the AI assistant used for generation and diagnosis.

The use cases show the system from a practical perspective. The farmer does not need to understand the underlying architecture; they only need to tap a crop card, browse results, or capture a photo. The app reduces complexity by packaging these actions in large cards and short labels.

**Figure 3.4 Prompt:** Create a use case diagram for Raitha Varta with a Farmer actor and system use cases such as view advisory cards, filter crops, read success stories, generate local advisory, and analyze crop disease image.

#### Fig 3.4: Use case diagram

The use case diagram highlights the app's farmer-centered behavior. It is intentionally simple because the app is designed for quick field use rather than complex workflows.

### 3.5 SEQUENCE DIAGRAM

The sequence diagram for the AI Crop Expert begins when the user opens the screen. The app requests camera permission if needed. Once permission is granted, the preview is shown and the user captures an image. The CameraX controller sends the bitmap to the view model, which compresses and encodes the image. The repository receives the Base64 image and calls Gemini. Gemini returns a diagnosis text, or the app captures the error and displays a clear fallback message.

The sequence diagram for the home advisory flow is similarly straightforward. The splash screen leads to the home screen, Room feeds the tips, the view model applies crop and location filters, and the Compose UI updates reactively. These two sequences demonstrate how the app combines local and cloud logic.

**Figure 3.5 Prompt:** Create a sequence diagram for Raitha Varta showing the user, CameraX preview, ExpertViewModel, TipRepository, Gemini API, and the final diagnosis display.

#### Fig 3.5: Sequence diagram

The design emphasizes short, clear request-response exchanges so the user receives value quickly.

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 19

# CHAPTER 4
## IMPLEMENTATION AND TESTING

### 4.1 HARDWARE REQUIREMENTS

The Raitha Varta application is intended to run on common Android smartphones and does not require specialized hardware. Because the app is designed for field use, it should be capable of running on entry-level and mid-range devices.

Processor: Any modern mobile processor with stable Android 8.0+ support.

RAM: 4 GB minimum, 6 GB or above recommended for smoother CameraX and Compose performance.

Storage: Sufficient local storage for app data, images, and Room database cache.

Input device: Touchscreen with camera support.

Output device: Mobile display with readable brightness in outdoor conditions.

Camera: Rear camera for crop image capture.

Network: Optional; required only for Gemini advisory and diagnosis features.

### 4.2 SOFTWARE REQUIREMENTS

#### 1. Mobile Application Development

Technology: Android Studio, Kotlin, Jetpack Compose

Architecture: MVVM with Repository pattern

UI Toolkit: Material 3 Compose

Navigation: Jetpack Navigation Compose

#### 2. Local Storage

Database: Room

Purpose: Store offline crop tips, daily tips, and cached advisory data

#### 3. Dependency Injection

Framework: Hilt

Purpose: Provide database, repository, and network dependencies cleanly

#### 4. API Communication

Library: Retrofit

Purpose: Send prompts and image data to the Gemini API

#### 5. Image Handling

Library: Coil

Purpose: Load local crop images in flash cards and support image presentation

#### 6. Camera Support

Library: CameraX

Purpose: Capture crop images for AI analysis

#### 7. Generative AI

Library: Gemini API client and REST request flow

Purpose: Generate crop advice and disease analysis

#### 8. Version Control and Build Tools

Language level: Java 11 compatibility in Gradle

Build system: Gradle Kotlin DSL

Versioning: Git and GitHub

### 4.3 SOURCE CODE

The following source snippets describe the core implementation approach used in the application.

#### i. Main Activity and Navigation

The main activity controls the app navigation and bottom bar. It starts with the splash screen and then moves to the home dashboard, crop library, success stories, and expert analysis screen.

```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaithaTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(onSplashFinished = {
                            navController.navigate("home") {
                                popUpTo("splash") { inclusive = true }
                            }
                        })
                    }
                    composable("home") { HomeScreen() }
                    composable("crops") { CropFilterScreen() }
                    composable("stories") { SuccessStoryScreen() }
                    composable("expert") { ExpertAskScreen() }
                }
            }
        }
    }
}
```

This implementation keeps navigation simple and readable while supporting a polished user experience.

#### ii. Home ViewModel

The HomeViewModel handles crop filters, district and season selection, local tips, and AI advisory generation.

```kotlin
fun generateTip() {
    val snapshot = uiState.value
    viewModelScope.launch {
        controls.update { it.copy(isGeneratingTip = true, insightMessage = null) }

        val crop = snapshot.selectedCrop.takeUnless { it == "All" }
            ?: dailyTip.value?.cropType
            ?: "Paddy"

        val aiResponse = repository.getGeminiTip(
            crop = crop,
            district = snapshot.selectedDistrict,
            season = snapshot.selectedSeason,
            apiKey = BuildConfig.GEMINI_API_KEY
        )

        val fallbackTip = repository.getOfflineTip(crop)
        val finalTip = aiResponse ?: fallbackTip?.let { "${it.title}. ${it.description}" }

        controls.update {
            it.copy(
                isGeneratingTip = false,
                aiTip = finalTip ?: "No advisory available at the moment.",
                insightMessage = if (aiResponse == null) {
                    "Showing offline advisory because the AI response was unavailable."
                } else {
                    "Fresh advisory generated for ${snapshot.selectedDistrict} in ${snapshot.selectedSeason}."
                }
            )
        }
    }
}
```

This logic ensures that the user still gets guidance even when the AI response is unavailable.

#### iii. Flash Card Item

The flash-card component loads a local crop image if no remote image URL exists. This allows the app to remain visual even when offline.

```kotlin
@Composable
fun FlashCardItem(tip: TipEntity, modifier: Modifier = Modifier) {
    val imageModel = tip.imageUrl?.takeIf { it.isNotBlank() }
        ?: localCropImageModel(tip.cropType)

    Card(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxWidth().weight(0.42f)) {
                AsyncImage(
                    model = imageModel,
                    contentDescription = tip.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
```

The card view is compact and readable, which is helpful in a farmer-facing application.

#### iv. AI Crop Expert Analysis

The expert screen captures the image, converts it to Base64, and sends it to Gemini for analysis.

```kotlin
private fun analyzeBitmap(bitmap: Bitmap) {
    viewModelScope.launch {
        statusMessage = "Analyzing image with AI..."
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        val base64Image = Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)

        val result = repository.analyzeDisease(base64Image, BuildConfig.GEMINI_API_KEY)
        val fallbackMessage = result.error ?: "Please retake the image in better light and try again."
        analysisResult = result.text ?: "AI analysis unavailable. $fallbackMessage"
    }
}
```

This approach gives clear feedback to the user rather than hiding the actual cause of failure.

#### v. Repository and Gemini Integration

The repository sends prompts to Gemini and returns either a direct response or an explicit error.

```kotlin
val prompt = buildString {
    append("You are an expert crop pathologist. Inspect the crop image and identify the most likely disease, pest, or nutrient deficiency from the image itself. ")
    append("Return exactly these lines: Disease: ..., Symptoms: ..., Treatment: ..., Fertilizer/Nutrient support: ....")
}
```

This prompt structure is important because the output must be actionable and easy to display in the UI.

### 4.4 TESTING APPROACH

The application was tested using both build validation and functional checks. Kotlin compilation was verified successfully after integrating the local image mapping, AI prompt improvements, and model endpoint updates. In addition to compile validation, the following areas were checked during manual inspection:

1. Splash screen transition behavior.
2. Home dashboard rendering and scroll interaction.
3. Crop chip filtering on the home and crop library screens.
4. Local crop image rendering in flash cards.
5. Camera permission flow in the expert screen.
6. Capture and AI analysis workflow.
7. Offline fallback behavior when Gemini data is not available.
8. Bottom navigation between the main screens.

#### Functional Testing Table

| Feature | Test Case | Expected Result | Status |
|---|---|---|---|
| App launch | Open app from launcher | Splash screen appears, then home opens | Pass |
| Offline tips | Disable network and open home | Local tips remain visible | Pass |
| Crop filtering | Select a crop chip | Flash cards update for selected crop | Pass |
| Local images | Open any flash card | Crop image displays from drawable resources | Pass |
| Advisory generation | Tap generate advisory | AI or offline advisory is shown | Pass |
| Camera permission | Open expert screen first time | Permission request appears | Pass |
| Image analysis | Tap camera preview/capture | Image is captured and sent for analysis | Pass |
| Error reporting | Force missing key or empty response | Specific message is shown | Pass |

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 32

# CHAPTER 5
## RESULTS AND DISCUSSIONS

### 5.1 DISCUSSION

Raitha Varta demonstrates how an Android application can combine offline agricultural knowledge and AI-powered image analysis in a single clean interface. The most important design choice in the application is the offline-first strategy. Instead of depending entirely on live API calls, the app stores crop guidance in Room and renders the content through swipeable cards. This means the app remains useful in areas where mobile internet is inconsistent.

The home screen gives the user immediate value. The daily tip card appears at the top, followed by crop filters and the advisory generator. The flash-card experience is intentionally visual and easy to scan. Each card uses a local crop image, a crop label, and a brief treatment explanation. This structure is helpful because it communicates with users who may prefer quick visual guidance rather than long paragraphs.

The AI Crop Expert is the most advanced part of the app. By combining CameraX and Gemini, the system allows the user to capture a crop image and request disease guidance. The prompt is structured so that the response is returned in short, practical lines that can be read quickly. The app also reports specific error conditions such as missing API keys, empty responses, or HTTP errors instead of returning a confusing generic message.

The success story section adds a human element to the app. It reminds the user that timely advisory tools can improve field outcomes. Even though the stories are simple, they support the app's overall educational tone.

**Figure 5.1 Prompt:** Capture the splash screen with the Raitha Varta logo, agriculture icon, and the offline-first crop advice tagline on the branded gradient background.

#### Fig 5.1: Splash screen

The splash screen gives the app a polished first impression and makes the transition into the dashboard feel deliberate.

**Figure 5.2 Prompt:** Capture the home screen showing the daily advisory hero card, crop chips, district and season selectors, generate advisory button, and the swipe flash cards with crop images.

#### Fig 5.2: Home screen

**Figure 5.3 Prompt:** Capture the crop library screen showing crop filter chips and several advisory cards with titles, descriptions, crop type, district, and season metadata.

#### Fig 5.3: Crop filter screen

**Figure 5.4 Prompt:** Capture the success story screen with farmer story cards, initials avatar circles, village names, crop labels, and yield improvement badges.

#### Fig 5.4: Success story screen

**Figure 5.5 Prompt:** Capture the AI Crop Expert screen showing the camera preview, capture button, loading overlay, and diagnosis card with disease, symptoms, treatment, and nutrient support.

#### Fig 5.5: AI Crop Expert screen

The visual design keeps the app readable in both bright and dark conditions. The theme colors provide strong contrast and a warm agricultural character. The Compose layout also helps the app adapt to different phone sizes without complex manual UI code.

### 5.2 RESULT ANALYSIS

The result analysis evaluates the effectiveness of the application across its major features. The app behaves as an offline-first advisory system, a crop browsing tool, and a camera-based diagnosis assistant.

Table 5.1: Functional Testing

| Module | Test Purpose | Expected Behavior | Result |
|---|---|---|---|
| Splash | Start the app | Animated splash then home dashboard | Success |
| Home advisory | Browse daily tip | Hero card and swipe cards visible | Success |
| Crop filtering | Filter by crop type | Card list updates by selection | Success |
| Offline mode | Disable network | Local content remains available | Success |
| Local images | Render flash cards | Crop images load from drawable resources | Success |
| Success stories | Open story page | Farmer cards display properly | Success |
| Camera permission | Open expert page | Permission request shown when needed | Success |
| AI diagnosis | Capture crop image | Gemini response or explicit error shown | Success |
| Error handling | Empty or invalid response | Clear fallback explanation shown | Success |

The results show that the application succeeds in meeting the main user expectations. It is stable, visually friendly, and practical for offline use. The local image strategy prevents blank cards, and the explicit AI error reporting helps debugging and improves transparency for the user.

The app also demonstrates extensibility. Since the UI is cleanly separated from the repository and database layers, future changes such as additional crops, more languages, richer diagnosis formatting, or new story content can be added without redesigning the whole application.

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 37

# CHAPTER 6
## CONCLUSION AND FUTURE SCOPE

### 6.1 CONCLUSION

Raitha Varta successfully delivers an offline-first agricultural advisory experience in a modern Android application. The project combines local Room-based crop tips, swipeable flash cards, crop-specific local images, story-based motivation, and an AI Crop Expert screen that uses CameraX and Gemini for image-based diagnosis. The result is an application that is useful even when the user is offline and more powerful when AI connectivity is available.

The project demonstrates the value of using Jetpack Compose for a responsive interface and MVVM with Hilt for maintainable architecture. The repository layer keeps local and remote logic separated, and the UI remains simple enough for practical farmer use. The use of structured AI prompts also improves the quality of responses by asking for disease, symptoms, treatment, and fertilizer support in a concise format.

In conclusion, the project meets its objective of creating a farmer-friendly mobile advisory tool that blends local content and generative AI. It provides a strong foundation for a production-ready agricultural application and an excellent base for future expansion.

### 6.2 FUTURE SCOPE

The future scope of Raitha Varta is broad because the current architecture is modular and extensible. Several meaningful enhancements can be added in future versions:

1. Multilingual voice support.
   Add text-to-speech and speech-to-text so farmers can interact with the app more naturally.

2. Weather-aware advisory.
   Integrate local weather APIs to tailor advice based on rainfall, temperature, and season.

3. Richer disease report formatting.
   Display AI results in separate cards for disease, symptoms, treatment, and fertilizer support.

4. Community and expert escalation.
   Allow users to share unclear cases with agricultural experts or a moderated farmer community.

5. Expanded crop library.
   Add more local crops, more images, and district-specific recommendations.

6. Personalized advisory history.
   Store previous advice and image analysis results so users can revisit earlier diagnoses.

7. Reminder and notification support.
   Notify users about spraying windows, irrigation intervals, or seasonal tasks.

8. Cloud sync option.
   Add an optional sync layer for users who want backup across devices.

9. Better diagnostics and caching.
   Cache successful AI responses for repeated image analysis and reduce duplicate API calls.

10. Deployment and field trials.
    Conduct pilot testing with farmers to collect feedback and refine the app for real-world agricultural use.

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 39

## REFERENCES

[1] Google, Android Developers Documentation, Jetpack Compose, Room, Hilt, CameraX, and Navigation Compose official guides.

[2] Google AI, Gemini API Documentation and Multimodal Prompting Guide, 2025.

[3] Android Developers, Room Persistence Library and Offline-First App Architecture documentation.

[4] Android Developers, CameraX and ImageCapture documentation for mobile image acquisition.

[5] Crop advisory references and agricultural extension material relevant to Karnataka farming conditions.

[6] Kotlin language reference and Jetpack Compose Material 3 design documentation.

RAITHA VARTA: OFFLINE-FIRST CROP ADVISORY AND AI DISEASE DIAGNOSIS MOBILE APPLICATION 21INT82
Dept. of CSE, DBIT 2024-2025 Page 40

CERTIFICATE AND MONTHLY REPORT

MONTHLY REPORT

Month  Topics Covered  Tasks Completed  Skills Gained

Oct 2024  Android Studio setup, Kotlin basics, Compose introduction, project planning  Created project skeleton, theme, navigation structure, and splash screen concept  Kotlin syntax, Compose UI basics, project organization

Nov 2024  Room database, offline data design, state flows, Hilt dependency injection  Seeded crop tips, built repository flow, added local advisory loading  Persistence design, MVVM thinking, dependency injection

Dec 2024  CameraX preview, image capture, Gemini prompt design, Retrofit integration  Built AI Crop Expert flow, encoded images to Base64, refined AI prompts  Camera handling, API integration, structured prompt writing

Jan 2025  UI polishing, local drawable images, crop cards, success stories, testing  Added local crop visuals, cleaned card layout, validated navigation and fallback behavior  UI refinement, resource mapping, debugging and testing

Feb 2025  Final documentation, result analysis, report preparation, demo readiness  Completed final build checks, prepared internship report, organized screenshots and figure prompts  Technical writing, presentation readiness, project closure

The monthly report above summarizes the learning journey behind the project. It reflects the gradual move from basic Android setup to offline storage, AI integration, and final polishing.
