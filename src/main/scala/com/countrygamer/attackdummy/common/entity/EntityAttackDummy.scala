package com.countrygamer.attackdummy.common.entity

import com.countrygamer.cgo.common.lib.util.Player
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.DamageSource
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
class EntityAttackDummy(world: World, x: Double, y: Double, z: Double)
		extends EntityDummy(world, x, y, z) {

	// Other Constructors
	def this(world: World) {
		this(world, 0.0D, 0.0D, 0.0D)

	}

	// End Constructors

	override def attackEntityFrom(source: DamageSource, damage: Float): Boolean = {

		source.getDamageType match {
			case "player" =>
				this.checkAndReport(damage, source.getEntity)
			case "arrow" | "thrown" | "indirectMagic" | "thorns" =>
				this.checkAndReport(damage, source.getEntity)
			case "onFire" | "fireball" =>
				if (source.isProjectile)
					this.checkAndReport(damage, source.getEntity)
			case _ =>
		}

		false
	}

	def checkAndReport(damage: Float, entity: Entity): Unit = {
		entity match {
			case player: EntityPlayer =>
				this.reportToPlayer(damage, player)
			case _ =>
		}
	}

	def reportToPlayer(damage: Float, player: EntityPlayer): Unit = {
		if (!player.worldObj.isRemote)
			Player.sendMessageToPlayer(player,
				"You did " + damage + " hit points of damage.")
	}

	/*
	def applyArmorDeductions(source: DamageSource, damage: Float): Float = {
		var newDamage: Float = damage
		if (!source.isUnblockable) {
			val i: Int = 25 - this.getTotalArmorValue()
			val f1: Float = damage * i.asInstanceOf[Float]
			this.damageArmor(damage)
			newDamage = f1 / 25.0F
		}
		newDamage
	}

	def getTotalArmorValue(): Int = {

	}
	*/

}
