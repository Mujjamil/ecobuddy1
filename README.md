# 🌍 EcoBuddy

EcoBuddy is an Android application designed to promote eco-friendly habits by visualizing and tracking your daily transportation choices and physical activity — all in one beautiful, interactive interface.

---

## ✨ Features

✅ **Donut Chart Visualization** Displays the proportional breakdown of transport modes (Walking, Bike, Public Transport) in a clear and modern Pie Chart using MPAndroidChart.

🚶‍♂️ **Daily Distance Tracking** Track distances covered for each transportation type: walking, biking, and public transit.

�� **Google Fit Integration** Retrieve real-time step count directly from Google Fit for accurate and effortless walking data tracking.

💡 **User-Friendly Interface** Minimal, elegant design with intuitive navigation, day selectors, and smooth transitions.

🧑‍💻 **Modular Codebase** Easily extend the application to support new features like rewards, challenges, or CO₂ emission savings.

---

## 🧑‍💻 Getting Started

### 🔧 Prerequisites

- 💻 Android Studio installed.
- 📱 Android device (or emulator) running **Android 10 (API Level 29)** or higher.
- 🧑‍💼 Google Account to enable Google Fit API.

---

### 📥 Installation

1️⃣ **Clone the Repository:**

```bash
git clone https://github.com/Mujjamil/ecobuddy1.git  
cd ecobuddy1
```

---

2️⃣ **Open in Android Studio:**

- Open Android Studio.
- Go to: `File > Open`.
- Select your `ecobuddy1` project folder.

---

3️⃣ **Sync Gradle:**

- Android Studio will detect the `build.gradle` files.
- Click **Sync Now** on the top bar.
- Wait until dependencies are resolved successfully.

---

## ⚙️ Google Fit API Integration

### 🧹 Step 1: Add Dependencies

In your `app/build.gradle`:

```gradle
dependencies {
    implementation 'com.google.android.gms:play-services-fitness:21.1.0'
}
```

---

### 🗂️ Step 2: Update AndroidManifest.xml

Add these permissions inside the `<manifest>` block:

```xml
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

For Android 10+ you must also request `ACTIVITY_RECOGNITION` runtime permission.

---

### ☁️ Step 3: Configure Google Cloud Project

1. Go to: [Google Cloud Console](https://console.cloud.google.com/).
2. Create or select a project.
3. Enable the **Fitness API** in "APIs & Services".
4. Go to "OAuth Consent Screen" and set your app name and scopes.
5. Create OAuth 2.0 credentials for Android:

- Add your **Package Name** (e.g., `com.yourdomain.ecobuddy`).
- Add your **SHA-1 Certificate Fingerprint**.

---

### 💻 Step 4: Implement Google Fit in Java

1. Create `FitnessOptions`:

```java
FitnessOptions fitnessOptions = FitnessOptions.builder()
    .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
    .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
    .build();
```

2. Check and Request Permissions:

```java
if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
    GoogleSignIn.requestPermissions(
        this,
        1001, // Request Code
        GoogleSignIn.getLastSignedInAccount(this),
        fitnessOptions
    );
} else {
    accessGoogleFit();
}
```

3. Handle Sign-In Result:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1001 && resultCode == RESULT_OK) {
        accessGoogleFit();
    }
}
```

4. Access Step Data:

```java
private void accessGoogleFit() {
    GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);

    Fitness.getHistoryClient(this, account)
        .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
        .addOnSuccessListener(dataSet -> {
            int totalSteps = dataSet.isEmpty() ? 0 :
                dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
            Log.d("Steps", "Total steps: " + totalSteps);
            // Update UI element here
        })
        .addOnFailureListener(e -> Log.e("Steps", "Failed to read steps.", e));
}
```

---

## 📸 Screenshots

| Main Dashboard | Step Tracking Integration | Donut Chart Visualization |
| -------------- | ------------------------- | ------------------------- |
|                |                           |                           |

> 💡 *Place your screenshots inside a **`/screenshots/`** folder and update the filenames here accordingly.*

---

## 💖 Acknowledgments

