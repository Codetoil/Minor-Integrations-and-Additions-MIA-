package com.github.sokyranthedragon.mia.integrations.tconstruct;

import com.github.sokyranthedragon.mia.Mia;
import com.github.sokyranthedragon.mia.config.MiaConfig;
import com.github.sokyranthedragon.mia.integrations.ModIds;
import com.github.sokyranthedragon.mia.integrations.base.IBaseMod;
import com.github.sokyranthedragon.mia.integrations.base.IModIntegration;
import net.minecraft.init.Items;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import static com.github.sokyranthedragon.mia.config.TConstructConfiguration.*;

public class TinkersConstruct implements IBaseMod
{
    private List<ITConstructIntegration> modIntegrations = new LinkedList<>();
    
    @Override
    public void addIntegration(IModIntegration integration)
    {
        if (!externalIntegrationsEnabled)
            return;
        
        if (integration instanceof ITConstructIntegration)
        {
            modIntegrations.add((ITConstructIntegration) integration);
            return;
        }
        
        Mia.LOGGER.warn("Incorrect TConstruct integration with id of " + integration.getModId() + ": " + integration.toString());
    }
    
    @Override
    public void register(BiConsumer<ModIds, IModIntegration> modIntegration)
    {
        if (enableXu2Integration && ModIds.EXTRA_UTILITIES.isLoaded)
            modIntegration.accept(ModIds.EXTRA_UTILITIES, new ExtraUtilsTConstructIntegration());
        if (enableJerIntegration && ModIds.JER.isLoaded)
            modIntegration.accept(ModIds.JER, new JerTConstructIntegration());
        if (enableTeIntegration && ModIds.THERMAL_EXPANSION.isLoaded)
            modIntegration.accept(ModIds.THERMAL_EXPANSION, new ThermalExpansionTConstructIntegration());
        if (enableDungeonTacticsIntegration && ModIds.DUNGEON_TACTICS.isLoaded)
            modIntegration.accept(ModIds.DUNGEON_TACTICS, new DungeonTacticsTConstructIntegration());
        if (ModIds.HATCHERY.isLoaded)
            modIntegration.accept(ModIds.HATCHERY, new HatcheryTConstructIntegration(enableHatcheryIntegration));
        if (enableFutureMcIntegration && ModIds.FUTURE_MC.isLoaded)
            modIntegration.accept(ModIds.FUTURE_MC, new FutureMcTConstructIntegration());
    }
    
    @Override
    public void init(FMLInitializationEvent event)
    {
        if (tconstructAdditionsEnabled && !MiaConfig.disableAllRecipes)
        {
            Fluid fluid = FluidRegistry.getFluid("iron");
            
            if (fluid != null)
            {
                TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Items.CHAINMAIL_HELMET, Material.VALUE_Nugget * 5 * 4), fluid));
                TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Items.CHAINMAIL_CHESTPLATE, Material.VALUE_Nugget * 8 * 4), fluid));
                TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Items.CHAINMAIL_LEGGINGS, Material.VALUE_Nugget * 7 * 4), fluid));
                TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(Items.CHAINMAIL_BOOTS, Material.VALUE_Nugget * 4 * 4), fluid));
            }
        }
        
        if (!modIntegrations.isEmpty())
        {
            ProgressManager.ProgressBar progressBar = ProgressManager.push("ThermalExpansion init", modIntegrations.size());
            for (ITConstructIntegration integration : modIntegrations)
            {
                progressBar.step(integration.getModId().modId);
                integration.init(event);
            }
            ProgressManager.pop(progressBar);
        }
    }
}
