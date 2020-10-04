package enginecrafter77.alternatingflux;

import blusunrize.immersiveengineering.common.Config.Mapped;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class ManualConstants {

	@Mapped(mapClass = blusunrize.immersiveengineering.common.Config.class, mapName = "manual_int")
	public static int alternatingflux_afTransferRate;
	
	@Mapped(mapClass = blusunrize.immersiveengineering.common.Config.class, mapName = "manual_double")
	public static double alternatingflux_afkmLossMin;
	
	@Mapped(mapClass = blusunrize.immersiveengineering.common.Config.class, mapName = "manual_double")
	public static double alternatingflux_afkmLossMax;
	
	@Mapped(mapClass = blusunrize.immersiveengineering.common.Config.class, mapName = "manual_int")
	public static int alternatingflux_afMaxLength;
	
	@SubscribeEvent
	public static void configChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.getModID().equals(AlternatingFlux.MODID))
		{
    		ConfigManager.sync(AlternatingFlux.MODID, net.minecraftforge.common.config.Config.Type.INSTANCE);
    		ManualConstants.refresh();
		}
	}
	
	public static void refresh()
	{
		alternatingflux_afTransferRate = AlternatingFluxConfig.wireTransferRate;
		alternatingflux_afkmLossMin = ((1024/AlternatingFluxConfig.wireLength)*AlternatingFluxConfig.wireLossRatio*100);
		alternatingflux_afkmLossMax = alternatingflux_afkmLossMin*1.4;
		// Crude rounding, because we can't control the formatting.
		alternatingflux_afkmLossMin = Math.round(alternatingflux_afkmLossMin * 10.0) / 10.0;
		alternatingflux_afkmLossMax = Math.round(alternatingflux_afkmLossMax * 10.0) / 10.0;
		alternatingflux_afMaxLength = AlternatingFluxConfig.wireLength;
		
		blusunrize.immersiveengineering.common.Config.validateAndMapValues(ManualConstants.class);
	}
	
}
