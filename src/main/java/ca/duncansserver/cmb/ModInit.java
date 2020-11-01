package ca.duncansserver.cmb;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.vehicle.BoatEntity.Type;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModInit implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "chestminecartboat";
    public static final String MOD_NAME = "Chest Minecart Boat";

    public static final Item CMB_OAK_ITEM = new ChestMinecartBoatItem(Type.OAK, new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final Item CMB_BIRCH_ITEM = new ChestMinecartBoatItem(Type.BIRCH, new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final Item CMB_SPRUCE_ITEM = new ChestMinecartBoatItem(Type.SPRUCE, new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final Item CMB_ACACIA_ITEM = new ChestMinecartBoatItem(Type.ACACIA, new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final Item CMB_JUNGLE_ITEM = new ChestMinecartBoatItem(Type.JUNGLE, new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));
    public static final Item CMB_DARK_OAK_ITEM = new ChestMinecartBoatItem(Type.DARK_OAK, new Item.Settings().group(ItemGroup.TRANSPORTATION).maxCount(1));

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        Registry.register(Registry.ITEM,new Identifier("cmb","oak_chest_minecart_boat"), CMB_OAK_ITEM);
        Registry.register(Registry.ITEM,new Identifier("cmb","birch_chest_minecart_boat"), CMB_BIRCH_ITEM);
        Registry.register(Registry.ITEM,new Identifier("cmb","spruce_chest_minecart_boat"), CMB_SPRUCE_ITEM);
        Registry.register(Registry.ITEM,new Identifier("cmb","acacia_chest_minecart_boat"), CMB_ACACIA_ITEM);
        Registry.register(Registry.ITEM,new Identifier("cmb","jungle_chest_minecart_boat"), CMB_JUNGLE_ITEM);
        Registry.register(Registry.ITEM,new Identifier("cmb","dark_oak_chest_minecart_boat"), CMB_DARK_OAK_ITEM);
        
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}