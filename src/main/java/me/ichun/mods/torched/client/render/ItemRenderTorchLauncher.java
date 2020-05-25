package me.ichun.mods.torched.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.ichun.mods.ichunutil.client.model.item.IModel;
import me.ichun.mods.torched.client.model.ModelTorchLauncher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class ItemRenderTorchLauncher extends ItemStackTileEntityRenderer
        implements IModel
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("torched", "textures/model/torchlauncher.png");
    private static final ItemCameraTransforms ITEM_CAMERA_TRANSFORMS = new ItemCameraTransforms(
            new ItemTransformVec3f(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.025F, 0F, 0.215F), new Vector3f(1.0F, 1.0F, 1.0F)),//tp left
            new ItemTransformVec3f(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(-0.025F, 0F, 0.215F), new Vector3f(1.0F, 1.0F, 1.0F)),//tp right
            new ItemTransformVec3f(new Vector3f(5F, 5F, -15F), new Vector3f(0.1F, 0F, 0.05F), new Vector3f(1.0F, 1.0F, 1.0F)),//fp left
            new ItemTransformVec3f(new Vector3f(5F, 5F, -15F), new Vector3f(0.1F, 0F, 0.05F), new Vector3f(1.0F, 1.0F, 1.0F)),//fp right
            new ItemTransformVec3f(new Vector3f(0F, 0F, -62.0F), new Vector3f(-0.20F, 0.61F, 0F), new Vector3f(1.5F, 1.5F, 1.5F)),//head
            new ItemTransformVec3f(new Vector3f(35F, 45F, -15.0F), new Vector3f(0.0F, -0.15F, 0.F), new Vector3f(0.65F, 0.65F, 0.65F)),//gui
            new ItemTransformVec3f(new Vector3f(35F, 45F, -15.0F), new Vector3f(0.0F, 0.075F, 0.F), new Vector3f(0.3F, 0.3F, 0.3F)),//ground
            new ItemTransformVec3f(new Vector3f(0F, 90F, 0F), new Vector3f(0.0F, 0.0F, -0.05F), new Vector3f(0.65F, 0.65F, 0.65F))//fixed
    );
    public static final ItemRenderTorchLauncher INSTANCE = new ItemRenderTorchLauncher();

    private ModelTorchLauncher launcherModel;

    private ItemRenderTorchLauncher()
    {
        launcherModel = new ModelTorchLauncher();
    }

    @Override
    public void render(ItemStack is, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        setToOrigin(stack);
        launcherModel.render(stack, bufferIn.getBuffer(RenderType.getEntityCutout(TEXTURE)), combinedLightIn, combinedOverlayIn, 1F, 1F, 1F, 1F);
    }

    @Override
    public ItemCameraTransforms getCameraTransforms()
    {
        return ITEM_CAMERA_TRANSFORMS;
    }

    @Override
    public void handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat){}

    @Override
    public void handleItemState(ItemStack stack, World world, LivingEntity entity) {}
}
