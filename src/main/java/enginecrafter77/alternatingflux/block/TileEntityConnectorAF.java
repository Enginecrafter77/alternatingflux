package enginecrafter77.alternatingflux.block;

import java.util.Set;

import blusunrize.immersiveengineering.api.TargetingInfo;
import blusunrize.immersiveengineering.api.energy.wires.ImmersiveNetHandler;
import blusunrize.immersiveengineering.api.energy.wires.ImmersiveNetHandler.Connection;
import blusunrize.immersiveengineering.api.energy.wires.TileEntityImmersiveConnectable;
import blusunrize.immersiveengineering.api.energy.wires.WireType;
import blusunrize.immersiveengineering.client.models.IOBJModelCallback;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockBounds;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IDirectionalTile;
import enginecrafter77.alternatingflux.wire.AFWireType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityConnectorAF extends TileEntityImmersiveConnectable implements ITickable, IDirectionalTile, IBlockBounds, IOBJModelCallback<IBlockState> {	
	@SideOnly(Side.CLIENT)
	private AxisAlignedBB renderAABB;
	
	public EnumFacing facing;
	
	boolean initialized;

	public TileEntityConnectorAF()
	{
		this.facing = EnumFacing.DOWN;
		this.initialized = true;
	}
	
	@Override
	public void update()
	{
		if(world.isRemote && initialized)
		{
			Set<Connection> conns = ImmersiveNetHandler.INSTANCE.getConnections(world, pos);
			if(conns != null)
			{
				for(Connection conn : conns)
				{
					if(pos.compareTo(conn.end) < 0 && world.isBlockLoaded(conn.end))
						this.markContainingBlockForUpdate(null);
				}
			}
			initialized = false;
		}
	}

	@Override
	public EnumFacing getFacing()
	{
		return this.facing;
	}

	@Override
	public void setFacing(EnumFacing facing)
	{
		this.facing = facing;
	}

	@Override
	public int getFacingLimitation()
	{
		return 0;
	}

	@Override
	public boolean mirrorFacingOnPlacement(EntityLivingBase placer)
	{
		return true;
	}

	@Override
	public boolean canHammerRotate(EnumFacing side, float hitX, float hitY, float hitZ, EntityLivingBase entity)
	{
		return false;
	}
	
	@Override
	public boolean canConnectCable(WireType cableType, TargetingInfo target, Vec3i offset)
	{
		if(cableType != AFWireType.instance) return false;
		return limitType == null || limitType == cableType;
	}
	
	@Override
	public boolean canRotate(EnumFacing axis)
	{
		return false;
	}

	@Override
	public boolean isEnergyOutput()
	{
		return false;
	}

	@Override
	public int outputEnergy(int amount, boolean simulate, int energyType)
	{
		return 0;
	}

	@Override
	protected float getBaseDamage(Connection c)
	{
		return 8 * 30F / c.cableType.getTransferRate();
	}
	
	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket)
	{
		super.writeCustomNBT(nbt, descPacket);
		nbt.setInteger("facing", facing.ordinal());
	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket)
	{
		super.readCustomNBT(nbt, descPacket);
		facing = EnumFacing.byIndex(nbt.getInteger("facing"));
	}
	
	
	@Override
	public Vec3d getConnectionOffset(Connection con)
	{
		EnumFacing side = facing.getOpposite();
		double conRadius = 0.375 - con.cableType.getRenderDiameter() / 2;
		return new Vec3d(0.5 + side.getXOffset() * conRadius, 0.5 + side.getYOffset() * conRadius, 0.5 + side.getZOffset() * conRadius);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		int inc = -this.getRenderRadiusIncrease();
		Vec3d start = new Vec3d(this.pos);
		Vec3d end = new Vec3d(this.pos);
		
		start.add(inc, inc, inc); // Subtracting max length
		inc = -inc + 1;
		end.add(inc, inc, inc); // Adding max length + 1
		return new AxisAlignedBB(start, end);
	}
	
	public int getRenderRadiusIncrease()
	{
		return AFWireType.instance.getMaxLength();
	}

	@Override
	public float[] getBlockBounds()
	{
		float length = .875f;
		float wMin = .3125f;
		float wMax = .6875f;
		switch (facing.getOpposite())
		{
		case UP:
			return new float[] { wMin, 0, wMin, wMax, length, wMax };
		case DOWN:
			return new float[] { wMin, 1 - length, wMin, wMax, 1, wMax };
		case SOUTH:
			return new float[] { wMin, wMin, 0, wMax, wMax, length };
		case NORTH:
			return new float[] { wMin, wMin, 1 - length, wMax, wMax, 1 };
		case EAST:
			return new float[] { 0, wMin, wMin, length, wMax, wMax };
		case WEST:
			return new float[] { 1 - length, wMin, wMin, 1, wMax, wMax };
		default:
			return new float[] { 0, 0, 0, 1, 1, 1 };
		}
	}
	
	@Override
	public boolean shouldRenderGroup(IBlockState object, String group)
	{
		return MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.TRANSLUCENT;
	}
}