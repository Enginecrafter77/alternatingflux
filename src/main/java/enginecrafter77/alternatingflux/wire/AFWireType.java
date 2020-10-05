package enginecrafter77.alternatingflux.wire;

import blusunrize.immersiveengineering.api.energy.wires.ImmersiveNetHandler.Connection;
import enginecrafter77.alternatingflux.AlternatingFlux;
import enginecrafter77.alternatingflux.AlternatingFluxConfig;
import enginecrafter77.alternatingflux.block.BlockTypes_Connector;
import blusunrize.immersiveengineering.api.energy.wires.WireApi;
import blusunrize.immersiveengineering.api.energy.wires.WireType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AFWireType extends WireType {
	public static final ResourceLocation texture = new ResourceLocation(AlternatingFlux.MODID, "block/connector/relay_af.obj"); 
	public static AFWireType instance;
	
	private AFWireType() {}
	
	public static void init()
	{
		instance = new AFWireType();
		IBlockState validconnection = AlternatingFlux.block_conn.getDefaultState().withProperty(AlternatingFlux.block_conn.property, BlockTypes_Connector.RELAY_AF);
		WireApi.registerFeedthroughForWiretype(instance, texture, AlternatingFlux.TEX_PASSTHROUGH_AF, new float[]{0, 0, 16, 16}, 0.75, validconnection, 8 * 30F / instance.getTransferRate(), 15, (f)->f);
	}

	/**
	 * In this case, this does not return the loss RATIO but the loss PER BLOCK
	 */
	@Override
	public double getLossRatio()
	{
		return AlternatingFluxConfig.wireLossRatio;
	}

	@Override
	public int getTransferRate()
	{
		return AlternatingFluxConfig.wireTransferRate;
	}

	@Override
	public int getColour(Connection connection)
	{
		return 0xFEFB9B;
	}

	@Override
	public double getSlack()
	{
		return 1.002;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getIcon(Connection connection)
	{
		return iconDefaultWire;
	}

	@Override
	public int getMaxLength()
	{
		return AlternatingFluxConfig.wireLength;
	}

	@Override
	public ItemStack getWireCoil()
	{
		return new ItemStack(AlternatingFlux.item_coil);
	}

	@Override
	public String getUniqueName()
	{
		return AlternatingFlux.MODID;
	}

	@Override
	public double getRenderDiameter()
	{
		return 0.078125D;
	}

	@Override
	public boolean isEnergyWire()
	{
		return true;
	}
	
	@Override
	public String getCategory()
	{
		return "AF";
	}

	@Override
	public double getDamageRadius()
	{
		return 0.5;
	}
		
	@Override
	public boolean canCauseDamage()
	{
		return true;
	}
}