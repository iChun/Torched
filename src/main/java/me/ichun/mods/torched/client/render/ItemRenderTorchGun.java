package me.ichun.mods.torched.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.ichun.mods.ichunutil.client.model.item.IModel;
import me.ichun.mods.ichunutil.client.render.RenderHelper;
import me.ichun.mods.torched.client.model.ModelTorchGun;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ItemTransformVec3f;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class ItemRenderTorchGun extends ItemStackTileEntityRenderer
    implements IModel
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("torched", "textures/model/torchgun.png");
    private static final ItemCameraTransforms ITEM_CAMERA_TRANSFORMS = new ItemCameraTransforms(
            new ItemTransformVec3f(new Vector3f(0F, -90F, 0F), new Vector3f(-0.025F, 0.0F, 0.075F), new Vector3f(0.5F, 0.5F, 0.5F)),//tp left
            new ItemTransformVec3f(new Vector3f(0F, 90F, 0F), new Vector3f(-0.025F, 0.0F, 0.075F), new Vector3f(0.5F, 0.5F, 0.5F)),//tp right
            new ItemTransformVec3f(new Vector3f(0F, -85F, 0F), new Vector3f(0.125F, -0.1F, -0.1F), new Vector3f(0.5F, 0.5F, 0.5F)),//fp left
            new ItemTransformVec3f(new Vector3f(0F, 85F, 0F), new Vector3f(0.125F, -0.1F, -0.1F), new Vector3f(0.5F, 0.5F, 0.5F)),//fp right
            new ItemTransformVec3f(new Vector3f(-5F, 90F, 0F), new Vector3f(0F, 0.538F, 0.2F), new Vector3f(0.5F, 0.5F, 0.5F)),//head
            new ItemTransformVec3f(new Vector3f(30F, 120F, 0F), new Vector3f(0.1F, -0.15F, 0F), new Vector3f(0.35F, 0.35F, 0.35F)),//gui
            new ItemTransformVec3f(new Vector3f(30F, 120F, 0F), new Vector3f(0F, 0.05F, 0F), new Vector3f(0.15F, 0.15F, 0.15F)),//ground
            new ItemTransformVec3f(new Vector3f(0F, 0F, 0F), new Vector3f(0F, 0.05F, 0F), new Vector3f(0.15F, 0.15F, 0.15F))//fixed
    );

    public static final ItemRenderTorchGun INSTANCE = new ItemRenderTorchGun();

    //Stuff to do in relation to getting the current perspective and the current player holding it
    private ItemCameraTransforms.TransformType currentPerspective;
    private PlayerEntity lastPlayer;

    //Params
    public ModelTorchGun torchGunModel;
    public Random rand;
    public ItemStack flintSteel;
    public ItemStack powder;
    public ItemStack torch;

    private ItemRenderTorchGun()
    {
        torchGunModel = new ModelTorchGun();
        rand = new Random();
        flintSteel = new ItemStack(Items.FLINT_AND_STEEL);
        powder = new ItemStack(Items.BLAZE_POWDER);
        torch = new ItemStack(Blocks.TORCH);
    }

    @Override
    public void render(ItemStack itemstack, MatrixStack stack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        setToOrigin(stack);

        Minecraft mc = Minecraft.getInstance();
        if(lastPlayer != null && lastPlayer == mc.player)
        {
            int count = 64;
            if(!lastPlayer.abilities.isCreativeMode)
            {
                count = 0;
                NonNullList<ItemStack> stacks = lastPlayer.inventory.mainInventory;
                for(ItemStack is : stacks)
                {
                    if(is.getItem() == Blocks.TORCH.asItem())
                    {
                        count += is.getCount();
                        if(count > 64)
                        {
                            count = 64;
                            break;
                        }
                    }
                }
            }

            rand.setSeed(lastPlayer.hashCode());

            for(int i = 0; i < count; i++)
            {
                stack.push();

                stack.translate(-0.9F, -0.05F, 0F);

                float scale = 0.35F;
                stack.scale(-scale, -scale, scale);
                double px = rand.nextGaussian() * 0.1F;
                double py = rand.nextGaussian() * 0.05F;
                double pz = rand.nextGaussian() * 0.275F;
                stack.translate(px, py, pz);
                BlockState state = Blocks.TORCH.getDefaultState();
                RenderHelper.renderBakedModel(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getModel(state), RenderTorch.TORCH_STACK, null, stack, bufferIn);
                stack.pop();
            }
        }

        if(itemstack.getDamage() < itemstack.getMaxDamage())
        {
            stack.push();

            stack.translate(-2.3F, -0.25F, 0F);
            stack.rotate(Vector3f.YP.rotationDegrees(180F));
            stack.rotate(Vector3f.ZP.rotationDegrees(45F));

            float scale = 0.6F;
            stack.scale(-scale, -scale, 1.25F);

            ItemStack is = Torched.eventHandlerClient.firing > 0 && lastPlayer == mc.player ? powder : flintSteel;
            IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(is);

            RenderHelper.renderBakedModel(model, is, null, stack, bufferIn);

            stack.pop();
        }


        torchGunModel.render(stack, bufferIn.getBuffer(RenderType.getEntityTranslucent(TEXTURE)), combinedLightIn, combinedOverlayIn, 1F, 1F, 1F, 1F);

        //reset these vars. they should be set per render.
        lastPlayer = null;
        currentPerspective = null;
    }

    @Override
    public ItemCameraTransforms getCameraTransforms()
    {
        return ITEM_CAMERA_TRANSFORMS;
    }

    @Override
    public void handleItemState(ItemStack stack, World world, LivingEntity entity)
    {
        if(entity instanceof AbstractClientPlayerEntity)
        {
            lastPlayer = (AbstractClientPlayerEntity)entity;
        }
    }

    @Override
    public void handlePerspective(ItemCameraTransforms.TransformType cameraTransformType, MatrixStack mat)
    {
        currentPerspective = cameraTransformType;
    }
}
