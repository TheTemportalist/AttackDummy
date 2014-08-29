package com.countrygamer.attackdummy.common.entity

import com.countrygamer.attackdummy.common.InventoryDummy
import com.countrygamer.attackdummy.common.init.ADItems
import com.countrygamer.cgo.common.lib.util.{Cursor, UtilDrops}
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.item.{ItemArmor, ItemStack}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util._
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class EntityDummy(world: World, x: Double, y: Double, z: Double) extends Entity(world) {

	val inventory: InventoryDummy = new InventoryDummy(this, 4, 1)

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
		if (player.getHeldItem != null && player.getHeldItem.getItem == Items.stick) {
			this.setDead()
			if (!player.capabilities.isCreativeMode)
				UtilDrops.spawnItemStack(this.worldObj, this.posX + 0.5, this.posY + 1,
					this.posZ + 0.5, new ItemStack(ADItems.attackDummy, 1, 0), this.rand,
					20)
			return true
		}
		else {
			val armorType: Int = this.getArmorType(player)
			if (armorType >= 0 && armorType < 4) {
				if (player.getHeldItem != null &&
						player.getHeldItem.getItem.isInstanceOf[ItemArmor]) {
					this.inventory
							.setInventorySlotContents(armorType, player.getHeldItem.copy())

					val heldStack: ItemStack = player.getHeldItem
					heldStack.stackSize = heldStack.stackSize - 1
					if (heldStack.stackSize > 0) {
						player.inventory.setInventorySlotContents(
							player.inventory.currentItem, heldStack)
					}
					else {
						player.inventory.setInventorySlotContents(
							player.inventory.currentItem, null)
					}

					return true
				}
				else if (player.getHeldItem == null) {
					if (this.inventory.getStackInSlot(armorType) != null) {
						val armorStack: ItemStack = this.inventory.getStackInSlot(armorType).copy()

						this.inventory.setInventorySlotContents(armorType, null)

						if (!player.inventory.addItemStackToInventory(armorStack)) {
							UtilDrops.spawnItemStack(this.worldObj, player.posX, player.posY,
								player.posZ, armorStack, this.rand, 40)
						}

						return true
					}
				}
			}
		}

		false
	}

	def getArmorType(player: EntityPlayer): Int = {
		var armorType: Int = -1
		var mop: MovingObjectPosition = Cursor.rayTrace(player, 5.0D)
		val checkEntities: Boolean = true
		if (checkEntities) {
			val position: Vec3 = Cursor.getPosition(player, 1.0F)
			val cursor: Vec3 = Cursor.getCursor(player, 5.0D, 1.0F)

			val entities: java.util.List[_] = player.worldObj
					.getEntitiesWithinAABBExcludingEntity(player,
			            player.boundingBox
					            .addCoord(cursor.xCoord, cursor.yCoord,
			                        cursor.zCoord)
					            .expand(1.0F, 1.0F, 1.0F))
			for (i <- 0 until entities.size()) {
				val entity: Entity = entities.get(i).asInstanceOf[Entity]
				if (entity.canBeCollidedWith) {
					val box: Float = entity.getCollisionBorderSize()
					val axisAlignedBB: AxisAlignedBB = entity.boundingBox.expand(
						box.asInstanceOf[Double],
						box.asInstanceOf[Double],
						box.asInstanceOf[Double]
					)

					val mop2: MovingObjectPosition = axisAlignedBB
							.calculateIntercept(position, cursor)

					if (axisAlignedBB.isVecInside(position)) {
						mop = new MovingObjectPosition(entity,
							if (mop2 == null) position else mop2.hitVec)
					}
					else if (mop2 != null) {
						mop = new MovingObjectPosition(entity, mop2.hitVec)
					}
				}
			}
		}
		if (mop != null) {
			val yOffset: Double = mop.hitVec.yCoord - this.posY
			if (yOffset >= 0) {
				if (yOffset < 0.5D) {
					armorType = 3
				}
				else if (yOffset < 1.0D) {
					armorType = 2
				}
				else if (yOffset < 1.5D) {
					armorType = 1
				}
				else {
					armorType = 0
				}
			}
		}
		armorType
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
		tagCom.setTag("inventory", this.inventory.writeToNBT(new NBTTagList()))

	}

	override def readEntityFromNBT(tagCom: NBTTagCompound): Unit = {
		this.setRotation(tagCom.getFloat("rotation"))
		this.inventory.readFromNBT(tagCom.getTagList("inventory", 10))

	}

}
