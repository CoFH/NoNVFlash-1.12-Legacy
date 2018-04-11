package cofh.nonvflash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod (modid = NoNVFlash.MOD_ID, name = NoNVFlash.MOD_NAME, version = NoNVFlash.VERSION, dependencies = NoNVFlash.DEPENDENCIES, clientSideOnly = true)
public class NoNVFlash {

	public static final String MOD_ID = "nonvflash";
	public static final String MOD_NAME = "No Night Vision Flashing";

	public static final String VERSION = "1.2.0";
	public static final String DEPENDENCIES = "required-after:forge@[14.0.0.0,15.0.0.0);";

	@Instance (MOD_ID)
	public static NoNVFlash instance;

	public static Configuration config;

	public static boolean fadeOut = true;
	public static int fadeTicks = 20;
	public static float maxBrightness = 1.0F;
	public static float fadeRate = maxBrightness / fadeTicks;

	/* INIT */
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		config = new Configuration(new File(event.getModConfigurationDirectory(), "/cofh/nonvflash/client.cfg"), VERSION, true);
		config.load();

		String category = "General";
		String comment = "Adjust this value to change the default brightness of the Night Vision effect. Setting this to 0 will effectively disable it.";
		maxBrightness = config.getFloat("Brightness", category, maxBrightness, 0.0F, 1.0F, comment);

		comment = "If TRUE, Night Vision brightness will gradually fade over a number of ticks instead of abruptly stopping.";
		fadeOut = config.getBoolean("FadeOutEffect", category, fadeOut, comment);

		comment = "If the fade out option is enabled (TRUE), adjust this value to change the duration of the fade.";
		fadeTicks = config.getInt("FadeOutTicks", category, fadeTicks, 10, 200, comment);
		fadeRate = maxBrightness / fadeTicks;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		config.save();

		SimpleReloadableResourceManager manager = ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager());

		int index = manager.reloadListeners.indexOf(Minecraft.getMinecraft().entityRenderer);
		manager.reloadListeners.remove(index);

		Minecraft.getMinecraft().entityRenderer = new EntityRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().getResourceManager()) {

			@Override
			public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {

				if (!NoNVFlash.fadeOut) {
					return maxBrightness;
				}
				int i = entitylivingbaseIn.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
				return i > NoNVFlash.fadeTicks ? maxBrightness : i * NoNVFlash.fadeRate;
			}
		};
		manager.registerReloadListener(Minecraft.getMinecraft().entityRenderer);
	}

}
