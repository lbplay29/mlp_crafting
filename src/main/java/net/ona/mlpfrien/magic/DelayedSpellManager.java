package net.ona.mlpfrien.magic;

import java.util.ArrayList;
import java.util.List;

public final class DelayedSpellManager {

    private static final List<DelayedSpell> DELAYED_SPELLS = new ArrayList<>();

    public static void schedule(int ticks, SpellContext context, List<SpellComponent> components) {
        DELAYED_SPELLS.add(new DelayedSpell(ticks, context, List.copyOf(components)));
    }

    public static void tick() {
        for (int i = DELAYED_SPELLS.size() - 1; i >= 0; i--) {
            DelayedSpell delayed = DELAYED_SPELLS.get(i);
            delayed.ticks--;
            if (delayed.ticks <= 0) {
                // Remove FIRST, because executing the spell could
                // schedule another delay.
                DELAYED_SPELLS.remove(i);

                SpellCaster.executeComponents(delayed.context, delayed.components);
            }
        }
    }

    private static class DelayedSpell {
        private int ticks;

        private final SpellContext context;
        private final List<SpellComponent> components;

        private DelayedSpell(int ticks, SpellContext context, List<SpellComponent> components) {
            this.ticks = ticks;
            this.context = context;
            this.components = components;
        }
    }
}
