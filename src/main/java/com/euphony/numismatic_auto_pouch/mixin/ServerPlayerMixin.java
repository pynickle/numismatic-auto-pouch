package com.euphony.numismatic_auto_pouch.mixin;

import com.euphony.numismatic_auto_pouch.NumismaticAutoPouch;
import com.glisco.numismaticoverhaul.item.CurrencyItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    @Inject(method = "onItemPickup", at = @At("TAIL"))
    private void numismaticAutoPouch$onItemPickup(ItemEntity itemEntity, CallbackInfo ci) {
        ItemStack pickedStack = itemEntity.getItem();
        if (!(pickedStack.getItem() instanceof CurrencyItem)) {
            return;
        }

        NumismaticAutoPouch.autoStorePickedCurrency((ServerPlayer) (Object) this, pickedStack.copy());
    }
}
