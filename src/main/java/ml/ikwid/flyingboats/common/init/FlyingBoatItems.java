package ml.ikwid.flyingboats.common.init;

import ml.ikwid.flyingboats.common.items.FlyingBoatItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;

public class FlyingBoatItems {
    public static Item FLYING_BOAT_ITEM;
    public static void init() {
        FLYING_BOAT_ITEM = registerItem(new FlyingBoatItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1), FlyingBoatEntities.FLYING_BOAT_ENTITY), "flying_boat");

    }

    public static Item registerItem(Item item, String name) {
        Registry.register(Registry.ITEM, "flyingboats:" + name, item);
        return item;
    }
}
