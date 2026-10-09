package net.ona.mlpfrien.magic;

import java.util.List;

public class UnicornSpells {
    public static UnicornSpell damage_bullet = UnicornSpell.registerSpell("Biden Blast",List.of(
            SpellComponent.PROJECTILE,SpellComponent.AMPLIFY,SpellComponent.EXPLODE,SpellComponent.WAIT,
            SpellComponent.PROJECTILE,SpellComponent.AMPLIFY,SpellComponent.EXPLODE,SpellComponent.WAIT));
}
