package gg.zr0x8.nightvision.mixin;


import com.mojang.authlib.GameProfile;
import gg.zr0x8.nightvision.Config;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {

    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public boolean hasStatusEffect(RegistryEntry<StatusEffect> effect) {
        if(effect == StatusEffects.BLINDNESS && Config.getConfigData().no_blindness)
            return false;

        if(effect == StatusEffects.DARKNESS && Config.getConfigData().no_blindness)
            return false;

        return super.hasStatusEffect(effect);
    }
}
