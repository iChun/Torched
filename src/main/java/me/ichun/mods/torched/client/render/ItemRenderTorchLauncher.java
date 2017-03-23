package me.ichun.mods.torched.client.render;

import me.ichun.mods.ichunutil.client.model.item.IModelBase;
import me.ichun.mods.torched.client.model.ModelTorchLauncher;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;

@SuppressWarnings("deprecation")
public class ItemRenderTorchLauncher implements IModelBase
{
    private ModelTorchLauncher launcherModel;
    private static final ResourceLocation texture = new ResourceLocation("torched","textures/model/torchlauncher.png");
    private static final ItemCameraTransforms cameraTransforms = new ItemCameraTransforms(
            new ItemTransformVec3f(new Vector3f(180.0F, 180.0F, 0.0F), new Vector3f(-0.025F, 0.155F, 0.225F), new Vector3f(-1.0F, -1.0F, 1.0F)),//tp left
            new ItemTransformVec3f(new Vector3f(180.0F, 180.0F, 0.0F), new Vector3f(-0.025F, 0.155F, 0.225F), new Vector3f(-1.0F, -1.0F, 1.0F)),//tp right
            new ItemTransformVec3f(new Vector3f(5F, 5F, -15F), new Vector3f(0.1F, 0.1F, 0.05F), new Vector3f(1.0F, 1.0F, 1.0F)),//fp left
            new ItemTransformVec3f(new Vector3f(5F, 5F, -15F), new Vector3f(0.1F, 0.1F, 0.05F), new Vector3f(1.0F, 1.0F, 1.0F)),//fp right
            new ItemTransformVec3f(new Vector3f(0F, 0F, 0.0F), new Vector3f(), new Vector3f(1.2F, 1.2F, 1.2F)),//head
            new ItemTransformVec3f(new Vector3f(35F, 45F, -15.0F), new Vector3f(0.0F, -0.025F, 0.F), new Vector3f(0.7F, 0.7F, 0.7F)),//gui
            new ItemTransformVec3f(new Vector3f(35F, 45F, -15.0F), new Vector3f(0.0F, 0.075F, 0.F), new Vector3f(0.3F, 0.3F, 0.3F)),//ground
            new ItemTransformVec3f(new Vector3f(35F, 45F, -15.0F), new Vector3f(0.0F, 0.075F, 0.F), new Vector3f(0.3F, 0.3F, 0.3F))//fixed
    );

    public ItemRenderTorchLauncher()
    {
        launcherModel = new ModelTorchLauncher();
    }

    @Override
    public ResourceLocation getTexture()
    {
        return texture;
    }

    @Override
    public void renderModel(float renderTick)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, 0.2F, 0.0F); //Handle item render offset

        launcherModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

        GlStateManager.popMatrix();
    }

    @Override
    public void postRender(){}

    @Override
    public ModelBase getModel()
    {
        return launcherModel;
    }

    @Override
    public ItemCameraTransforms getCameraTransforms()
    {
        return cameraTransforms;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, Pair<? extends IBakedModel, Matrix4f> pair)
    {
        return pair;
    }

    @Override
    public boolean useVanillaCameraTransform()
    {
        return true;
    }

    @Override
    public void handleBlockState(@Nullable IBlockState state, @Nullable EnumFacing side, long rand){}

    @Override
    public void handleItemState(ItemStack stack, World world, EntityLivingBase entity) {}
}
