/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
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
    private void replymod$handlePlayerChat(ClientboundPlayerChatPacket packet, CallbackInfo ci){
        int type = packet.chatType().chatType();
        if(type == 3 || type == 2){
            Component component = packet.chatType().targetName();
            if(component != null){
                ReplyMod.lastMessenger = component.getString();
            }
        }
    }
}
