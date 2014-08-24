package com.countrygamer.attackdummy.client.render.models

import com.countrygamer.cgo.client.render.model.{ModelHelper, ModelWrapper}

/**
 *
 *
 * @author CountryGamer
 */
class ModelDummy() extends ModelWrapper(64, 64) {

	// Default Constructor
	{
		// leg stick
		ModelHelper.createModel(this, this,
			0.0F, 8.0F, 0.0F,
			-1.0F, -12.0F, -1.0F,
			0.0F, 0.0F, 0.0F,
			2, 12, 2,
			0, 32
		)

		// body
		ModelHelper.createModel(this, this,
			0.0F, 20.0F, 0.0F,
			-4.0F, -12.0F, -2.0F,
			0.0F, 0.0F, 0.0F,
			8, 12, 4,
			4, 16
		)

		// head
		ModelHelper.createModel(this, this,
			0.0F, 32.0F, 0.0F,
			-4.0F, -8.0F, -4.0F,
			0.0F, 0.0F, 0.0F,
			8, 8, 8,
			0, 0
		)

		// right arm
		ModelHelper.createModel(this, this,
			0.0F, 29.0F, 0.0F,
			4.0F, -1.0F, -1.0F,
			0.0F, 0.0F, 0.0F,
			8, 2, 2,
			8, 32
		)

		// left arm
		ModelHelper.createModel(this, this,
			0.0F, 29.0F, 0.0F,
			-12.0F, -1.0F, -1.0F,
			0.0F, 0.0F, 0.0F,
			8, 2, 2,
			8, 32
		)

	}
	// End Constructor

}
