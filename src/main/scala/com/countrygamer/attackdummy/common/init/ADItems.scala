package com.countrygamer.attackdummy.common.init

import com.countrygamer.attackdummy.common.AttackDummy
import com.countrygamer.attackdummy.common.entity.{EntityAttackDummy, EntityDefenseDummy, EntityDummy}
import com.countrygamer.attackdummy.common.item.ItemDummy
import com.countrygamer.cgo.wrapper.common.registries.ItemRegister
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.world.World

/**
 *
 *
 * @author CountryGamer
 */
object ADItems extends ItemRegister {

	var attackDummy: Item = null
	var defenseDummy: Item = null

	override def register(): Unit = {

		this.attackDummy = new ItemDummy(AttackDummy.pluginID, "Attack Dummy") {
			override def getEntity(world: World, x: Double, y: Double, z: Double): EntityDummy = {
				new EntityAttackDummy(world, x, y, z)
			}
		}
		this.attackDummy.setCreativeTab(CreativeTabs.tabCombat)
		this.defenseDummy = new ItemDummy(AttackDummy.pluginID, "Defense Dummy") {
			override def getEntity(world: World, x: Double, y: Double, z: Double): EntityDummy = {
				new EntityDefenseDummy(world, x, y, z)
			}
		}
		this.defenseDummy.setCreativeTab(CreativeTabs.tabCombat)

	}

	override def registerItemsPostBlock(): Unit = {

	}

	override def registerCrafting(): Unit = {
		GameRegistry.addShapedRecipe(new ItemStack(this.attackDummy),
			" p ",
			"shs",
			" s ",
			Character.valueOf('p'), new ItemStack(Blocks.pumpkin, 1, 0),
			Character.valueOf('s'), new ItemStack(Items.stick, 1, 0),
			Character.valueOf('h'), new ItemStack(Blocks.hay_block, 1, 0)
		)
		GameRegistry.addShapedRecipe(new ItemStack(this.defenseDummy),
			" p ",
			"shs",
			" s ",
			Character.valueOf('p'), new ItemStack(Blocks.pumpkin, 1, 0),
			Character.valueOf('s'), new ItemStack(Blocks.cobblestone, 1, 0),
			Character.valueOf('h'), new ItemStack(Blocks.iron_block, 1, 0)
		)

	}

	override def registerSmelting(): Unit = {

	}

	override def registerOther(): Unit = {

	}

}
