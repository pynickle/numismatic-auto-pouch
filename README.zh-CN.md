# Numismatic Auto Pouch

一个面向 **Minecraft 1.20.1 Fabric** 的轻量模组，用于与 [Numismatic Overhaul](https://www.curseforge.com/minecraft/mc-mods/numismatic-overhaul) 联动。

当玩家拾取 Numismatic 货币时，会自动将其存入钱包余额。

---

## 功能特性

- 基于事件触发（不做每 Tick 的全背包轮询）
- 仅在发生货币拾取时自动存入
- 支持 Numismatic Overhaul 的 `CurrencyItem` 货币物品
- 纯服务端逻辑，依赖 Numismatic Overhaul 的货币组件同步

---

## 当前行为

- 在成功拾取物品后触发（`ServerPlayer#onItemPickup`）
- 如果拾取物为 Numismatic 货币类型，则自动换算并存入钱包

> 说明：当前实现采用“拾取驱动”而不是“持续扫描整个背包”的方式。

---

## 运行需求

- Minecraft **1.20.1**
- Fabric Loader **0.18.4+**
- Fabric API **0.92.7+1.20.1**
- Numismatic Overhaul（已验证 `0.2.18+1.20` 版本线）
- owo-lib **0.11.2+1.20**
- Java **17**

---

## 开发环境

```bash
./gradlew genSources
./gradlew build
```

运行开发客户端：

```bash
./gradlew runClient
```

---

## 构建

```bash
./gradlew build
```

构建产物输出目录：

`build/libs/`

---

## 项目结构

- `src/main/java/com/euphony/numismatic_auto_pouch/NumismaticAutoPouch.java`  
  主入口与钱包入账桥接逻辑
- `src/main/java/com/euphony/numismatic_auto_pouch/mixin/ServerPlayerMixin.java`  
  拾取事件注入点
- `src/main/resources/fabric.mod.json`  
  模组元数据与依赖声明
- `src/main/resources/numismatic_auto_pouch.mixins.json`  
  Mixin 配置

---

## 许可证

本项目使用 **AGPL-3.0** 许可证，详见 [LICENSE](./LICENSE)。

---

## English README

英文文档见 [README.md](./README.md)。
