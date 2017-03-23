package me.ichun.mods.torched.client.render;

import me.ichun.mods.ichunutil.client.render.RendererHelper;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.util.Random;

public class RenderTorchFirework extends Render<EntityTorchFirework>
{
    private Random rand = new Random();
    private ItemStack itemstackGunpowder = new ItemStack(Items.GUNPOWDER);
    private ItemStack itemstackGoldNugget = new ItemStack(Items.GOLD_NUGGET);

    public RenderTorchFirework(RenderManager manager)
    {
        super(manager);
    }

    @Override
    public void doRender(EntityTorchFirework torch, double d, double d1, double d2, float f, float f1)
    {
        Block block = Blocks.TORCH;
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(d, d1, d2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        GlStateManager.pushMatrix();

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.disableLighting();
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
        float scale = 1.0F + (torch.getRocket() ? 1.0F :((float)torch.getTorches() / 96F));
        GlStateManager.scale(scale, scale, scale);

        GlStateManager.rotate(torch.prevRotationYaw + (torch.rotationYaw - torch.prevRotationYaw) * f1 - 90F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(90F + torch.prevRotationPitch + (torch.rotationPitch - torch.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(180F, 1.0F, 0.0F, 0.0F);

        IBlockState state = block.getStateFromMeta(0);
        RendererHelper.renderBakedModel(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state), -1, null);

        GlStateManager.enableLighting();

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        rand.setSeed(387L);

        int count = torch.getGP();

        float var22 = 0.25F;

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        float var13 = 0.5F;
        GlStateManager.scale(var13, var13, var13);
        GlStateManager.translate(-0.5F, 0.0F, 0.0F);

        for(int i = 0; i < count; i++)
        {
            GlStateManager.pushMatrix();

            GlStateManager.translate(0.5F, 0.5F, 0.0F);

            if (i > 0)
            {
                GlStateManager.translate(0.0F, -0.3F, 0.0F);
                float var24 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                float var19 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.1F / var22;
                float var20 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                GlStateManager.translate(var24, var19, var20);
                GlStateManager.rotate(rand.nextFloat() * 360F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate((rand.nextFloat() * 180F) - 90F, 1.0F, 0.0F, 0.0F);
            }

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemstackGunpowder);
            RendererHelper.renderBakedModel(model, -1, itemstackGunpowder);

            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();

        GlStateManager.pushMatrix();

        if(!torch.getActive())
        {
            bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            rand.setSeed(327L);

            count = torch.getSplits();

            var22 = 0.23F;

            var13 = 0.5F;
            GlStateManager.scale(var13, var13, var13);
            GlStateManager.translate(-0.5F, 0.0F, 0.0F);

            for(int i = 0; i < count; i++)
            {
                GlStateManager.pushMatrix();

                GlStateManager.translate(0.5F, 0.3F, 0.0F);

                float var24 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                float var19 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.1F / var22;
                float var20 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                GlStateManager.translate(var24, var19, var20);
                GlStateManager.rotate(rand.nextFloat() * 360F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate((rand.nextFloat() * 180F) - 90F, 1.0F, 0.0F, 0.0F);

                IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemstackGoldNugget);
                RendererHelper.renderBakedModel(model, -1, itemstackGoldNugget);

                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTorchFirework par1Entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<EntityTorchFirework>
    {
        @Override
        public Render<? super EntityTorchFirework> createRenderFor(RenderManager manager)
        {
            return new RenderTorchFirework(manager);
        }
    }
}
