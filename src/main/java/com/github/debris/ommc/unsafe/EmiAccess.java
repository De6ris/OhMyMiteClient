package com.github.debris.ommc.unsafe;

import dev.emi.emi.api.EmiApi;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.EmiStackInteraction;
import net.minecraft.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiAccess {
    @Nullable
    public static ItemStack getHoverItemStack() {
        EmiStackInteraction interaction = EmiApi.getHoveredStack(true);
        if (interaction.isEmpty()) {
            return null;
        }

        List<EmiStack> emiStacks = interaction.getStack().getEmiStacks();
        if (emiStacks.isEmpty()) return null;

        return emiStacks.get(0).getItemStack();
    }
}
