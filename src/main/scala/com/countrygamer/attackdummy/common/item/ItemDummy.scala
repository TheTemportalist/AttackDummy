package com.countrygamer.attackdummy.common.item

import com.countrygamer.attackdummy.common.entity.EntityDummy
import com.countrygamer.cgo.common.lib.util.Cursor
import com.countrygamer.cgo.wrapper.common.item.ItemWrapper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
abstract class ItemDummy(pluginID: String, name: String) extends ItemWrapper(pluginID, name) {

	override def onItemUse(itemStack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int,
			z: Int, side: Int, offsetX: Float, offsetY: Float, offsetZ: Float): Boolean = {
		val placeCoords: Array[Int] = Cursor.getNewCoordsFromSide(x, y, z, side)
		val x1: Int = placeCoords(0)
		val y1: Int = placeCoords(1)
		val z1: Int = placeCoords(2)

		if (world.getBlock(x1, y1 + 1, z1) == Blocks.air &&
				world.getBlock(x1, y1 + 2, z1) == Blocks.air) {
			if (!world.isRemote)
				world.spawnEntityInWorld(
					this.getEntity(world, x1 + 0.5, y1, z1 + 0.5).setRotation(player.rotationYaw))
			return true
		}
		false
	}

	def getEntity(world: World, x: Double, y: Double, z: Double): EntityDummy

}
