package net.ona.mlpfrien.ponyvisuals.render;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.AppearanceData;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PonyTextureManager {

    private static final Map<UUID, PonyTexture> TEXTURES = new HashMap<>();

    private static final ResourceLocation BASE_TEXTURE = ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "textures/entity/pony_base.png");

    public static ResourceLocation getTexture(AbstractClientPlayer player) throws IOException {
        PonyData currentData = player.getData(PonyAttachments.PONY_DATA);
        AppearanceData currentAppData = player.getData(PonyAttachments.APPEARANCE_DATA);

        PonyTexture cached = TEXTURES.get(player.getUUID());

        if (cached == null||!currentData.equals(cached.lastData)||!currentAppData.equals(cached.lastAppData)) {
            return createTexture(player);
        }
        return cached.location;
    }


    public static ResourceLocation createTexture(AbstractClientPlayer player) {
        AppearanceData data = player.getData(PonyAttachments.APPEARANCE_DATA);
        PonyData dataP = player.getData(PonyAttachments.PONY_DATA);
        try {
            NativeImage image = loadBaseTexture();

            CutieMarkManager.applyCutieMarkTexture(image,player);
            applyCoatColor(image, data.coatColor());
            applyEyeColor(image, data.eyeColor());
            applyManeColor(image, data.maneColor());

            DynamicTexture texture = new DynamicTexture(image);
            ResourceLocation location = ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, "dynamic/pony/" + player.getUUID());
            Minecraft.getInstance().getTextureManager().register(location, texture);

            TEXTURES.put(player.getUUID(), new PonyTexture(location, texture,dataP,data));
            return location;

        } catch (IOException e) {
            throw new RuntimeException("Failed to create pony texture for " + player.getName().getString(), e);
        }
    }


    private static NativeImage loadBaseTexture() throws IOException {
        try (InputStream stream = Minecraft.getInstance().getResourceManager().open(BASE_TEXTURE)) {
            return NativeImage.read(stream);
        }
    }

    private static void applyEyeColor(NativeImage image, int eyeRgb) {
        int marker = rgbToAbgr(0x00FF00);
        int markerDark = rgbToAbgr(0x00c800);
        int replacement = rgbToAbgr(eyeRgb);
        int replacementDark = rgbToAbgr(darkenColor(eyeRgb,80));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getPixelRGBA(x, y) == marker) {
                    image.setPixelRGBA(x, y, replacement);
                } else if (image.getPixelRGBA(x, y) == markerDark) {
                    image.setPixelRGBA(x, y, replacementDark);
                }
            }
        }
    }

    private static void applyManeColor(NativeImage image, int maneRgb) {
        int marker = rgbToAbgr(0x0000FF);
        int markerDark = rgbToAbgr(0x0000c8);
        int replacement = rgbToAbgr(maneRgb);
        int replacementDark = rgbToAbgr(darkenColor(maneRgb,40));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getPixelRGBA(x, y) == marker) {
                    image.setPixelRGBA(x, y, replacement);
                } else if (image.getPixelRGBA(x, y) == markerDark) {
                    image.setPixelRGBA(x, y, replacementDark);
                }
            }
        }
    }


    private static void applyCoatColor(NativeImage image, int coatRgb) {
        int markerLight = rgbToAbgr(0xFF0000);
        int marker = rgbToAbgr(0xd80000);
        int markerDark = rgbToAbgr(0xc80000);
        int markerDarker = rgbToAbgr(0xc00000);
        int replacementLight = rgbToAbgr(coatRgb);
        int replacement = rgbToAbgr(darkenColor(coatRgb,30));
        int replacementDark = rgbToAbgr(darkenColor(coatRgb,60));
        int replacementDarker = rgbToAbgr(darkenColor(coatRgb,90));

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getPixelRGBA(x, y) == marker) {
                    image.setPixelRGBA(x, y, replacement);
                } else if (image.getPixelRGBA(x, y) == markerDark) {
                    image.setPixelRGBA(x, y, replacementDark);
                } else if (image.getPixelRGBA(x, y) == markerDarker) {
                    image.setPixelRGBA(x, y, replacementDarker);
                } else if (image.getPixelRGBA(x, y) == markerLight) {
                    image.setPixelRGBA(x, y, replacementLight);
                }
            }
        }
    }

    private static int darkenColor(int color, int darkening) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        r = Math.clamp(r - darkening, 0, 255);
        g = Math.clamp(g - darkening, 0, 255);
        b = Math.clamp(b - darkening+5, 0, 255);

        return (r << 16) | (g << 8) | b;
    }

    public static int rgbToAbgr(int rgb) {
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        return 0xFF000000 | (b << 16) | (g << 8) | r;
    }

    private record PonyTexture(ResourceLocation location, DynamicTexture texture, PonyData lastData, AppearanceData lastAppData) {}
}
