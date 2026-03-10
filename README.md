# Numismatic Auto Pouch

A lightweight Fabric mod for **Minecraft 1.20.1** that integrates with [Numismatic Overhaul](https://www.curseforge.com/minecraft/mc-mods/numismatic-overhaul).

When you pick up Numismatic coins, they are automatically deposited into your wallet balance.

---

## Features

- Event-driven auto deposit (no per-tick inventory polling)
- Deposits only when coin pickups happen
- Supports Numismatic Overhaul coin items (`CurrencyItem`)
- Server-side logic, synchronized through Numismatic Overhaul currency component

---

## Current Behavior

- Triggers on successful item pickup (`ServerPlayer#onItemPickup`)
- If the picked-up item is a Numismatic currency item, it is converted into wallet value automatically

> Note: The mod currently follows pickup-driven logic, not a global “scan entire inventory continuously” approach.

---

## Requirements

- Minecraft **1.20.1**
- Fabric Loader **0.18.4+**
- Fabric API **0.92.7+1.20.1**
- Numismatic Overhaul (tested with `0.2.18+1.20` line)
- owo-lib **0.11.2+1.20**
- Java **17**

---

## Development Setup

```bash
./gradlew genSources
./gradlew build
```

Run in dev environment:

```bash
./gradlew runClient
```

---

## Build

```bash
./gradlew build
```

Output jars are generated under:

`build/libs/`

---

## Project Structure

- `src/main/java/com/euphony/numismatic_auto_pouch/NumismaticAutoPouch.java`  
  Main mod initializer and wallet deposit bridge
- `src/main/java/com/euphony/numismatic_auto_pouch/mixin/ServerPlayerMixin.java`  
  Pickup hook for triggering auto deposit
- `src/main/resources/fabric.mod.json`  
  Mod metadata and dependency declarations
- `src/main/resources/numismatic_auto_pouch.mixins.json`  
  Mixin configuration

---

## License

This project is licensed under **AGPL-3.0**. See [LICENSE](./LICENSE).

---

## Chinese README

For Chinese documentation, see [README.zh-CN.md](./README.zh-CN.md).
