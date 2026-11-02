package net.ona.mlpfrien.ponyvisuals;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;

import java.io.IOException;

/**
 * Loads the mod's custom core shaders.
 * Files live in assets/mlpfrien/shaders/core/ (magic_aura.json / .vsh / .fsh).
 */
@EventBusSubscriber(modid = MyLittlePonyCraftingFriends.MODID, value = Dist.CLIENT)
public class PonyShaders {
    /** Null until resources load (or if the shader fails to compile). */
    public static ShaderInstance magicAura;

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(
                new ShaderInstance(event.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "magic_aura"),
                        DefaultVertexFormat.NEW_ENTITY),
                shader -> magicAura = shader);
    }
}
