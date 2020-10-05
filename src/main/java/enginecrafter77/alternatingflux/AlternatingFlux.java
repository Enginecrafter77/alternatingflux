package enginecrafter77.alternatingflux;

import org.apache.logging.log4j.Logger;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import enginecrafter77.alternatingflux.block.BlockConnector;
import enginecrafter77.alternatingflux.item.ItemWireCoil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = AlternatingFlux.MODID, version = AlternatingFlux.VERSION, dependencies = "required-after:immersiveengineering@[0.12,)", acceptedMinecraftVersions = "[1.12.2]")
@Mod.EventBusSubscriber
public class AlternatingFlux {
	public static final String MODID = "alternatingflux";
	public static final String VERSION = "${version}";
	public static final String MODNAME = "Alternating Flux";
	
	public static Logger logger;
	
	@Mod.Instance(MODID)
	public static AlternatingFlux instance = new AlternatingFlux();
	public static CreativeTabs creativeTab;
	
	public static Item item_conn, item_coil, dust_sca, ingot_sca, wire_sca, plate_sca;
	public static BlockConnector block_conn;
	
	public static ResourceLocation TEX_PASSTHROUGH_AF = new ResourceLocation(AlternatingFlux.MODID, "blocks/passthrough_af");
	
	@SidedProxy(clientSide = "enginecrafter77.alternatingflux.ClientProxy", serverSide = "enginecrafter77.alternatingflux.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		
		// Initialize the creative tab
		if(AlternatingFluxConfig.mergeTab) AlternatingFlux.creativeTab = ImmersiveEngineering.creativeTab;
		else
		{
			AlternatingFlux.creativeTab = new CreativeTabs(MODID) {
				@Override
				public ItemStack createIcon()
				{
					return new ItemStack(AlternatingFlux.item_coil, 1, 0);
				}
			};
		}
		
		AlternatingFlux.dust_sca = new Item().setRegistryName(new ResourceLocation(AlternatingFlux.MODID, "dust_sca")).setTranslationKey("dust_sca").setCreativeTab(creativeTab);
		AlternatingFlux.ingot_sca = new Item().setRegistryName(new ResourceLocation(AlternatingFlux.MODID, "ingot_sca")).setTranslationKey("ingot_sca").setCreativeTab(creativeTab);
		AlternatingFlux.plate_sca = new Item().setRegistryName(new ResourceLocation(AlternatingFlux.MODID, "plate_sca")).setTranslationKey("plate_sca").setCreativeTab(creativeTab);
		AlternatingFlux.wire_sca = new Item().setRegistryName(new ResourceLocation(AlternatingFlux.MODID, "wire_sca")).setTranslationKey("wire_sca").setCreativeTab(creativeTab);
		AlternatingFlux.block_conn = new BlockConnector();
		AlternatingFlux.item_coil = new ItemWireCoil();
		
		AlternatingFlux.proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		AlternatingFlux.proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		AlternatingFlux.proxy.postInit();
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(block_conn);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> reg = event.getRegistry();
		reg.register(AlternatingFlux.block_conn.getItemBlock());
		reg.registerAll(item_coil, dust_sca, ingot_sca, plate_sca, wire_sca);
	}
}