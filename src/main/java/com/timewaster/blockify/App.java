package com.timewaster.blockify;

import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.player.PlayerJoinEvent;

public class App extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        populateMaterialMap();
        getServer().getPluginManager().registerEvents(this, this);
        registerBlockifierRecipe();
    }

    @EventHandler
    public void onItemMove(InventoryMoveItemEvent event) {
        Inventory source = event.getSource();
        Location sourceLocation = source.getLocation();
        Block sourceBlock = sourceLocation.getBlock();
        InventoryType destinationType = event.getDestination().getType();

        // check if source and destination block type are correct
        if (source.getType() == InventoryType.HOPPER
                && sourceBlock.getType() == Material.HOPPER
                && chestTypes.contains(destinationType)) {
            String sourceBlockName = ((Hopper) sourceBlock.getState()).getCustomName();

            // check if destination block is a Blockifier
            if (sourceBlockName != null && sourceBlockName.equals("Blockifier")) {
                Material stackBeingMovedMaterial = event.getItem().getType();

                // check if stack being moved is blockifiable
                if (materialTypes.contains(stackBeingMovedMaterial)) {
                    Integer blockAmount = materialTypesWithFourItems.contains(stackBeingMovedMaterial) ? 3 : 8;

                    // check if source inventory contains enough items of stack being moved
                    if (source.contains(stackBeingMovedMaterial, blockAmount)) {
                        source.removeItem(new ItemStack(stackBeingMovedMaterial, blockAmount));
                        event.setItem(new ItemStack(materialConversions.get(stackBeingMovedMaterial), 1));
                        sourceLocation.getWorld().spawnParticle(Particle.ELECTRIC_SPARK,
                                sourceLocation.add(0.5, 0.5, 0.5), 10);
                    } else { // if not cancel event to hold back items until enough items are available
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private void registerBlockifierRecipe() {
        // create a custom hopper version
        ItemStack blockifier = new ItemStack(Material.HOPPER, 1);
        ItemMeta itemmeta = blockifier.getItemMeta();
        itemmeta.setDisplayName("Blockifier");
        ArrayList<String> lore = new ArrayList<String>();
        itemmeta.setLore(lore);
        itemmeta.setCustomModelData(739);
        blockifier.setItemMeta(itemmeta);

        // create crafting recipe
        NamespacedKey recipeName = new NamespacedKey(this, "Blockifier");
        ShapedRecipe recipe = new ShapedRecipe(recipeName, blockifier);
        recipe.setCategory(CraftingBookCategory.REDSTONE);
        recipe.shape("IGR", "EHE", "RGI");
        recipe.setIngredient('I', Material.IRON_BLOCK);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        recipe.setIngredient('E', Material.EMERALD_BLOCK);
        recipe.setIngredient('H', Material.HOPPER);
        Bukkit.addRecipe(recipe);
    }

    // What items can be blockified
    // Criteria:
    // - The resulting block needs to be of the same type of material, so no
    // material conversions
    // - The blockifying must be doable by a player with a crafting table
    private static final Set<Material> materialTypes = Set.of(
            Material.AMETHYST_SHARD, // 4 > AMETHYST_BLOCK
            Material.BONE_MEAL, // 9 > BONE_BLOCK
            Material.CLAY_BALL, // 4 > CLAY
            Material.COAL, // 9 > COAL_BLOCK
            Material.COPPER_INGOT, // 9 > COPPER_BLOCK
            Material.DIAMOND, // 9 > DIAMOND_BLOCK
            Material.DRIED_KELP, // 9 > DRIED_KELP_BLOCK
            Material.EMERALD, // 9 > EMERALD_BLOCK
            Material.GLOWSTONE_DUST, // 4 > GLOWSTONE
            Material.GOLD_INGOT, // 9 > GOLD_BLOCK
            Material.IRON_INGOT, // 9 > IRON_BLOCK
            Material.LAPIS_LAZULI, // 9 > LAPIS_BLOCK
            Material.MELON_SLICE, // 9 > MELON
            Material.NETHER_BRICK, // 4 > NETHER_BRICKS
            Material.NETHER_WART, // 9 > NETHER_WART_BLOCK
            Material.NETHERITE_INGOT, // 9 > NETHERITE_BLOCK
            Material.POINTED_DRIPSTONE, // 4 > DRIPSTONE_BLOCK
            Material.PRISMARINE_SHARD, // 4 > PRISMARINE
            Material.QUARTZ, // 4 > QUARTZ_BLOCK
            Material.RAW_COPPER, // 9 > RAW_COPPER_BLOCK
            Material.RAW_GOLD, // 9 > RAW_GOLD_BLOCK
            Material.RAW_IRON, // 9 > RAW_IRON_BLOCK
            Material.REDSTONE, // 9 > REDSTONE_BLOCK
            Material.SLIME_BALL, // 9 > SLIME_BLOCK
            Material.SNOWBALL, // 4 > SNOW_BLOCK
            Material.WHEAT // 9 > HAY_BLOCK
    );

    // Materials that use 4 instead of 9 items to create a block
    private static final Set<Material> materialTypesWithFourItems = Set.of(
            Material.AMETHYST_SHARD,
            Material.CLAY_BALL,
            Material.GLOWSTONE_DUST,
            Material.NETHER_BRICK,
            Material.POINTED_DRIPSTONE,
            Material.PRISMARINE_SHARD,
            Material.QUARTZ,
            Material.SNOWBALL);

    // Allowed destination blocks
    private static final Set<InventoryType> chestTypes = Set.of(
            InventoryType.BARREL,
            InventoryType.CHEST,
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            InventoryType.HOPPER,
            InventoryType.SHULKER_BOX);

    // Map of materials to convert to
    private HashMap<Material, Material> materialConversions = new HashMap<Material, Material>();

    private void populateMaterialMap() {
        materialConversions.put(Material.AMETHYST_SHARD, Material.AMETHYST_BLOCK);
        materialConversions.put(Material.BONE_MEAL, Material.BONE_BLOCK);
        materialConversions.put(Material.CLAY_BALL, Material.CLAY);
        materialConversions.put(Material.COAL, Material.COAL_BLOCK);
        materialConversions.put(Material.COPPER_INGOT, Material.COPPER_BLOCK);
        materialConversions.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
        materialConversions.put(Material.DRIED_KELP, Material.DRIED_KELP_BLOCK);
        materialConversions.put(Material.EMERALD, Material.EMERALD_BLOCK);
        materialConversions.put(Material.GLOWSTONE_DUST, Material.GLOWSTONE);
        materialConversions.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
        materialConversions.put(Material.IRON_INGOT, Material.IRON_BLOCK);
        materialConversions.put(Material.LAPIS_LAZULI, Material.LAPIS_BLOCK);
        materialConversions.put(Material.MELON_SLICE, Material.MELON);
        materialConversions.put(Material.NETHER_BRICK, Material.NETHER_BRICKS);
        materialConversions.put(Material.NETHER_WART, Material.NETHER_WART_BLOCK);
        materialConversions.put(Material.NETHERITE_INGOT, Material.NETHERITE_BLOCK);
        materialConversions.put(Material.POINTED_DRIPSTONE, Material.DRIPSTONE_BLOCK);
        materialConversions.put(Material.PRISMARINE_SHARD, Material.PRISMARINE);
        materialConversions.put(Material.QUARTZ, Material.QUARTZ_BLOCK);
        materialConversions.put(Material.RAW_COPPER, Material.RAW_COPPER_BLOCK);
        materialConversions.put(Material.RAW_GOLD, Material.RAW_GOLD_BLOCK);
        materialConversions.put(Material.RAW_IRON, Material.RAW_IRON_BLOCK);
        materialConversions.put(Material.REDSTONE, Material.REDSTONE_BLOCK);
        materialConversions.put(Material.SLIME_BALL, Material.SLIME_BLOCK);
        materialConversions.put(Material.SNOWBALL, Material.SNOW_BLOCK);
        materialConversions.put(Material.WHEAT, Material.HAY_BLOCK);
    }
}
