package net.bypell.spectersofnature.entity.client;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bypell.spectersofnature.entity.animations.ModAnimationDefinitions;
import net.bypell.spectersofnature.entity.custom.TreeGhostEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class TreeGhostModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart treeghost;

	public TreeGhostModel(ModelPart root) {
		this.treeghost = root.getChild("treeghost");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition treeghost = partdefinition.addOrReplaceChild("treeghost", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition head = treeghost.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition torso = treeghost.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offset(0.0015F, 1.9882F, 0.7579F));

		PartDefinition cube_r1 = torso.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 8).addBox(-1.5F, -1.5F, -1.25F, 3.0F, 3.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -0.4F, 0.218F, -0.007F, -0.0494F));

		PartDefinition belly = torso.addOrReplaceChild("belly", CubeListBuilder.create(), PartPose.offset(-0.0174F, 2.7065F, 0.9174F));

		PartDefinition cube_r2 = belly.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(13, 5).addBox(-1.3F, -1.5F, -1.25F, 2.7F, 3.0F, 2.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, -0.4F, 0.3467F, -0.028F, 0.018F));

		PartDefinition tail = belly.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0159F, 3.0052F, 0.4247F));

		PartDefinition cube_r3 = tail.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(9, 11).addBox(-1.4F, -1.5F, -1.5F, 2.9F, 3.0F, 2.5F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.3F, 1.45F, -0.7F, 1.1F, 1.4F, 1.1F, new CubeDeformation(0.0F))
		.texOffs(0, 2).addBox(-0.1F, 1.45F, -1.5F, 1.1F, 1.4F, 1.1F, new CubeDeformation(0.0F))
		.texOffs(9, 8).addBox(0.3F, 1.45F, -0.2F, 1.1F, 1.4F, 1.1F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.3F, 0.9F, 0.4832F, 0.0495F, -0.1037F));

		PartDefinition elbowleft = torso.addOrReplaceChild("elbowleft", CubeListBuilder.create(), PartPose.offsetAndRotation(1.862F, 1.3118F, -0.2494F, 0.0F, 0.1745F, 0.0F));

		PartDefinition armleft_r1 = elbowleft.addOrReplaceChild("armleft_r1", CubeListBuilder.create().texOffs(0, 14).addBox(-0.2135F, -0.55F, -0.0585F, 1.1F, 1.1F, 2.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.5F, -2.6F, 0.1609F, -0.518F, -0.0802F));

		PartDefinition forearmleft_r1 = elbowleft.addOrReplaceChild("forearmleft_r1", CubeListBuilder.create().texOffs(17, 1).addBox(-0.2424F, -0.55F, -2.8482F, 1.1F, 1.1F, 2.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 0.5F, -2.6F, 0.14F, -0.0691F, -0.0097F));

		PartDefinition elbowright = torso.addOrReplaceChild("elbowright", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.965F, 1.3118F, -0.2494F, 0.0F, -0.1745F, 0.0F));

		PartDefinition armright_r1 = elbowright.addOrReplaceChild("armright_r1", CubeListBuilder.create().texOffs(12, 0).addBox(-0.8865F, -0.55F, -0.0585F, 1.1F, 1.1F, 2.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.5F, -2.6F, 0.1609F, 0.518F, 0.0802F));

		PartDefinition forearmright_r1 = elbowright.addOrReplaceChild("forearmright_r1", CubeListBuilder.create().texOffs(5, 17).addBox(-0.8576F, -0.55F, -2.8482F, 1.1F, 1.1F, 2.9F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 0.5F, -2.6F, 0.14F, 0.0691F, 0.0097F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.animate(((TreeGhostEntity) entity).floatAnimationState, ModAnimationDefinitions.TREEGHOST_FLOATING, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		treeghost.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return treeghost;
	}
}