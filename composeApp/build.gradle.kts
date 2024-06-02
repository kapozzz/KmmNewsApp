import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.pluginSerialization)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Ktor
            implementation("io.ktor:ktor-client-okhttp:2.3.11")

        }
        commonMain.dependencies {

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Ktor
            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.11")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.11")
            implementation("io.ktor:ktor-client-logging:2.3.11")

            // Kotlin serialization
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

            // Voyager
            implementation("cafe.adriel.voyager:voyager-navigator:1.0.0")
            implementation("cafe.adriel.voyager:voyager-screenmodel:1.0.0")
            implementation("cafe.adriel.voyager:voyager-transitions:1.0.0")
            implementation("cafe.adriel.voyager:voyager-koin:1.0.0")

            // Koin
            implementation(platform("io.insert-koin:koin-bom:3.5.6"))
            implementation("io.insert-koin:koin-core")

            // Kamel for media
            implementation("media.kamel:kamel-image:0.9.5")

        }
        iosMain.dependencies {

            implementation("io.ktor:ktor-client-darwin:2.3.11")

        }
        nativeMain.dependencies {

            implementation("io.ktor:ktor-client-darwin:2.3.11")

        }
    }
}

android {
    namespace = "org.kapozzz.news"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.kapozzz.news"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


