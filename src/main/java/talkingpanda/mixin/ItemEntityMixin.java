package talkingpanda.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;



@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(cancellable = true,method = "damage",at= @At("HEAD"))
    public void damage(DamageSource source, float amount,CallbackInfoReturnable<Boolean> cir) {
        // if explosion don't do damage
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)) cir.setReturnValue(false);
        
    }
    @Inject(cancellable = true,method="isFireImmune",at = @At("HEAD"))
    private void injected(CallbackInfoReturnable<Boolean> cir)
    {
        ItemEntity ie = (ItemEntity)(Object)this;
        NbtCompound nbt = new NbtCompound();
        ie.writeNbt(nbt);
        if(nbt.getBoolean("Invulnerable")) cir.setReturnValue(true);
    }    
}