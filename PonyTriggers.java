package net.ona.mlpfrien.init;

import com.google.common.collect.Maps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.util.Map;
import java.util.function.Supplier;

public class PonyTriggers {
    public static DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, MyLittlePonyCraftingFriends.MODID);

    public static DeferredHolder<CriterionTrigger<?>,CutieMarkTrigger> CUTIE_MARK_TRIGGER = TRIGGERS.register("cutie_mark",CutieMarkTrigger::new);

}
