package ml.ikwid.flyingboats.common;

import ml.ikwid.flyingboats.common.init.FlyingBoatEntities;
import ml.ikwid.flyingboats.common.init.FlyingBoatItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Direction;

public class Flyingboats implements ModInitializer {
    public static final TrackedDataHandler<Direction> FACING = new TrackedDataHandler<Direction>() {
        @Override
        public void write(PacketByteBuf buf, Direction dir) {
            buf.writeEnumConstant(dir);
        }

        @Override
        public Direction read(PacketByteBuf buf) {
            return buf.readEnumConstant(Direction.class);
        }

        @Override
        public Direction copy(Direction dir) {
            return dir;
        }
    };

    @Override
    public void onInitialize() {
        TrackedDataHandlerRegistry.register(FACING);

        FlyingBoatEntities.init();
        FlyingBoatItems.init();
    }
}
