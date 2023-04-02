# Blockify

![](logo.png 'Blockifier Logo')

A Spigot/~~Paper~~ plugin implementing a Hopper that turns items into their "Block of ..." form.

Paper has a bug in an inventory implementation, it doesn't work on Paper at the moment. Bug report is opened.

## What it does

This Spigot/~~Paper~~ plugin implements a new Hopper type called Blockifier, which for instance turns 9 "Iron Ingots" into 1 "Block of Iron" (A complete list of compressions possible is below).

Blockifier holds back items that have a block form until enough items are collected to create the block. Once the required number of items are collected, the block is then created and pushed into the destination Chest, Hopper, Dropper, etc.

Since items that have a block form are held back if there aren't enough present, it is possible to clog Blockifier until it cannot pull new items. Therefore, it is recommended to use Blockifier after a sorter.

Here is Blockifier's crafting recipe:

![Blockifier Recipe](recipe.png 'Blockifier Recipe')

Blockifier looks like a Hopper, unfortunately Bukkit has no way to change the appearance of a placed block. When you interact with Blockifier the name will be "Blockifier" and not "Item Hopper".

## Installation

Copy [Blockify 1.0.1](https://github.com/SebiTimeWaster/Blockify/raw/main/target/Blockify_1.0.1-MC_1.19.3+.jar) into the `/plugin` directory of your Spigot or ~~Paper~~ server and restart it.

### Compatibility

[Blockify 1.0.1](https://github.com/SebiTimeWaster/Blockify/raw/main/target/Blockify_1.0.1-MC_1.19.3+.jar) supports [Minecraft 1.19.3](https://minecraft.fandom.com/wiki/Java_Edition_1.19.3) and above.

## What items are blockified?

Criteria used to select what compressions are allowed:

-   The resulting block form needs to be of the same type of material (I.e. Iron Ingot -> Iron Block)
-   The compression must be doable by a player with a crafting table

This is a list of all compressions Blockifier does:

| Source Item       | Amount | Resulting Block   |
| ----------------- | :----: | ----------------- |
| AMETHYST_SHARD    |   4    | AMETHYST_BLOCK    |
| BONE_MEAL         |   9    | BONE_BLOCK        |
| CLAY_BALL         |   4    | CLAY              |
| COAL              |   9    | COAL_BLOCK        |
| COPPER_INGOT      |   9    | COPPER_BLOCK      |
| DIAMOND           |   9    | DIAMOND_BLOCK     |
| DRIED_KELP        |   9    | DRIED_KELP_BLOCK  |
| EMERALD           |   9    | EMERALD_BLOCK     |
| GLOWSTONE_DUST    |   4    | GLOWSTONE         |
| GOLD_INGOT        |   9    | GOLD_BLOCK        |
| HONEYCOMB         |   4    | HONEYCOMB_BLOCK   |
| IRON_INGOT        |   9    | IRON_BLOCK        |
| LAPIS_LAZULI      |   9    | LAPIS_BLOCK       |
| MELON_SLICE       |   9    | MELON             |
| NETHER_BRICK      |   4    | NETHER_BRICKS     |
| NETHER_WART       |   9    | NETHER_WART_BLOCK |
| NETHERITE_INGOT   |   9    | NETHERITE_BLOCK   |
| POINTED_DRIPSTONE |   4    | DRIPSTONE_BLOCK   |
| PRISMARINE_SHARD  |   4    | PRISMARINE        |
| QUARTZ            |   4    | QUARTZ_BLOCK      |
| RAW_COPPER        |   9    | RAW_COPPER_BLOCK  |
| RAW_GOLD          |   9    | RAW_GOLD_BLOCK    |
| RAW_IRON          |   9    | RAW_IRON_BLOCK    |
| REDSTONE          |   9    | REDSTONE_BLOCK    |
| SLIME_BALL        |   9    | SLIME_BLOCK       |
| SNOWBALL          |   4    | SNOW_BLOCK        |
| WHEAT             |   9    | HAY_BLOCK         |

These compressions are only performed when the Hopper is pointing to a target that is a BARREL, CHEST, DISPENSER, DROPPER, HOPPER, or a SHULKER_BOX, but not if any other block is the target or a player interacts with the inventory of Blockifier.

Items that are not in this list are transported like it is a normal Hopper.

## Version history

v1.0.1 - Minor additions

-   Added missing HONEYCOMB compression
-   Better readme descriptions
-   Added version history

v1.0.0 - Initial version

-   Material compressions for 26 materials
-   Crafting recipe for Blockifier
-   Custom name for Blockifier
