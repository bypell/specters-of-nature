package net.bypell.spectersofnature.entity.custom;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.antlr.runtime.tree.Tree;
import org.jetbrains.annotations.Nullable;

public class TreeGhostEntity extends Monster {

    public TreeGhostEntity(EntityType<? extends TreeGhostEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
        this.setNoGravity(true);
    }

    public final AnimationState floatAnimationState = new AnimationState();

    @Override
    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);

        if (this.level().isClientSide()) {

        }
    }

    private void setupAnimationStates() {

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new TreeGhostEntity.FloatTowardsPlayerGoal());
        this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 100.0F, 1.0F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 2f)
                .add(Attributes.FOLLOW_RANGE, 80f)
                .add(Attributes.ATTACK_SPEED, 1f);
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


    class FloatTowardsPlayerGoal extends Goal {
        @Override
        public boolean canUse() {
            LivingEntity livingentity = TreeGhostEntity.this.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                return true;
            }
            else {
                return false;
            }
        }

        @Override
        public void start() {
            LivingEntity livingentity = TreeGhostEntity.this.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.getEyePosition();
                TreeGhostEntity.this.moveControl.setWantedPosition(vec3.x, vec3.y, vec3.z, 1.0D);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return TreeGhostEntity.this.getTarget() != null && TreeGhostEntity.this.getTarget().isAlive();
        }
    }
}
