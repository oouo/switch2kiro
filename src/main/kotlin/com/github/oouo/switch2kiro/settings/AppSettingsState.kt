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
        if (kiroPath == "kiro" && !isOnPath("kiro")) {
            kiroPath = detectDefaultKiroPath()
        }
    }

    companion object {
        fun getInstance(): AppSettingsState =
            ApplicationManager.getApplication().getService(AppSettingsState::class.java)

        fun isOnPath(name: String): Boolean {
            return try {
                val os = System.getProperty("os.name").lowercase()
                val cmd = if (os.contains("windows")) arrayOf("where.exe", name) else arrayOf("which", name)
                ProcessBuilder(*cmd).start().waitFor() == 0
            } catch (_: Exception) {
                false
            }
        }

        fun detectDefaultKiroPath(): String {
            if (isOnPath("kiro")) return "kiro"

            val os = System.getProperty("os.name").lowercase()
            return when {
                os.contains("windows") -> detectWindows()
                os.contains("mac") -> detectMac()
                else -> detectLinux()
            }
        }

        private fun detectWindows(): String {
            // 1. Query Windows registry for Kiro install location
            val regPath = queryWindowsRegistry()
            if (regPath != null) return regPath

            // 2. Check common install locations
            val localAppData = System.getenv("LOCALAPPDATA")
            val candidates = mutableListOf<String>()
            if (localAppData != null) {
                candidates.add("$localAppData\\Programs\\Kiro\\Kiro.exe")
                candidates.add("$localAppData\\Programs\\kiro\\Kiro.exe")
            }
            candidates.add("C:\\Program Files\\Kiro\\Kiro.exe")
            candidates.add("C:\\Program Files (x86)\\Kiro\\Kiro.exe")

            // 3. Scan all drive letters
            for (drive in 'A'..'Z') {
                val driveRoot = "$drive:\\"
                if (File(driveRoot).exists()) {
                    candidates.add("${drive}:\\Program Files\\Kiro\\Kiro.exe")
                    candidates.add("${drive}:\\Kiro\\Kiro.exe")
                }
            }

            return candidates.firstOrNull { File(it).exists() } ?: "kiro"
        }

        private fun queryWindowsRegistry(): String? {
            return try {
                val regRoots = listOf(
                    "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall",
                    "HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall"
                )
                for (regRoot in regRoots) {
                    // Step 1: enumerate all subkeys
                    val enumProcess = ProcessBuilder(
                        "reg", "query", regRoot
                    ).redirectErrorStream(true).start()
                    val enumOutput = enumProcess.inputStream.bufferedReader().readText()
                    enumProcess.waitFor()

                    // Step 2: for each subkey, check if DisplayName contains "Kiro"
                    for (line in enumOutput.lines()) {
                        val subKey = line.trim()
                        if (subKey.isEmpty() || !subKey.startsWith("HK")) continue

                        val queryProcess = ProcessBuilder(
                            "reg", "query", subKey, "/v", "DisplayName"
                        ).redirectErrorStream(true).start()
                        val queryOutput = queryProcess.inputStream.bufferedReader().readText()
                        queryProcess.waitFor()

                        if (!queryOutput.contains("Kiro", ignoreCase = true)) continue

                        // Step 3: read InstallLocation from this subkey
                        val locProcess = ProcessBuilder(
                            "reg", "query", subKey, "/v", "InstallLocation"
                        ).redirectErrorStream(true).start()
                        val locOutput = locProcess.inputStream.bufferedReader().readText()
                        locProcess.waitFor()

                        val regex = Regex("""InstallLocation\s+REG_SZ\s+(.+)""")
                        val match = regex.find(locOutput) ?: continue
                        val installDir = match.groupValues[1].trim().trimEnd('\\')
                        val exePath = "$installDir\\Kiro.exe"
                        if (File(exePath).exists()) return exePath
                    }
                }
                null
            } catch (_: Exception) {
                null
            }
        }

        private fun detectMac(): String {
            val candidates = listOf(
                "/Applications/Kiro.app/Contents/MacOS/Kiro",
                System.getenv("HOME")?.let { "$it/Applications/Kiro.app/Contents/MacOS/Kiro" }
            )
            return candidates.filterNotNull().firstOrNull { File(it).exists() } ?: "kiro"
        }

        private fun detectLinux(): String {
            val candidates = listOf(
                "/usr/bin/kiro",
                "/usr/local/bin/kiro",
                "/snap/bin/kiro",
                System.getenv("HOME")?.let { "$it/.local/bin/kiro" }
            )
            return candidates.filterNotNull().firstOrNull { File(it).exists() } ?: "kiro"
        }
    }
}
