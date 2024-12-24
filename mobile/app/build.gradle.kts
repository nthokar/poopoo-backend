plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "1.4.32"
}

android {
    namespace = "ru.raperan.poopoo"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.raperan.poopoo"
        minSdk = 24
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
            signingConfig = signingConfigs.getByName("debug")
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation ("androidx.compose.material3:material3:1.1.0-alpha04")
    implementation ("androidx.compose.material:material-icons-extended:1.5.4")

    implementation("io.ktor:ktor-client-serialization:2.3.0") // Сериализация
    implementation("io.ktor:ktor-client-android:2.3.0")

    implementation ("io.ktor:ktor-client-logging:2.3.1")  // Для логирования запросов (необязательно)
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0") // Для HTTP клиента
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("io.ktor:ktor-serialization:2.3.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1") // или актуальная версия

    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.1.0")
    implementation("androidx.compose.material:material:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("com.github.javafaker:javafaker:1.0.2")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("androidx.compose.ui:ui:1.3.0")
    implementation("androidx.compose.foundation:foundation:1.3.0")
    implementation("androidx.compose.material3:material3:1.0.0")
    implementation("androidx.compose.material:material:1.3.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}