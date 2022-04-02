package ml.ikwid.flyingboats.common.init;

import ml.ikwid.flyingboats.common.entities.FlyingBoatEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class FlyingBoatEntities {
    public static EntityType<FlyingBoatEntity> FLYING_BOAT_ENTITY;
    public static void init() {
        FLYING_BOAT_ENTITY = register("flying_boat", createBoatEntityType(FlyingBoatEntity::new));
    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, "flyingboats:" + s, entityType);
    }

    private static <T extends Entity> EntityType<T> createBoatEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.fixed(5f, 1f)).trackRangeBlocks(64).trackedUpdateRate(1).forceTrackedVelocityUpdates(true).build();
    }
}
