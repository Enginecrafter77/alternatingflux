package enginecrafter77.alternatingflux.block;

import com.google.common.collect.ImmutableSet;
import blusunrize.immersiveengineering.api.energy.wires.WireType;
import blusunrize.immersiveengineering.common.blocks.metal.TileEntityTransformerHV;

public class TileEntityTransformerAF extends TileEntityTransformerHV
{
	public TileEntityTransformerAF()
	{
		this.acceptableLowerWires = ImmutableSet.of(WireType.HV_CATEGORY);
	}
	
	@Override
	protected boolean canTakeLV()
	{
		return false;
	}
	
	@Override
	protected boolean canTakeMV()
	{
		return false;
	}
	
	@Override
	protected boolean canTakeHV()
	{
		return true;
	}
	
	@Override
	protected float getLowerOffset()
	{
		return super.getHigherOffset();
	}

	@Override
	protected float getHigherOffset()
	{
		return .75F;
	}

	@Override
	public String getHigherWiretype()
	{
		return "AF";
	}

}