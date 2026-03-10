# Switch2Kiro

[中文文档](#中文文档)

> 💡 This project is inspired by and references [switch2cursor](https://github.com/qczone/switch2cursor).

## 🔍 Introduction

A JetBrains IDE plugin that enhances development efficiency by enabling seamless switching between JetBrains IDE and [Kiro](https://kiro.dev).

## 🌟 Features

- 🚀 **Seamless Editor Switching**
  - One-click switch between JetBrains IDE and Kiro
  - Automatically positions to the same cursor location (line and column)
  - Perfectly maintains editing context without interrupting workflow

- ⌨️ **Convenient Shortcut Support**
  - `Alt+Shift+P` — Open project in Kiro
  - `Alt+Shift+O` — Open current file in Kiro
  - macOS uses `Option` instead of `Alt`

- 🔧 **Multiple Access Methods**
  - Keyboard shortcuts
  - Editor context menu
  - IDE Tools menu

## 🛠️ Installation

### Method 1: Install via JetBrains Marketplace

1. Open IDE → Settings → Plugins → Marketplace
2. Search for `Switch2Kiro`
3. Click Install
4. Click OK to apply changes

### Method 2: Local Installation

1. Download the latest plugin package from [Releases](https://github.com/oouo/switch2kiro/releases)
2. IDE → Settings → Plugins → ⚙️ → Install Plugin from Disk...
3. Select the downloaded plugin package
4. Click OK to apply changes

## 🚀 Usage

### Open Project

- **Shortcut:** `Alt+Shift+P` (macOS: `Option+Shift+P`)
- **Context Menu:** Right-click in project view → Open Project In Kiro
- **Tools Menu:** Tools → Open Project In Kiro

### Open Current File

- **Shortcut:** `Alt+Shift+O` (macOS: `Option+Shift+O`)
- **Context Menu:** Right-click in editor → Open File In Kiro
- **Tools Menu:** Tools → Open File In Kiro

### Configuration

In Settings/Preferences → Tools → Switch2Kiro:
- Set Kiro executable path (default is `kiro`)
- Customize shortcuts through Keymap settings

## Requirements

- [Kiro](https://kiro.dev) installed
- Compatible with all JetBrains IDEs (version 2022.3 and above)

## 🧑‍💻 Developer Guide

### Build Project

```bash
# Clone repository
git clone https://github.com/oouo/switch2kiro.git

# Build plugin
cd switch2kiro
./gradlew buildPlugin
# Plugin package will be generated in build/distributions/ directory
```

## 🙋 FAQ

### 1. Why doesn't the shortcut/menu click switch to Kiro after installation?
Check if the correct Kiro executable path is configured in Settings → Tools → Switch2Kiro.

### 2. Which IDEs are supported?
Supports all JetBrains IDEs, including: IntelliJ IDEA, PyCharm, WebStorm, GoLand, RustRover, Android Studio, etc.

### 3. Which versions are supported?
The plugin is developed based on JDK 17 and currently only supports JetBrains IDE version 2022.3 and above.

### 4. How to modify plugin shortcuts?
Modify in Settings → Keymap → Plugins → Switch2Kiro.

## 📄 License

This project is licensed under the MIT License.

## 🙏 Acknowledgements

This project is inspired by and references [switch2cursor](https://github.com/qczone/switch2cursor) by [qczone](https://github.com/qczone). Thanks for the great work!

---

# 中文文档

> 💡 本项目参考了 [switch2cursor](https://github.com/qczone/switch2cursor)，感谢原作者的优秀工作。

## 🔍 简介

一个 JetBrains IDE 插件，通过在 JetBrains IDE 和 [Kiro](https://kiro.dev) 之间无缝切换来提升开发效率。

## 🌟 功能特性

- 🚀 **无缝编辑器切换**
  - 一键在 JetBrains IDE 和 Kiro 之间切换
  - 自动定位到相同的光标位置（行号和列号）
  - 完美保持编辑上下文，不中断工作流

- ⌨️ **便捷快捷键**
  - `Alt+Shift+P` — 在 Kiro 中打开项目
  - `Alt+Shift+O` — 在 Kiro 中打开当前文件
  - macOS 使用 `Option` 代替 `Alt`

- 🔧 **多种访问方式**
  - 键盘快捷键
  - 编辑器右键菜单
  - IDE 工具菜单

## 🛠️ 安装

### 方式一：通过 JetBrains 插件市场安装

1. 打开 IDE → Settings → Plugins → Marketplace
2. 搜索 `Switch2Kiro`
3. 点击 Install 安装
4. 点击 OK 应用更改

### 方式二：本地安装

1. 从 [Releases](https://github.com/oouo/switch2kiro/releases) 下载最新插件包
2. IDE → Settings → Plugins → ⚙️ → Install Plugin from Disk...
3. 选择下载的插件包
4. 点击 OK 应用更改

## 🚀 使用方法

### 打开项目

- **快捷键：** `Alt+Shift+P`（macOS: `Option+Shift+P`）
- **右键菜单：** 在项目视图中右键 → Open Project In Kiro
- **工具菜单：** Tools → Open Project In Kiro

### 打开当前文件

- **快捷键：** `Alt+Shift+O`（macOS: `Option+Shift+O`）
- **右键菜单：** 在编辑器中右键 → Open File In Kiro
- **工具菜单：** Tools → Open File In Kiro

### 配置

在 Settings/Preferences → Tools → Switch2Kiro 中：
- 设置 Kiro 可执行文件路径（默认为 `kiro`）
- 通过 Keymap 设置自定义快捷键

## 系统要求

- 已安装 [Kiro](https://kiro.dev)
- 兼容所有 JetBrains IDE（版本 2022.3 及以上）

## 🧑‍💻 开发指南

### 构建项目

```bash
# 克隆仓库
git clone https://github.com/oouo/switch2kiro.git

# 构建插件
cd switch2kiro
./gradlew buildPlugin
# 插件包将生成在 build/distributions/ 目录下
```

## 🙋 常见问题

### 1. 安装后快捷键/菜单点击无法切换到 Kiro？
请检查 Settings → Tools → Switch2Kiro 中是否配置了正确的 Kiro 可执行文件路径。

### 2. 支持哪些 IDE？
支持所有 JetBrains IDE，包括：IntelliJ IDEA、PyCharm、WebStorm、GoLand、RustRover、Android Studio 等。

### 3. 支持哪些版本？
插件基于 JDK 17 开发，目前仅支持 JetBrains IDE 2022.3 及以上版本。

### 4. 如何修改插件快捷键？
在 Settings → Keymap → Plugins → Switch2Kiro 中修改。

## 📄 许可证

本项目采用 MIT 许可证。

## 🙏 致谢

本项目参考了 [qczone](https://github.com/qczone) 的 [switch2cursor](https://github.com/qczone/switch2cursor)，感谢原作者的优秀工作！
