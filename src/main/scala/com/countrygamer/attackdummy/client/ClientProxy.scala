package com.countrygamer.attackdummy.client

import com.countrygamer.attackdummy.client.render.models.RenderDummy
import com.countrygamer.attackdummy.common.CommonProxy
import com.countrygamer.attackdummy.common.entity.EntityDummy
import cpw.mods.fml.client.registry.RenderingRegistry
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class ClientProxy() extends CommonProxy {

	override def registerRender(): Unit = {

		/*
		RenderingRegistry.registerEntityRenderingHandler(classOf[EntityAttackDummy],
			new RenderDummy("attack"))
		RenderingRegistry.registerEntityRenderingHandler(classOf[EntityDefenseDummy],
			new RenderDummy("defense"))
		*/
		RenderingRegistry.registerEntityRenderingHandler(classOf[EntityDummy],
			new RenderDummy())

	}

	override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int): AnyRef = {
		null
	}

}
