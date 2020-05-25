package me.ichun.mods.torched.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ModelTorchGun extends Model
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
		super(RenderType::getEntityTranslucentCull);

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

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha)
	{
		MagL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha); //first pass
		

		//rest of gun
		main.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		grip.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		stock2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		Holderright.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		sightright.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		handle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		stock1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		stock3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		stock4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		Rail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		Ironsightleft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		sighttop.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		sightleft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		sightbottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		sightattachment.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		ironsightmiddle.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		ironsightright.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		ironsightbottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		barrel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		holderthingright.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		Holderleft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
		Holdertingleft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}
}
