package me.ichun.mods.torched.common.entity;

import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityTorch extends Entity
{
    private static final DataParameter<Integer> SHOOTER_ID = EntityDataManager.createKey(EntityTorch.class, DataSerializers.VARINT);

    public EntityLivingBase shooter;
    public int damage;
    public int age;
    public int ceilingBounce;
    public boolean fromFirework;
    public boolean firstUpdate;

    public EntityTorch(World world)
    {
        super(world);
        shooter = null;
        setSize(0.5F, 0.5F);
        damage = 2;
        ceilingBounce = 0;
        firstUpdate = true;
        fromFirework = false;
    }

    public EntityTorch(World world, EntityLivingBase living)
    {
        this(world);
        getDataManager().set(SHOOTER_ID, living.getEntityId());
        shooter = living;

        setLocationAndAngles(living.posX, living.posY + (double)living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);
        posY -= 0.20D;

        switch(ItemHandler.getHandSide(living, ItemHandler.getUsableDualHandedItem(living)))
        {
            case RIGHT:
            {
                posX -= (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                posZ -= (double)(MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                break;
            }
            case LEFT:
            {
                posX += (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                posZ += (double)(MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                break;
            }
        }

        setPosition(posX, posY, posZ);
        motionX = (double)(-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionZ = (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionY = (double)(-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
        setThrowableHeading(motionX, motionY, motionZ, 2F, 1.0F);
    }

    public EntityTorch(World world, EntityTorchFirework firework, EntityLivingBase living)
    {
        this(world);
        if(living != null)
        {
            //			dataWatcher.updateObject(20, living.entityId);
        }
        shooter = living;

        setLocationAndAngles(firework.posX, firework.posY + (double)0.5F * ((float)firework.getTorches() / 96F), firework.posZ, firework.rotationYaw, firework.rotationPitch);
        setPosition(posX, posY, posZ);
        motionX = (double)(-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionZ = (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionY = (double)(-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
        setThrowableHeading(motionX, motionY, motionZ, ((float)firework.gunpowderCount / ((float)firework.getTorches() / 2.75F)), 1.0F);
        if(firework.getRocket())
        {
            motionY *= 1.15D;
        }
        fromFirework = true;
    }

    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float var9 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        this.motionX = par1;
        this.motionY = par3;
        this.motionZ = par5;
        float var10 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
    }

    @Override
    public void onUpdate()
    {
        if(firstUpdate)
        {
            firstUpdate = false;
            if(worldObj.isRemote && shooter == null && getDataManager().get(SHOOTER_ID) != -1)
            {
                Entity ent = worldObj.getEntityByID(getDataManager().get(SHOOTER_ID));
                if(ent instanceof EntityLivingBase)
                {
                    shooter = (EntityLivingBase)ent;
                    prevRotationYaw = rotationYaw = -shooter.rotationYawHead;
                    prevRotationPitch = rotationPitch = -shooter.rotationPitch;
                    if(ent instanceof EntityPlayer)
                    {
                        Torched.proxy.nudgeHand((EntityPlayer)ent);
                    }
                }
            }
        }

        if(isDead)
        {
            return;
        }
        super.onUpdate();

        age++;

        if(age > 1200)
        {
            setDead();
            return;
        }

        Torched.proxy.spawnTorchFlame(this);

        if(ceilingBounce > 0)
        {
            ceilingBounce--;
        }

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var1) * 180.0D / Math.PI);
        }

        Vec3d var17 = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult mop = this.worldObj.rayTraceBlocks(var17, var3, false, true, false);
        var17 = new Vec3d(this.posX, this.posY, this.posZ);
        var3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (mop != null)
        {
            var3 = new Vec3d(mop.hitVec.xCoord, mop.hitVec.yCoord, mop.hitVec.zCoord);
        }

        Entity collidedEnt = null;
        List var6 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double var7 = 0.0D;
        int var9;
        float var11;

        for (var9 = 0; var9 < var6.size(); ++var9)
        {
            Entity var10 = (Entity)var6.get(var9);

            if(var10 instanceof EntityTorch || var10 instanceof EntityTorchFirework)
            {
                continue;
            }

            if (var10.canBeCollidedWith() && (var10 != this.shooter || age >= 6))
            {
                var11 = 0.3F;
                AxisAlignedBB var12 = var10.getEntityBoundingBox().expand((double)var11, (double)var11, (double)var11);
                RayTraceResult var13 = var12.calculateIntercept(var17, var3);

                if (var13 != null)
                {
                    double var14 = var17.distanceTo(var13.hitVec);

                    if (var14 < var7 || var7 == 0.0D)
                    {
                        collidedEnt = var10;
                        var7 = var14;
                    }
                }
            }
        }

        float var20;
        float var26;

        if (collidedEnt != null)
        {
            var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int var23 = MathHelper.ceiling_double_int((double)var20 * this.damage);

            DamageSource var21 = null;

            if (this.shooter == null)
            {
                var21 = (new EntityDamageSourceIndirect("torchedRand", this, this)).setProjectile();
            }
            else if(shooter == collidedEnt)
            {
                if(fromFirework)
                {
                    var21 = (new EntityDamageSourceIndirect("torchedFireSelf", this, this.shooter)).setProjectile();
                }
                else
                {
                    var21 = (new EntityDamageSourceIndirect("torchedSelf", this, this.shooter)).setProjectile();
                }
            }
            else
            {
                if(fromFirework)
                {
                    var21 = (new EntityDamageSourceIndirect("torchedFire", this, this.shooter)).setProjectile();
                }
                else
                {
                    var21 = (new EntityDamageSourceIndirect("torched", this, this.shooter)).setProjectile();
                }
            }

            if (this.isBurning() && !(collidedEnt instanceof EntityEnderman))
            {
                collidedEnt.setFire(5);
            }

            if (collidedEnt.attackEntityFrom(var21, var23))
            {
                if (collidedEnt instanceof EntityLivingBase)
                {
                    EntityLivingBase var24 = (EntityLivingBase)collidedEnt;

                    if (this.shooter != null)
                    {
                        Enchantments.THORNS.onUserHurt(var24, this.shooter, EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.THORNS, var24));
                    }

                    if (this.shooter != null && collidedEnt != this.shooter && collidedEnt instanceof EntityPlayer && this.shooter instanceof EntityPlayerMP)
                    {
                        ((EntityPlayerMP)this.shooter).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }
                }
            }
            else
            {
                this.motionX *= -0.10000000149011612D;
                this.motionY *= -0.10000000149011612D;
                this.motionZ *= -0.10000000149011612D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
            }
        }

        if (mop != null)
        {
            IBlockState state = this.worldObj.getBlockState(mop.getBlockPos());
            Block bId = state.getBlock();
            this.motionX = (double)((float)(mop.hitVec.xCoord - this.posX));
            this.motionY = (double)((float)(mop.hitVec.yCoord - this.posY));
            this.motionZ = (double)((float)(mop.hitVec.zCoord - this.posZ));
            var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            this.posX -= this.motionX / (double)var20 * 0.05000000074505806D;
            this.posY -= this.motionY / (double)var20 * 0.05000000074505806D;
            this.posZ -= this.motionZ / (double)var20 * 0.05000000074505806D;

            if (!bId.isAir(state, this.worldObj, mop.getBlockPos()))
            {
                bId.onEntityCollidedWithBlock(this.worldObj, mop.getBlockPos(), state, this);
            }

            if(!worldObj.isRemote && mop.sideHit == EnumFacing.DOWN)
            {
                if(ceilingBounce > 0)
                {
                    dropItem(Item.getItemFromBlock(Blocks.TORCH), 1);
                    setDead();
                    return;
                }
                ceilingBounce = 5;
                motionY *= -0.8D;
            }

            if(mop.sideHit != EnumFacing.DOWN && bId != Blocks.VINE && !(bId instanceof BlockBush)
                    && (bId.isAir(state, this.worldObj, mop.getBlockPos()) || !bId.isReplaceable(worldObj, mop.getBlockPos()) || bId == Blocks.SNOW_LAYER))
            {
                BlockPos newPos = mop.getBlockPos();
                if (mop.sideHit == EnumFacing.UP && bId != Blocks.SNOW_LAYER || mop.sideHit != EnumFacing.UP)
                {
                    newPos = newPos.offset(mop.sideHit, 1);
                }

                if(!worldObj.isRemote)
                {
                    if(worldObj.canBlockBePlaced(Blocks.TORCH, newPos, false, mop.sideHit, this, null))
                    {
                        worldObj.setBlockState(newPos, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, mop.sideHit));
                        worldObj.playSound(null, (double)((float)newPos.getX() + 0.5F), (double)((float)newPos.getY() + 0.5F), (double)((float)newPos.getZ() + 0.5F), Blocks.TORCH.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (Blocks.TORCH.getSoundType().getVolume() + 1.0F) / 2.0F, Blocks.TORCH.getSoundType().getPitch() * 0.8F);
                    }
                    else
                    {
                        dropItem(Item.getItemFromBlock(Blocks.TORCH), 1);
                    }
                }
                setDead();
                return;
            }
            else
            {
                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)var20) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float var22 = 0.99F;
        var11 = 0.04F;

        if (this.isInWater())
        {
            for (int var25 = 0; var25 < 4; ++var25)
            {
                var26 = 0.25F;
                this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)var26, this.posY - this.motionY * (double)var26, this.posZ - this.motionZ * (double)var26, this.motionX, this.motionY, this.motionZ);
            }

            var22 = 0.8F;
        }

        this.motionX *= (double)var22;
        this.motionY *= (double)var22;
        this.motionZ *= (double)var22;
        this.motionY -= (double)var11;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.doBlockCollisions();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 10D;

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    @Override
    public void entityInit()
    {
        getDataManager().register(SHOOTER_ID, -1);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag)
    {
        damage = tag.getInteger("damage");
        age = tag.getInteger("age");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag)
    {
        tag.setInteger("damage", damage);
        tag.setInteger("age", age);
    }

}
