package me.ichun.mods.torched.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTorchLauncher extends ModelBase
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

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		handle.render(f5);
		barrel9.render(f5);
		barrel10.render(f5);
		barrel11.render(f5);
		barrel12.render(f5);
		barrel13.render(f5);
		barrel14.render(f5);
		barrel15.render(f5);
		barrel16.render(f5);
		barrel1.render(f5);
		barrel8.render(f5);
		barrel7.render(f5);
		barrel2.render(f5);
		barrel3.render(f5);
		barrel6.render(f5);
		barrel4.render(f5);
		barrel5.render(f5);
		
		backright.render(f5);
		backmiddle.render(f5);
		backbottom.render(f5);
		backtop.render(f5);
		backleft.render(f5);
		baselauncher.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
	}

}
