package com.github.sokyranthedragon.mia.integrations.dungeontactics;

import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.integrations.jer.IJerIntegration;
import jeresources.api.IDungeonRegistry;
import jeresources.api.IMobRegistry;
import jeresources.api.IPlantRegistry;
import jeresources.api.conditionals.LightLevel;
import jeresources.api.drop.LootDrop;
import jeresources.api.drop.PlantDrop;
import jeresources.entry.MobEntry;
import jeresources.util.LootTableHelper;
import jeresources.util.MobTableBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.LootTableManager;
import pegbeard.dungeontactics.entities.DTEntityTowerGuardian;
import pegbeard.dungeontactics.handlers.DTBlocks;
import pegbeard.dungeontactics.handlers.DTConfigHandler;
import pegbeard.dungeontactics.handlers.DTItems;
import pegbeard.dungeontactics.handlers.DTLoots;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Set;

@ParametersAreNonnullByDefault
class JerDungeonTacticsIntegration implements IJerIntegration
{
    @Nonnull
    @Override
    public Set<Class<? extends EntityLivingBase>> addMobs(MobTableBuilder builder, Set<Class<? extends EntityLivingBase>> ignoreMobOverrides)
    {
        builder.add(DTLoots.TOWERGUARDIAN_LOOT, DTEntityTowerGuardian.class);
        
        return Collections.singleton(
                DTEntityTowerGuardian.class
        );
    }
    
    @Override
    public void configureMob(ResourceLocation resource, EntityLivingBase entity, LootTableManager manager, IMobRegistry mobRegistry)
    {
        LootTable loot = manager.getLootTableFromLocation(resource);
        LootDrop[] drops = LootTableHelper.toDrops(loot).toArray(new LootDrop[0]);
        if (entity instanceof DTEntityTowerGuardian)
            mobRegistry.register(entity, LightLevel.any, drops);
    }
    
    @Override
    public void overrideExistingMobDrops(MobEntry mobEntry)
    {
        if (mobEntry.getEntity() instanceof EntityWither || mobEntry.getEntity() instanceof EntityDragon || mobEntry.getEntity() instanceof EntityGuardian)
        {
            if (DTConfigHandler.hearts != 0)
            {
                mobEntry.addDrops(new LootDrop(DTItems.HEART_JAR, 0, 2));
                mobEntry.addDrops(new LootDrop(DTItems.HEART_GOLDEN, 0, 1));
            }
            
            if (mobEntry.getEntity() instanceof EntityWither)
            {
                float chance = DTConfigHandler.boneCharms / 100f;
                float charmChance = chance * 2 / 10f;
                
                mobEntry.addDrop(new LootDrop(DTItems.PHYLACTERY, chance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_BARREN, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_BLEAK, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_DARKENED, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_EMACIATED, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_FAMINE, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_HEAVY, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_SAPPING, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_SEARING, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_TOXIC, charmChance));
                mobEntry.addDrop(new LootDrop(DTItems.CHARM_UNINTELLIGIBLE, charmChance));
            }
        }
//        if (mobEntry.getEntity() instanceof EntityWitch || mobEntry.getEntity() instanceof EntityEvoker)
//        {
//            List<ItemStack> injected = Arrays.asList(new ItemStack(DTItems.MAGIC_POWDER), new ItemStack(DTItems.MAGIC_SCROLL));
//            JerOverrideHelper.removeDuplicateEntries(injected, mobEntry.getDropsItemStacks());
//
//            for (ItemStack item : injected)
//            {
//                mobEntry.addDrops(new LootDrop(item));
//            }
//        }
    }
    
    @Override
    public void addDungeonLoot(IDungeonRegistry dungeonRegistry)
    {
        final String dungeon = "chests/dt_dungeon";
        final String tower = "chests/dt_tower";
        final String miniBunker = "chests/dt_mini_bunker";
        final String pirateShip = "chests/dt_pirate_ship";
        final String gypsyWagon = "chests/dt_gypsy_wagon";
        final String wizardTower = "chests/dt_wizard_tower";
        
        dungeonRegistry.registerCategory(dungeon, "mia.jer.dungeon.dt_dungeon");
        dungeonRegistry.registerCategory(tower, "mia.jer.dungeon.dt_tower");
        dungeonRegistry.registerCategory(miniBunker, "mia.jer.dungeon.dt_mini_bunker");
        dungeonRegistry.registerCategory(pirateShip, "mia.jer.dungeon.dt_pirate_ship");
        dungeonRegistry.registerCategory(gypsyWagon, "mia.jer.dungeon.dt_gypsy_wagon");
        dungeonRegistry.registerCategory(wizardTower, "mia.jer.dungeon.dt_wizard_tower");
        
        dungeonRegistry.registerChest(dungeon, LootTableList.CHESTS_NETHER_BRIDGE);
        dungeonRegistry.registerChest(dungeon, LootTableList.CHESTS_SIMPLE_DUNGEON);
        
        dungeonRegistry.registerChest(tower, LootTableList.CHESTS_NETHER_BRIDGE);
        dungeonRegistry.registerChest(tower, LootTableList.CHESTS_SIMPLE_DUNGEON);
        dungeonRegistry.registerChest(tower, LootTableList.CHESTS_SPAWN_BONUS_CHEST);
        dungeonRegistry.registerChest(tower, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
        
        dungeonRegistry.registerChest(miniBunker, LootTableList.CHESTS_STRONGHOLD_CORRIDOR);
        
        dungeonRegistry.registerChest(pirateShip, LootTableList.CHESTS_VILLAGE_BLACKSMITH);
        dungeonRegistry.registerChest(pirateShip, LootTableList.CHESTS_STRONGHOLD_CORRIDOR);
        dungeonRegistry.registerChest(pirateShip, LootTableList.CHESTS_SIMPLE_DUNGEON);
        
        dungeonRegistry.registerChest(gypsyWagon, DTLoots.WAGGON_LOOT);
        
        dungeonRegistry.registerChest(wizardTower, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
        dungeonRegistry.registerChest(wizardTower, DTLoots.TOWER_INGREDIANTS);
        dungeonRegistry.registerChest(wizardTower, DTLoots.TOWER_MAGIC);
        dungeonRegistry.registerChest(wizardTower, LootTableList.CHESTS_STRONGHOLD_LIBRARY);
    }
    
    @Override
    public void addPlantDrops(IPlantRegistry plantRegistry)
    {
        plantRegistry.registerWithSoil(
                new ItemStack(DTBlocks.CHERRYBOMB_BUSH),
                Blocks.DIRT.getDefaultState(),
                new PlantDrop(new ItemStack(DTItems.CHERRYBOMB), 1, 3));
        plantRegistry.registerWithSoil(
                new ItemStack(DTBlocks.INCINDIBERRY_BUSH),
                Blocks.DIRT.getDefaultState(),
                new PlantDrop(new ItemStack(DTItems.INCINDIBERRY), 1, 3));
        plantRegistry.registerWithSoil(
                new ItemStack(DTBlocks.GLOWCURRENT_BUSH),
                Blocks.DIRT.getDefaultState(),
                new PlantDrop(new ItemStack(DTItems.GLOWCURRENT), 1, 3));
    }
    
    @Nonnull
    @Override
    public ModIds getModId()
    {
        return ModIds.DUNGEON_TACTICS;
    }
}
