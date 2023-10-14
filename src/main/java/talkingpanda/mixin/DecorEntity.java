package talkingpanda.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.AbstractDecorationEntity;


@Mixin(AbstractDecorationEntity.class)
public class DecorEntity {
    @Overwrite()
    public boolean damage(DamageSource source, float amount)
    {
        // Player didn't attack don't do anything
        if(!source.getAttacker().isPlayer()) return false;
        // Player attacked die
		AbstractDecorationEntity entity = (AbstractDecorationEntity)(Object)this;
		 if (!entity.isRemoved() && !entity.getWorld().isClient) {
            entity.kill();
			entity.velocityModified = true;
            entity.onBreak(source.getAttacker());
        }
		return true;
    }
}