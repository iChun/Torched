package me.ichun.mods.torched.client.render;

import me.ichun.mods.ichunutil.client.model.item.IModelBase;
import me.ichun.mods.ichunutil.client.model.item.ModelBaseWrapper;
import me.ichun.mods.ichunutil.client.render.RendererHelper;
import me.ichun.mods.torched.client.model.ModelTorchGun;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Random;

@SuppressWarnings("deprecation")
public class ItemRenderTorchGun implements IModelBase
{
    public ModelTorchGun torchGunModel;
    public Random rand;
    public ItemStack flintSteel;
    public ItemStack powder;
    public ItemStack torch;
    private ItemStack heldStack;
    private ItemCameraTransforms.TransformType currentPerspective;
    public EntityPlayer lastPlayer;

    private static final ResourceLocation texture = new ResourceLocation("torched","textures/model/torchgun.png");
    private static final ItemCameraTransforms cameraTransforms = new ItemCameraTransforms(
            new ItemTransformVec3f(new Vector3f(95F, -90F, -60F), new Vector3f(-0.055F, -0.165F, -0.15F), new Vector3f(-1.0F, -1.0F, 1.0F)),//tp left
            new ItemTransformVec3f(new Vector3f(95F, 90F, 60F), new Vector3f(-0.025F, -0.165F, -0.15F), new Vector3f(-1.0F, -1.0F, 1.0F)),//tp right
            new ItemTransformVec3f(new Vector3f(5F, -90F, 30F), new Vector3f(-0.1F, -0.1F, -0.1F), new Vector3f(1F, 1F, 1F)),//fp left
            new ItemTransformVec3f(new Vector3f(5F, 95F, -30F), new Vector3f(0.1F, -0.1F, -0.1F), new Vector3f(1F, 1F, 1F)),//fp right
            new ItemTransformVec3f(new Vector3f(0F, 0F, 0F), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F)),//head
            new ItemTransformVec3f(new Vector3f(10F, 140F, -10F), new Vector3f(0F, -0.15F, 0F), new Vector3f(0.65F, 0.65F, 0.65F)),//gui
            new ItemTransformVec3f(new Vector3f(-10F, 140F, -10F), new Vector3f(0F, -0.05F, 0F), new Vector3f(0.3F, 0.3F, 0.3F)),//ground
            new ItemTransformVec3f(new Vector3f(-10F, 140F, -10F), new Vector3f(0F, -0.05F, 0F), new Vector3f(0.3F, 0.3F, 0.3F))//fixed
    );

    public ItemRenderTorchGun()
    {
        torchGunModel = new ModelTorchGun();
        rand = new Random();
        flintSteel = new ItemStack(Items.FLINT_AND_STEEL);
        powder = new ItemStack(Items.BLAZE_POWDER);
        torch = new ItemStack(Blocks.TORCH);
    }

    @Override
    public ResourceLocation getTexture()
    {
        return texture;
    }

    @Override
    public void renderModel(float renderTick)
    {
        Minecraft mc = Minecraft.getMinecraft();
        boolean isFirstPerson = ModelBaseWrapper.isFirstPerson(currentPerspective);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.75F, 0.2F, 0.0F);
        GlStateManager.scale(-0.75F, -0.75F, 0.75F);

        //Torch Render
        GlStateManager.pushMatrix();

        GlStateManager.rotate(125F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-20F, 1.0F, 1.0F, 0.0F);

        if(isFirstPerson)
        {
            GlStateManager.translate(-0.18F, 0.28F, 1.15F);
        }
        else
        {
            GlStateManager.translate(-0.31F, 0.35F, 1.26F);
        }

        float scale1 = 0.15F;
        GlStateManager.scale(scale1, scale1, scale1);

        mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        rand.setSeed(187L);

        float var22 = 0.45F;
        int count = 64;
        if(lastPlayer == mc.thePlayer && !Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode)
        {
            count = 0;
            ItemStack[] stacks = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
            for(ItemStack is : stacks)
            {
                if(is != null && is.getItem() == Item.getItemFromBlock(Blocks.TORCH))
                {
                    count += is.stackSize;
                }
            }
            if(count > 64)
            {
                count = 64;
            }
        }

        for(int i = 0; i < count; i++)
        {
            GlStateManager.pushMatrix();

            GlStateManager.rotate(20F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-10F, 1.0F, 0.0F, 5F);

            if (i > 0)
            {
                float var24 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.15F / var22;
                float var19 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.1F / var22;
                float var20 = (this.rand.nextFloat() * 2.0F - 1.0F) * 0.15F / var22;
                GlStateManager.translate(var24 - ((float)(i % 8) * 0.13F), var19, var20 - ((float)(i % 8) * 0.15F));
                if(i == 40 || i == 16)
                {
                    GlStateManager.translate(-0.4F, 0.0F, 0.0F);
                }
            }

            IBlockState state = Blocks.TORCH.getStateFromMeta(0);
            RendererHelper.renderBakedModel(mc.getBlockRendererDispatcher().getBlockModelShapes().getModelForState(state), -1, torch);

            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
        //End Torch Render

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GlStateManager.rotate(-6F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-150F, 0.0F, 0.0F, 1.0F);

        GlStateManager.translate(-0.8F, 0.05F, -0.08F);

        float scale = 0.6F;
        GlStateManager.scale(scale, scale, scale);

        if(isFirstPerson)
        {
            GlStateManager.translate(0.30F, 0.00F, -0.20F);
        }

        GlStateManager.disableCull();
        GlStateManager.depthMask(false);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        torchGunModel.render(0.0625F, isFirstPerson, 0);

        if(isFirstPerson)
        {
            GlStateManager.enableCull();
        }
        GlStateManager.depthMask(true);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        torchGunModel.render(0.0625F, isFirstPerson, 1);

        GlStateManager.rotate(165F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(-45F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-3.5F, 0.0F, 1.0F, 0.0F);

        GlStateManager.translate(1.5F, 0.6F, -1.7F);

        scale = 0.4F;
        GlStateManager.scale(scale, scale, scale);

        if(heldStack != null && heldStack.getItemDamage() < heldStack.getMaxDamage())
        {
            ItemStack is = Torched.eventHandlerClient.firing > 0 && lastPlayer == mc.thePlayer ? powder : flintSteel;

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(is);
            GlStateManager.translate(-0.125F, 0.5F, 0.2F);
            GlStateManager.rotate(230F, 0F, 1F, 0F);
            GlStateManager.rotate(50F, 0F, 0F, 1F);

            RendererHelper.renderBakedModel(model, -1, is);

            GlStateManager.translate(0.0F, 0.0F, 0.03F);

            RendererHelper.renderBakedModel(model, -1, is);

            GlStateManager.translate(0.0F, 0.0F, 0.03F);

            RendererHelper.renderBakedModel(model, -1, is);
        }

        GlStateManager.enableLighting();

        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
    }

    @Override
    public void postRender()
    {
        lastPlayer = null;
        currentPerspective = null;
    }

    @Override
    public ModelBase getModel()
    {
        return torchGunModel;
    }

    @Override
    public ItemCameraTransforms getCameraTransforms()
    {
        return cameraTransforms;
    }

    @Override
    public void handleBlockState(@Nullable IBlockState state, @Nullable EnumFacing side, long rand){}

    @Override
    public void handleItemState(ItemStack stack, World world, EntityLivingBase entity)
    {
        if(entity instanceof EntityPlayer)
        {
            lastPlayer = (EntityPlayer)entity;
        }
        heldStack = stack;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, Pair<? extends IBakedModel, Matrix4f> pair)
    {
        currentPerspective = cameraTransformType;
        return pair;
    }

    @Override
    public boolean useVanillaCameraTransform()
    {
        return true;
    }
}
