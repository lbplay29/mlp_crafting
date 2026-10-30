package net.ona.mlpfrien.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.worldgen.WindingPathFeature;

public class PonyFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE,MyLittlePonyCraftingFriends.MODID);

    public static final DeferredHolder<Feature<?>, WindingPathFeature> WINDING_PATH_X = FEATURES.register("winding_path_x",()-> new WindingPathFeature(false));
    public static final DeferredHolder<Feature<?>, WindingPathFeature> WINDING_PATH_Z = FEATURES.register("winding_path_z",()-> new WindingPathFeature(true));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
