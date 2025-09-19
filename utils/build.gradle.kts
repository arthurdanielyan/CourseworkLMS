plugins {
    alias(libs.plugins.lms.android.library)
}

android {
    namespace = "com.coursework.utils"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.koin.core)
}
