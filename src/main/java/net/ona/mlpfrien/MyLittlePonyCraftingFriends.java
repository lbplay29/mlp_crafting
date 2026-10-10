package net.ona.mlpfrien;

import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.init.PonyEntities;
import net.ona.mlpfrien.init.PonyItems;
import net.ona.mlpfrien.network.PonyNetworking;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MyLittlePonyCraftingFriends.MODID)
public class MyLittlePonyCraftingFriends {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "mlpfrien";

    public static final Logger LOGGER = LogUtils.getLogger();

    public MyLittlePonyCraftingFriends(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);

        PonyAttachments.ATTACHMENT_TYPES.register(modEventBus);

        modEventBus.addListener(PonyNetworking::registerPayloads);
        PonyItems.register(modEventBus);
        PonyEntities.ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(PonyItems.HAY_BURGUER);
            event.accept(PonyItems.CARAMEL_APPLE);
            event.accept(PonyItems.SOUR_APPLE);
            event.accept(PonyItems.GREEN_APPLE);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Equestria servers are online!");
    }
}
