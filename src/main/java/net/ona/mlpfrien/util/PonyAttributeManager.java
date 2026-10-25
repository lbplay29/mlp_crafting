package net.ona.mlpfrien.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;

import java.util.List;
import java.util.Objects;

public class PonyAttributeManager {
    public static void applyModifiers(PonyData data, Player player){
            for (PonyData.RACES race : PonyData.RACES.values()) {
                List<PonyData.AttModifiers> modifiers = race.getRaceModifiers();
                for (PonyData.AttModifiers modifier : modifiers) {
                    Objects.requireNonNull(player.getAttribute(modifier.attribute())).removeModifier(modifier.id());
                }
            }
            List<PonyData.AttModifiers> modifiers = data.race().getRaceModifiers();
            for (PonyData.AttModifiers modifier : modifiers) {
                Objects.requireNonNull(player.getAttribute(modifier.attribute())).addPermanentModifier(new AttributeModifier(modifier.id(), modifier.amount(), modifier.operation()));
            }
            for (PonyData.CUTIE_MARKS cutieMark : PonyData.CUTIE_MARKS.values()) {
                List<PonyData.AttModifiers> modifiers2 = cutieMark.getCutieModifiers();
                for (PonyData.AttModifiers modifier : modifiers2) {
                    Objects.requireNonNull(player.getAttribute(modifier.attribute())).removeModifier(modifier.id());
                }
            }
            List<PonyData.AttModifiers> modifiers2 = data.cutieMark().getCutieModifiers();
            for (PonyData.AttModifiers modifier : modifiers2) {
                if (modifier.skill()==null || player.getData(PonyAttachments.LEVEL_DATA).hasSkill(modifier.skill())) {
                    Objects.requireNonNull(player.getAttribute(modifier.attribute())).addPermanentModifier(
                            new AttributeModifier(modifier.id(), modifier.amount(), modifier.operation()));
                }
            }
    }
}
