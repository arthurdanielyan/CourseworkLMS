plugins {
    alias(libs.plugins.lms.android.library)
    alias(libs.plugins.lms.uses.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.coursework.corePresenation"
}

dependencies {

    api(libs.androidx.core.ktx)
    api(libs.androidx.compose.runtime)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.material3)
    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.coil.compose)

    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.navigation.compose)
}
