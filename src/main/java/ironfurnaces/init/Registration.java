/*
 * Copyright 2025 pizzaatime and XenoMustache
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ironfurnaces.init;

import com.mojang.serialization.Codec;
import ironfurnaces.IronFurnaces;
import ironfurnaces.blocks.BlockWirelessEnergyHeater;
import ironfurnaces.blocks.furnaces.*;
import ironfurnaces.blocks.furnaces.other.BlockAllthemodiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockUnobtainiumFurnace;
import ironfurnaces.blocks.furnaces.other.BlockVibraniumFurnace;
import ironfurnaces.capability.PlayerFurnacesListProvider;
import ironfurnaces.capability.PlayerShowConfigProvider;
import ironfurnaces.container.BlockWirelessEnergyHeaterContainer;
import ironfurnaces.container.furnaces.*;
import ironfurnaces.container.furnaces.other.BlockAllthemodiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockUnobtainiumFurnaceContainer;
import ironfurnaces.container.furnaces.other.BlockVibraniumFurnaceContainer;
import ironfurnaces.items.*;
import ironfurnaces.items.augments.*;
import ironfurnaces.items.upgrades.*;
import ironfurnaces.recipes.GeneratorRecipe;
import ironfurnaces.recipes.SimpleGeneratorRecipe;
import ironfurnaces.tileentity.BlockWirelessEnergyHeaterTile;
import ironfurnaces.tileentity.furnaces.*;
import ironfurnaces.tileentity.furnaces.other.BlockAllthemodiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockUnobtainiumFurnaceTile;
import ironfurnaces.tileentity.furnaces.other.BlockVibraniumFurnaceTile;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.fml.ModList;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static ironfurnaces.IronFurnaces.MOD_ID;

public class Registration {

    // Registradores modernos para blocos e itens (gerenciam automaticamente o setId)
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, MOD_ID);
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MOD_ID);

    public static void init(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILES.register(modEventBus);
        CONTAINERS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        DATA_COMPONENT_TYPES.register(modEventBus);
        ATTACHMENT_TYPES.register(modEventBus);
    }

    // Serialization via INBTSerializable
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerFurnacesListProvider>> PLAYER_FURNACES_LIST = ATTACHMENT_TYPES.register(
            "furnaces_list", () -> AttachmentType.serializable(PlayerFurnacesListProvider::new).build());

    // Serialization via INBTSerializable
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<PlayerShowConfigProvider>> PLAYER_SHOW_CONFIG = ATTACHMENT_TYPES.register(
            "show_config", () -> AttachmentType.serializable(PlayerShowConfigProvider::new).build());

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> ENERGY = DATA_COMPONENT_TYPES.register("energy",
            () -> DataComponentType.<Integer>builder()
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
                    .build()
    );

    public static final Supplier<DataComponentType<CustomData>> FURNACE_SETTINGS = DATA_COMPONENT_TYPES.register("furnace_settings",
            () -> DataComponentType.<CustomData>builder()
                    .persistent(CustomData.CODEC)
                    .networkSynchronized(CustomData.STREAM_CODEC)
                    .build()
    );

    public static final String GENERATOR_ID = "generator_blasting";

    public static final class RecipeTypes {
        @SuppressWarnings("removal")
        public static mezz.jei.api.recipe.RecipeType<GeneratorRecipe> GENERATOR_BLASTING = mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_blasting", GeneratorRecipe.class);
        @SuppressWarnings("removal")
        public static mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> GENERATOR_SMOKING = mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_smoking", SimpleGeneratorRecipe.class);
        @SuppressWarnings("removal")
        public static mezz.jei.api.recipe.RecipeType<SimpleGeneratorRecipe> GENERATOR_REGULAR = mezz.jei.api.recipe.RecipeType.create(IronFurnaces.MOD_ID, "generator_regular", SimpleGeneratorRecipe.class);
    }

    public static Supplier<RecipeType<GeneratorRecipe>> GENERATOR_RECIPE_TYPE = RECIPE_TYPES.register(GENERATOR_ID, () -> new RecipeType<GeneratorRecipe>() {
        @Override
        public String toString() {
            return GENERATOR_ID;
        }
    });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<GeneratorRecipe>> GENERATOR_RECIPE_SERIALIZER =
            RECIPE_SERIALIZERS.register(GENERATOR_ID, () -> new RecipeSerializer<>(GeneratorRecipe.CODEC, GeneratorRecipe.STREAM_CODEC));

    // ========== IRON FURNACE ==========
    public static final DeferredHolder<Block, BlockIronFurnace> IRON_FURNACE = BLOCKS.registerBlock(
            BlockIronFurnace.IRON_FURNACE,
            BlockIronFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> IRON_FURNACE_ITEM = ITEMS.registerItem(
            BlockIronFurnace.IRON_FURNACE,
            props -> new ItemFurnace(IRON_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockIronFurnaceTile>> IRON_FURNACE_TILE = TILES.register(BlockIronFurnace.IRON_FURNACE, () -> new BlockEntityType<>(BlockIronFurnaceTile::new, IRON_FURNACE.get()));
    public static final Supplier<MenuType<BlockIronFurnaceContainer>> IRON_FURNACE_CONTAINER = CONTAINERS.register(BlockIronFurnace.IRON_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockIronFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== GOLD FURNACE ==========
    public static final DeferredHolder<Block, BlockGoldFurnace> GOLD_FURNACE = BLOCKS.registerBlock(
            BlockGoldFurnace.GOLD_FURNACE,
            BlockGoldFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> GOLD_FURNACE_ITEM = ITEMS.registerItem(
            BlockGoldFurnace.GOLD_FURNACE,
            props -> new ItemFurnace(GOLD_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockGoldFurnaceTile>> GOLD_FURNACE_TILE = TILES.register(BlockGoldFurnace.GOLD_FURNACE, () -> new BlockEntityType<>(BlockGoldFurnaceTile::new, GOLD_FURNACE.get()));
    public static final Supplier<MenuType<BlockGoldFurnaceContainer>> GOLD_FURNACE_CONTAINER = CONTAINERS.register(BlockGoldFurnace.GOLD_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockGoldFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== DIAMOND FURNACE ==========
    public static final DeferredHolder<Block, BlockDiamondFurnace> DIAMOND_FURNACE = BLOCKS.registerBlock(
            BlockDiamondFurnace.DIAMOND_FURNACE,
            BlockDiamondFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> DIAMOND_FURNACE_ITEM = ITEMS.registerItem(
            BlockDiamondFurnace.DIAMOND_FURNACE,
            props -> new ItemFurnace(DIAMOND_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockDiamondFurnaceTile>> DIAMOND_FURNACE_TILE = TILES.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> new BlockEntityType<>(BlockDiamondFurnaceTile::new, DIAMOND_FURNACE.get()));
    public static final Supplier<MenuType<BlockDiamondFurnaceContainer>> DIAMOND_FURNACE_CONTAINER = CONTAINERS.register(BlockDiamondFurnace.DIAMOND_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockDiamondFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== EMERALD FURNACE ==========
    public static final DeferredHolder<Block, BlockEmeraldFurnace> EMERALD_FURNACE = BLOCKS.registerBlock(
            BlockEmeraldFurnace.EMERALD_FURNACE,
            BlockEmeraldFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> EMERALD_FURNACE_ITEM = ITEMS.registerItem(
            BlockEmeraldFurnace.EMERALD_FURNACE,
            props -> new ItemFurnace(EMERALD_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockEmeraldFurnaceTile>> EMERALD_FURNACE_TILE = TILES.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> new BlockEntityType<>(BlockEmeraldFurnaceTile::new, EMERALD_FURNACE.get()));
    public static final Supplier<MenuType<BlockEmeraldFurnaceContainer>> EMERALD_FURNACE_CONTAINER = CONTAINERS.register(BlockEmeraldFurnace.EMERALD_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockEmeraldFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== OBSIDIAN FURNACE ==========
    public static final DeferredHolder<Block, BlockObsidianFurnace> OBSIDIAN_FURNACE = BLOCKS.registerBlock(
            BlockObsidianFurnace.OBSIDIAN_FURNACE,
            BlockObsidianFurnace::new,
            props -> props.strength(50.0F, 1200.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> OBSIDIAN_FURNACE_ITEM = ITEMS.registerItem(
            BlockObsidianFurnace.OBSIDIAN_FURNACE,
            props -> new ItemFurnace(OBSIDIAN_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockObsidianFurnaceTile>> OBSIDIAN_FURNACE_TILE = TILES.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> new BlockEntityType<>(BlockObsidianFurnaceTile::new, OBSIDIAN_FURNACE.get()));
    public static final Supplier<MenuType<BlockObsidianFurnaceContainer>> OBSIDIAN_FURNACE_CONTAINER = CONTAINERS.register(BlockObsidianFurnace.OBSIDIAN_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockObsidianFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== CRYSTAL FURNACE ==========
    public static final DeferredHolder<Block, BlockCrystalFurnace> CRYSTAL_FURNACE = BLOCKS.registerBlock(
            BlockCrystalFurnace.CRYSTAL_FURNACE,
            BlockCrystalFurnace::new,
            props -> props.strength(0.3F, 0.3F).noOcclusion().mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> CRYSTAL_FURNACE_ITEM = ITEMS.registerItem(
            BlockCrystalFurnace.CRYSTAL_FURNACE,
            props -> new ItemFurnace(CRYSTAL_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockCrystalFurnaceTile>> CRYSTAL_FURNACE_TILE = TILES.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> new BlockEntityType<>(BlockCrystalFurnaceTile::new, CRYSTAL_FURNACE.get()));
    public static final Supplier<MenuType<BlockCrystalFurnaceContainer>> CRYSTAL_FURNACE_CONTAINER = CONTAINERS.register(BlockCrystalFurnace.CRYSTAL_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockCrystalFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== NETHERITE FURNACE ==========
    public static final DeferredHolder<Block, BlockNetheriteFurnace> NETHERITE_FURNACE = BLOCKS.registerBlock(
            BlockNetheriteFurnace.NETHERITE_FURNACE,
            BlockNetheriteFurnace::new,
            props -> props.strength(50.0F, 1200.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> NETHERITE_FURNACE_ITEM = ITEMS.registerItem(
            BlockNetheriteFurnace.NETHERITE_FURNACE,
            props -> new ItemFurnace(NETHERITE_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockNetheriteFurnaceTile>> NETHERITE_FURNACE_TILE = TILES.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> new BlockEntityType<>(BlockNetheriteFurnaceTile::new, NETHERITE_FURNACE.get()));
    public static final Supplier<MenuType<BlockNetheriteFurnaceContainer>> NETHERITE_FURNACE_CONTAINER = CONTAINERS.register(BlockNetheriteFurnace.NETHERITE_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockNetheriteFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== COPPER FURNACE ==========
    public static final DeferredHolder<Block, BlockCopperFurnace> COPPER_FURNACE = BLOCKS.registerBlock(
            BlockCopperFurnace.COPPER_FURNACE,
            BlockCopperFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> COPPER_FURNACE_ITEM = ITEMS.registerItem(
            BlockCopperFurnace.COPPER_FURNACE,
            props -> new ItemFurnace(COPPER_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockCopperFurnaceTile>> COPPER_FURNACE_TILE = TILES.register(BlockCopperFurnace.COPPER_FURNACE, () -> new BlockEntityType<>(BlockCopperFurnaceTile::new, COPPER_FURNACE.get()));
    public static final Supplier<MenuType<BlockCopperFurnaceContainer>> COPPER_FURNACE_CONTAINER = CONTAINERS.register(BlockCopperFurnace.COPPER_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockCopperFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== SILVER FURNACE ==========
    public static final DeferredHolder<Block, BlockSilverFurnace> SILVER_FURNACE = BLOCKS.registerBlock(
            BlockSilverFurnace.SILVER_FURNACE,
            BlockSilverFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemFurnace> SILVER_FURNACE_ITEM = ITEMS.registerItem(
            BlockSilverFurnace.SILVER_FURNACE,
            props -> new ItemFurnace(SILVER_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockSilverFurnaceTile>> SILVER_FURNACE_TILE = TILES.register(BlockSilverFurnace.SILVER_FURNACE, () -> new BlockEntityType<>(BlockSilverFurnaceTile::new, SILVER_FURNACE.get()));
    public static final Supplier<MenuType<BlockSilverFurnaceContainer>> SILVER_FURNACE_CONTAINER = CONTAINERS.register(BlockSilverFurnace.SILVER_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockSilverFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== UPGRADE ITEMS ==========
    public static final DeferredHolder<Item, ItemUpgradeIron> IRON_UPGRADE = ITEMS.registerItem("upgrade_iron", ItemUpgradeIron::new);
    public static final DeferredHolder<Item, ItemUpgradeGold> GOLD_UPGRADE = ITEMS.registerItem("upgrade_gold", ItemUpgradeGold::new);
    public static final DeferredHolder<Item, ItemUpgradeDiamond> DIAMOND_UPGRADE = ITEMS.registerItem("upgrade_diamond", ItemUpgradeDiamond::new);
    public static final DeferredHolder<Item, ItemUpgradeEmerald> EMERALD_UPGRADE = ITEMS.registerItem("upgrade_emerald", ItemUpgradeEmerald::new);
    public static final DeferredHolder<Item, ItemUpgradeObsidian> OBSIDIAN_UPGRADE = ITEMS.registerItem("upgrade_obsidian", ItemUpgradeObsidian::new);
    public static final DeferredHolder<Item, ItemUpgradeCrystal> CRYSTAL_UPGRADE = ITEMS.registerItem("upgrade_crystal", ItemUpgradeCrystal::new);
    public static final DeferredHolder<Item, ItemUpgradeNetherite> NETHERITE_UPGRADE = ITEMS.registerItem("upgrade_netherite", ItemUpgradeNetherite::new);
    public static final DeferredHolder<Item, ItemUpgradeCopper> COPPER_UPGRADE = ITEMS.registerItem("upgrade_copper", ItemUpgradeCopper::new);
    public static final DeferredHolder<Item, ItemUpgradeSilver> SILVER_UPGRADE = ITEMS.registerItem("upgrade_silver", ItemUpgradeSilver::new);
    public static final DeferredHolder<Item, ItemUpgradeObsidian2> OBSIDIAN2_UPGRADE = ITEMS.registerItem("upgrade_obsidian2", ItemUpgradeObsidian2::new);
    public static final DeferredHolder<Item, ItemUpgradeIron2> IRON2_UPGRADE = ITEMS.registerItem("upgrade_iron2", ItemUpgradeIron2::new);
    public static final DeferredHolder<Item, ItemUpgradeGold2> GOLD2_UPGRADE = ITEMS.registerItem("upgrade_gold2", ItemUpgradeGold2::new);
    public static final DeferredHolder<Item, ItemUpgradeSilver2> SILVER2_UPGRADE = ITEMS.registerItem("upgrade_silver2", ItemUpgradeSilver2::new);

    // ========== ALLTHEMODIUM FURNACE ==========
    public static final DeferredHolder<Block, BlockAllthemodiumFurnace> ALLTHEMODIUM_FURNACE = BLOCKS.registerBlock(
            BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE,
            BlockAllthemodiumFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemAllthemodiumFurnace> ALLTHEMODIUM_FURNACE_ITEM = ITEMS.registerItem(
            BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE,
            props -> new ItemAllthemodiumFurnace(ALLTHEMODIUM_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockAllthemodiumFurnaceTile>> ALLTHEMODIUM_FURNACE_TILE = TILES.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> new BlockEntityType<>(BlockAllthemodiumFurnaceTile::new, ALLTHEMODIUM_FURNACE.get()));
    public static final Supplier<MenuType<BlockAllthemodiumFurnaceContainer>> ALLTHEMODIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockAllthemodiumFurnace.ALLTHEMODIUM_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockAllthemodiumFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== VIBRANIUM FURNACE ==========
    public static final DeferredHolder<Block, BlockVibraniumFurnace> VIBRANIUM_FURNACE = BLOCKS.registerBlock(
            BlockVibraniumFurnace.VIBRANIUM_FURNACE,
            BlockVibraniumFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemVibraniumFurnace> VIBRANIUM_FURNACE_ITEM = ITEMS.registerItem(
            BlockVibraniumFurnace.VIBRANIUM_FURNACE,
            props -> new ItemVibraniumFurnace(VIBRANIUM_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockVibraniumFurnaceTile>> VIBRANIUM_FURNACE_TILE = TILES.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> new BlockEntityType<>(BlockVibraniumFurnaceTile::new, VIBRANIUM_FURNACE.get()));
    public static final Supplier<MenuType<BlockVibraniumFurnaceContainer>> VIBRANIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockVibraniumFurnace.VIBRANIUM_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockVibraniumFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== UNOBTAINIUM FURNACE ==========
    public static final DeferredHolder<Block, BlockUnobtainiumFurnace> UNOBTAINIUM_FURNACE = BLOCKS.registerBlock(
            BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE,
            BlockUnobtainiumFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemUnobtainiumFurnace> UNOBTAINIUM_FURNACE_ITEM = ITEMS.registerItem(
            BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE,
            props -> new ItemUnobtainiumFurnace(UNOBTAINIUM_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockUnobtainiumFurnaceTile>> UNOBTAINIUM_FURNACE_TILE = TILES.register(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE, () -> new BlockEntityType<>(BlockUnobtainiumFurnaceTile::new, UNOBTAINIUM_FURNACE.get()));
    public static final Supplier<MenuType<BlockUnobtainiumFurnaceContainer>> UNOBTAINIUM_FURNACE_CONTAINER = CONTAINERS.register(BlockUnobtainiumFurnace.UNOBTAINIUM_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockUnobtainiumFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== UPGRADES (AT) ==========
    public static final DeferredHolder<Item, ItemUpgradeAllthemodium> ALLTHEMODIUM_UPGRADE = ITEMS.registerItem("upgrade_allthemodium", ItemUpgradeAllthemodium::new);
    public static final DeferredHolder<Item, ItemUpgradeVibranium> VIBRANIUM_UPGRADE = ITEMS.registerItem("upgrade_vibranium", ItemUpgradeVibranium::new);
    public static final DeferredHolder<Item, ItemUpgradeUnobtainium> UNOBTAINIUM_UPGRADE = ITEMS.registerItem("upgrade_unobtainium", ItemUpgradeUnobtainium::new);

    // ========== WIRELESS ENERGY HEATER ==========
    public static final DeferredHolder<Block, BlockWirelessEnergyHeater> HEATER = BLOCKS.registerBlock(
            BlockWirelessEnergyHeater.HEATER,
            BlockWirelessEnergyHeater::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, BlockItemHeater> HEATER_ITEM = ITEMS.registerItem(
            BlockWirelessEnergyHeater.HEATER,
            props -> new BlockItemHeater(HEATER.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockWirelessEnergyHeaterTile>> HEATER_TILE = TILES.register(BlockWirelessEnergyHeater.HEATER, () -> new BlockEntityType<>(BlockWirelessEnergyHeaterTile::new, HEATER.get()));
    public static final Supplier<MenuType<BlockWirelessEnergyHeaterContainer>> HEATER_CONTAINER = CONTAINERS.register(BlockWirelessEnergyHeater.HEATER, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockWirelessEnergyHeaterContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    public static final DeferredHolder<Item, ItemHeater> ITEM_HEATER = ITEMS.registerItem("item_heater", ItemHeater::new);

    // ========== AUGMENTS ==========
    public static final DeferredHolder<Item, ItemAugmentBlasting> BLASTING_AUGMENT = ITEMS.registerItem("augment_blasting", ItemAugmentBlasting::new);
    public static final DeferredHolder<Item, ItemAugmentSmoking> SMOKING_AUGMENT = ITEMS.registerItem("augment_smoking", ItemAugmentSmoking::new);
    public static final DeferredHolder<Item, ItemAugmentFactory> FACTORY_AUGMENT = ITEMS.registerItem("augment_factory", ItemAugmentFactory::new);
    public static final DeferredHolder<Item, ItemAugmentGenerator> GENERATOR_AUGMENT = ITEMS.registerItem("augment_generator", ItemAugmentGenerator::new);
    public static final DeferredHolder<Item, ItemAugmentSpeed> SPEED_AUGMENT = ITEMS.registerItem("augment_speed", ItemAugmentSpeed::new);
    public static final DeferredHolder<Item, ItemAugmentFuel> FUEL_AUGMENT = ITEMS.registerItem("augment_fuel", ItemAugmentFuel::new);

    // ========== MISC ITEMS ==========
    public static final DeferredHolder<Item, ItemSpooky> ITEM_SPOOKY = ITEMS.registerItem("item_spooky", ItemSpooky::new);
    public static final DeferredHolder<Item, ItemXmas> ITEM_XMAS = ITEMS.registerItem("item_xmas", ItemXmas::new);
    public static final DeferredHolder<Item, ItemFurnaceCopy> ITEM_COPY = ITEMS.registerItem("item_copy", props -> new ItemFurnaceCopy(props.stacksTo(1)));
    public static final DeferredHolder<Item, Item> RAINBOW_CORE = ITEMS.registerItem("rainbow_core", Item::new);
    public static final DeferredHolder<Item, Item> RAINBOW_PLATING = ITEMS.registerItem("rainbow_plating", Item::new);
    public static final DeferredHolder<Item, ItemRainbowCoal> RAINBOW_COAL = ITEMS.registerItem("rainbow_coal", props -> new ItemRainbowCoal(props.stacksTo(1)));

    // ========== MILLION FURNACE ==========
    public static final DeferredHolder<Block, BlockMillionFurnace> MILLION_FURNACE = BLOCKS.registerBlock(
            BlockMillionFurnace.MILLION_FURNACE,
            BlockMillionFurnace::new,
            props -> props.strength(3.0F, 3.0F).mapColor(MapColor.METAL)
    );
    public static final DeferredHolder<Item, ItemMillionFurnace> MILLION_FURNACE_ITEM = ITEMS.registerItem(
            BlockMillionFurnace.MILLION_FURNACE,
            props -> new ItemMillionFurnace(MILLION_FURNACE.get(), props.useBlockDescriptionPrefix())
    );
    public static final Supplier<BlockEntityType<BlockMillionFurnaceTile>> MILLION_FURNACE_TILE = TILES.register(BlockMillionFurnace.MILLION_FURNACE, () -> new BlockEntityType<>(BlockMillionFurnaceTile::new, MILLION_FURNACE.get()));
    public static final Supplier<MenuType<BlockMillionFurnaceContainer>> MILLION_FURNACE_CONTAINER = CONTAINERS.register(BlockMillionFurnace.MILLION_FURNACE, () -> IMenuTypeExtension.create(
            (windowId, playerInv, extraData) -> new BlockMillionFurnaceContainer(windowId, playerInv.player.level(), extraData.readBlockPos(), playerInv, playerInv.player)));

    // ========== CREATIVE TAB ==========
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> tabIronFurnaces = CREATIVE_MODE_TABS.register("ironfurnaces_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> IRON_FURNACE.get().asItem().getDefaultInstance())
            .title(Component.translatable("itemGroup.ironfurnaces"))
            .displayItems((parameters, output) -> {
                output.accept(Registration.IRON_FURNACE_ITEM.get());
                output.accept(Registration.GOLD_FURNACE_ITEM.get());
                output.accept(Registration.DIAMOND_FURNACE_ITEM.get());
                output.accept(Registration.EMERALD_FURNACE_ITEM.get());
                output.accept(Registration.OBSIDIAN_FURNACE_ITEM.get());
                output.accept(Registration.CRYSTAL_FURNACE_ITEM.get());
                output.accept(Registration.NETHERITE_FURNACE_ITEM.get());
                output.accept(Registration.COPPER_FURNACE_ITEM.get());
                output.accept(Registration.SILVER_FURNACE_ITEM.get());

                output.accept(Registration.IRON_UPGRADE.get());
                output.accept(Registration.GOLD_UPGRADE.get());
                output.accept(Registration.DIAMOND_UPGRADE.get());
                output.accept(Registration.EMERALD_UPGRADE.get());
                output.accept(Registration.OBSIDIAN_UPGRADE.get());
                output.accept(Registration.CRYSTAL_UPGRADE.get());
                output.accept(Registration.NETHERITE_UPGRADE.get());
                output.accept(Registration.COPPER_UPGRADE.get());
                output.accept(Registration.SILVER_UPGRADE.get());

                output.accept(Registration.OBSIDIAN2_UPGRADE.get());
                output.accept(Registration.IRON2_UPGRADE.get());
                output.accept(Registration.GOLD2_UPGRADE.get());
                output.accept(Registration.SILVER2_UPGRADE.get());
                output.accept(Registration.HEATER_ITEM.get());
                output.accept(Registration.ITEM_HEATER.get());
                output.accept(Registration.BLASTING_AUGMENT.get());
                output.accept(Registration.SMOKING_AUGMENT.get());
                output.accept(Registration.FACTORY_AUGMENT.get());

                output.accept(Registration.GENERATOR_AUGMENT.get());
                output.accept(Registration.SPEED_AUGMENT.get());
                output.accept(Registration.FUEL_AUGMENT.get());
                output.accept(Registration.ITEM_SPOOKY.get());
                output.accept(Registration.ITEM_XMAS.get());
                output.accept(Registration.ITEM_COPY.get());
                output.accept(Registration.RAINBOW_CORE.get());
                output.accept(Registration.RAINBOW_PLATING.get());

                output.accept(Registration.MILLION_FURNACE_ITEM.get());
                output.accept(Registration.RAINBOW_COAL.get());

                if (ModList.get().isLoaded("allthemodium")) {
                    output.accept(Registration.ALLTHEMODIUM_FURNACE_ITEM.get());
                    output.accept(Registration.VIBRANIUM_FURNACE_ITEM.get());
                    output.accept(Registration.UNOBTAINIUM_FURNACE_ITEM.get());
                    output.accept(Registration.ALLTHEMODIUM_UPGRADE.get());
                    output.accept(Registration.VIBRANIUM_UPGRADE.get());
                    output.accept(Registration.UNOBTAINIUM_UPGRADE.get());
                }
            }).build());
}
