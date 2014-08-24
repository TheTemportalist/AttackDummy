package com.countrygamer.attackdummy.common.entity

import net.minecraft.util.{DamageSource, MathHelper}
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class EntityDefenseDummy(world: World, x: Double, y: Double, z: Double)
		extends EntityDummy(world, x, y, z) {

	def this(world: World) {
		this(world, 0.0D, 0.0D, 0.0D)

	}

	override def entityInit(): Unit = {
		super.entityInit()
		this.dataWatcher.addObject(17, java.lang.Float.valueOf(50)) // max health
		this.dataWatcher.addObject(18, java.lang.Float.valueOf(50)) // current health

	}

	def setMaxHealth(health: Float): EntityDefenseDummy = {
		this.dataWatcher.updateObject(17, java.lang.Float.valueOf(health))
		this
	}

	def getMaxHealth(): Float = {
		this.dataWatcher.getWatchableObjectFloat(17)
	}

	def setHealth(health: Float): EntityDefenseDummy = {
		this.dataWatcher.updateObject(18,
			java.lang.Float.valueOf(MathHelper.clamp_float(health, 0.0F, this.getMaxHealth())))
		this
	}

	def getHealth(): Float = {
		this.dataWatcher.getWatchableObjectFloat(18)
	}

	override def attackEntityFrom(source: DamageSource, damage: Float): Boolean = {
		val newHealth: Float = this.getHealth() - damage
		if (newHealth <= 0.0F) {
			this.setDead()
		}
		else {
			this.setHealth(newHealth)
		}
		true
	}

}
