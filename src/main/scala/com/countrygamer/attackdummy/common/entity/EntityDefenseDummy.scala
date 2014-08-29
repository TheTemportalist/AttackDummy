package com.countrygamer.attackdummy.common.entity

import net.minecraft.entity.{Entity, EntityCreature}
import net.minecraft.util.DamageSource
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

	}

	override def attackEntityFrom(source: DamageSource, damage: Float): Boolean = {
		val newHealth: Float = this.getHealth() - this.applyArmorCalculations(source, damage)
		if (newHealth <= 0.0F) {
			this.setDead()
		}
		else {
			this.setHealth(newHealth)
		}
		true
	}

	override def onUpdate(): Unit = {
		super.onUpdate()
		val entities: java.util.List[_] = this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
			this.boundingBox.expand(6.0D, 6.0D, 6.0D), MobSelector)
		for (i <- 0 until entities.size()) {
			val entity: Entity = entities.get(i).asInstanceOf[Entity]
			entity match {
				case creature: EntityCreature =>
					if (creature.getEntityToAttack != this) {
						creature.setTarget(this)
						return
					}
				case _ =>
			}
		}
	}

}
