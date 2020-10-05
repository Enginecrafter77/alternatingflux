package enginecrafter77.alternatingflux.block;

import java.util.Collection;
import java.util.HashSet;

import blusunrize.immersiveengineering.api.IEProperties;
import blusunrize.immersiveengineering.api.energy.wires.TileEntityImmersiveConnectable;
import blusunrize.immersiveengineering.client.models.IOBJModelCallback;
import blusunrize.immersiveengineering.common.blocks.ItemBlockIEBase;
import enginecrafter77.alternatingflux.AlternatingFlux;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

public class BlockConnector extends BlockUnregisteredIETileProvider<BlockTypes_Connector>
{
	public static final ResourceLocation id = new ResourceLocation(AlternatingFlux.MODID, "connector");
	
	public BlockConnector()
	{
		super(BlockConnector.id, Material.IRON, PropertyEnum.create("type", BlockTypes_Connector.class), ItemBlockIEBase.class, IEProperties.FACING_ALL, IEProperties.BOOLEANS[0], IEProperties.BOOLEANS[1], IEProperties.MULTIBLOCKSLAVE, IOBJModelCallback.PROPERTY);
		this.setHardness(3.0F);
		this.setResistance(15.0F);
		this.lightOpacity = 0;
		this.setMetaBlockLayer(BlockTypes_Connector.RELAY_AF.getMeta(), BlockRenderLayer.SOLID, BlockRenderLayer.TRANSLUCENT);
		this.setAllNotNormalBlock();
		this.setMetaMobilityFlag(BlockTypes_Connector.TRANSFORMER_AF.getMeta(), EnumPushReaction.BLOCK);
		
		this.setCreativeTab(AlternatingFlux.creativeTab);
		this.setTranslationKey("connector");
	}
	
	@Override
	public boolean useCustomStateMapper()
	{
		return true;
	}
	
	@Override
	public String getCustomStateMapping(int meta, boolean itemBlock)
	{
		return meta == BlockTypes_Connector.TRANSFORMER_AF.getMeta() ? "transformer_af" : null;
	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		BlockStateContainer base = super.createBlockState();
		Collection<IUnlistedProperty<?>> list = new HashSet<IUnlistedProperty<?>>();
		if(base instanceof ExtendedBlockState)
			list.addAll(((ExtendedBlockState)base).getUnlistedProperties());
		list.add(IEProperties.CONNECTIONS);
		return new ExtendedBlockState(this, base.getProperties().toArray(new IProperty[0]), list.toArray(new IUnlistedProperty<?>[0]));
	}
	
	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		state = super.getExtendedState(state, world, pos);
		if(state instanceof IExtendedBlockState)
		{
			IExtendedBlockState ext = (IExtendedBlockState) state;
			TileEntity te = world.getTileEntity(pos);
			if (!(te instanceof TileEntityImmersiveConnectable))
				return state;
			state = ext.withProperty(IEProperties.CONNECTIONS, ((TileEntityImmersiveConnectable)te).genConnBlockstate());
		}
		return state;
	}
	
	@Override
	public boolean canIEBlockBePlaced(World world, BlockPos pos, IBlockState newState, EnumFacing side, float hitX, float hitY, float hitZ, EntityPlayer player, ItemStack stack)
	{
		if(stack.getItemDamage() == BlockTypes_Connector.TRANSFORMER_AF.getMeta())
		{
			for(int offset = 1; offset <= 2; offset++)
			{
				BlockPos current_block = pos.up(offset);
				if(world.isOutsideBuildHeight(current_block) || !world.getBlockState(current_block).getBlock().isReplaceable(world, current_block))
					return false;
			}
		}
		return true;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		super.neighborChanged(state, world, pos, blockIn, fromPos);
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityConnectorAF)
		{
			TileEntityConnectorAF connector = (TileEntityConnectorAF) te;
			if(world.isAirBlock(pos.offset(connector.facing)))
			{
				this.dropBlockAsItem(connector.getWorld(), pos, world.getBlockState(pos), 0);
				connector.getWorld().setBlockToAir(pos);
				return;
			}
		}
	}

	@Override
	public TileEntity createBasicTE(World world, BlockTypes_Connector type)
	{
		switch(type)
		{
			case RELAY_AF:
				return new TileEntityConnectorAF();
			case TRANSFORMER_AF:
				return new TileEntityTransformerAF();
			default:
				return null;
		}
	}

	@Override
	public boolean allowHammerHarvest(IBlockState state)
	{
		return true;
	}
}