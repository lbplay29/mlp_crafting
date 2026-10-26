package net.ona.mlpfrien.events;

import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.CropGrowEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.*;
import net.ona.mlpfrien.data.skill.PonySkills;
import net.ona.mlpfrien.data.skill.SkillManager;
import net.ona.mlpfrien.data.skill.SkillStateData;
import net.ona.mlpfrien.data.skill.SkillTrees;
import net.ona.mlpfrien.init.PonyTriggers;
import net.ona.mlpfrien.magic.DelayedSpellManager;
import net.ona.mlpfrien.magic.SpellCaster;
import net.ona.mlpfrien.util.PonyAttributeManager;
import net.ona.mlpfrien.util.ReplacingPlaceContext;

import java.util.List;

@EventBusSubscriber(modid = MyLittlePonyCraftingFriends.MODID)
public class PonyEvents {
    private static int castingTime = 0;

    @SubscribeEvent
    public static void playerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {return;}
        SkillStateData skillState = player.getData(PonyAttachments.SKILL_STATE_DATA);
        int distance = player.getStats().getValue(Stats.CUSTOM.get(Stats.SPRINT_ONE_CM));
        PonyData data = player.getData(PonyAttachments.PONY_DATA);
        UnicornData unicornData = player.getData(PonyAttachments.UNICORN_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.EXPLORING) {
            if (distance>=100000) {
                if (!data.discoveredCM()) {
                    discoverMark(data, (ServerPlayer) event.getEntity());
                }
            }
        }
        if (unicornData.mana()<unicornData.max_mana()) {
            SpellCaster.useMana(player,-0.1f);
        }
        if (SkillManager.hasSkill(player,PonySkills.HIGH_VISION.id()) && skillState.isActive(PonySkills.HIGH_VISION)) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,10,0,false,false));
        }
        if (unicornData.isCasting() && castingTime <= 0) {
            castingTime = 20;
        }

        if (castingTime > 0) {
            castingTime--;
            if (castingTime == 0) {
                UnicornData currentData = player.getData(PonyAttachments.UNICORN_DATA);
                player.setData(PonyAttachments.UNICORN_DATA, new UnicornData(currentData.spells(), currentData.equippedSpells(), currentData.spellSlots(), currentData.mana(),
                        currentData.max_mana(), currentData.level(), false));
            }
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("mlp").then(Commands.literal("data").then(Commands.literal("reset").executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    player.setData(PonyAttachments.PONY_DATA, PonyData.defaultData());
                                    player.setData(PonyAttachments.UNICORN_DATA, UnicornData.defaultData());
                                    player.setData(PonyAttachments.APPEARANCE_DATA, AppearanceData.defaultData());
                                    player.setData(PonyAttachments.LEVEL_DATA, LevelData.defaultData());
                                    return 1;}))));
    }

    @SubscribeEvent
    public static void playerEaten(LivingEntityUseItemEvent.Finish event) {
        if (!event.getItem().has(DataComponents.FOOD)) {
            return;
        }
        if (event.getEntity().getData(PonyAttachments.PONY_DATA).race().canUseMagic()) {
            UnicornData data = event.getEntity().getData(PonyAttachments.UNICORN_DATA);
            float heal = event.getItem().getFoodProperties(event.getEntity()).nutrition() *10;
            event.getEntity().setData(PonyAttachments.UNICORN_DATA, new UnicornData(data.spells(),data.equippedSpells(), data.spellSlots(),
                    Math.min(data.mana()+heal, data.max_mana()), data.max_mana(), data.level(), data.isCasting()));
        }
    }

    @SubscribeEvent
    public static void blockBroken(BlockEvent.BreakEvent event) {
        PonyData data = event.getPlayer().getData(PonyAttachments.PONY_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.MINING) {
            if (event.getState().is(BlockTags.DIAMOND_ORES)) {
                if (!data.discoveredCM()) {
                    discoverMark(data, (ServerPlayer) event.getPlayer());
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
        PonyData data = event.getEntity().getData(PonyAttachments.PONY_DATA);
        if (data.cutieMark()== PonyData.CUTIE_MARKS.BUILDING) {
            if (event.getState().is(BlockTags.LOGS)) {
                if (!data.discoveredCM()) {
                    discoverMark(data, (ServerPlayer) event.getEntity());
                }
            }
        }
    }

    @SubscribeEvent
    public static void playerExp(PlayerXpEvent.PickupXp event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {return;}
        LevelData data = player.getData(PonyAttachments.LEVEL_DATA);
        if (data.skillXp()>=100) {
            List<SkillTrees.SkillTree> availableTrees = SkillTrees.getAll().stream().filter(skillTree -> skillTree.isAvailable(player.getData(PonyAttachments.PONY_DATA))).toList();
            if (!availableTrees.isEmpty()) {
                player.setData(PonyAttachments.LEVEL_DATA,new LevelData(data.skills(), data.skillPoints()+2, 0));
                player.playNotifySound(SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 10f, 1f);
                player.displayClientMessage(Component.literal("Level up! +2 Skill points"),true);
            }
        } else {
            player.setData(PonyAttachments.LEVEL_DATA,new LevelData(data.skills(), data.skillPoints(), data.skillXp()+event.getOrb().getValue()));
        }
    }

    @SubscribeEvent
    public static void replacing(UseItemOnBlockEvent event) {
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.ITEM_AFTER_BLOCK) {return;}
        if (!(event.getPlayer() instanceof Player player)) {return;}
        if (!SkillManager.hasSkill(player, PonySkills.REPLACING.id())) {return;}

        SkillStateData skillState = player.getData(PonyAttachments.SKILL_STATE_DATA);
        if (!skillState.isActive(PonySkills.REPLACING)) {return;}

        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof BlockItem blockItem)) {return;}

        BlockPos pos = event.getPos();
        BlockState oldState = event.getLevel().getBlockState(pos);

        if (oldState.getDestroySpeed(event.getLevel(), pos) < 0) {return;}
        if (event.getLevel().getBlockEntity(pos) != null) {return;}

        if (!player.isCreative()) {
            player.addItem(event.getLevel().getBlockState(pos).getBlock().asItem().getDefaultInstance());
        }

        BlockPlaceContext context = new ReplacingPlaceContext(event.getUseOnContext());
        blockItem.place(context);

        event.cancelWithResult(ItemInteractionResult.SUCCESS);
    }

    @SubscribeEvent
    public static void extraBreakingHarvest(PlayerEvent.HarvestCheck event) {
        Player player = event.getEntity();

        if (!SkillManager.hasSkill(player, PonySkills.EXTRA_BREAKING.id())) {return;}
        if (!player.getMainHandItem().isEmpty() && !(player.getMainHandItem().getItem() instanceof BlockItem)) {return;}

        BlockState state = event.getTargetBlock();
        if (!canBreakWithStoneHands(state)) {return;}
        event.setCanHarvest(true);
    }
    @SubscribeEvent
    public static void extraBreakingSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();

        if (!SkillManager.hasSkill(player, PonySkills.EXTRA_BREAKING.id())) {return;}
        if (!player.getMainHandItem().isEmpty() && !(player.getMainHandItem().getItem() instanceof BlockItem)) {return;}

        BlockState state = event.getState();
        if (!canBreakWithStoneHands(state)) {return;}
        event.setNewSpeed(Math.max(event.getNewSpeed(), 7));
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void extraFortune(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof ServerPlayer player)) {return;}
        if (!SkillManager.hasSkill(player, PonySkills.EXTRA_FORTUNE.id())) {return;}

        ServerLevel level = event.getLevel();

        Holder.Reference<Enchantment> fortune = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);

        ItemStack originalTool = event.getTool();

        ItemStack fortuneTool;

        if (!originalTool.isEmpty()) {
            fortuneTool = originalTool.copy();
        } else {
            return;
        }

        int currentFortune = fortuneTool.getEnchantmentLevel(fortune);

        EnchantmentHelper.updateEnchantments(fortuneTool, enchantments -> enchantments.set(fortune, currentFortune + 1));
        List<ItemStack> newDrops = Block.getDrops(event.getState(), level, event.getPos(), event.getBlockEntity(), player, fortuneTool);
        event.getDrops().clear();

        for (ItemStack drop : newDrops) {
            ItemEntity entity = new ItemEntity(level, event.getPos().getX() + 0.5, event.getPos().getY() + 0.5, event.getPos().getZ() + 0.5, drop);
            event.getDrops().add(entity);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {return;}
        PonyAttributeManager.applyModifiers(player.getData(PonyAttachments.PONY_DATA),player);
    }

    @SubscribeEvent
    public static void earthPonyCropBoost(CropGrowEvent.Pre event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {return;}
        double EARTH_PONY_GROWTH_RADIUS = 120.0;

        BlockPos cropPos = event.getPos();
        boolean earthPonyNearby = level.players().stream()
                .filter(player -> player.distanceToSqr(Vec3.atCenterOf(cropPos)) <= EARTH_PONY_GROWTH_RADIUS * EARTH_PONY_GROWTH_RADIUS)
                .anyMatch(player -> {PonyData data = player.getData(PonyAttachments.PONY_DATA);return data.race() == PonyData.RACES.EARTH;});
        if (!earthPonyNearby) {return;}

        level.sendParticles(ParticleTypes.COMPOSTER,cropPos.getX(),cropPos.getY(),cropPos.getZ(),10,0.5,0.25,0.5,0.02 );
        event.setResult(CropGrowEvent.Pre.Result.GROW);
    }

    public static void discoverMark(PonyData data, ServerPlayer player) {
        player.setData(PonyAttachments.PONY_DATA, new PonyData(data.race(),data.cutieMark(), true));
        PonyTriggers.CUTIE_MARK_TRIGGER.get().trigger(player);
    }

    private static boolean canBreakWithStoneHands(BlockState state) {
        if (state.is(BlockTags.NEEDS_IRON_TOOL) || state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return false;
        }
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_AXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL)
                || state.is(BlockTags.MINEABLE_WITH_HOE);
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event) {

        DelayedSpellManager.tick();
    }
}
