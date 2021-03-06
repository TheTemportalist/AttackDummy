package com.countrygamer.attackdummy.common

import com.countrygamer.attackdummy.common.init.{ADEntity, ADItems}
import com.countrygamer.cgo.wrapper.common.PluginWrapper
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import cpw.mods.fml.common.{Mod, SidedProxy}

/**
 *
 *
 * @author CountryGamer
 */
@Mod(modid = AttackDummy.pluginID, name = AttackDummy.pluginName, version = "@PLUGIN_VERSION@",
	guiFactory = "com.countrygamer.attackdummy.client.gui.configFactory.AttackDummyFactory",
	modLanguage = "scala",
	dependencies = "required-after:Forge@[10.13,);required-after:cgo@[3,);"
)
object AttackDummy extends PluginWrapper {

	final val pluginID = "attackdummy"
	final val pluginName = "AttackDummy"

	@SidedProxy(clientSide = "com.countrygamer.attackdummy.client.ClientProxy",
		serverSide = "com.countrygamer.attackdummy.common.CommonProxy"
	)
	var proxy: CommonProxy = null

	@Mod.EventHandler
	def preInit(event: FMLPreInitializationEvent): Unit = {
		super.preInitialize(this.pluginID, this.pluginName, event, this.proxy, ADOptions, ADItems,
			ADEntity)

		//RegisterHelper.registerPacketHandler(this.pluginID, classOf[PacketSwapArmor])

	}

	@Mod.EventHandler
	def init(event: FMLInitializationEvent): Unit = {

	}

	@Mod.EventHandler
	def postInit(event: FMLPostInitializationEvent): Unit = {

	}

}
