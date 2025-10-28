plugins {
    alias(libs.plugins.lms.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.coursework.data"

}

dependencies {

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.paging.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    implementation(projects.utils)
    implementation(projects.domain)
}