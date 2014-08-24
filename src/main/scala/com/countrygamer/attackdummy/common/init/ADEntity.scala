package com.countrygamer.attackdummy.common.init

import com.countrygamer.attackdummy.common.AttackDummy
import com.countrygamer.attackdummy.common.entity.{EntityAttackDummy, EntityDefenseDummy}
import com.countrygamer.cgo.wrapper.common.registries.EntityRegister
import cpw.mods.fml.common.registry.EntityRegistry
import net.minecraft.entity.EntityList

/**
 *
 *
 * @author CountryGamer
 */
object ADEntity extends EntityRegister {

	override def register(): Unit = {
		EntityRegistry
				.registerModEntity(classOf[EntityAttackDummy], "Attack Dummy", 1, AttackDummy, 80,
		            3, false)
		EntityRegistry
				.registerModEntity(classOf[EntityDefenseDummy], "Defense Dummy", 2, AttackDummy, 80,
		            3, false)

	}

	override def addEntityMappings(): Unit = {
		EntityList.addMapping(classOf[EntityAttackDummy], "Attack Dummy", this.getNewEntityID())
		EntityList.addMapping(classOf[EntityDefenseDummy], "Defense Dummy", this.getNewEntityID())

	}

	override def addEntitySpawns(): Unit = {
	}

}
