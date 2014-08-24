package com.countrygamer.attackdummy.client.render.models

import com.countrygamer.attackdummy.common.AttackDummy
import com.countrygamer.attackdummy.common.entity.{EntityAttackDummy, EntityDefenseDummy, EntityDummy}
import com.countrygamer.cgo.client.render.model.ModelWrapper
import net.minecraft.client.renderer.entity.Render
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

/**
 *
 *
 * @author CountryGamer
 */
class RenderDummy() extends Render() {

	val attackDummy: ResourceLocation = new
					ResourceLocation(AttackDummy.pluginID, "textures/model/entity/AttackDummy.png")
	val defenseDummy: ResourceLocation = new
					ResourceLocation(AttackDummy.pluginID, "textures/model/entity/DefenseDummy.png")
	val model: ModelDummy = new ModelDummy()

	override def doRender(entity: Entity, viewX: Double, viewY: Double, viewZ: Double, f1: Float,
			f2: Float): Unit = {
		GL11.glPushMatrix()

		GL11.glTranslated(viewX, viewY, viewZ)
		GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F)
		entity match {
			case dummy: EntityDummy =>
				GL11.glRotatef(dummy.getRotation(), 0.0F, 1.0F, 0.0F)
			case _ =>
		}

		this.bindEntityTexture(entity)
		this.model.renderModel(ModelWrapper.f5)

		GL11.glPopMatrix()
	}

	override def getEntityTexture(entity: Entity): ResourceLocation = {
		entity match {
			case attack: EntityAttackDummy =>
				this.attackDummy
			case defense: EntityDefenseDummy =>
				this.defenseDummy
			case _ =>
				this.attackDummy
		}
	}

}
