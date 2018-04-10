package cofh.nonvflash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class ProxyClient extends Proxy {

	/* INIT */
	@Override
	public void postInit(FMLPostInitializationEvent event) {

		super.postInit(event);

		SimpleReloadableResourceManager manager = ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager());

		int index = manager.reloadListeners.indexOf(Minecraft.getMinecraft().entityRenderer);
		manager.reloadListeners.remove(index);

		Minecraft.getMinecraft().entityRenderer = new EntityRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().getResourceManager()) {

			@Override
			public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {

				if (!NoNVFlash.fadeOut) {
					return 1.0F;
				}
				int i = entitylivingbaseIn.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
				return i > NoNVFlash.fadeTicks ? 1.0F : i * NoNVFlash.fadeRate;
			}
		};
		manager.registerReloadListener(Minecraft.getMinecraft().entityRenderer);
	}

}
