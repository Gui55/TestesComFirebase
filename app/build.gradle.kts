import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.appmaroto"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appmaroto"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    val properties = Properties()
    val urlFile = file("../url.properties")
    if (urlFile.exists()) {
        properties.load(FileInputStream(urlFile))
    }
    val baseUrl = properties.getProperty("BASE_URL")

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        }
    }

    buildFeatures{
        viewBinding = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    // Koin for Android
    implementation(libs.koin.android)
    // or Koin for Compose (unstable version)
    implementation(libs.koin.androidx.compose)
    implementation(libs.firebase.ui.auth)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.inappmessaging.display)

    implementation(libs.firebase.firestore.ktx)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

}