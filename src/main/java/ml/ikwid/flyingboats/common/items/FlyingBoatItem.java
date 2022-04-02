package ml.ikwid.flyingboats.common.items;

import ml.ikwid.flyingboats.common.entities.FlyingBoatEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class FlyingBoatItem extends Item {
    EntityType<FlyingBoatEntity> type;

    public FlyingBoatItem(Item.Settings settings, EntityType<FlyingBoatEntity> entityType) {
        super(settings);
        this.type = entityType;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if(hand == Hand.OFF_HAND && playerEntity.getStackInHand(Hand.MAIN_HAND).getItem() instanceof FlyingBoatItem) {
            return new TypedActionResult<>(ActionResult.PASS, playerEntity.getStackInHand(hand));
        } else {
            ItemStack stackInHand = playerEntity.getStackInHand(hand);
            if(!world.isClient) {
                FlyingBoatEntity flyingBoatEntity = Objects.requireNonNull(this.type.create(world));
                flyingBoatEntity.setPos(playerEntity.getX(), playerEntity.getY() + 0.3, playerEntity.getZ());
                flyingBoatEntity.setYaw(playerEntity.getYaw());
                world.spawnEntity(flyingBoatEntity);
            }
            if(!playerEntity.getAbilities().creativeMode) {
                stackInHand.decrement(1);
            }
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            return new TypedActionResult<>(ActionResult.SUCCESS, stackInHand);
        }
    }
}
