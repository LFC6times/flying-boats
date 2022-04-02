package ml.ikwid.flyingboats.client;

import ml.ikwid.flyingboats.common.entities.FlyingBoatEntity;
import ml.ikwid.flyingboats.common.init.FlyingBoatEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BoatItem;

@Environment(EnvType.CLIENT)
public class FlyingboatsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenders();
    }

    public static void registerRenders() {
        registerItemEntityRenders(
                FlyingBoatEntities.FLYING_BOAT_ENTITY
        );
    }

    @SafeVarargs
    private static void registerItemEntityRenders(EntityType<? extends FlyingBoatEntity>... entityTypes) {
        for(EntityType<? extends FlyingBoatEntity> entityType : entityTypes) {
            registerItemEntityRender(entityType);
        }
    }

    private static <T extends BoatEntity> void registerItemEntityRender(EntityType<T> entityType) {
        EntityRendererRegistry.register(entityType, BoatEntityRenderer::new);
    }

}
