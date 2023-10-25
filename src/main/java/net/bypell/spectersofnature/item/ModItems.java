package net.bypell.spectersofnature.item;

import net.bypell.spectersofnature.SpectersOfNature;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SpectersOfNature.MOD_ID);

    public static final RegistryObject<Item> GOLDEN_CRUCIFIX = ITEMS.register("golden_crucifix",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
