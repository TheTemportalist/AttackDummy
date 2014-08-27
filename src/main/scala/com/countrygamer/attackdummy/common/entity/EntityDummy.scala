package com.countrygamer.attackdummy.common.entity

import com.countrygamer.attackdummy.common.AttackDummy
import com.countrygamer.attackdummy.common.init.ADItems
import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.cgo.common.lib.util.{Cursor, UtilDrops}
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.MovingObjectPosition
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class EntityDummy(world: World, x: Double, y: Double, z: Double) extends Entity(world) {

	// Default Constructor
	{
		this.yOffset = 0.0F
		this.setSize(0.9F, 1.9F)

		this.isImmuneToFire = true

		this.setPosition(x, y, z)
	}

	// End Constructor

	// Other Constructors
	def this(world: World) {
		this(world, 0.0D, 0.0D, 0.0D)

	}

	// End Constructors

	override def setFire(fireTicks: Int): Unit = {
	}

	override def handleWaterMovement(): Boolean = {
		this.isInWater
	}

	override def canBeCollidedWith: Boolean = {
		true
	}

	override def interactFirst(player: EntityPlayer): Boolean = {
		if (player.getHeldItem != null) {
			if (player.getHeldItem.getItem == Items.stick) {
				this.setDead()
				if (!player.capabilities.isCreativeMode)
					UtilDrops.spawnItemStack(this.worldObj, this.posX + 0.5, this.posY + 1,
						this.posZ + 0.5, new ItemStack(ADItems.attackDummy, 1, 0), this.rand,
						20)
				return true
			}
			else {
				for (i <- 0 until 4) {
					if (player.getHeldItem.getItem.isValidArmor(player.getHeldItem, i, this)) {
						val mop: MovingObjectPosition = Cursor.rayTrace(player, 5.0D)
						if (mop == null) {
							LogHelper.info(AttackDummy.pluginName, "error")
							return false
						}

						val offsetX: Double = mop.hitVec.xCoord - this.posX
						val offsetY: Double = mop.hitVec.yCoord - this.posY
						val offsetZ: Double = mop.hitVec.zCoord - this.posZ

						LogHelper.info(AttackDummy.pluginName, "mop" +
								"\n" + mop.hitVec.xCoord + "\n" + mop.hitVec.yCoord + "\n" +
								mop.hitVec.zCoord)
						LogHelper.info(AttackDummy.pluginName, "pos" +
								"\n" + this.posX + "\n" + this.posY + "\n" +
								this.posZ)
						LogHelper.info(AttackDummy.pluginName, "offset" +
								"\n" + offsetX + "\n" + offsetY + "\n" + offsetZ)
					}
				}
			}
		}
		false
	}

	override def entityInit(): Unit = {
		this.dataWatcher.addObject(6, java.lang.Float.valueOf(0.0f))
	}

	def getRotation(): Float = {
		this.dataWatcher.getWatchableObjectFloat(6)
	}

	def setRotation(rot: Float): EntityDummy = {
		this.dataWatcher.updateObject(6, java.lang.Float.valueOf(rot))
		this
	}

	override def writeEntityToNBT(tagCom: NBTTagCompound): Unit = {
		tagCom.setFloat("rotation", this.getRotation())

	}

	override def readEntityFromNBT(tagCom: NBTTagCompound): Unit = {
		this.setRotation(tagCom.getFloat("rotation"))

	}

}
