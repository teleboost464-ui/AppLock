package dev.pranav.applock.features.applist.domain

import android.content.pm.ApplicationInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppListModeTest {
    @Test
    fun essentialRequiresLauncherActivityRegardlessOfInstallSource() {
        assertTrue(AppListMode.ESSENTIAL.includes(0, hasLauncherActivity = true))
        assertTrue(AppListMode.ESSENTIAL.includes(ApplicationInfo.FLAG_SYSTEM, true))
        assertFalse(AppListMode.ESSENTIAL.includes(ApplicationInfo.FLAG_SYSTEM, false))
        assertFalse(AppListMode.ESSENTIAL.includes(0, hasLauncherActivity = false))
    }

    @Test
    fun installedIncludesUserAppsWithOrWithoutLauncherActivity() {
        assertTrue(AppListMode.INSTALLED.includes(0, hasLauncherActivity = true))
        assertTrue(AppListMode.INSTALLED.includes(0, hasLauncherActivity = false))
        assertFalse(AppListMode.INSTALLED.includes(ApplicationInfo.FLAG_SYSTEM, true))
        assertFalse(AppListMode.INSTALLED.includes(ApplicationInfo.FLAG_UPDATED_SYSTEM_APP, true))
    }

    @Test
    fun systemIncludesPreinstalledAppsAndTheirUpdates() {
        assertTrue(AppListMode.SYSTEM.includes(ApplicationInfo.FLAG_SYSTEM, false))
        assertTrue(AppListMode.SYSTEM.includes(ApplicationInfo.FLAG_UPDATED_SYSTEM_APP, true))
        assertFalse(AppListMode.SYSTEM.includes(0, hasLauncherActivity = false))
    }

    @Test
    fun savedModesRoundTripAndUnknownValuesDefaultToEssential() {
        AppListMode.entries.forEach { mode ->
            assertEquals(mode, AppListMode.fromPreference(mode.name))
        }
        assertEquals(AppListMode.ESSENTIAL, AppListMode.fromPreference(null))
        assertEquals(AppListMode.ESSENTIAL, AppListMode.fromPreference("UNKNOWN"))
    }
}
