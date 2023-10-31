package net.bypell.spectersofnature.entity.event;

import net.bypell.spectersofnature.SpectersOfNature;
import net.bypell.spectersofnature.entity.ModEntities;
import net.bypell.spectersofnature.entity.custom.TreeGhostEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = SpectersOfNature.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.TREE_GHOST.get(), TreeGhostEntity.createAttributes().build());
    }


    // ne fonctionne pas (le mob n'est pas censé spawn naturellement de toutes facons vu qu'il despawn quand il est hors de vue)
    // je vais donc pas essayer de corriger tout ca
    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SpawnPlacements.register(ModEntities.TREE_GHOST.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, TreeGhostEntity::canSpawn);
        });
    }
}
