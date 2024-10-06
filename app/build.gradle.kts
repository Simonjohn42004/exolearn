plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.exolearnapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.exolearnapp"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //CSV Reader
    implementation (libs.opencsv)
    implementation (libs.commons.csv)

    //Animation
    implementation (libs.lottie)

    //Flexbox Layout for responsiveness in app
    implementation (libs.flexbox)

    //Recycler View animation
    implementation (libs.recyclerview.animators)

    //Firebase BOM
    implementation(platform(libs.firebase.bom))

    //Firebase dependencies
    //analytics
    implementation(libs.firebase.analytics)

    //Authentication
    implementation(libs.firebase.auth)

    //FireStore
    implementation(libs.firebase.firestore)

    //Splash Screen
    implementation(libs.core.splashscreen)

    //Google Material
    implementation (libs.material)



}