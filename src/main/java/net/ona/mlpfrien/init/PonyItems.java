package net.ona.mlpfrien.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.util.List;
import java.util.Optional;

public class PonyItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MyLittlePonyCraftingFriends.MODID);

    public static final DeferredItem<Item> HAY_BURGUER = ITEMS.register("hay_burguer",()-> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .saturationModifier(1.5f).nutrition(8).build())));

    public static final DeferredItem<Item> CARAMEL_APPLE = ITEMS.register("caramel_apple",()-> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .saturationModifier(0.5f).nutrition(6).build())));
    public static final DeferredItem<Item> GREEN_APPLE = ITEMS.register("green_apple",()-> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .saturationModifier(0.2f).nutrition(4).build())));
    public static final DeferredItem<Item> SOUR_APPLE = ITEMS.register("sour_apple",()-> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .saturationModifier(0.2f).nutrition(4).build())));
    public static final DeferredItem<Item> ZAP_APPLE = ITEMS.register("zap_apple",()-> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .saturationModifier(1.5f).nutrition(6).build()).rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> CRUSADER_EMBLEM = ITEMS.register("crusader_emblem", ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
