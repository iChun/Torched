package me.ichun.mods.torched.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.ichun.mods.ichunutil.client.render.RenderHelper;
import me.ichun.mods.torched.common.entity.EntityTorch;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTorch extends EntityRenderer<EntityTorch>
{
    public static final RenderType RENDER_TYPE = RenderType.makeType("entity_cutout_no_lighting", DefaultVertexFormats.ENTITY, 7, 256, true, false, RenderType.State.getBuilder().texture(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false)).transparency(RenderState.NO_TRANSPARENCY).alpha(RenderState.DEFAULT_ALPHA).overlay(RenderState.OVERLAY_ENABLED).build(true));
    public static final ItemStack TORCH_STACK = new ItemStack(Blocks.TORCH.asItem());

    public RenderTorch(EntityRendererManager manager)
    {
        super(manager);
    }

    @Override
    public void render(EntityTorch torch, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        stack.rotate(Vector3f.YP.rotationDegrees(torch.prevRotationYaw + (torch.rotationYaw - torch.prevRotationYaw) * partialTicks - 90F));
        stack.rotate(Vector3f.ZP.rotationDegrees(90F + torch.prevRotationPitch + (torch.rotationPitch - torch.prevRotationPitch) * partialTicks));

        BlockState state = Blocks.TORCH.getDefaultState();
        RenderHelper.renderBakedModel(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(state), TORCH_STACK, RENDER_TYPE, stack, bufferIn);
    }

    @Override
    protected int getBlockLight(EntityTorch entityIn, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getEntityTexture(EntityTorch par1Entity)
    {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    public static class RenderFactory implements IRenderFactory<EntityTorch>
    {
        @Override
        public EntityRenderer<? super EntityTorch> createRenderFor(EntityRendererManager manager)
        {
            return new RenderTorch(manager);
        }
    }

}
