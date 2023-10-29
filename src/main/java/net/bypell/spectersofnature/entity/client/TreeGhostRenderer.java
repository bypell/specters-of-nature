package net.bypell.spectersofnature.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.bypell.spectersofnature.SpectersOfNature;
import net.bypell.spectersofnature.entity.custom.TreeGhostEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class TreeGhostRenderer extends MobRenderer<TreeGhostEntity, TreeGhostModel<TreeGhostEntity>> {
    public TreeGhostRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new TreeGhostModel<>(pContext.bakeLayer(ModModelLayers.TREE_GHOST_LAYER)), 0f);
    }

    @Override
    public ResourceLocation getTextureLocation(TreeGhostEntity pEntity) {
        return new ResourceLocation(SpectersOfNature.MOD_ID, "textures/entity/treeghost_texture.png");
    }

    @Override
    public void render(TreeGhostEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {
        pMatrixStack.scale(2.0f, 2.0f, 2.0f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
