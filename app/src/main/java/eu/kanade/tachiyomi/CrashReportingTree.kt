package eu.kanade.tachiyomi

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR) {
            FirebaseCrashlytics.getInstance().log(message)
            if (throwable != null) {
                FirebaseCrashlytics.getInstance().recordException(throwable)
            }
        } else return
    }
}
