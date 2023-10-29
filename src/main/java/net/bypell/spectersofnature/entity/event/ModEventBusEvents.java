package net.bypell.spectersofnature.entity.event;

import net.bypell.spectersofnature.SpectersOfNature;
import net.bypell.spectersofnature.entity.ModEntities;
import net.bypell.spectersofnature.entity.custom.TreeGhostEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SpectersOfNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TREE_GHOST.get(), TreeGhostEntity.createAttributes().build());
    }
}
