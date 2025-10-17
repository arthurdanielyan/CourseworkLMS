plugins {
    alias(libs.plugins.lms.android.library)
}

android {
    namespace = "com.coursework.data"

}

dependencies {

    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.paging.runtime.ktx)

    implementation(projects.domain)
}