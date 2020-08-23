package stopbuggy.mixins;

import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin
{
	@Shadow
	private static boolean hasItemInOffHand(PiglinEntity piglin)
	{
		return false;
	}

	@Redirect(
			method = "onAttacked",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/entity/mob/PiglinBrain;hasItemInOffHand(Lnet/minecraft/entity/mob/PiglinEntity;)Z",
					ordinal = 0
			)
	)
	private static boolean stopConsumeOffHandItemInDeath(PiglinEntity piglin)
	{
		return hasItemInOffHand(piglin) && !piglin.isDead();
	}
}
