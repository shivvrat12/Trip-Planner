import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}


val properties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    properties.load(localPropertiesFile.inputStream())
}

val mapsApiKey: String = properties.getProperty("MAPS_API_KEY", "YOUR_REAL_API_KEY")

android {
    namespace = "com.xo.tripplanner"
    println("MAPS_API_KEY in Gradle: $mapsApiKey") // Debugging - prints the key during the build
    compileSdk = 35

    defaultConfig {
        applicationId = "com.xo.tripplanner"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        resValue("string", "maps_api_key", mapsApiKey)
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    //Material3
    implementation ("androidx.compose.material:material:1.7.0")
    implementation ("androidx.compose.material:material-icons-extended:1.7.0")
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.storage)

    //Google Maps
    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    //Google Places
    implementation ("com.google.android.libraries.places:places:4.1.0")



    //navigation
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //preferencesDatastore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.5.0")
    implementation ("com.squareup.okhttp3:okhttp:3.8.1")


    //Coil
    implementation("io.coil-kt:coil:2.6.0")
    implementation("io.coil-kt:coil-compose:2.6.0")


    //ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}