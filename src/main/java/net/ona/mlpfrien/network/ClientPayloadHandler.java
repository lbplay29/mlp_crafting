package net.ona.mlpfrien.network;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.ona.mlpfrien.gui.PonyCreatorGUI;
import net.ona.mlpfrien.gui.PonyInfoGUI;

public class ClientPayloadHandler {
    public static void handleScreenChange(ChangeScreenPayload payload, IPayloadContext context) {
        switch (payload.screen()) {
            case creator ->Minecraft.getInstance().setScreen(new PonyCreatorGUI(false));
            case creator_simple -> Minecraft.getInstance().setScreen(new PonyCreatorGUI(true));
            case info -> Minecraft.getInstance().setScreen(new PonyInfoGUI());
        }
    }
}
