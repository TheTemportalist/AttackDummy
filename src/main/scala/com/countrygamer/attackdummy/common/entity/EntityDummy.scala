package com.countrygamer.attackdummy.common.entity

import com.countrygamer.attackdummy.common.AttackDummy
import com.countrygamer.attackdummy.common.init.ADItems
import com.countrygamer.cgo.common.lib.LogHelper
import com.countrygamer.cgo.common.lib.util.UtilDrops
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.init.Items
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{MathHelper, MovingObjectPosition, Vec3}
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
						val mop: MovingObjectPosition = this.rayTrace(player, 5.0D)
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

	// Local until update
	def rayTrace(player: EntityPlayer, reachDistance: Double): MovingObjectPosition = {
		val partialTicks: Float = 1.0F
		val checkLiquids: Boolean = true
		val checkEntities: Boolean = true

		val position: Vec3 = this.getPosition(player, partialTicks)
		val cursor: Vec3 = this.getCursor(player, reachDistance, partialTicks)

		player.worldObj.func_147447_a(position, cursor, checkLiquids, false, checkEntities)
	}

	def getPosition(entity: EntityLivingBase, partialTicks: Float): Vec3 = {
		val x: Double = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks
		val y: Double = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks
		val z: Double = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks
		Vec3.createVectorHelper(x, y, z)
	}

	def getCursor(entity: EntityLivingBase, reachDistance: Double, partialTicks: Float): Vec3 = {
		val pitch: Float = entity.prevRotationPitch +
				(entity.prevRotationPitch - entity.rotationPitch) * partialTicks
		val yaw: Float = entity.prevRotationYaw +
				(entity.prevRotationYaw - entity.rotationYaw * partialTicks)

		val cosYaw: Float = MathHelper
				.cos(-yaw * 0.017453292F - java.lang.Math.PI.asInstanceOf[Float])
		val sinYaw: Float = MathHelper
				.sin(-yaw * 0.017453292F - java.lang.Math.PI.asInstanceOf[Float])
		val cosPitch: Float = -MathHelper.cos(-pitch * 0.017453292F)
		val sinPitch: Float = MathHelper.sin(-pitch * 0.017453292F)
		val cursor: Vec3 = Vec3.createVectorHelper(
			(sinYaw * cosPitch).asInstanceOf[Double],
			sinPitch.asInstanceOf[Double],
			(cosYaw * cosPitch).asInstanceOf[Double]
		)
		this.getPosition(entity, partialTicks)
				.addVector(cursor.xCoord * reachDistance, cursor.yCoord * reachDistance,
		            cursor.zCoord * reachDistance)
	}

}
