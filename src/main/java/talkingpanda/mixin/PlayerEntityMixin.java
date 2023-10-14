package talkingpanda.mixin;

import org.spongepowered.asm.mixin.Mixin;


import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import talkingpanda.NoExplosionDamagetoMobs;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(cancellable = true,method = "dropItem(Lnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/entity/ItemEntity;", at = @At("RETURN"))
    private void dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership ,CallbackInfoReturnable<ItemEntity> info) {
        if(info.getReturnValue() == null || !throwRandomly) return;
        // if player die throwRandomly is set to true when dropping it's false
        
        NbtCompound nbt = new NbtCompound();
        ItemEntity itemEntity = info.getReturnValue();
        
        itemEntity.writeNbt(nbt);

        // Player died set nevber despwan and mark as a death ıtem
       
        nbt.putInt("Age", Short.MIN_VALUE);
        nbt.putBoolean("Invulnerable", true);
        
        //itemEntity.readCustomDataFromNbt(nbt);
        itemEntity.readNbt(nbt);
        info.setReturnValue(itemEntity);

    }
}