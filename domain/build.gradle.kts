plugins {
    alias(libs.plugins.lms.android.library)
}

android {
    namespace = "com.coursework.domain"

}

dependencies {

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.paging.runtime.ktx)
}