📊 Special thanks to [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) for charting components.\
🏃‍♂️ Thanks to [Google Fit API](https://developers.google.com/fit) for real-time fitness data.

---

## 📜 License

*(Add your license info here, e.g., MIT, Apache 2.0, etc.)*

# 🌍 EcoBuddy

EcoBuddy is an Android application designed to promote eco-friendly habits by visualizing and tracking your daily transportation choices and physical activity — all in one beautiful, interactive interface.

---

## ✨ Features

✅ **Donut Chart Visualization** Displays the proportional breakdown of transport modes (Walking, Bike, Public Transport) in a clear and modern Pie Chart using MPAndroidChart.

🚶‍♂️ **Daily Distance Tracking** Track distances covered for each transportation type: walking, biking, and public transit.

�� **Google Fit Integration** Retrieve real-time step count directly from Google Fit for accurate and effortless walking data tracking.

💡 **User-Friendly Interface** Minimal, elegant design with intuitive navigation, day selectors, and smooth transitions.

🧑‍💻 **Modular Codebase** Easily extend the application to support new features like rewards, challenges, or CO₂ emission savings.

---

## 🧑‍💻 Getting Started

### 🔧 Prerequisites

- 💻 Android Studio installed.
- 📱 Android device (or emulator) running **Android 10 (API Level 29)** or higher.
- 🧑‍💼 Google Account to enable Google Fit API.

---

### 📥 Installation

1️⃣ **Clone the Repository:**

```bash
git clone https://github.com/Mujjamil/ecobuddy1.git  
cd ecobuddy1
```

---

2️⃣ **Open in Android Studio:**

- Open Android Studio.
- Go to: `File > Open`.
- Select your `ecobuddy1` project folder.

---

3️⃣ **Sync Gradle:**

- Android Studio will detect the `build.gradle` files.
- Click **Sync Now** on the top bar.
- Wait until dependencies are resolved successfully.

---

## ⚙️ Google Fit API Integration

### 🧹 Step 1: Add Dependencies

In your `app/build.gradle`:

```gradle
dependencies {
    implementation 'com.google.android.gms:play-services-fitness:21.1.0'
}
```

---

### 🗂️ Step 2: Update AndroidManifest.xml

Add these permissions inside the `<manifest>` block:

```xml
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

For Android 10+ you must also request `ACTIVITY_RECOGNITION` runtime permission.

---

### ☁️ Step 3: Configure Google Cloud Project

1. Go to: [Google Cloud Console](https://console.cloud.google.com/).
2. Create or select a project.
3. Enable the **Fitness API** in "APIs & Services".
4. Go to "OAuth Consent Screen" and set your app name and scopes.
5. Create OAuth 2.0 credentials for Android:

- Add your **Package Name** (e.g., `com.yourdomain.ecobuddy`).
- Add your **SHA-1 Certificate Fingerprint**.

---

### 💻 Step 4: Implement Google Fit in Java

1. Create `FitnessOptions`:

```java
FitnessOptions fitnessOptions = FitnessOptions.builder()
    .addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE, FitnessOptions.ACCESS_READ)
    .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
    .build();
```

2. Check and Request Permissions:

```java
if (!GoogleSignIn.hasPermissions(GoogleSignIn.getLastSignedInAccount(this), fitnessOptions)) {
    GoogleSignIn.requestPermissions(
        this,
        1001, // Request Code
        GoogleSignIn.getLastSignedInAccount(this),
        fitnessOptions
    );
} else {
    accessGoogleFit();
}
```

3. Handle Sign-In Result:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1001 && resultCode == RESULT_OK) {
        accessGoogleFit();
    }
}
```

4. Access Step Data:

```java
private void accessGoogleFit() {
    GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(this, fitnessOptions);

    Fitness.getHistoryClient(this, account)
        .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
        .addOnSuccessListener(dataSet -> {
            int totalSteps = dataSet.isEmpty() ? 0 :
                dataSet.getDataPoints().get(0).getValue(Field.FIELD_STEPS).asInt();
            Log.d("Steps", "Total steps: " + totalSteps);
            // Update UI element here
        })
        .addOnFailureListener(e -> Log.e("Steps", "Failed to read steps.", e));
}
```

---

---

## 💖 Acknowledgments

📊 Special thanks to [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) for charting components.\
🏃‍♂️ Thanks to [Google Fit API](https://developers.google.com/fit) for real-time fitness data.

---


