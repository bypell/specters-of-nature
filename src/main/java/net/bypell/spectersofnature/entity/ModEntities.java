package net.bypell.spectersofnature.entity;

import net.bypell.spectersofnature.SpectersOfNature;
import net.bypell.spectersofnature.entity.custom.TreeGhostEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SpectersOfNature.MOD_ID);

    public static final RegistryObject<EntityType<TreeGhostEntity>> TREE_GHOST =
            ENTITY_TYPES.register("treeghost", () -> EntityType.Builder.of(TreeGhostEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 2.0f).build("treeghost"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
