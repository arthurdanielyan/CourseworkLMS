plugins {
    alias(libs.plugins.lms.android.library)
    alias(libs.plugins.lms.uses.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.coursework.featureSearchBooks"
}

dependencies {

    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.navigation.compose) // TODO: remove
    implementation(libs.kotlinx.serialization.json)

    implementation(projects.corePresentation)
    implementation(projects.utils)
    implementation(projects.domain)
    implementation(projects.featureBookDetails)
    implementation(projects.featureEditBook)
}