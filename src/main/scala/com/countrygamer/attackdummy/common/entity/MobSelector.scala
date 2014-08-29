package com.countrygamer.attackdummy.common.entity

import net.minecraft.command.IEntitySelector
import net.minecraft.entity.Entity
import net.minecraft.entity.monster.IMob

/**
 *
 *
 * @author CountryGamer
 */
object MobSelector extends IEntitySelector {

	override def isEntityApplicable(entity: Entity): Boolean = {
		entity.isInstanceOf[IMob]
	}

}
