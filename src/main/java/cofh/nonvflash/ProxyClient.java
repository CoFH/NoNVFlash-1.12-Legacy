package cofh.nonvflash;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.EntityLivingBase;
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

				return 1.0F;
			}
		};
		manager.registerReloadListener(Minecraft.getMinecraft().entityRenderer);
	}

}
