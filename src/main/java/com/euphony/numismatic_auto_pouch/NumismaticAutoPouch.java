package com.euphony.numismatic_auto_pouch;

import com.glisco.numismaticoverhaul.currency.CurrencyHelper;
import com.glisco.numismaticoverhaul.item.CurrencyItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NumismaticAutoPouch implements ModInitializer {

    private static final String MOD_COMPONENTS_CLASS = "com.glisco.numismaticoverhaul.ModComponents";
    private static final String COMPONENT_KEY_CLASS = "dev.onyxstudios.cca.api.v3.component.ComponentKey";

    private static Object currencyComponentKey;
    private static Method componentKeyGetMethod;
    private static Method currencyModifyMethod;
    private static boolean noCurrencyApiReady;

    static {
        try {
            Class<?> modComponentsClass = Class.forName(MOD_COMPONENTS_CLASS);
            Class<?> componentKeyClass = Class.forName(COMPONENT_KEY_CLASS);

            Field currencyField = modComponentsClass.getDeclaredField("CURRENCY");
            currencyComponentKey = currencyField.get(null);
            componentKeyGetMethod = componentKeyClass.getMethod("get", Object.class);

            Class<?> currencyComponentClass = Class.forName("com.glisco.numismaticoverhaul.currency.CurrencyComponent");
            currencyModifyMethod = currencyComponentClass.getMethod("modify", long.class);
            noCurrencyApiReady = true;
        } catch (ReflectiveOperationException exception) {
            noCurrencyApiReady = false;
        }
    }

    @Override
    public void onInitialize() {
    }

    public static void autoStorePickedCurrency(ServerPlayer player, ItemStack pickedStack) {
        if (!noCurrencyApiReady) {
            return;
        }

        if (!(pickedStack.getItem() instanceof CurrencyItem currencyItem)) {
            return;
        }

        long pickedValue = currencyItem.getValue(pickedStack);
        if (pickedValue <= 0) {
            return;
        }

        if (!removePickedCurrencyFromInventory(player, pickedStack)) {
            return;
        }

        addToCurrencyComponent(player, pickedValue);
    }

    private static boolean removePickedCurrencyFromInventory(ServerPlayer player, ItemStack pickedStack) {
        Inventory inventory = player.getInventory();
        int remainingToRemove = pickedStack.getCount();

        for (int slot = 0; slot < inventory.getContainerSize() && remainingToRemove > 0; slot++) {
            ItemStack inventoryStack = inventory.getItem(slot);
            if (!ItemStack.isSameItemSameTags(inventoryStack, pickedStack)) {
                continue;
            }

            int removing = Math.min(remainingToRemove, inventoryStack.getCount());
            inventoryStack.shrink(removing);
            remainingToRemove -= removing;

            if (inventoryStack.isEmpty()) {
                inventory.setItem(slot, ItemStack.EMPTY);
            }
        }

        return remainingToRemove == 0;
    }

    private static void addToCurrencyComponent(ServerPlayer player, long value) {
        if (!noCurrencyApiReady || value <= 0) {
            return;
        }

        try {
            Object currencyComponent = componentKeyGetMethod.invoke(currencyComponentKey, player);
            currencyModifyMethod.invoke(currencyComponent, value);
        } catch (ReflectiveOperationException exception) {
            CurrencyHelper.offerAsCoins(player, value);
            noCurrencyApiReady = false;
        }
    }
}
