package me.ichun.mods.torched.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelTorchGun extends ModelBase
{
	public ModelRenderer main;
	public ModelRenderer grip;
	public ModelRenderer stock2;
	public ModelRenderer Holderright;
	public ModelRenderer sightright;
	public ModelRenderer handle;
	public ModelRenderer MagL;
	public ModelRenderer stock1;
	public ModelRenderer stock3;
	public ModelRenderer stock4;
	public ModelRenderer Rail;
//	public ModelRenderer MagR;
	public ModelRenderer Ironsightleft;
	public ModelRenderer sighttop;
	public ModelRenderer sightleft;
	public ModelRenderer sightbottom;
	public ModelRenderer sightattachment;
	public ModelRenderer ironsightmiddle;
	public ModelRenderer ironsightright;
	public ModelRenderer ironsightbottom;
	public ModelRenderer barrel;
	public ModelRenderer holderthingright;
	public ModelRenderer Holderleft;
	public ModelRenderer Holdertingleft;

	public ModelTorchGun()
	{
		textureWidth = 256;
		textureHeight = 128;

		main = new ModelRenderer(this, 0, 0);
		main.addBox(-19F, -7F, -2.5F, 21, 4, 5);
		main.setRotationPoint(0F, 0F, 0F);
		setRotation(main, 0F, 0F, 0F);
		
		grip = new ModelRenderer(this, 48, 22);
		grip.addBox(-2F, -4F, -1.5F, 5, 8, 3);
		grip.setRotationPoint(0F, 0F, 0F);
		setRotation(grip, 0F, 0F, -0.1745329F);
		
		stock2 = new ModelRenderer(this, 15, 2);
		stock2.addBox(8F, -6F, -1F, 4, 5, 2);
		stock2.setRotationPoint(0F, 0F, 0F);
		setRotation(stock2, 0F, 0F, 0F);
		
		Holderright = new ModelRenderer(this, 66, 3);
		Holderright.addBox(-37F, -8F, 0.5F, 12, 1, 1);
		Holderright.setRotationPoint(0F, 0F, 0F);
		setRotation(Holderright, 0F, 0F, -0.1396263F);
		
		sightright = new ModelRenderer(this, 66, 0);
		sightright.addBox(-27F, -13.5F, 1F, 1, 2, 1);
		sightright.setRotationPoint(0F, 0F, 0F);
		setRotation(sightright, 0F, 0F, 0F);
		
		handle = new ModelRenderer(this, 9, 1);
		handle.addBox(-30F, -7F, -2.5F, 11, 3, 5);
		handle.setRotationPoint(0F, 0F, 0F);
		setRotation(handle, 0F, 0F, 0F);
		
		MagL = new ModelRenderer(this, 48, 33);
		MagL.addBox(-5F, -3F, -16.5F, 10, 6, 5);
		MagL.setRotationPoint(0F, 0F, 0F);
		setRotation(MagL, 0F, (float)Math.toRadians(90F), 0F);
		
		stock1 = new ModelRenderer(this, 15, 3);
		stock1.addBox(0F, -8F, -1F, 8, 4, 2);
		stock1.setRotationPoint(0F, 0F, 0F);
		setRotation(stock1, 0F, 0F, 0.2617994F);
		
		stock3 = new ModelRenderer(this, 15, 0);
		stock3.addBox(12F, -6F, -1F, 4, 6, 2);
		stock3.setRotationPoint(0F, 0F, 0F);
		setRotation(stock3, 0F, 0F, 0F);
		
		stock4 = new ModelRenderer(this, 15, 0);
		stock4.addBox(16F, -6F, -1F, 4, 7, 2);
		stock4.setRotationPoint(0F, 0F, 0F);
		setRotation(stock4, 0F, 0F, 0F);
		
		Rail = new ModelRenderer(this, 0, 10);
		Rail.addBox(-29F, -9.5F, -1.5F, 28, 1, 3);
		Rail.setRotationPoint(0F, 0F, 0F);
		setRotation(Rail, 0F, 0F, 0F);
		
//		MagR = new ModelRenderer(this, 48, 33);
//		MagR.addBox(-11F, -7F, 2.5F, 10, 6, 5);
//		MagR.setRotationPoint(0F, 0F, 0F);
//		setRotation(MagR, 0F, 0F, 0F);
		
		Ironsightleft = new ModelRenderer(this, 66, 0);
		Ironsightleft.addBox(-7F, -11.5F, -2F, 2, 2, 1);
		Ironsightleft.setRotationPoint(0F, 0F, 0F);
		setRotation(Ironsightleft, 0F, 0F, 0F);
		
		sighttop = new ModelRenderer(this, 66, 0);
		sighttop.addBox(-27F, -14.5F, -1F, 1, 1, 2);
		sighttop.setRotationPoint(0F, 0F, 0F);
		setRotation(sighttop, 0F, 0F, 0F);
		
		sightleft = new ModelRenderer(this, 66, 0);
		sightleft.addBox(-27F, -13.5F, -2F, 1, 2, 1);
		sightleft.setRotationPoint(0F, 0F, 0F);
		setRotation(sightleft, 0F, 0F, 0F);
		
		sightbottom = new ModelRenderer(this, 66, 0);
		sightbottom.addBox(-27F, -11.5F, -1F, 1, 1, 2);
		sightbottom.setRotationPoint(0F, 0F, 0F);
		setRotation(sightbottom, 0F, 0F, 0F);
		
		sightattachment = new ModelRenderer(this, 66, 0);
		sightattachment.addBox(-28F, -10.5F, -2F, 5, 2, 4);
		sightattachment.setRotationPoint(0F, 0F, 0F);
		setRotation(sightattachment, 0F, 0F, 0F);
		
		ironsightmiddle = new ModelRenderer(this, 66, 0);
		ironsightmiddle.addBox(-7F, -11F, -0.5F, 1, 1, 1);
		ironsightmiddle.setRotationPoint(0F, 0F, 0F);
		setRotation(ironsightmiddle, 0F, 0F, 0F);
		
		ironsightright = new ModelRenderer(this, 66, 0);
		ironsightright.addBox(-7F, -11.5F, 1F, 2, 2, 1);
		ironsightright.setRotationPoint(0F, 0F, 0F);
		setRotation(ironsightright, 0F, 0F, 0F);
		
		ironsightbottom = new ModelRenderer(this, 66, 0);
		ironsightbottom.addBox(-7F, -10.5F, -2F, 5, 2, 4);
		ironsightbottom.setRotationPoint(0F, 0F, 0F);
		setRotation(ironsightbottom, 0F, 0F, 0F);
		
		barrel = new ModelRenderer(this, 0, 14);
		barrel.addBox(-36F, -8.5F, -2F, 37, 4, 4);
		barrel.setRotationPoint(0F, 0F, 0F);
		setRotation(barrel, 0F, 0F, 0F);
		
		holderthingright = new ModelRenderer(this, 66, 3);
		holderthingright.addBox(-31F, -9F, 0.5F, 1, 1, 1);
		holderthingright.setRotationPoint(0F, 0F, 0F);
		setRotation(holderthingright, 0F, 0F, -0.1396263F);
		
		Holderleft = new ModelRenderer(this, 66, 3);
		Holderleft.addBox(-37F, -8F, -1.5F, 12, 1, 1);
		Holderleft.setRotationPoint(0F, 0F, 0F);
		setRotation(Holderleft, 0F, 0F, -0.1396263F);
		
		Holdertingleft = new ModelRenderer(this, 66, 3);
		Holdertingleft.addBox(-31F, -9F, -1.5F, 1, 1, 1);
		Holdertingleft.setRotationPoint(0F, 0F, 0F);
		setRotation(Holdertingleft, 0F, 0F, -0.1396263F);
	}

	public void render(float f5, boolean firstPerson, int pass)
	{
		if(pass > 0)
		{
			main.render(f5);
			grip.render(f5);
			stock2.render(f5);
			Holderright.render(f5);
			sightright.render(f5);
			handle.render(f5);
			stock1.render(f5);
			stock3.render(f5);
			stock4.render(f5);
			Rail.render(f5);
			Ironsightleft.render(f5);
			sighttop.render(f5);
			sightleft.render(f5);
			sightbottom.render(f5);
			sightattachment.render(f5);
			ironsightmiddle.render(f5);
			ironsightright.render(f5);
			ironsightbottom.render(f5);
			barrel.render(f5);
			holderthingright.render(f5);
			Holderleft.render(f5);
			Holdertingleft.render(f5);
		}
		
		MagL.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity ent)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, ent);
	}

}
