package me.ichun.mods.torched.client.render;

import me.ichun.mods.ichunutil.client.render.RendererHelper;
import me.ichun.mods.torched.common.entity.EntityTorch;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTorch extends Render<EntityTorch>
{
    public RenderTorch(RenderManager manager)
    {
        super(manager);
    }

    @Override
    public void doRender(EntityTorch torch, double d, double d1, double d2, float f, float f1)
    {
        Block block = Blocks.TORCH;
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(d, d1, d2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        bindEntityTexture(torch);
        GlStateManager.disableLighting();
        GlStateManager.rotate(torch.prevRotationYaw + (torch.rotationYaw - torch.prevRotationYaw) * f1 - 90F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(90F + torch.prevRotationPitch + (torch.rotationPitch - torch.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);

        IBlockState state = block.getStateFromMeta(0);
        RendererHelper.renderBakedModel(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state), -1, null);

        GlStateManager.enableLighting();

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTorch par1Entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<EntityTorch>
    {
        @Override
        public Render<? super EntityTorch> createRenderFor(RenderManager manager)
        {
            return new RenderTorch(manager);
        }
    }

}
