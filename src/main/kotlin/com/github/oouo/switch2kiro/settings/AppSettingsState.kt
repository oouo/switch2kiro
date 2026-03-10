package com.github.oouo.switch2kiro.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import java.io.File

@State(
    name = "com.github.oouo.switch2kiro.settings.AppSettingsState",
    storages = [Storage("Switch2KiroSettings.xml")]
)
class AppSettingsState : PersistentStateComponent<AppSettingsState> {
    var kiroPath: String = detectDefaultKiroPath()

    override fun getState(): AppSettingsState = this

    override fun loadState(state: AppSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
        // If saved path is the bare "kiro" default and it's not actually on PATH, re-detect
        if (kiroPath == "kiro" && !isExecutableOnPath("kiro")) {
            kiroPath = detectDefaultKiroPath()
        }
    }

    companion object {
        fun getInstance(): AppSettingsState =
            ApplicationManager.getApplication().getService(AppSettingsState::class.java)

        private fun isExecutableOnPath(name: String): Boolean {
            return try {
                val os = System.getProperty("os.name").lowercase()
                val cmd = if (os.contains("windows")) arrayOf("where.exe", name) else arrayOf("which", name)
                val process = ProcessBuilder(*cmd).start()
                process.waitFor() == 0
            } catch (_: Exception) {
                false
            }
        }

        fun detectDefaultKiroPath(): String {
            // First check if "kiro" is on PATH
            if (isExecutableOnPath("kiro")) return "kiro"

            val os = System.getProperty("os.name").lowercase()
            val candidates = when {
                os.contains("windows") -> listOf(
                    System.getenv("LOCALAPPDATA")?.let { "$it\\Programs\\Kiro\\Kiro.exe" },
                    System.getenv("LOCALAPPDATA")?.let { "$it\\Programs\\kiro\\Kiro.exe" },
                    "C:\\Program Files\\Kiro\\Kiro.exe",
                    "C:\\Program Files (x86)\\Kiro\\Kiro.exe"
                )
                os.contains("mac") -> listOf(
                    "/Applications/Kiro.app/Contents/MacOS/Kiro",
                    System.getenv("HOME")?.let { "$it/Applications/Kiro.app/Contents/MacOS/Kiro" }
                )
                else -> listOf(
                    "/usr/bin/kiro",
                    "/usr/local/bin/kiro",
                    "/snap/bin/kiro",
                    System.getenv("HOME")?.let { "$it/.local/bin/kiro" }
                )
            }

            return candidates
                .filterNotNull()
                .firstOrNull { File(it).exists() }
                ?: "kiro"
        }
    }
}
