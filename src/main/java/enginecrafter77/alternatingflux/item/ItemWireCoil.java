package enginecrafter77.alternatingflux.item;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntityEnergyMeter;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityFeedthrough;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityRedstoneBreaker;
import blusunrize.immersiveengineering.common.items.IEItemInterfaces.IColouredItem;
import enginecrafter77.alternatingflux.AlternatingFlux;
import enginecrafter77.alternatingflux.block.TileEntityConnectorAF;
import enginecrafter77.alternatingflux.block.TileEntityTransformerAF;
import enginecrafter77.alternatingflux.wire.AFWireType;
import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.api.energy.wires.IWireCoil;
import blusunrize.immersiveengineering.api.energy.wires.TileEntityImmersiveConnectable;
import blusunrize.immersiveengineering.api.energy.wires.WireType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemWireCoil extends Item implements IWireCoil, IColouredItem {
	public final Collection<Class<? extends TileEntityImmersiveConnectable>> valid_connections;
	
	public ItemWireCoil()
	{
		super();
		this.valid_connections = new HashSet<Class<? extends TileEntityImmersiveConnectable>>();
		
		this.valid_connections.add(TileEntityConnectorAF.class);
		this.valid_connections.add(TileEntityTransformerAF.class);
		this.valid_connections.add(TileEntityRedstoneBreaker.class);
		this.valid_connections.add(TileEntityEnergyMeter.class);
		this.valid_connections.add(TileEntityFeedthrough.class);
		
		this.setRegistryName(new ResourceLocation(AlternatingFlux.MODID, "coil_sca"));
		this.setCreativeTab(AlternatingFlux.creativeTab);
		this.setTranslationKey("coil_sca");
	}

	@Override
	public WireType getWireType(ItemStack stack)
	{
		return AFWireType.instance;
	}
	
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey("linkingPos"))
		{
			int[] link = stack.getTagCompound().getIntArray("linkingPos");
			if(link != null && link.length > 3)
			{
				tooltip.add(I18n.format(Lib.DESC_INFO + "attachedToDim", link[1], link[2], link[3], link[0]));
			}
		}
	}
	
    @Nonnull
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
        return ApiUtils.doCoilUse(this, player, world, pos, hand, side, hitX, hitY, hitZ);
    }

    @Override
	public boolean canConnectCable(ItemStack stack, TileEntity targettile)
	{
		for(Class<? extends TileEntityImmersiveConnectable> tileclass : this.valid_connections)
		{
			if(targettile.getClass() == tileclass) return true;
		}
		return false;
	}
}