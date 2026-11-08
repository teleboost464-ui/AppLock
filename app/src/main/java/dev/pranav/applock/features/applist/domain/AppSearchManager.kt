package dev.pranav.applock.features.applist.domain

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppSearchManager(private val context: Context) {
    data class Apps(
        val installed: List<ApplicationInfo> = emptyList(),
        val launchablePackages: Set<String> = emptySet()
    )

    suspend fun loadApps(): Apps = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val launcherIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        Apps(
            installed = packageManager.getInstalledApplications(0)
                .filter { it.packageName != context.packageName },
            launchablePackages = packageManager.queryIntentActivities(launcherIntent, 0)
                .mapTo(mutableSetOf()) { it.activityInfo.packageName }
        )
    }
}
