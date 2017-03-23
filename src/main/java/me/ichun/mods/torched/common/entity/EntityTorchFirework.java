package me.ichun.mods.torched.common.entity;

import me.ichun.mods.ichunutil.common.core.util.EntityHelper;
import me.ichun.mods.ichunutil.common.item.ItemHandler;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class EntityTorchFirework extends Entity
{
    private static final DataParameter<Integer> TORCHES = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GUNPOWDER = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_ACTIVE = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SPLIT_COUNT = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_ROCKET = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.BOOLEAN);

    public EntityLivingBase initiator;
    public int age;
    public int gunpowderCount;
    public boolean activating;
    public boolean isSplit;
    public float fuel;

    public int xOrigin;
    public int zOrigin;

    public int prevTorches;

    public EntityTorchFirework(World par1World)
    {
        super(par1World);
        initiator = null;
        age = 0;
        gunpowderCount = 0;

        activating = false;
        setSize(1.0F, 1.0F);
        fuel = 0.0F;

        prevTorches = 1;

        prevRotationPitch = rotationPitch = -90F;
    }

    public EntityTorchFirework(World world, int i, int j, int k, boolean isMax)
    {
        this(world);
        setLocationAndAngles(i + 0.5D, j + 1.001D, k + 0.5D, 0.0F, -90F);
        rotationYaw = rand.nextInt(4) * 90F;
        if(isMax)
        {
            addTorches(511);
            addGP(511);
        }
    }

    public EntityTorchFirework(World world, EntityLivingBase living)
    {
        this(world);
        initiator = living;
        setActive(true);
        addTorches(7);
        addGP(-1);
        activating = true;
        gunpowderCount = 2;
        fuel = gunpowderCount * 80F;
        setRocket(true);

        setLocationAndAngles(living.posX, living.posY + (double)living.getEyeHeight(), living.posZ, living.rotationYaw, living.rotationPitch);
        posY -= 0.46000000149011612D;
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

        setSize(0.9F, 0.9F);
        setPosition(posX, posY, posZ);
        motionX = (double)(-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionZ = (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        motionY = (double)(-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
        setThrowableHeading(motionX, motionY, motionZ, 2F, 5.0F);
        prevTorches = 8;
    }

    public EntityTorchFirework(World world, double i, double j, double k, float yaw, int torchCount, int gunPowder, EntityTorchFirework firework)
    {
        this(world);
        setLocationAndAngles(i, j, k, yaw, -88F);
        setActive(true);
        addGP(-1);

        initiator = firework.initiator;

        activating = true;

        addTorches(torchCount - 1);

        gunpowderCount = gunPowder + 1;

        fuel = gunpowderCount * 40F * (float)Math.pow(((float)firework.gunpowderCount / ((float)firework.getTorches() / 3.75F)), 2);

        double moX = (double)(-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        double moZ = (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI));
        double moY = (double)(-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI));
        setThrowableHeading(moX, moY, moZ, (float)Math.sqrt(firework.motionX * firework.motionX + firework.motionY * firework.motionY + firework.motionZ * firework.motionZ), 0.0F);
        motionY = firework.motionY * 0.90D;

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
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double par1)
    {
        return par1 < 4096.0D;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    protected void entityInit()
    {
        getDataManager().register(TORCHES, 1);
        getDataManager().register(GUNPOWDER, 1);
        getDataManager().register(IS_ACTIVE, false);
        getDataManager().register(SPLIT_COUNT, 0);
        getDataManager().register(IS_ROCKET, false);
    }

    public int getTorches()
    {
        return getDataManager().get(TORCHES);
    }

    public void addTorches(int i)
    {
        getDataManager().set(TORCHES, getTorches() + i);
    }

    public int getGP()
    {
        return getDataManager().get(GUNPOWDER);
    }

    public void addGP(int i)
    {
        getDataManager().set(GUNPOWDER, getGP() + i);
    }

    public boolean getActive()
    {
        return getDataManager().get(IS_ACTIVE);
    }

    public void setActive(boolean flag)
    {
        getDataManager().set(IS_ACTIVE, flag);
    }

    public int getSplits()
    {
        return getDataManager().get(SPLIT_COUNT);
    }

    public void addSplit()
    {
        getDataManager().set(SPLIT_COUNT, getSplits() + 1);
    }

    public void setSplits(int i)
    {
        getDataManager().set(SPLIT_COUNT, i);
    }

    public boolean getRocket()
    {
        return getDataManager().get(IS_ROCKET);
    }

    public void setRocket(boolean flag)
    {
        getDataManager().set(IS_ROCKET, flag);
    }

    @Override
    public void onUpdate()
    {
        if(isDead)
        {
            return;
        }
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        if(age == 0)
        {
            prevRotationYaw = rotationYaw;
            prevRotationPitch = rotationPitch;
        }
        age++;
        if(activating || getActive() || isBurning())
        {
            if(getGP() <= 0)
            {
                posY += 0.4F;
                Torched.proxy.spawnTorchFlame(this);
                posY -= 0.4F;

                if(!getRocket())
                {
                    this.motionX *= 1.15D;
                    this.motionZ *= 1.15D;
                    this.motionY += isSplit ? -0.01D : 0.04D;
                }

                boolean forceBlow = false;

                if(Math.sqrt(motionX * motionX) > 5D)
                {
                    forceBlow = true;
                }
                if(Math.sqrt(motionZ * motionZ) > 5D)
                {
                    forceBlow = true;
                }

                if(isCollidedVertically && worldObj.isRemote)
                {
                    motionY = 0.01D;
                }
                if(getRocket())
                {
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

                        if (var10.canBeCollidedWith() && (var10 != initiator || age >= 3))
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

                    if(mop != null || collidedEnt != null)
                    {
                        if(collidedEnt != null)
                        {
                            DamageSource var21;
                            if (this.initiator == null)
                            {
                                var21 = (new EntityDamageSourceIndirect("rptRand", this, this)).setProjectile();
                            }
                            else if(collidedEnt == initiator)
                            {
                                var21 = (new EntityDamageSourceIndirect("rptSelf", this, initiator)).setProjectile();
                            }
                            else
                            {
                                var21 = (new EntityDamageSourceIndirect("rpt", this, initiator)).setProjectile();
                            }
                            collidedEnt.attackEntityFrom(var21, 3);
                        }
                        if(mop != null)
                        {
                            posX = mop.hitVec.xCoord - (motionX * 1.05D);
                            posY = mop.hitVec.yCoord - (motionY * 1.05D);
                            posZ = mop.hitVec.zCoord - (motionZ * 1.05D);
                        }
                        forceBlow = true;
                    }

                    this.posX += this.motionX;
                    this.posY += this.motionY;
                    this.posZ += this.motionZ;

                    setPosition(posX, posY, posZ);
                }
                else
                {
                    this.moveEntity(this.motionX, this.motionY, this.motionZ);
                }
                float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

                while (this.rotationYaw - this.prevRotationYaw < -180.0F)
                {
                    this.prevRotationYaw -= 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
                {
                    this.prevRotationYaw += 360.0F;
                }

                fuel -= getTorches();

                if(!worldObj.isRemote && (fuel <= 0.0F || isSplit && motionY < -0.1D || forceBlow) && !isDead)
                {
                    worldObj.createExplosion(initiator, posX, posY, posZ, getRocket() ? 2F : gunpowderCount < 5 ? 0.5F : 3.5F, false);
                    if(getSplits() > 0)
                    {
                        int torch = (int)Math.floor((float)getTorches() / ((float)getSplits() + 1));
                        int gunPowder = (int)Math.ceil((float)gunpowderCount / ((float)getSplits() + 1));
                        float splits = getSplits() + 1;
                        for(int i = 0; i <= getSplits() && i < getTorches(); i++)
                        {
                            worldObj.spawnEntityInWorld(new EntityTorchFirework(worldObj, posX, posY + (double)0.2F * ((float)this.getTorches() / 96F), posZ, i * 360F / splits, torch, gunPowder, this));
                        }
                    }
                    else
                    {
                        for(int i = 0; i < getTorches(); i++)
                        {
                            rotationYaw = worldObj.rand.nextFloat() * 360F;
                            rotationPitch = worldObj.rand.nextFloat() * -85F - 5F;
                            worldObj.spawnEntityInWorld(new EntityTorch(worldObj, this, initiator));
                        }
                    }
                    setDead();
                    return;
                }

                float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
                this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

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
            }
            else if(!worldObj.isRemote && worldObj.getWorldTime() % 2L == 0 && activating)
            {
                int minus = (int)Math.ceil((getGP() / 100) + 1);
                addGP(-minus);
                gunpowderCount += minus;
                if(getGP() <= 0)
                {
                    setActive(true);
                    EntityHelper.playSoundAtEntity(this, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.NEUTRAL, 3.0F, 1.0F);
                    fuel = gunpowderCount * 140F;
                } //256 torches and 96 gunpowder
            }
            if(isBurning())
            {
                extinguish();
                activating = true;
                gunpowderCount = getGP();
                fuel = gunpowderCount * 140F;
                addGP(-getGP());
                setActive(true);
                EntityHelper.playSoundAtEntity(this, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.NEUTRAL, 3.0F, 1.0F);
            }
        }
        else if(!worldObj.isRemote && worldObj.getWorldTime() % 2L == 0)
        {
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.0D, 0.2D, 0.0D));
            for(int i = 0; i < list.size(); i++)
            {
                Entity ent = (Entity)list.get(i);
                if(!(ent instanceof EntityItem))
                {
                    continue;
                }

                EntityItem item = (EntityItem)ent;
                if(item.getEntityItem() != null)
                {
                    if(item.getEntityItem().getItem() == Item.getItemFromBlock(Blocks.TORCH))
                    {
                        if(getTorches() + item.getEntityItem().stackSize <= 512)
                        {
                            addTorches(item.getEntityItem().stackSize);
                            item.setDead();
                        }
                        else if(getTorches() < 512)
                        {
                            addTorches((getTorches() + item.getEntityItem().stackSize) - 512);
                            item.getEntityItem().stackSize -= ((getTorches() + item.getEntityItem().stackSize) - 512);
                        }
                    }
                    else if(item.getEntityItem().getItem() == Items.GUNPOWDER)
                    {
                        if(getGP() + item.getEntityItem().stackSize <= 512)
                        {
                            addGP(item.getEntityItem().stackSize);
                            item.setDead();
                        }
                        else if(getGP() < 512)
                        {
                            addGP((getGP() + item.getEntityItem().stackSize) - 512);
                            item.getEntityItem().stackSize = (getGP() + item.getEntityItem().stackSize) - 512;
                        }
                    }
                }
            }
            if(worldObj.getBlockState(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ))).getBlock() == Blocks.FIRE)
            {
                setFire(8);
            }
            else if(worldObj.isBlockPowered(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ))) || worldObj.isBlockPowered(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY) - 1, (int)Math.floor(posZ))))
            {
                extinguish();
                activating = true;
                gunpowderCount = getGP();
                fuel = gunpowderCount * 140F;
                addGP(-getGP());
                setActive(true);
                EntityHelper.playSoundAtEntity(this, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.NEUTRAL, 3.0F, 1.0F);
            }
            else if(!worldObj.isSideSolid(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY - 1), (int)Math.floor(posZ)), EnumFacing.getFront(1), true))
            {
                this.attackEntityFrom(DamageSource.generic, 1);
            }

        }
        else
        {
            prevRotationPitch = rotationPitch = -90F;
        }
        if(prevTorches != getTorches())
        {
            prevTorches = getTorches();
            setSize(1.0F, 1.0F + ((float) getTorches() / 192F));
            setPosition(posX, posY, posZ);
        }
        //		setDead();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand)
    {
        if(!worldObj.isRemote && !activating)
        {
            ItemStack is = player.getHeldItem(hand);
            if(is != null)
            {
                if(is.getItem() == Item.getItemFromBlock(Blocks.TORCH) && getTorches() < 512)
                {
                    addTorches(1);
                    if(!player.capabilities.isCreativeMode)
                    {
                        is.stackSize--;
                        if(is.stackSize <= 0)
                        {
                            player.inventory.mainInventory[player.inventory.currentItem] = null;
                        }
                    }
                    player.swingArm(hand);
                    return true;
                }
                else if(is.getItem() == Items.GUNPOWDER && getGP() < 512)
                {
                    addGP(1);
                    if(!player.capabilities.isCreativeMode)
                    {
                        is.stackSize--;
                        if(is.stackSize <= 0)
                        {
                            player.inventory.mainInventory[player.inventory.currentItem] = null;
                        }
                    }
                    player.swingArm(hand);
                    return true;
                }
                else if(is.getItem() == Items.FLINT_AND_STEEL)
                {
                    activating = true;
                    if(!player.capabilities.isCreativeMode)
                    {
                        is.setItemDamage(is.getItemDamage() + 1);
                    }
                    initiator = player;
                    player.swingArm(hand);
                    return true;
                }
                else if(is.getItem() == Items.GOLD_NUGGET && getSplits() < 16)
                {
                    addSplit();
                    if(!player.capabilities.isCreativeMode)
                    {
                        is.stackSize--;
                        if(is.stackSize <= 0)
                        {
                            player.inventory.mainInventory[player.inventory.currentItem] = null;
                        }
                    }
                    player.swingArm(hand);
                    return true;
                }
            }
        }
        if(worldObj.isRemote && player.getHeldItem(hand) != null && (player.getHeldItem(hand).getItem() == Item.getItemFromBlock(Blocks.TORCH) || player.getHeldItem(hand).getItem() == Items.GUNPOWDER || player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL || player.getHeldItem(hand).getItem() == Items.GOLD_NUGGET))
        {
            player.swingArm(hand);
            return true;
        }
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float i)
    {
        if(source == DamageSource.inFire || source == DamageSource.onFire)
        {
            setFire(8);
            return true;
        }
        if(i > 0 && !getActive())
        {
            if(!worldObj.isRemote)
            {
                while(getTorches() > 0)
                {
                    int count = getTorches() > 64 ? 64 : getTorches();
                    entityDropItem(new ItemStack(Blocks.TORCH, count), 0.5F);
                    addTorches(-count);
                }
                while(getGP() > 0)
                {
                    int count = getGP() > 64 ? 64 : getGP();
                    entityDropItem(new ItemStack(Items.GUNPOWDER, count), 0.5F);
                    addGP(-count);
                }
                for(int ij = 0; ij < getSplits(); ij++)
                {
                    entityDropItem(new ItemStack(Items.GOLD_NUGGET, 1), 0.5F);
                }
                setDead();
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(Torched.itemTorchFirework, 1);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag)
    {
        age = tag.getInteger("age");
        addTorches(tag.getInteger("torchCount") - 1);
        addGP(tag.getInteger("gpCount") - 1);
        setActive(tag.getBoolean("active"));
        activating = tag.getBoolean("activating");
        gunpowderCount = tag.getInteger("gunpowderCount");
        fuel = tag.getFloat("fuel");
        setSplits(tag.getInteger("splits"));

        isSplit = tag.getBoolean("isSplit");
        xOrigin = tag.getInteger("xOrigin");
        zOrigin = tag.getInteger("zOrigin");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag)
    {
        tag.setInteger("age", age);
        tag.setInteger("torchCount", getTorches());
        tag.setInteger("gpCount", getGP());
        tag.setBoolean("active", getActive());
        tag.setBoolean("activating", activating);
        tag.setInteger("gunpowderCount", gunpowderCount);
        tag.setFloat("fuel", fuel);
        tag.setInteger("splits", getSplits());
        tag.setBoolean("isSplit", isSplit);
        tag.setInteger("xOrigin", xOrigin);
        tag.setInteger("zOrigin", zOrigin);
    }
}
