plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.ecommerceapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ecommerceapp"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)



    //Navigation component
    val nav_version = "2.5.2"
    implementation ("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation ( "androidx.navigation:navigation-ui-ktx:$nav_version")

    // https://mvnrepository.com/artifact/br.com.simplepass/loading-button-android
    implementation("com.github.leandroborgesferreira:loading-button-android:2.3.0")


    //Glide
    implementation ("com.github.bumptech.glide:glide:4.13.0")

    //circular image
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // https://mvnrepository.com/artifact/io.github.vejei.viewpagerindicator/viewpagerindicator
//    implementation("io.github.vejei.viewpagerindicator:viewpagerindicator:1.0.0-alpha.1")


    // https://mvnrepository.com/artifact/com.shuhart.stepview/stepview
//    implementation("com.shuhart.stepview:stepview:1.5.1")


    //Android Ktx
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")

    //Dagger hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")


    //Coroutines with firebase
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")

//    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.11")

}