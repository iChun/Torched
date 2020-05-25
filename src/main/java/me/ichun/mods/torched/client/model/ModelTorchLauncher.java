package me.ichun.mods.torched.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelTorchLauncher extends Model
{
	//fields
	public ModelRenderer handle;
	public ModelRenderer barrel9;
	public ModelRenderer barrel10;
	public ModelRenderer barrel11;
	public ModelRenderer barrel12;
	public ModelRenderer barrel13;
	public ModelRenderer barrel14;
	public ModelRenderer barrel15;
	public ModelRenderer barrel16;
	public ModelRenderer barrel1;
	public ModelRenderer barrel8;
	public ModelRenderer barrel7;
	public ModelRenderer barrel2;
	public ModelRenderer barrel3;
	public ModelRenderer barrel6;
	public ModelRenderer barrel4;
	public ModelRenderer barrel5;
	public ModelRenderer backright;
	public ModelRenderer backmiddle;
	public ModelRenderer backbottom;
	public ModelRenderer backtop;
	public ModelRenderer backleft;
	public ModelRenderer baselauncher;

	public ModelTorchLauncher()
	{
		super(RenderType::getEntityTranslucentCull);

		textureWidth = 256;
		textureHeight = 256;

		handle = new ModelRenderer(this, 44, 0);
		handle.addBox(-1F, -1F, -3F, 2, 4, 2);
		handle.setRotationPoint(0F, 0F, 0F);
		setRotation(handle, 0F, 0F, 0F);
		barrel9 = new ModelRenderer(this, 0, 14);
		barrel9.addBox(-1F, -7F, 4F, 2, 1, 8);
		barrel9.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel9, 0F, 0F, 0F);
		barrel10 = new ModelRenderer(this, 0, 33);
		barrel10.addBox(1F, -6F, 4F, 1, 1, 8);
		barrel10.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel10, 0F, 0F, 0F);
		barrel11 = new ModelRenderer(this, 0, 23);
		barrel11.addBox(2F, -5F, 4F, 1, 2, 8);
		barrel11.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel11, 0F, 0F, 0F);
		barrel12 = new ModelRenderer(this, 0, 42);
		barrel12.addBox(1F, -3F, 4F, 1, 1, 8);
		barrel12.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel12, 0F, 0F, 0F);
		barrel13 = new ModelRenderer(this, 0, 51);
		barrel13.addBox(-1F, -2F, 4F, 2, 1, 8);
		barrel13.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel13, 0F, 0F, 0F);
		barrel14 = new ModelRenderer(this, 0, 42);
		barrel14.addBox(-2F, -3F, 4F, 1, 1, 8);
		barrel14.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel14, 0F, 0F, 0F);
		barrel15 = new ModelRenderer(this, 0, 23);
		barrel15.addBox(-3F, -5F, 4F, 1, 2, 8);
		barrel15.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel15, 0F, 0F, 0F);
		barrel16 = new ModelRenderer(this, 0, 33);
		barrel16.addBox(-2F, -6F, 4F, 1, 1, 8);
		barrel16.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel16, 0F, 0F, 0F);
		barrel1 = new ModelRenderer(this, 0, 14);
		barrel1.addBox(-1F, -7F, -12F, 2, 1, 8);
		barrel1.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel1, 0F, 0F, 0F);
		barrel8 = new ModelRenderer(this, 0, 33);
		barrel8.addBox(-2F, -6F, -12F, 1, 1, 8);
		barrel8.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel8, 0F, 0F, 0F);
		barrel7 = new ModelRenderer(this, 0, 23);
		barrel7.addBox(-3F, -5F, -12F, 1, 2, 8);
		barrel7.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel7, 0F, 0F, 0F);
		barrel2 = new ModelRenderer(this, 0, 33);
		barrel2.addBox(1F, -6F, -12F, 1, 1, 8);
		barrel2.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel2, 0F, 0F, 0F);
		barrel3 = new ModelRenderer(this, 0, 23);
		barrel3.addBox(2F, -5F, -12F, 1, 2, 8);
		barrel3.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel3, 0F, 0F, 0F);
		barrel6 = new ModelRenderer(this, 0, 42);
		barrel6.addBox(-2F, -3F, -12F, 1, 1, 8);
		barrel6.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel6, 0F, 0F, 0F);
		barrel4 = new ModelRenderer(this, 0, 42);
		barrel4.addBox(1F, -3F, -12F, 1, 1, 8);
		barrel4.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel4, 0F, 0F, 0F);
		barrel5 = new ModelRenderer(this, 0, 51);
		barrel5.addBox(-1F, -2F, -12F, 2, 1, 8);
		barrel5.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel5, 0F, 0F, 0F);
		backright = new ModelRenderer(this, 0, 0);
		backright.addBox(-2F, -5F, 11F, 1, 2, 1);
		backright.setRotationPoint(0F, 0F, 0F);
		setRotation(backright, 0F, 0F, 0F);
		backmiddle = new ModelRenderer(this, 28, 6);
		backmiddle.addBox(-1F, -5F, 11F, 2, 2, 1);
		backmiddle.setRotationPoint(0F, 0F, 0F);
		setRotation(backmiddle, 0F, 0F, 0F);
		backbottom = new ModelRenderer(this, 0, 0);
		backbottom.addBox(-1F, -3F, 11F, 2, 1, 1);
		backbottom.setRotationPoint(0F, 0F, 0F);
		setRotation(backbottom, 0F, 0F, 0F);
		backtop = new ModelRenderer(this, 0, 0);
		backtop.addBox(-1F, -6F, 11F, 2, 1, 1);
		backtop.setRotationPoint(0F, 0F, 0F);
		setRotation(backtop, 0F, 0F, 0F);
		backleft = new ModelRenderer(this, 0, 0);
		backleft.addBox(1F, -5F, 11F, 1, 2, 1);
		backleft.setRotationPoint(0F, 0F, 0F);
		setRotation(backleft, 0F, 0F, 0F);
		baselauncher = new ModelRenderer(this, 0, 0);
		baselauncher.addBox(-3F, -7F, -4F, 6, 6, 8);
		baselauncher.setRotationPoint(0F, 0F, 0F);
		setRotation(baselauncher, 0F, 0F, 0F);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		handle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel10.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel11.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel12.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel13.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel14.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel15.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel16.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

		backright.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		backmiddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		backbottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		backtop.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		backleft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		baselauncher.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
