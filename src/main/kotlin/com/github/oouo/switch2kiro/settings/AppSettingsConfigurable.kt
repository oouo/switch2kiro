package com.github.oouo.switch2kiro.settings

import com.intellij.openapi.options.Configurable
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JTextField
import com.intellij.ui.components.JBLabel
import com.intellij.util.ui.FormBuilder

class AppSettingsConfigurable : Configurable {
    private var mySettingsComponent: AppSettingsComponent? = null

    override fun getDisplayName(): String = "Switch2Kiro"

    override fun createComponent(): JComponent {
        mySettingsComponent = AppSettingsComponent()
        return mySettingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = AppSettingsState.getInstance()
        return mySettingsComponent!!.kiroPath != settings.kiroPath
    }

    override fun apply() {
        val settings = AppSettingsState.getInstance()
        settings.kiroPath = mySettingsComponent!!.kiroPath
    }

    override fun reset() {
        val settings = AppSettingsState.getInstance()
        mySettingsComponent!!.kiroPath = settings.kiroPath
    }

    override fun disposeUIResources() {
        mySettingsComponent = null
    }
}

class AppSettingsComponent {
    val panel: JPanel
    private val kiroPathText = JTextField()

    init {
        panel = FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Kiro Path: "), kiroPathText, 1, false)
            .addComponentFillVertically(JPanel(), 0)
            .panel
    }

    var kiroPath: String
        get() = kiroPathText.text
        set(value) {
            kiroPathText.text = value
        }
}
