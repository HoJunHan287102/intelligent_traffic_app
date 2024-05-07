plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.loginscreen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.loginscreen"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        mlModelBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.6.0")
    implementation("androidx.navigation:navigation-ui:2.6.0")
    implementation("com.google.firebase:firebase-auth:22.3.0")
    implementation("com.google.firebase:firebase-database:20.3.1")
    // CameraX dependencies
//    implementation("androidx.camera:camera-camera2:1.1.0-alpha05")
//    implementation("androidx.camera:camera-lifecycle:1.1.0-alpha05")
//    implementation("androidx.camera:camera-core:1.1.0-alpha05")
    implementation("org.tensorflow:tensorflow-lite-support:0.1.0")
    implementation("org.tensorflow:tensorflow-lite-metadata:0.1.0")
    implementation ("com.google.android.gms:play-services-mlkit-text-recognition:19.0.0")
    // CameraX core library using camera2 implementation
//    implementation ("androidx.camera:camera-camera2:1.0.0-beta08")
    // CameraX Lifecycle Library
//    implementation ("androidx.camera:camera-lifecycle:1.0.0-beta08")
    // CameraX View class
//    implementation ("androidx.camera:camera-view:1.0.0-alpha15")
//    implementation ("androidx.camera:camera-core:1.2.2")
//    implementation ("androidx.camera:camera-camera2:1.2.2")
//    implementation ("androidx.camera:camera-lifecycle:1.2.2")
//    implementation ("androidx.camera:camera-view:1.2.2")
//    implementation("com.theartofdev.edmodo:android-image-cropper:2.8.0")
//    implementation("com.google.android.gms:play-services-vision:19.0.0")
    implementation("com.google.firebase:firebase-ml-vision:24.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}