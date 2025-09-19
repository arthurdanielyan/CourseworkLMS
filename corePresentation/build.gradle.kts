plugins {
    alias(libs.plugins.lms.android.library)
    alias(libs.plugins.lms.uses.compose)
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

    implementation(libs.koin.core)
}
