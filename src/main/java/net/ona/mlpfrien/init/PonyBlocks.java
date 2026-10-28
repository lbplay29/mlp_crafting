package net.ona.mlpfrien.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.block.AppleLeavesBlock;
import net.ona.mlpfrien.block.PonyLog;
import net.ona.mlpfrien.worldgen.PonyTreeGrowers;

import java.util.function.Supplier;

public class PonyBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MyLittlePonyCraftingFriends.MODID);

    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_LOG = registerBlock("golden_log", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GOLDEN_LOG = registerBlock("stripped_golden_log", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)),Rarity.RARE);
    public static final DeferredBlock<RotatedPillarBlock> GOLDEN_WOOD = registerBlock("golden_wood", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GOLDEN_WOOD = registerBlock("stripped_golden_wood", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)),Rarity.RARE);
    public static final DeferredBlock<Block> GOLDEN_PLANKS = registerBlock("golden_planks", ()->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)),Rarity.RARE);
    public static final DeferredBlock<SaplingBlock> GOLDEN_SAPLING = registerBlock("golden_sapling",()->
            new SaplingBlock(PonyTreeGrowers.GOLDEN_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)),Rarity.RARE);
    public static final DeferredBlock<SlabBlock> GOLDEN_SLAB = registerBlock("golden_slab", ()->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<StairBlock> GOLDEN_STAIRS = registerBlock("golden_stairs", ()->
            new StairBlock(Blocks.OAK_PLANKS.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<FenceBlock> GOLDEN_FENCE = registerBlock("golden_fence", ()->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<FenceGateBlock> GOLDEN_FENCE_GATE = registerBlock("golden_fence_gate", ()->
            new FenceGateBlock(WoodType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<PressurePlateBlock> GOLDEN_PRESSURE_PlATE = registerBlock("golden_pressure_plate", ()->
            new PressurePlateBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<ButtonBlock> GOLDEN_BUTTON = registerBlock("golden_button", ()->
            new ButtonBlock(BlockSetType.OAK,20,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<DoorBlock> GOLDEN_DOOR = registerBlock("golden_door", ()->
            new DoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<TrapDoorBlock> GOLDEN_TRAPDOOR = registerBlock("golden_trapdoor", ()->
            new TrapDoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).ignitedByLava()),Rarity.RARE);
    public static final DeferredBlock<LeavesBlock> GOLDEN_LEAVES = registerBlock("golden_leaves",()->
            new AppleLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).ignitedByLava(),()-> Items.GOLDEN_APPLE,3),Rarity.RARE);

    public static final DeferredBlock<RotatedPillarBlock> ZAP_LOG = registerBlock("zap_log", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_ZAP_LOG = registerBlock("stripped_zap_log", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)),Rarity.EPIC);
    public static final DeferredBlock<RotatedPillarBlock> ZAP_WOOD = registerBlock("zap_wood", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_ZAP_WOOD = registerBlock("stripped_zap_wood", ()->
            new PonyLog(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)),Rarity.EPIC);
    public static final DeferredBlock<Block> ZAP_PLANKS = registerBlock("zap_planks", ()->
            new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)),Rarity.EPIC);
    public static final DeferredBlock<SaplingBlock> ZAP_SAPLING = registerBlock("zap_sapling",()->
            new SaplingBlock(PonyTreeGrowers.ZAP_TREE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)),Rarity.EPIC);
    public static final DeferredBlock<SlabBlock> ZAP_SLAB = registerBlock("zap_slab", ()->
            new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<StairBlock> ZAP_STAIRS = registerBlock("zap_stairs", ()->
            new StairBlock(Blocks.OAK_PLANKS.defaultBlockState(),BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<FenceBlock> ZAP_FENCE = registerBlock("zap_fence", ()->
            new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<FenceGateBlock> ZAP_FENCE_GATE = registerBlock("zap_fence_gate", ()->
            new FenceGateBlock(WoodType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<PressurePlateBlock> ZAP_PRESSURE_PlATE = registerBlock("zap_pressure_plate", ()->
            new PressurePlateBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<ButtonBlock> ZAP_BUTTON = registerBlock("zap_button", ()->
            new ButtonBlock(BlockSetType.OAK,20,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<DoorBlock> ZAP_DOOR = registerBlock("zap_door", ()->
            new DoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<TrapDoorBlock> ZAP_TRAPDOOR = registerBlock("zap_trapdoor", ()->
            new TrapDoorBlock(BlockSetType.OAK,BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).ignitedByLava()),Rarity.EPIC);
    public static final DeferredBlock<LeavesBlock> ZAP_LEAVES = registerBlock("zap_leaves",()->
            new AppleLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).ignitedByLava(),PonyItems.ZAP_APPLE,3),Rarity.EPIC);

    public static final DeferredBlock<LeavesBlock> APPLE_LEAVES = registerBlock("apple_leaves",()->
            new AppleLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).ignitedByLava(),()-> Items.APPLE,2),Rarity.COMMON);
//    public static final DeferredBlock<LeavesBlock> GREEN_APPLE_LEAVES = registerBlock("green_apple_leaves",()->
//            new AppleLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).ignitedByLava(),PonyItems.GREEN_APPLE,2),Rarity.COMMON);
//    public static final DeferredBlock<LeavesBlock> SOUR_APPLE_LEAVES = registerBlock("sour_apple_leaves",()->
//            new AppleLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).ignitedByLava(),PonyItems.SOUR_APPLE,2),Rarity.COMMON);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block, Rarity rarity) {
        DeferredBlock<T> blockInQuestion = BLOCKS.register(name, block);
        PonyItems.ITEMS.register(name,()-> new BlockItem(blockInQuestion.get(), new Item.Properties().rarity(rarity)));
        return blockInQuestion;
    }
}
