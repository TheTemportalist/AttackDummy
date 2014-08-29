package com.countrygamer.attackdummy.common

import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}

/**
 *
 *
 * @author CountryGamer
 */
class InventoryDummy(val owner: Entity, val inventorySize: Int, val maxStackSize: Int)
		extends IInventory {

	var inventory: Array[ItemStack] = new Array[ItemStack](this.inventorySize)
	private var inventoryChanged: Boolean = false

	override def getSizeInventory: Int = {
		this.inventory.length
	}

	override def decrStackSize(slot: Int, stackSize: Int): ItemStack = {
		if (this.inventory(slot) != null) {
			var itemStack: ItemStack = null
			if (this.inventory(slot).stackSize <= stackSize) {
				itemStack = this.inventory(slot)
				this.inventory(slot) = null
				itemStack
			}
			else {
				itemStack = this.inventory(slot).splitStack(stackSize)
				if (this.inventory(slot).stackSize == 0) {
					this.inventory(slot) = null
				}
				itemStack
			}
		}
		else {
			null
		}
	}

	override def getStackInSlotOnClosing(slot: Int): ItemStack = {
		if (this.inventory(slot) != null) {
			val itemStack: ItemStack = this.inventory(slot)
			this.inventory(slot) = null
			itemStack
		}
		else {
			null
		}
	}

	override def setInventorySlotContents(slot: Int, stack: ItemStack): Unit = {
		this.inventory(slot) = stack
	}

	override def getStackInSlot(slot: Int): ItemStack = {
		this.inventory(slot)
	}

	override def getInventoryName: String = {
		"dummy.inventory"
	}

	override def hasCustomInventoryName: Boolean = {
		false
	}

	override def getInventoryStackLimit: Int = {
		this.maxStackSize
	}

	override def markDirty(): Unit = {
		this.inventoryChanged = true
	}

	override def isUseableByPlayer(player: EntityPlayer): Boolean = {
		if (this.owner.isDead) false else player.getDistanceSqToEntity(this.owner) <= 64.0D
	}

	override def openInventory(): Unit = {}

	override def closeInventory(): Unit = {}

	override def isItemValidForSlot(slot: Int, stack: ItemStack): Boolean = {
		true
	}

	def writeToNBT(tagList: NBTTagList): NBTTagList = {
		var stackTag: NBTTagCompound = null
		for (slot <- 0 until this.inventory.length) {
			if (this.inventory(slot) != null) {
				stackTag = new NBTTagCompound
				stackTag.setByte("Slot", slot.toByte)
				this.inventory(slot).writeToNBT(stackTag)
				tagList.appendTag(stackTag)
			}
		}
		tagList
	}

	def readFromNBT(tagList: NBTTagList): Unit = {
		this.inventory = new Array[ItemStack](this.inventorySize)
		for (i <- 0 until tagList.tagCount()) {
			val stackTag: NBTTagCompound = tagList.getCompoundTagAt(i)
			val slot: Int = stackTag.getByte("Slot") & 255
			val itemStack: ItemStack = ItemStack.loadItemStackFromNBT(stackTag)
			if (itemStack != null) {
				this.inventory(slot) = itemStack
			}
		}
	}

}
