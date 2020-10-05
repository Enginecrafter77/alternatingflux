package enginecrafter77.alternatingflux;

import net.minecraftforge.common.config.Config;

@Config(modid = AlternatingFlux.MODID, name = AlternatingFlux.MODID, category = "general")
public class AlternatingFluxConfig {
	@Config.Comment({"The transfer rates for the wires."})
	@Config.LangKey("config.alternatingflux.wireTransferRate.name")
	public static int wireTransferRate = 131072;
	
	@Config.Comment({"True to disable creation of Alternating Flux creative tab. The items will be available under Immersive Engineering tab"})
	@Config.LangKey("config.alternatingflux.mergeTab.name")
	@Config.RequiresMcRestart
	public static boolean mergeTab = false;
	
	@Config.Comment({
		"The percentage of power lost every X blocks of distance for a wire network. (X is equal to the maximum wire length.)",
		"The default value of 0.005 for AF is approximately equivalent to 10.67-14.93% over 1024 blocks, depending on the capacity penalty.",
		"If you'd like to halve that penalty to 5.33-7.47%, set this value to 0.0025."
	})
	@Config.LangKey("config.alternatingflux.wireLossRatio.name")
	public static double wireLossRatio = 0.005;
	
	@Config.Comment({"The maximum length of a single wire. ", "The loss penalty is applied for each of these lengths, so increasing this value increases the efficiency of the wire."})
	@Config.LangKey("config.alternatingflux.wireLength.name")
	public static int wireLength = 48;
}