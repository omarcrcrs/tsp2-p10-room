plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.p10room"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.p10room"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ViewModel en Compose: viewModel() y viewModelFactory { initializer { } }
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // collectAsStateWithLifecycle(): deja de escuchar la BD cuando la app pasa a segundo plano
    implementation(libs.androidx.lifecycle.runtime.compose)
    // Navegación entre pantallas (NavHost, composable, navArgument)
    implementation(libs.androidx.navigation.compose)

    // Room: ORM sobre SQLite. room-compiler corre con KSP y genera el código del DAO.
    // (Desde Room 2.7 el soporte de corrutinas y Flow vive en room-runtime;
    //  room-ktx se deja igual que en el material del curso.)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
