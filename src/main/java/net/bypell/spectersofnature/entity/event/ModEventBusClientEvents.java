package net.bypell.spectersofnature.entity.event;

import net.bypell.spectersofnature.SpectersOfNature;
import net.bypell.spectersofnature.entity.client.ModModelLayers;
import net.bypell.spectersofnature.entity.client.TreeGhostModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpectersOfNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {
    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.TREE_GHOST_LAYER, TreeGhostModel::createBodyLayer);
    }
}
