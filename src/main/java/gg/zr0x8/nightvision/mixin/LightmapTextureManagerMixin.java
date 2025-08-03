package gg.zr0x8.nightvision.mixin;

import gg.zr0x8.nightvision.Config;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightmapTextureManager.class)
public class LightmapTextureManagerMixin {
    @Inject(method = "getDarkness", at = @At("HEAD"), cancellable = true)
    private void getDarkness(LivingEntity entity, float factor, float delta, CallbackInfoReturnable<Float> cir) {
        if (Config.getConfigData().no_blindness)
            cir.setReturnValue(0.0f);
    }
    @Inject(method = "getDarknessFactor", at = @At("HEAD"), cancellable = true)
    private void getDarknessFactor(float factor, CallbackInfoReturnable<Float> cir) {
        if (Config.getConfigData().no_blindness)
            cir.setReturnValue(0.0f);
    }
}
