package cofh.nonvflash;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod (modid = NoNVFlash.MOD_ID, name = NoNVFlash.MOD_NAME, version = NoNVFlash.VERSION, dependencies = NoNVFlash.DEPENDENCIES, clientSideOnly = true)
public class NoNVFlash {

	public static final String MOD_ID = "nonvflash";
	public static final String MOD_NAME = "No Night Vision Flashing";

	public static final String VERSION = "1.1.0";
	public static final String DEPENDENCIES = "required-after:forge@[14.0.0.0,15.0.0.0);";

	@Instance (MOD_ID)
	public static NoNVFlash instance;

	@SidedProxy (clientSide = "cofh.nonvflash.ProxyClient", serverSide = "cofh.nonvflash.Proxy")
	public static Proxy proxy;

	public static Configuration config;

	public static boolean fadeOut = true;
	public static int fadeTicks = 20;
	public static float fadeRate = 1.0F / fadeTicks;

	/* INIT */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		config = new Configuration(new File(event.getModConfigurationDirectory(), "/cofh/nonvflash/client.cfg"), VERSION, true);
		config.load();

		String category = "General";
		String comment = "If TRUE, Night Vision brightness will gradually fade over a number of ticks instead of abruptly stopping.";

		fadeOut = config.getBoolean("FadeOutEffect", category, fadeOut, comment);

		comment = "If the fade out option is enabled (TRUE), adjust this value to change the duration of the fade.";
		fadeTicks = config.getInt("FadeOutTicks", category, fadeTicks, 10, 200, comment);
		fadeRate = 1.0F / fadeTicks;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		config.save();

		proxy.postInit(event);
	}

}
