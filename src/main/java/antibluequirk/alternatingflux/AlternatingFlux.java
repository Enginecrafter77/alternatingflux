package antibluequirk.alternatingflux;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import antibluequirk.alternatingflux.block.BlockConnector;
import antibluequirk.alternatingflux.item.ItemWireCoil;
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
	public static CreativeTabs creativeTab = new CreativeTabs(MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(AlternatingFlux.item_coil, 1, 0);
		}
	};
	
	public static final List<Block> blocks = new ArrayList<>();
	public static final List<Item> items = new ArrayList<>(); //TODO remove
	
	public static Item item_conn, item_coil, item_wire;
	public static BlockConnector block_conn;
	
	public static ResourceLocation TEX_PASSTHROUGH_AF = new ResourceLocation(AlternatingFlux.MODID, "blocks/passthrough_af");
	
	@SidedProxy(clientSide = "antibluequirk.alternatingflux.client.ClientProxy", serverSide = "antibluequirk.alternatingflux.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		Config.preInit(event);
		
		item_wire = new Item().setRegistryName(new ResourceLocation(AlternatingFlux.MODID, "wire_constantan")).setTranslationKey("wire_constantan").setCreativeTab(creativeTab);
		block_conn = new BlockConnector();
		item_coil = new ItemWireCoil();
		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
	
	private static ResourceLocation createRegistryName(String unlocalized)
	{
		unlocalized = unlocalized.substring(unlocalized.indexOf(MODID));
		unlocalized = unlocalized.replaceFirst("\\.", ":");
		return new ResourceLocation(unlocalized);
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {		
		for(Block block : blocks)
		{
			logger.info("Registering legacy item with TK {}", block.getTranslationKey());
			event.getRegistry().register(block.setRegistryName(createRegistryName(block.getTranslationKey())));
			logger.info("Registered legacy block with TK {} and registry name {}", block.getTranslationKey(), block.getRegistryName().toString());
		}
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		for(Item item : items)
		{
			logger.info("Registering legacy item with TK {}", item.getTranslationKey());
			reg.register(item.setRegistryName(createRegistryName(item.getTranslationKey())));
			logger.info("Registered legacy item with TK {} and registry name {}", item.getTranslationKey(), item.getRegistryName().toString());
		}
		
		reg.registerAll(item_coil, item_wire);
	}
}