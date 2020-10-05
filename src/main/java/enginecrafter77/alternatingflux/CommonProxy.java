package enginecrafter77.alternatingflux;

import blusunrize.immersiveengineering.api.crafting.AlloyRecipe;
import blusunrize.immersiveengineering.api.crafting.CrusherRecipe;
import blusunrize.immersiveengineering.api.crafting.IngredientStack;
import enginecrafter77.alternatingflux.block.TileEntityConnectorAF;
import enginecrafter77.alternatingflux.block.TileEntityTransformerAF;
import enginecrafter77.alternatingflux.wire.AFWireType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void preInit()
	{
		GameRegistry.registerTileEntity(TileEntityConnectorAF.class, new ResourceLocation(AlternatingFlux.MODID, "af_connector"));
		GameRegistry.registerTileEntity(TileEntityTransformerAF.class, new ResourceLocation(AlternatingFlux.MODID, "af_transformer"));
	}
	
	public void init()
	{
		AFWireType.init();
		
		AlloyRecipe.addRecipe(new ItemStack(AlternatingFlux.ingot_sca), new ItemStack(Items.GLOWSTONE_DUST), new IngredientStack("ingotElectrum"), 200);
		AlloyRecipe.addRecipe(new ItemStack(AlternatingFlux.ingot_sca), new ItemStack(Items.GLOWSTONE_DUST), new IngredientStack("dustElectrum"), 200);
		CrusherRecipe.addRecipe(new ItemStack(AlternatingFlux.dust_sca), new ItemStack(AlternatingFlux.ingot_sca), 2400);
		FurnaceRecipes.instance().addSmeltingRecipe(new ItemStack(AlternatingFlux.dust_sca), new ItemStack(AlternatingFlux.ingot_sca), 0.1F);
	}

	public void postInit() {}
}