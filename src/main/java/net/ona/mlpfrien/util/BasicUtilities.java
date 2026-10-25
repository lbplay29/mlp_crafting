package net.ona.mlpfrien.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.ona.mlpfrien.MyLittlePonyCraftingFriends;
import net.ona.mlpfrien.data.PonyAttachments;
import net.ona.mlpfrien.data.PonyData;

public class BasicUtilities {
    public static int loopingInt(int max, int current) {
        if (current>=max) {
            current=0;
        } else {
            current++;
        }
        return current;
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MyLittlePonyCraftingFriends.MODID, path);
    }
}
