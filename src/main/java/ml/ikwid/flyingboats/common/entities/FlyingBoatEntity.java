package ml.ikwid.flyingboats.common.entities;

import ml.ikwid.flyingboats.common.init.FlyingBoatItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;

public class FlyingBoatEntity extends BoatEntity implements JumpingMount {

    public FlyingBoatEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setJumpStrength(int strength) {
        double yaw = this.getYaw();
        double cleanYaw;
        double blocksOfMovement = 2;
        /*
         * 0 is +z
         * 90 is -x
         * 180 is -z
         * 270 is +x
         * 360 is +z
         *
         * we allocate 2 blocks of movement
         */

        cleanYaw = yaw;

        if(yaw >= 270) {
            cleanYaw -= 270;
        } else if(yaw >= 180) {
            cleanYaw -= 180;
        } else if(yaw >= 90) {
            cleanYaw -= 90;
        }

        double ratioZtoX = 1 - (cleanYaw / 90);

        this.setVelocity(this.getVelocity().add(blocksOfMovement * (1 - ratioZtoX) * (yaw <= 180 ? -1 : 1), 0.2, blocksOfMovement * ratioZtoX * (yaw >= 90 && yaw <= 270 ? -1 : 1)));
    }

    @Override
    public boolean canJump() {
        return true;
    }

    @Override
    public void startJumping(int height) {

    }

    @Override
    public void stopJumping() {

    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPos) {
        /*
        if(!onGround) {
            this.setPos(this.getX(), this.getY() + (heightDifference / 2), this.getZ());
        }

         */
    }

    @Override
    public Item asItem() {
        return FlyingBoatItems.FLYING_BOAT_ITEM;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (this.world.isClient || this.isRemoved()) {
            return true;
        }
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
        this.scheduleVelocityUpdate();
        this.emitGameEvent(GameEvent.ENTITY_DAMAGED, source.getAttacker());
        bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode;
        if (bl || this.getDamageWobbleStrength() > 40.0f) {
            if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                this.dropItem(this.asItem());
                if(source.isProjectile() || source.isFire() || source.isExplosive()) {
                    Explosion explosion = new Explosion(this.world, this, this.getX(), this.getY(), this.getZ(), 3, true, Explosion.DestructionType.BREAK);
                    explosion.collectBlocksAndDamageEntities();
                    explosion.affectWorld(true);
                }
            }
            this.discard();
        }
        return true;
    }
}
