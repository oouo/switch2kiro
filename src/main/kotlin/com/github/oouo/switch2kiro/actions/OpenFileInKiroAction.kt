package com.github.oouo.switch2kiro.actions

import com.github.oouo.switch2kiro.settings.AppSettingsState
import com.github.oouo.switch2kiro.utils.WindowUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile

class OpenFileInKiroAction : AnAction() {
    private val logger = Logger.getInstance(OpenFileInKiroAction::class.java)

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project: Project = e.project ?: return
        val virtualFile: VirtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val editor: Editor? = e.getData(CommonDataKeys.EDITOR)

        val line = editor?.caretModel?.logicalPosition?.line?.plus(1) ?: 1
        val column = editor?.caretModel?.logicalPosition?.column?.plus(1) ?: 1

        val filePath = virtualFile.path
        val settings = AppSettingsState.getInstance()
        val kiroPath = settings.kiroPath

        val command = when {
            System.getProperty("os.name").lowercase().contains("mac") -> {
                if (kiroPath.endsWith(".app") || kiroPath.contains(".app/")) {
                    arrayOf("open", "-a", kiroPath, "--args", "--goto", "$filePath:$line:$column")
                } else {
                    arrayOf(kiroPath, "--goto", "$filePath:$line:$column")
                }
            }
            System.getProperty("os.name").lowercase().contains("windows") -> {
                arrayOf(kiroPath, "--goto", "$filePath:$line:$column")
            }
            else -> {
                arrayOf(kiroPath, "--goto", "$filePath:$line:$column")
            }
        }

        try {
            logger.info("Executing command: ${command.joinToString(" ")}")
            ProcessBuilder(*command).start()
        } catch (ex: Exception) {
            logger.error("Failed to execute Kiro command: ${ex.message}", ex)
            com.intellij.openapi.ui.Messages.showErrorDialog(
                project,
                """
                ${ex.message}
                
                Please check:
                1. Kiro path is correctly configured in Settings > Tools > Switch2Kiro
                2. Kiro is properly installed on your system
                3. The configured path points to a valid Kiro executable
                """.trimIndent(),
                "Error"
            )
        }

        WindowUtils.activeWindow()
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)

        e.presentation.isEnabledAndVisible = project != null &&
                virtualFile != null &&
                !virtualFile.isDirectory
    }
}
