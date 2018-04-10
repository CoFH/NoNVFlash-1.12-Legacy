package cofh.nonvflash;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

@Mod (modid = NoNVFlash.MOD_ID, name = NoNVFlash.MOD_NAME, version = NoNVFlash.VERSION, dependencies = NoNVFlash.DEPENDENCIES, clientSideOnly = true)
public class NoNVFlash {

	public static final String MOD_ID = "nonvflash";
	public static final String MOD_NAME = "No Night Vision Flashing";

	public static final String VERSION = "1.0.0";
	public static final String DEPENDENCIES = "required-after:forge@[14.0.0.0,15.0.0.0);";

	@Instance (MOD_ID)
	public static NoNVFlash instance;

	@SidedProxy (clientSide = "cofh.nonvflash.ProxyClient", serverSide = "cofh.nonvflash.Proxy")
	public static Proxy proxy;

	/* INIT */
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		proxy.postInit(event);
	}

}
