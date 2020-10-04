package enginecrafter77.alternatingflux.block;

import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.blocks.BlockIEBase;
import blusunrize.immersiveengineering.common.blocks.BlockIETileProvider;
import blusunrize.immersiveengineering.common.blocks.ItemBlockIEBase;
import enginecrafter77.alternatingflux.AlternatingFlux;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class BlockUnregisteredIETileProvider<E extends Enum<E> & BlockIEBase.IBlockEnum> extends BlockIETileProvider<E> {
	private final ItemBlockIEBase itemblock;
	
	public BlockUnregisteredIETileProvider(ResourceLocation name, Material material, PropertyEnum<E> mainProperty, Class<? extends ItemBlockIEBase> itemBlock, Object... additionalProperties)
	{
		super(name.getPath(), material, mainProperty, itemBlock, additionalProperties);
		
		this.itemblock = this.pullItemBlock(itemBlock);
		this.itemblock.setRegistryName(name);
		this.unregisterBlock();
		
		this.setRegistryName(name);
	}
	
	public ItemBlockIEBase getItemBlock()
	{
		return this.itemblock;
	}
	
	@Override
	public String createRegistryName()
	{
		return AlternatingFlux.MODID + ":" + name;
	}
	
	private final void unregisterBlock()
	{
		Block rBlock = IEContent.registeredIEBlocks.remove(IEContent.registeredIEBlocks.size() - 1);
		if(this != rBlock) throw new IllegalStateException("fixupBlock was not called at the appropriate time, removed block did not match");
	}
	
	private final ItemBlockIEBase pullItemBlock(Class<? extends ItemBlockIEBase> itemBlock)
	{
		Item rItem = IEContent.registeredIEItems.remove(IEContent.registeredIEItems.size() - 1);
		if(rItem.getClass() != itemBlock) throw new IllegalStateException("fixupBlock was not called at the appropriate time");
		
		return (ItemBlockIEBase)rItem;
	}
}
