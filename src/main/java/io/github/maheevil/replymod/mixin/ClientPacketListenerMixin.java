package io.github.maheevil.replymod.mixin;

import io.github.maheevil.replymod.ReplyMod;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Inject(
            method = "handlePlayerChat",
            at = @At(
                    value = "HEAD"
                    //target = "net/minecraft/client/multiplayer/ClientPacketListener.sendCommand(Ljava/lang/String;)V",
                    //shift = At.Shift.AFTER
            )
            //locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private void rep(ClientboundPlayerChatPacket packet, CallbackInfo ci){

        int type = packet.chatType().chatType();
        if(type == 3 || type == 2){
            Component component = packet.chatType().targetName();
            if(component != null){
                ReplyMod.lastMessenger = component.getString();
            }

        }
    }
}
