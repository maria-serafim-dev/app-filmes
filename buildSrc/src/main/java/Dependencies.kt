object Android{
    object Versions {
        const val core = "1.8.0"
        const val appCompat = "1.4.2"
        const val lifecycle = "2.4.1"
    }

    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object Lifecycle{
    object Versions {
        const val lifecycle = "2.5.1"
    }

    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val viewModelSavedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
}

object View{
    object Versions {
        const val material = "1.6.1"
        const val constraint = "2.1.4"
        const val viewpager2 = "1.0.0"
        const val splashscreen = "1.0.0"
        const val drawerlayout = "1.1.1"
        const val recyclerview = "1.2.1"
    }

    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"
    const val drawerlayout = "androidx.drawerlayout:drawerlayout:${Versions.drawerlayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"

}

object UnitTest {
    object Versions {
        const val junit = "4.13.2"
        const val ext = "1.1.3"
        const val espresso = "3.4.0"
    }

    const val junit = "junit:junit:${Versions.junit}"
    const val ext = "androidx.test.ext:junit:${Versions.ext}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Navigation {
    object Versions {
        const val core = "2.5.1"
    }

    const val core = "androidx.navigation:navigation-ui-ktx:${Versions.core}"
    const val fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.core}"
}

object Retrofit {
    object Versions {
        const val retrofit = "2.9.0"
    }

    const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object OkHttp {
    object Versions {
        const val okhttp = "4.9.3"
    }

    const val core = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
}


object Picasso {
    object Versions {
        const val picasso = "2.71828"
    }

    const val core = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Facebook {
    object Versions {
        const val facebook = "8.2.0"
    }

    const val sdk = "com.facebook.android:facebook-android-sdk:${Versions.facebook}"
}

object Google {
    object Versions {
        const val google = "20.2.0"
    }

    const val auth = "com.google.android.gms:play-services-auth:${Versions.google}"
}

object Firebase {
    object Versions {
        const val bom = "29.1.0"
    }

    const val bom = "com.google.firebase:firebase-bom:${Versions.bom}"
    const val database = "com.google.firebase:firebase-database"
    const val auth = "com.google.firebase:firebase-auth-ktx"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    const val perf = "com.google.firebase:firebase-perf-ktx"
}

object Coroutines {
    object Versions {
        const val coroutines = "1.6.1"
    }

    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val playServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
}

object Canarinho {
    object Versions {
        const val canarinho = "v2.0.1"
    }

    const val core = "com.github.concretesolutions:canarinho:${Versions.canarinho}"
}
