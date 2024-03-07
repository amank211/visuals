import com.android.sdklib.computeReleaseNameAndDetails
import org.gradle.internal.impldep.org.eclipse.jgit.lib.InflaterCache.release

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.pie.visuals"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
        version = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    productFlavors {
        register("pie") {
            aarMetadata {
                minCompileSdk = 30
            }
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications {
        create<MavenPublication>("visuals") {
            groupId = "com.github.amank211"
            artifactId = "visuals"
            version = "1.0"
            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
        }
    }

    repositories {
        maven {
            name = "visuals"
            url = uri(layout.buildDirectory.dir("visuals"))
        }
    }
}
