package net.ona.mlpfrien.ponyvisuals.render;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;
import net.ona.mlpfrien.network.PonyDataPayload;

import java.io.IOException;
import java.io.InputStream;

public class CutieMarkManager {
    public static void applyCutieMarkTexture(NativeImage image, Player player) throws IOException {
        int marker = PonyTextureManager.rgbToAbgr(0xFF00FF);
        int markerM = PonyTextureManager.rgbToAbgr(0x00FFFF);
        NativeImage mark = loadCMTexture(player.getData(PonyAttachments.PONY_DATA));
        int progressX=0;
        int progressY=0;
        int progressMX=3;
        int progressMY=0;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if (image.getPixelRGBA(x, y) == marker) {
                    image.setPixelRGBA(x, y, newColor(mark,progressX,progressY,player.getData(PonyAttachments.APPEARANCE_DATA).coatColor()));
                    if (progressX>=3) {
                        progressX = 0;
                        if (progressY>=3) {
                            progressY=0;
                        } else {
                            progressY++;
                        }
                    } else {
                        progressX++;
                    }
                } else if (image.getPixelRGBA(x, y) == markerM) {
                    image.setPixelRGBA(x, y, newColor(mark,progressMX,progressMY,player.getData(PonyAttachments.APPEARANCE_DATA).coatColor()));
                    if (progressMX<=0) {
                        progressMX = 3;
                        if (progressMY>=3) {
                            progressMY=0;
                        } else {
                            progressMY++;
                        }
                    } else {
                        progressMX--;
                    }
                }
            }
        }
    }

    private static int newColor(NativeImage image, int x, int y, int coatColor) {
        int alpha = (image.getPixelRGBA(x,y) >>> 24) & 0xFF;
        if (alpha==0) {
            return PonyTextureManager.rgbToAbgr(coatColor);
        }
        return image.getPixelRGBA(x,y);
    }

    private static NativeImage loadCMTexture(PonyData data) throws IOException {
        String mark = data.cutieMark().toString().toLowerCase();
        if (!data.discoveredCM()) {
            mark="blank";
        }
        try (InputStream stream = Minecraft.getInstance().getResourceManager().open(ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID,
                "textures/entity/cutie_marks/"+mark+".png"))) {
            return NativeImage.read(stream);
        }
    }

    public static void changeCutieMark(Player player, PonyData.CUTIE_MARKS newMark) {
        PonyData data = player.getData(PonyAttachments.PONY_DATA);
        PacketDistributor.sendToServer(new PonyDataPayload(new PonyData(data.race(),newMark, data.discoveredCM())));
    }

    public static void discoverCutieMarkServer(Player player, boolean discovery) {
        PonyData data = player.getData(PonyAttachments.PONY_DATA);
        player.setData(PonyAttachments.PONY_DATA, new PonyData(data.race(),data.cutieMark(), discovery));
    }
}
