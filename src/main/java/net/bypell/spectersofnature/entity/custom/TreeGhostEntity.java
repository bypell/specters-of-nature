package net.bypell.spectersofnature.entity.custom;

import net.bypell.spectersofnature.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class TreeGhostEntity extends Monster {

    private int banishDelayTicks;

    public TreeGhostEntity(EntityType<? extends TreeGhostEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
        this.moveControl = new TreeGhostEntity.TreeGhostMoveControl(this);
    }

    public final AnimationState floatAnimationState = new AnimationState();

    @Override
    public void tick() {
        addParticlesAroundSelf(ParticleTypes.SMOKE, 4);
        super.tick();
        setNoGravity(true);

        if (this.level().isClientSide()) {
            this.floatAnimationState.startIfStopped(this.tickCount);
        }
        if (this.getTarget() != null && this.getTarget() instanceof Player) {
            Player player = (Player) this.getTarget();
            if (this.distanceTo(player) < 5.0f && player.getMainHandItem().getItem() == ModItems.GOLDEN_CRUCIFIX.get() && isPlayerLookingAtMe(player)) {
                if (--this.banishDelayTicks <= 0) {
                    this.banishDelayTicks = 10;
                    this.hurt(this.damageSources().starve(), 3.0F);
                }
            }

        }
    }

    private void setupAnimationStates() {

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new TreeGhostEntity.TreeGhostFlyAttackGoal());
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 10f)
                .add(Attributes.ATTACK_KNOCKBACK, 20f)
                .add(Attributes.FOLLOW_RANGE, 80f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_SPEED, 0.1f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.ELDER_GUARDIAN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ALLAY_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // check pour empecher les attaques normales avec épée (faut un crucifix)
        if (source != this.damageSources().starve()) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource pCause) {
        super.die(pCause);
        Player player = this.level().getNearestPlayer(this, 8.0f);
        if (player != null) {
            if (player.getMainHandItem().getItem() == ModItems.GOLDEN_CRUCIFIX.get()) {
                player.getMainHandItem().shrink(1);
            }
        }
    }

    private boolean isPlayerLookingAtMe(Player player) {
        Vec3 vec3 = player.getViewVector(1.0F).normalize();
        Vec3 vec31 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d0 = vec31.length();
        vec31 = vec31.normalize();
        double d1 = vec3.dot(vec31);
        return d1 < 1f && d1 > 0.5f;
    }


    protected void addParticlesAroundSelf(ParticleOptions pParticleOption, int quantity) {
        for(int i = 0; i < quantity; ++i) {
            double d0 = this.random.nextGaussian() * 0.05D;
            double d1 = this.random.nextGaussian() * 0.05D;
            double d2 = this.random.nextGaussian() * 0.05D;
            this.level().addParticle(pParticleOption, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
        }

    }

    class TreeGhostFlyAttackGoal extends Goal {
        public TreeGhostFlyAttackGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            LivingEntity livingentity = TreeGhostEntity.this.getTarget();
            if (livingentity != null && livingentity.isAlive() && !TreeGhostEntity.this.getMoveControl().hasWanted()) {
                return true;
                //return TreeGhostEntity.this.distanceToSqr(livingentity) > 1.0D;
            } else {
                return false;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return TreeGhostEntity.this.getMoveControl().hasWanted() && TreeGhostEntity.this.getTarget() != null
                    && TreeGhostEntity.this.getTarget().isAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            LivingEntity livingentity = TreeGhostEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                TreeGhostEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            TreeGhostEntity.this.noPhysics = true;
            LivingEntity livingentity = TreeGhostEntity.this.getTarget();
            if (livingentity != null) {
                if (TreeGhostEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    TreeGhostEntity.this.doHurtTarget(livingentity);
                }
                Vec3 vec3 = livingentity.getEyePosition();
                TreeGhostEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y - 1f, vec3.z, 1.0D);
            }
        }

        public void stop() {
            TreeGhostEntity.this.hurt(TreeGhostEntity.this.damageSources().starve(), 200.0F);
        }
    }

    class TreeGhostMoveControl extends MoveControl {
        public TreeGhostMoveControl(TreeGhostEntity treeGhost) {
            super(treeGhost);
        }

        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                Vec3 vec3 = new Vec3(this.wantedX - TreeGhostEntity.this.getX(), this.wantedY - TreeGhostEntity.this.getY(), this.wantedZ - TreeGhostEntity.this.getZ());
                double d0 = vec3.length();

                TreeGhostEntity.this.setDeltaMovement(vec3.scale(0.25D / d0));
                if (TreeGhostEntity.this.getTarget() == null) {
                    Vec3 vec31 = TreeGhostEntity.this.getDeltaMovement();
                    TreeGhostEntity.this.setYRot(-((float) Mth.atan2(vec31.x, vec31.z)) * (180F / (float)Math.PI));
                    TreeGhostEntity.this.yBodyRot = TreeGhostEntity.this.getYRot();
                } else {
                    double d2 = TreeGhostEntity.this.getTarget().getX() - TreeGhostEntity.this.getX();
                    double d1 = TreeGhostEntity.this.getTarget().getZ() - TreeGhostEntity.this.getZ();
                    TreeGhostEntity.this.setYRot(-((float)Mth.atan2(d2, d1)) * (180F / (float)Math.PI));
                    TreeGhostEntity.this.yBodyRot = TreeGhostEntity.this.getYRot();
                }
            }
        }
    }

    public static boolean canSpawn(EntityType<TreeGhostEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        //return checkAnimalSpawnRules(entityType, level, spawnType, pos, random) && pos.getY() > 100;
        return level.getBlockState(pos.below()).isAir();
    }

}
