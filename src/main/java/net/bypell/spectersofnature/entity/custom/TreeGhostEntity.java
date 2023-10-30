package net.bypell.spectersofnature.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.antlr.runtime.tree.Tree;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class TreeGhostEntity extends Monster {

    public TreeGhostEntity(EntityType<? extends TreeGhostEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
        this.moveControl = new TreeGhostEntity.TreeGhostMoveControl(this);
    }

    public final AnimationState floatAnimationState = new AnimationState();

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        setNoGravity(true);

        if (this.level().isClientSide()) {
            this.floatAnimationState.startIfStopped(this.tickCount);
        }
    }

    private void setupAnimationStates() {

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TreeGhostEntity.TreeGhostFlyAttackGoal());
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 100.0F, 1.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 1000D)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.ATTACK_KNOCKBACK, 1f)
                .add(Attributes.FOLLOW_RANGE, 80f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ATTACK_SPEED, 0.1f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.VEX_CHARGE;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // empeche les attaques nomrales avec épée (faut un crucifix)
        return false;
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

            //TreeGhostEntity.this.playSound(SoundEvents.VEX_CHARGE, 1.0F, 1.0F);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = TreeGhostEntity.this.getTarget();
            if (livingentity != null) {
                if (TreeGhostEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
                    TreeGhostEntity.this.doHurtTarget(livingentity);
                }
                Vec3 vec3 = livingentity.getEyePosition();
                TreeGhostEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y - 1f, vec3.z, 1.0D);
            }
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

                TreeGhostEntity.this.setDeltaMovement(vec3.scale(0.2D / d0));
                System.out.println(vec3.scale(this.speedModifier * 0.05D / d0));
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

}
