package net.bypell.spectersofnature.item;

import net.bypell.spectersofnature.SpectersOfNature;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SpectersOfNature.MOD_ID);

    public static final RegistryObject<CreativeModeTab> SPECTERS_OF_NATURE_TAB = CREATIVE_MODE_TABS.register("specters_of_nature_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.GOLDEN_CRUCIFIX.get()))
                    .title(Component.translatable("creativetab.specters_of_nature_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.GOLDEN_CRUCIFIX.get());
                    })
                    .build());
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
