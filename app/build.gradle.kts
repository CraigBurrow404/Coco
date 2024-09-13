plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.burrow.sensorActivity2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.burrow.sensorActivity2"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    implementation(libs.android.daggerhilt)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.paging.compose.android)
    implementation(libs.transportation.consumer)

    annotationProcessor (libs.androidx.room.runtime)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.datastore.preferences.core.jvm)
    implementation(libs.androidx.hilt)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    //implementation(libs.androidx.room.compiler)
    implementation(libs.androidx.room.guava)
    implementation(libs.androidx.room.paging)
    implementation(libs.androidx.room.runtime)
    //implementation(libs.androidx.room.rxjava2)
    //implementation(libs.androidx.room.rxjava3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.room.ktx)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter)
    implementation(libs.coil)
    implementation(libs.accompanist)
    implementation (libs.android.daggerhilt)

    // For instrumentation tests
    androidTestImplementation  (libs.hilt.android.testing)
    androidTestAnnotationProcessor (libs.google.daggerhilt.compiler)

    // For local unit tests
    testImplementation (libs.hilt.android.testing)
    testAnnotationProcessor (libs.google.daggerhilt.compiler)
    //annotationProcessor (libs.google.daggerhilt.compiler)

    testImplementation (libs.androidx.room.testing)
    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    ksp (libs.androidx.room.compiler)
    ksp (libs.google.daggerhilt.compiler)
}