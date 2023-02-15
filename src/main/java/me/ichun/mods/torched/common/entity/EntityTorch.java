package me.ichun.mods.torched.common.entity;

import me.ichun.mods.ichunutil.common.entity.util.EntityHelper;
import me.ichun.mods.ichunutil.common.item.DualHandedItem;
import me.ichun.mods.torched.common.Torched;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityTorch extends Entity
{
    private static final DataParameter<Integer> SHOOTER_ID = EntityDataManager.createKey(EntityTorch.class, DataSerializers.VARINT);

    public LivingEntity shooter;
    public int damage;
    public int age;
    public int ceilingBounce;
    public boolean fromFirework;
    public boolean firstUpdate;

    public EntityTorch(EntityType<?> type, World world)
    {
        super(type, world);
        shooter = null;
        damage = 2;
        ceilingBounce = 0;
        firstUpdate = true;
        fromFirework = false;
    }

    public EntityTorch setShooter(LivingEntity living)
    {
        getDataManager().set(SHOOTER_ID, living.getEntityId());
        shooter = living;

        setLocationAndAngles(living.getPosX(), living.getPosY() + (double)living.getEyeHeight() - 0.20D, living.getPosZ(), living.rotationYaw, living.rotationPitch);

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

        setPosition(pX, getPosY(), pZ);
        setThrowableHeading((-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI)), (-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI)), (MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI)), 2F, 1.0F);

        return this;
    }

    public EntityTorch setFirework(EntityTorchFirework firework, LivingEntity living)
    {
        shooter = living;

        setLocationAndAngles(firework.getPosX(), firework.getPosY() + (double)0.5F * ((float)firework.getTorches() / 96F), firework.getPosZ(), firework.rotationYaw, firework.rotationPitch);
        setThrowableHeading((-MathHelper.sin(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI)), (-MathHelper.sin(rotationPitch / 180.0F * (float)Math.PI)), (MathHelper.cos(rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(rotationPitch / 180.0F * (float)Math.PI)), ((float)firework.gunpowderCount / ((float)firework.getTorches() / 2.75F)), 1.0F);
        if(firework.getRocket())
        {
            setMotion(getMotion().mul(1D, 1.15D, 1D));
        }
        fromFirework = true;

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
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)var10) * 180.0D / Math.PI);
    }

    @Override
    public void tick()
    {
        if(firstUpdate)
        {
            firstUpdate = false;
            if(world.isRemote && shooter == null && getDataManager().get(SHOOTER_ID) != -1)
            {
                Entity ent = world.getEntityByID(getDataManager().get(SHOOTER_ID));
                if(ent instanceof LivingEntity)
                {
                    shooter = (LivingEntity)ent;
                    prevRotationYaw = rotationYaw = -shooter.rotationYawHead;
                    prevRotationPitch = rotationPitch = -shooter.rotationPitch;
                    if(ent instanceof PlayerEntity && ent.world.isRemote)
                    {
                        Torched.eventHandlerClient.nudgeHand((PlayerEntity)ent);
                    }
                }
            }
        }

        if(!isAlive())
        {
            return;
        }
        super.tick();

        age++;

        if(age > 1200)
        {
            remove();
            return;
        }

        if(world.isRemote)
        {
            spawnParticle();
        }

        if(ceilingBounce > 0)
        {
            ceilingBounce--;
        }

        Vector3d motion = getMotion();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float var1 = MathHelper.sqrt(motion.x * motion.x + motion.z * motion.z);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(motion.x, motion.z) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(motion.y, (double)var1) * 180.0D / Math.PI);
        }

        RayTraceResult result = EntityHelper.rayTrace(world, getPositionVec(), getPositionVec().add(getMotion()), this, true, RayTraceContext.BlockMode.COLLIDER, b -> true, RayTraceContext.FluidMode.NONE, e -> !(e instanceof EntityTorch || e instanceof EntityTorchFirework || e instanceof ItemEntity || age < 7 && e == shooter));

        if (result.getType() == RayTraceResult.Type.ENTITY)
        {
            Entity collidedEnt = ((EntityRayTraceResult)result).getEntity();
            double velo = MathHelper.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z);
            int var23 = MathHelper.ceil(velo * this.damage);

            DamageSource source;
            if (this.shooter == null)
            {
                source = (new IndirectEntityDamageSource("torchedRand", this, this)).setProjectile();
            }
            else if(shooter == collidedEnt)
            {
                if(fromFirework)
                {
                    source = (new IndirectEntityDamageSource("torchedFireSelf", this, this.shooter)).setProjectile();
                }
                else
                {
                    source = (new IndirectEntityDamageSource("torchedSelf", this, this.shooter)).setProjectile();
                }
            }
            else
            {
                if(fromFirework)
                {
                    source = (new IndirectEntityDamageSource("torchedFire", this, this.shooter)).setProjectile();
                }
                else
                {
                    source = (new IndirectEntityDamageSource("torched", this, this.shooter)).setProjectile();
                }
            }

            if (this.isBurning() && !(collidedEnt instanceof EndermanEntity))
            {
                collidedEnt.setFire(5);
            }

            if (collidedEnt.attackEntityFrom(source, var23))
            {
                if (collidedEnt instanceof LivingEntity)
                {
                    LivingEntity var24 = (LivingEntity)collidedEnt;

                    if (this.shooter != null)
                    {
                        Enchantments.THORNS.onUserHurt(var24, this.shooter, EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.THORNS, var24));
                    }

                    if (this.shooter != null && collidedEnt != this.shooter && collidedEnt instanceof PlayerEntity && this.shooter instanceof ServerPlayerEntity)
                    {
                        ((ServerPlayerEntity)this.shooter).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.HIT_PLAYER_ARROW, 0.0F)); //arrow sound
                    }
                }
            }
            else
            {
                setMotion(getMotion().mul(-0.1D, -0.1D, -0.1D));
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
            }
        }
        else if(result.getType() == RayTraceResult.Type.BLOCK)
        {
            BlockRayTraceResult blockResult = (BlockRayTraceResult)result;
            BlockPos pos = blockResult.getPos();
            BlockState state = this.world.getBlockState(pos);
            if(blockResult.getFace() != Direction.DOWN)
            {
                float amp = 0.2F;
                setMotion(this.getPosX() - result.getHitVec().x, this.getPosY() - result.getHitVec().y, this.getPosZ() - result.getHitVec().z);
                setMotion(getMotion().mul(amp, amp, amp));
            }
            motion = getMotion();
            double velo = MathHelper.sqrt(motion.x * motion.x + motion.y * motion.y + motion.z * motion.z);
            setPosition(getPosX() - (motion.x / velo * 0.05D), getPosY() - (motion.y / velo * 0.05D), getPosZ() - (motion.z / velo * 0.05D) );

            if (!state.isAir(world, pos))
            {
                state.onEntityCollision(world, pos, this);
            }

            if(!world.isRemote && blockResult.getFace() == Direction.DOWN)
            {
                if(ceilingBounce > 0)
                {
                    entityDropItem(Blocks.TORCH.asItem());
                    remove();
                    return;
                }
                ceilingBounce = 5;
                setMotion(getMotion().mul(1D, -0.8D, 1D));
            }

            if(blockResult.getFace() != Direction.DOWN && Block.hasEnoughSolidSide(world, pos, blockResult.getFace()) && world.getBlockState(pos.offset(blockResult.getFace(), 1)).getMaterial().isReplaceable())
            {
                BlockPos newPos = pos.offset(blockResult.getFace(), 1);
                if(!world.isRemote)
                {
                    if(world.getBlockState(newPos).isReplaceable(new DirectionalPlaceContext(world, newPos, blockResult.getFace().getOpposite(), ItemStack.EMPTY, blockResult.getFace())))
                    {
                        if(blockResult.getFace() == Direction.UP)
                        {
                            world.setBlockState(newPos, Blocks.TORCH.getDefaultState());
                        }
                        else
                        {
                            world.setBlockState(newPos, Blocks.WALL_TORCH.getDefaultState().with(WallTorchBlock.HORIZONTAL_FACING, blockResult.getFace()));
                        }
                        world.playSound(null, (double)((float)newPos.getX() + 0.5F), (double)((float)newPos.getY() + 0.5F), (double)((float)newPos.getZ() + 0.5F), Blocks.TORCH.getSoundType(world.getBlockState(newPos)).getPlaceSound(), SoundCategory.BLOCKS, (Blocks.TORCH.getSoundType(world.getBlockState(newPos)).getVolume() + 1.0F) / 2.0F, Blocks.TORCH.getSoundType(world.getBlockState(newPos)).getPitch() * 0.8F);
                    }
                    else
                    {
                        entityDropItem(Blocks.TORCH.asItem());
                    }
                }
                remove();
                return;
            }
            else
            {
                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                if(blockResult.getFace() == Direction.UP)
                {
                    entityDropItem(Blocks.TORCH.asItem());
                    remove();
                    return;
                }
            }
        }

        motion = getMotion();
        setPosition(getPosX() + motion.x, getPosY() + motion.y, getPosZ() + motion.z);
        this.rotationYaw = (float)(Math.atan2(motion.x, motion.z) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(motion.y, MathHelper.sqrt(motion.x * motion.x + motion.z * motion.z)) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
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
        float mag = 0.99F;

        if (this.isInWater())
        {
            float var26 = 0.25F;
            for (int var25 = 0; var25 < 4; ++var25)
            {
                this.world.addParticle(ParticleTypes.BUBBLE, this.getPosX() - motion.x * (double)var26, this.getPosY() - motion.y * (double)var26, this.getPosZ() - motion.z * (double)var26, motion.x, motion.y, motion.z);
            }

            mag = 0.8F;
        }

        setMotion(getMotion().mul(mag, mag, mag).add(0D, -0.04D, 0D));
        this.setPosition(getPosX(), getPosY(), getPosZ());
        this.doBlockCollisions();
    }

    @OnlyIn(Dist.CLIENT)
    public void spawnParticle()
    {
        Vector3d mo = getMotion();
        double particleSpeed = 0.75D;
        Minecraft.getInstance().particles.addParticle(Torched.Particles.FLAME.get(), this.getPosX() - mo.x * 0.3D, this.getPosY() - mo.y * 0.3D, this.getPosZ() - mo.z * 0.3D, mo.x * particleSpeed, mo.y * particleSpeed, mo.z * particleSpeed);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getBoundingBox().getAverageEdgeLength() * 10D;

        if (Double.isNaN(d0))
        {
            d0 = 1.0D;
        }

        d0 = d0 * 64.0D * getRenderDistanceWeight();
        return distance < d0 * d0;
    }

    @Override
    public void registerData()
    {
        getDataManager().register(SHOOTER_ID, -1);
    }

    @Override
    public void readAdditional(CompoundNBT tag)
    {
        damage = tag.getInt("damage");
        age = tag.getInt("age");
    }

    @Override
    public void writeAdditional(CompoundNBT tag)
    {
        tag.putInt("damage", damage);
        tag.putInt("age", age);
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
