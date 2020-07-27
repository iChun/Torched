package me.ichun.mods.torched.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.ichun.mods.ichunutil.client.render.RenderHelper;
import me.ichun.mods.torched.common.entity.EntityTorchFirework;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import java.util.Random;

public class RenderTorchFirework extends EntityRenderer<EntityTorchFirework>
{
    private Random rand = new Random();
    private static final ItemStack GUNPOWDER_STACK = new ItemStack(Items.GUNPOWDER);
    private static final ItemStack GOLD_NUGGET_STACK = new ItemStack(Items.GOLD_NUGGET);

    public RenderTorchFirework(EntityRendererManager manager)
    {
        super(manager);
    }

    @Override
    public void render(EntityTorchFirework torch, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        stack.push();

        stack.translate(0.0F, 0.5F, 0.0F);
        float scale = 1.0F + (torch.getRocket() ? 1.0F :((float)torch.getTorches() / 96F));
        stack.scale(scale, scale, scale);

        stack.rotate(Vector3f.YP.rotationDegrees(torch.prevRotationYaw + (torch.rotationYaw - torch.prevRotationYaw) * partialTicks - 90F));
        stack.rotate(Vector3f.ZP.rotationDegrees(90F + torch.prevRotationPitch + (torch.rotationPitch - torch.prevRotationPitch) * partialTicks));

        BlockState state = Blocks.TORCH.getDefaultState();
        RenderHelper.renderBakedModel(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(state), RenderTorch.TORCH_STACK, RenderTorch.RENDER_TYPE, stack, bufferIn);

        stack.pop();

        stack.push();

        rand.setSeed(387L);

        int count = torch.getGP();

        float var22 = 0.25F;
        float var13 = 0.5F;
        stack.scale(var13, var13, var13);
        stack.translate(-0.5F, 0.0F, 0.0F);

        for(int i = 0; i < count; i++)
        {
            stack.push();

            stack.translate(0.5F, 0.5F, 0.0F);

            if (i > 0)
            {
                stack.translate(0.0F, -0.3F, 0.0F);
                float var24 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                float var19 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.1F / var22;
                float var20 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                stack.translate(var24, var19, var20);
                stack.rotate(Vector3f.YP.rotationDegrees(rand.nextFloat() * 360F));
                stack.rotate(Vector3f.XP.rotationDegrees((rand.nextFloat() * 180F) - 90F));
            }

            IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(GUNPOWDER_STACK);
            RenderHelper.renderBakedModel(model, GUNPOWDER_STACK, null, stack, bufferIn);

            stack.pop();
        }

        stack.pop();

        if(!torch.getActive())
        {
            stack.push();
            rand.setSeed(327L);

            count = torch.getSplits();

            var22 = 0.23F;

            var13 = 0.5F;
            stack.scale(var13, var13, var13);
            stack.translate(-0.5F, 0.0F, 0.0F);

            for(int i = 0; i < count; i++)
            {
                stack.push();

                stack.translate(0.5F, 0.3F, 0.0F);

                float var24 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                float var19 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.1F / var22;
                float var20 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F / var22;
                stack.translate(var24, var19, var20);
                stack.rotate(Vector3f.YP.rotationDegrees(rand.nextFloat() * 360F));
                stack.rotate(Vector3f.XP.rotationDegrees((rand.nextFloat() * 180F) - 90F));

                IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(GOLD_NUGGET_STACK);
                RenderHelper.renderBakedModel(model, GOLD_NUGGET_STACK, null, stack, bufferIn);

                stack.pop();
            }
            stack.pop();
        }
    }

    @Override
    protected int getBlockLight(EntityTorchFirework entityIn, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityTorchFirework par1Entity)
    {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<EntityTorchFirework>
    {
        @Override
        public EntityRenderer<? super EntityTorchFirework> createRenderFor(EntityRendererManager manager)
        {
            return new RenderTorchFirework(manager);
        }
    }
}
