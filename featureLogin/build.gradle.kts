plugins {
    alias(libs.plugins.lms.android.library)
    alias(libs.plugins.lms.uses.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.coursework.featurelogin"
}

dependencies {

    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)

    implementation(projects.corePresentation)
    implementation(projects.utils)
}