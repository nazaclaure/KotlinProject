import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "edu.ucb.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "edu.ucb.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "GLOBAL_PROVIDER", "\"CalyrSoft\"")
    }

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            val appName = "\"app-dev\""
            val baseUrl = "\"https://dev.api.calyrsoft.com\""
            applicationIdSuffix = ".dev"

            buildConfigField("String", "APP_NAME", appName)
            buildConfigField("String", "BASE_URL", baseUrl)
        }
        create("prod") {
            dimension = "environment"
            val appName = "\"app-prod\""
            val baseUrl = "\"http://prod.api.calyrsoft.com\""
            applicationIdSuffix = ".app"

            buildConfigField("String", "APP_NAME", appName)
            buildConfigField("String", "BASE_URL", baseUrl)
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        resValues = true
        buildConfig = true
    }
}