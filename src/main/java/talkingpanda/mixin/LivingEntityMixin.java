package talkingpanda.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(cancellable = true,method = "damage",at = @At("HEAD"))
    private void injected(DamageSource source,float amount,CallbackInfoReturnable<Boolean> cir)
    {
        LivingEntity entity = (LivingEntity)(Object)this;
        if(entity.isPlayer()) return;
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)) cir.cancel();
    }
   
}
