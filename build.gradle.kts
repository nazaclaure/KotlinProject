plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidMultiplatformLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.wire) apply false
}

allprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "org.jetbrains.androidx.navigation" || requested.group == "androidx.navigation") {
                useVersion("2.9.2")
            }
        }
    }
}
// Wire trae OkHttp 5.5.0 (pide compileSdk 37). Se fija una version compatible con compileSdk 36.
subprojects {
    configurations.all {
        resolutionStrategy.eachDependency {
            if (requested.group == "com.squareup.okhttp3") {
                useVersion("5.1.0")
            }
        }
    }
}
