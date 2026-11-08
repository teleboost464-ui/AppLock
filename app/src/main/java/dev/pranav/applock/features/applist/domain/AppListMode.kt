package dev.pranav.applock.features.applist.domain

import android.content.pm.ApplicationInfo

enum class AppListMode {
    ESSENTIAL,
    INSTALLED,
    SYSTEM;

    fun includes(flags: Int, hasLauncherActivity: Boolean): Boolean {
        val systemFlags = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
        val isSystem = (flags and systemFlags) != 0
        return when (this) {
            ESSENTIAL -> hasLauncherActivity
            INSTALLED -> !isSystem
            SYSTEM -> isSystem
        }
    }

    companion object {
        fun fromPreference(value: String?): AppListMode =
            entries.firstOrNull { it.name == value } ?: ESSENTIAL
    }
}
