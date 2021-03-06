package com.github.sokyranthedragon.mia.integrations.futuremc;

import com.gendeathrow.hatchery.api.crafting.ShredderRecipe;
import com.gendeathrow.hatchery.block.shredder.ShredderTileEntity;
import com.gendeathrow.hatchery.core.config.ConfigLootHandler;
import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.integrations.hatchery.IHatcheryIntegration;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import thedarkcolour.futuremc.init.Init;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

import static com.github.sokyranthedragon.mia.integrations.hatchery.Hatchery.getDrop;

@MethodsReturnNonnullByDefault
class HatcheryFutureMcIntegration implements IHatcheryIntegration
{
    private final boolean modEnabled;
    
    HatcheryFutureMcIntegration(boolean modEnabled)
    {
        this.modEnabled = modEnabled;
    }
    
    @Override
    public void registerShredder()
    {
        ShredderTileEntity.shredderRecipes.add(new ShredderRecipe(new ItemStack(Init.LILY_OF_VALLEY), new ItemStack(Init.DYES, 2, 0)));
        ShredderTileEntity.shredderRecipes.add(new ShredderRecipe(new ItemStack(Init.CORNFLOWER), new ItemStack(Init.DYES, 2, 1)));
        ShredderTileEntity.shredderRecipes.add(new ShredderRecipe(new ItemStack(Init.WITHER_ROSE), new ItemStack(Init.DYES, 2, 3)));
    }
    
    @Override
    public boolean isModEnabled()
    {
        return modEnabled;
    }
    
    @Override
    public int getCurrentLootVersion()
    {
        return 0;
    }
    
    @Nonnull
    @Override
    public List<ConfigLootHandler.ItemDrop> getDefaultEggDrops()
    {
        List<ConfigLootHandler.ItemDrop> drops = new LinkedList<>();
        
        drops.add(getDrop(Init.TRIDENT, 1));
        drops.add(getDrop(Init.BLUE_ICE, 1));
        
        return drops;
    }
    
    @Override
    public ModIds getModId()
    {
        return ModIds.FUTURE_MC;
    }
}
