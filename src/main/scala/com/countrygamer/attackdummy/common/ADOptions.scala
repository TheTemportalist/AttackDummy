package com.countrygamer.attackdummy.common

import com.countrygamer.cgo.wrapper.common.registries.OptionRegister
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.gui.GuiScreen

/**
 *
 *
 * @author CountryGamer
 */
object ADOptions extends OptionRegister {

	override def register(): Unit = {

	}

	@SideOnly(Side.CLIENT)
	override def getGuiConfigClass: Class[_ <: GuiScreen] = {
		classOf[com.countrygamer.attackdummy.client.gui.configFactory.GuiConfig]
	}

}
