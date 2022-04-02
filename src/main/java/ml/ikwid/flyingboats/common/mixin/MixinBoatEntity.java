package ml.ikwid.flyingboats.common.mixin;

import net.minecraft.entity.vehicle.BoatEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BoatEntity.class)
public class MixinBoatEntity {
    @Shadow private float velocityDecay;

    @Inject(method = "updateVelocity", at = @At(value = "TAIL"))
    private void updateVelocity(CallbackInfo ci) {
        this.velocityDecay = 0;
    }
}
