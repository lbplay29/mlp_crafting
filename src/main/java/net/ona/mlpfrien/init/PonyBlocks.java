package net.ona.mlpfrien.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.util.function.Supplier;

public class PonyBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MyLittlePonyCraftingFriends.MODID);

    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_LOG = registerBlock("golden_log", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GOLDEN_LOG = registerBlock("stripped_golden_log", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)),Rarity.UNCOMMON);
    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_WOOD = registerBlock("golden_wood", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GOLDEN_WOOD = registerBlock("stripped_golden_wood", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)),Rarity.UNCOMMON);
    public static final DeferredBlock<Block> GOLDEN_PLANKS = registerBlock("golden_planks", ()->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)),Rarity.UNCOMMON);
    public static final DeferredBlock<LeavesBlock> GOLDEN_LEAVES = registerBlock("golden_leaves",()->
            new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<SaplingBlock> GOLDEN_SAPLING = registerBlock("golden_sapling",()->
            new SaplingBlock(PonyTreeGrowers.GOLDEN_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)),Rarity.UNCOMMON);

    public static final DeferredBlock<SlabBlock> GOLDEN_SLAB = registerBlock("golden_slab", ()->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<StairBlock> GOLDEN_STAIRS = registerBlock("golden_stairs", ()->
            new StairBlock(Blocks.OAK_PLANKS.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<FenceBlock> GOLDEN_FENCE = registerBlock("golden_fence", ()->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<FenceGateBlock> GOLDEN_FENCE_GATE = registerBlock("golden_fence_gate", ()->
            new FenceGateBlock(WoodType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<PressurePlateBlock> GOLDEN_PRESSURE_PlATE = registerBlock("golden_pressure_plate", ()->
            new PressurePlateBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<ButtonBlock> GOLDEN_BUTTON = registerBlock("golden_button", ()->
            new ButtonBlock(BlockSetType.OAK,20,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).ignitedByLava()),Rarity.UNCOMMON);

    public static final DeferredBlock<DoorBlock> GOLDEN_DOOR = registerBlock("golden_door", ()->
            new DoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).ignitedByLava()),Rarity.UNCOMMON);
    public static final DeferredBlock<TrapDoorBlock> GOLDEN_TRAPDOOR = registerBlock("golden_trapdoor", ()->
            new TrapDoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).ignitedByLava()),Rarity.UNCOMMON);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Rarity rarity) {
        DeferredBlock<T> blockInQuestion = BLOCKS.register(name, block);
        PonyItems.ITEMS.register(name,()-> new BlockItem(blockInQuestion.get(), new Item.Properties().rarity(rarity)));
        return blockInQuestion;
    }
}
