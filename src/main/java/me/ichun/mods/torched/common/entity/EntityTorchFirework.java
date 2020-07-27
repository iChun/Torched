package me.ichun.mods.torched.common.entity;

import me.ichun.mods.ichunutil.common.entity.util.EntityHelper;
import me.ichun.mods.ichunutil.common.item.DualHandedItem;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class EntityTorchFirework extends Entity
{
    private static final DataParameter<Integer> TORCHES = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GUNPOWDER = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_ACTIVE = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SPLIT_COUNT = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_ROCKET = EntityDataManager.createKey(EntityTorchFirework.class, DataSerializers.BOOLEAN);

    public LivingEntity initiator;
    public int age;
    public int gunpowderCount;
    public boolean activating;
    public boolean isSplit;
    public float fuel;

    public int xOrigin;
    public int zOrigin;

    public int prevTorches;

    public EntitySize currentSize;

    public EntityTorchFirework(EntityType<?> type, World par1World)
    {
        super(type, par1World);
        initiator = null;
        age = 0;
        gunpowderCount = 0;

        activating = false;
        fuel = 0.0F;

        prevTorches = 1;

        prevRotationPitch = rotationPitch = -90F;

        currentSize = type.getSize();
    }

    public EntityTorchFirework placed(int i, int j, int k, boolean isMax)
    {
        setLocationAndAngles(i + 0.5D, j + 1.001D, k + 0.5D, rand.nextInt(4) * 90F, 90F);
        if(isMax)
        {
            addTorches(511);
            addGP(511);
        }

        return this;
    }

    public EntityTorchFirework shot(LivingEntity living)
    {
        initiator = living;
        setActive(true);
        addTorches(7);
        addGP(-1);
        activating = true;
        gunpowderCount = 2;
        fuel = gunpowderCount * 80F;
        setRocket(true);

        setLocationAndAngles(living.getPosX(), living.getPosY() + (double)living.getEyeHeight() - 0.46D, living.getPosZ(), living.rotationYaw, living.rotationPitch);
        double pX = getPosX();
        double pZ = getPosZ();
        switch(DualHandedItem.getHandSide(living, DualHandedItem.getUsableDualHandedItem(living)))
        {
            case RIGHT:
            {
                pX -= (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                pZ -= (double)(MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                break;
            }
            case LEFT:
            {
                pX += (double)(MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                pZ += (double)(MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * 0.2F);
                break;
            }
        }

        currentSize = EntitySize.flexible(0.9F, 0.9F);
        recalculateSize();
        setPosition(pX, getPosY(), pZ);
        setThrowableHeading((-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI)), (-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI)), (MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI)), 2F, 5.0F);
        prevTorches = 8;

        return this;
    }

    public EntityTorchFirework fromExplosion(double i, double j, double k, float yaw, int torchCount, int gunPowder, EntityTorchFirework firework)
    {
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
        Vector3d fMo = firework.getMotion();
        setThrowableHeading(moX, moY, moZ, (float)Math.sqrt(fMo.x * fMo.x + fMo.y * fMo.y + fMo.z * fMo.z), 0.0F);
        setMotion(getMotion().x, fMo.y * 0.9D, getMotion().z);

        return this;
    }

    public void setThrowableHeading(double par1, double par3, double par5, float par7, float par8)
    {
        float var9 = MathHelper.sqrt(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)var9;
        par3 /= (double)var9;
        par5 /= (double)var9;
        par1 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par3 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par5 += this.rand.nextGaussian() * 0.007499999832361937D * (double)par8;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        setMotion(par1, par3, par5);
        float var10 = MathHelper.sqrt(par1 * par1 + par5 * par5);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, var10) * 180.0D / Math.PI);
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return currentSize;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
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
    protected void registerData()
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

    public boolean getRocket() //return true if RPT
    {
        return getDataManager().get(IS_ROCKET);
    }

    public void setRocket(boolean flag)
    {
        getDataManager().set(IS_ROCKET, flag);
    }

    @Override
    public void tick()
    {
        if(!isAlive())
        {
            return;
        }
        this.lastTickPosX = this.getPosX();
        this.lastTickPosY = this.getPosY();
        this.lastTickPosZ = this.getPosZ();
        super.tick();
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
                if(world.isRemote)
                {
                    spawnParticle();
                }

                if(!getRocket())
                {
                    Vector3d mo = getMotion();
                    setMotion(mo.x * 1.15D, mo.y + (isSplit ? -0.01D : 0.04D), mo.z * 1.15D);
                }

                Vector3d mo = getMotion();
                boolean forceBlow = Math.abs(mo.x) > 5D || Math.abs(mo.z) > 5D;
                if(collidedVertically && world.isRemote)
                {
                    setMotion(getMotion().x, 0.01D, getMotion().z);
                }
                if(getRocket())
                {
                    RayTraceResult result = EntityHelper.rayTrace(world, getPositionVec(), getPositionVec().add(getMotion()), this, true, RayTraceContext.BlockMode.COLLIDER, b -> true, RayTraceContext.FluidMode.NONE, e -> !(e instanceof EntityTorch || e instanceof EntityTorchFirework || age < 4 && e == initiator));

                    if(result.getType() != RayTraceResult.Type.MISS)
                    {
                        if(result.getType() == RayTraceResult.Type.ENTITY)
                        {
                            Entity ent = ((EntityRayTraceResult)result).getEntity();
                            DamageSource var21;
                            if (this.initiator == null)
                            {
                                var21 = (new IndirectEntityDamageSource("rptRand", this, this)).setProjectile();
                            }
                            else if(ent == initiator)
                            {
                                var21 = (new IndirectEntityDamageSource("rptSelf", this, initiator)).setProjectile();
                            }
                            else
                            {
                                var21 = (new IndirectEntityDamageSource("rpt", this, initiator)).setProjectile();
                            }
                            ent.attackEntityFrom(var21, 3);
                        }
                        else
                        {
                            Vector3d hitVec = result.getHitVec();
                            mo = getMotion();
                            setPosition(hitVec.x - (mo.x * 1.05D), hitVec.y - (mo.y * 1.05D), hitVec.z - (mo.z * 1.05D));
                        }
                        forceBlow = true;
                    }

                    mo = getMotion();
                    setPosition(getPosX() + mo.x, getPosY() + mo.y, getPosZ() + mo.z);
                }
                else
                {
                    this.move(MoverType.SELF, getMotion());
                }
                mo = getMotion();
                this.rotationYaw = (float)(Math.atan2(mo.x, mo.z) * 180.0D / Math.PI);

                while (this.rotationYaw - this.prevRotationYaw < -180.0F)
                {
                    this.prevRotationYaw -= 360.0F;
                }

                while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
                {
                    this.prevRotationYaw += 360.0F;
                }

                fuel -= getTorches();

                if(!world.isRemote && (fuel <= 0.0F || isSplit && mo.y < -0.1D || forceBlow) && isAlive())
                {
                    world.createExplosion(initiator, getPosX(), getPosY(), getPosZ(), getRocket() ? 2F : gunpowderCount < 5 ? 0.5F : 3.5F, Explosion.Mode.NONE);
                    if(getSplits() > 0)
                    {
                        int torch = (int)Math.floor((float)getTorches() / ((float)getSplits() + 1));
                        int gunPowder = (int)Math.ceil((float)gunpowderCount / ((float)getSplits() + 1));
                        float splits = getSplits() + 1;
                        for(int i = 0; i <= getSplits() && i < getTorches(); i++)
                        {
                            world.addEntity(new EntityTorchFirework(Torched.EntityTypes.TORCH_FIREWORK.get(), world).fromExplosion(getPosX(), getPosY() + (double)0.2F * ((float)this.getTorches() / 96F), getPosZ(), i * 360F / splits, torch, gunPowder, this));
                        }
                    }
                    else
                    {
                        for(int i = 0; i < getTorches(); i++)
                        {
                            rotationYaw = world.rand.nextFloat() * 360F;
                            rotationPitch = world.rand.nextFloat() * -85F - 5F;
                            world.addEntity(new EntityTorch(Torched.EntityTypes.TORCH.get(), world).setFirework(this, initiator));
                        }
                    }
                    remove();
                    return;
                }

                mo = getMotion();
                this.rotationYaw = (float)(Math.atan2(mo.x, mo.z) * 180.0D / Math.PI);

                for (this.rotationPitch = (float)(Math.atan2(mo.y, MathHelper.sqrt(mo.x * mo.x + mo.z * mo.z)) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
            }
            else if(!world.isRemote && this.ticksExisted % 2L == 0 && activating)
            {
                int minus = (int)Math.ceil((getGP() / 100F) + 1);
                addGP(-minus);
                gunpowderCount += minus;
                if(getGP() <= 0)
                {
                    setActive(true);
                    EntityHelper.playSound(this, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.NEUTRAL, 3.0F, 1.0F);
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
                EntityHelper.playSound(this, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.NEUTRAL, 3.0F, 1.0F);
            }
        }
        else if(!world.isRemote && this.ticksExisted % 2L == 0)
        {
            List<Entity> list = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(0.0D, 0.2D, 0.0D), e -> e instanceof ItemEntity);
            for(int i = 0; i < list.size(); i++)
            {
                Entity ent = list.get(i);
                ItemEntity item = (ItemEntity)ent;
                item.getItem();
                if(item.getItem().getItem() == Blocks.TORCH.asItem())
                {
                    if(getTorches() + item.getItem().getCount() <= 512)
                    {
                        addTorches(item.getItem().getCount());
                        item.remove();
                    }
                    else if(getTorches() < 512)
                    {
                        addTorches((getTorches() + item.getItem().getCount()) - 512);
                        item.getItem().shrink(((getTorches() + item.getItem().getCount()) - 512));
                    }
                }
                else if(item.getItem().getItem() == Items.GUNPOWDER)
                {
                    if(getGP() + item.getItem().getCount() <= 512)
                    {
                        addGP(item.getItem().getCount());
                        item.remove();
                    }
                    else if(getGP() < 512)
                    {
                        addGP((getGP() + item.getItem().getCount()) - 512);
                        item.getItem().shrink((getGP() + item.getItem().getCount()) - 512);;
                    }
                }
            }
            BlockPos thisPos = new BlockPos(getPositionVec());
            if(world.getBlockState(thisPos).getBlock() == Blocks.FIRE)
            {
                setFire(8);
            }
            else if(world.isBlockPowered(thisPos) || world.isBlockPowered(new BlockPos(thisPos.add(0, -1, 0))))
            {
                extinguish();
                activating = true;
                gunpowderCount = getGP();
                fuel = gunpowderCount * 140F;
                addGP(-getGP());
                setActive(true);
                EntityHelper.playSound(this, SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.NEUTRAL, 3.0F, 1.0F);
            }
            else if(!world.getBlockState(thisPos.add(0, -1, 0)).isSolidSide(world, thisPos.add(0, -1, 0), Direction.UP))
            {
                this.attackEntityFrom(DamageSource.GENERIC, 1);
            }

        }
        else
        {
            prevRotationPitch = rotationPitch = 90F;
        }
        if(prevTorches != getTorches())
        {
            prevTorches = getTorches();
            currentSize = EntitySize.flexible(1.0F, 1.0F + ((float) getTorches() / 192F));
            recalculateSize();
            setPosition(getPosX(), getPosY(), getPosZ());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticle()
    {
        Vector3d mo = getMotion();
        double particleSpeed = 0.75D;
        float scale = 1.0F + (this.getRocket() ? 1.0F : this.getTorches() / 96F);

        Particle particle = Minecraft.getInstance().worldRenderer.addParticleUnchecked(Torched.Particles.FLAME.get(), true, this.getPosX() - mo.x * 0.3D, this.getPosY() - mo.y * 0.3D + 0.4D, this.getPosZ() - mo.z * 0.3D, mo.x * particleSpeed, mo.y * particleSpeed, mo.z * particleSpeed);
        if(particle != null)
        {
            particle.multiplyParticleScaleBy(scale);
        }
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand)
    {
        if(!world.isRemote && !activating)
        {
            ItemStack is = player.getHeldItem(hand);
            if(is.getItem() == Blocks.TORCH.asItem() && getTorches() < 512)
            {
                addTorches(1);
                if(!player.abilities.isCreativeMode)
                {
                    is.shrink(1);
                    if(is.isEmpty())
                    {
                        player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
                    }
                }
                player.swingArm(hand);
                return ActionResultType.CONSUME;
            }
            else if(is.getItem() == Items.GUNPOWDER && getGP() < 512)
            {
                addGP(1);
                if(!player.abilities.isCreativeMode)
                {
                    is.shrink(1);
                    if(is.isEmpty())
                    {
                        player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
                    }
                }
                player.swingArm(hand);
                return ActionResultType.CONSUME;
            }
            else if(is.getItem() == Items.FLINT_AND_STEEL)
            {
                activating = true;
                if(!player.abilities.isCreativeMode)
                {
                    is.setDamage(is.getDamage() + 1);
                }
                initiator = player;
                player.swingArm(hand);
                return ActionResultType.CONSUME;
            }
            else if(is.getItem() == Items.GOLD_NUGGET && getSplits() < 16)
            {
                addSplit();
                if(!player.abilities.isCreativeMode)
                {
                    is.shrink(1);
                    if(is.isEmpty())
                    {
                        player.inventory.mainInventory.set(player.inventory.currentItem, ItemStack.EMPTY);
                    }
                }
                player.swingArm(hand);
                return ActionResultType.CONSUME;
            }
        }
        if(world.isRemote && (player.getHeldItem(hand).getItem() == Blocks.TORCH.asItem() || player.getHeldItem(hand).getItem() == Items.GUNPOWDER || player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL || player.getHeldItem(hand).getItem() == Items.GOLD_NUGGET))
        {
            player.swingArm(hand);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float i)
    {
        if(source.isFireDamage())
        {
            setFire(8);
            return true;
        }
        if(i > 0 && !getActive())
        {
            if(!world.isRemote)
            {
                while(getTorches() > 0)
                {
                    int count = Math.min(getTorches(), 64);
                    entityDropItem(new ItemStack(Blocks.TORCH, count), 0.5F);
                    addTorches(-count);
                }
                while(getGP() > 0)
                {
                    int count = Math.min(getGP(), 64);
                    entityDropItem(new ItemStack(Items.GUNPOWDER, count), 0.5F);
                    addGP(-count);
                }
                for(int ij = 0; ij < getSplits(); ij++)
                {
                    entityDropItem(new ItemStack(Items.GOLD_NUGGET, 1), 0.5F);
                }
                remove();
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target)
    {
        return new ItemStack(Torched.Items.TORCH_FIREWORK.get());
    }

    @Override
    protected void readAdditional(CompoundNBT tag)
    {
        age = tag.getInt("age");
        addTorches(tag.getInt("torchCount") - 1);
        addGP(tag.getInt("gpCount") - 1);
        setActive(tag.getBoolean("active"));
        activating = tag.getBoolean("activating");
        gunpowderCount = tag.getInt("gunpowderCount");
        fuel = tag.getFloat("fuel");
        setSplits(tag.getInt("splits"));

        isSplit = tag.getBoolean("isSplit");
        xOrigin = tag.getInt("xOrigin");
        zOrigin = tag.getInt("zOrigin");
    }

    @Override
    protected void writeAdditional(CompoundNBT tag)
    {
        tag.putInt("age", age);
        tag.putInt("torchCount", getTorches());
        tag.putInt("gpCount", getGP());
        tag.putBoolean("active", getActive());
        tag.putBoolean("activating", activating);
        tag.putInt("gunpowderCount", gunpowderCount);
        tag.putFloat("fuel", fuel);
        tag.putInt("splits", getSplits());
        tag.putBoolean("isSplit", isSplit);
        tag.putInt("xOrigin", xOrigin);
        tag.putInt("zOrigin", zOrigin);
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
