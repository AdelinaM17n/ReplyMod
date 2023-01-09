/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.github.maheevil.replymod.mixin;

import io.github.maheevil.replymod.ReplyMod;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerChatPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow private ClientLevel level;

    @Inject(
            method = "handlePlayerChat",
            at = @At(
                    value = "RETURN"
            )
    )
    private void replymod$handlePlayerChat(ClientboundPlayerChatPacket packet, CallbackInfo ci){
        int type = packet.chatType().chatType();

        // Type 2 is for incoming direct messages
        // Type 3 is for outgoing direct messages
        if(type == 2){
            ReplyMod.lastMessenger = Objects.requireNonNull(this.level.getPlayerByUUID(packet.sender())).getName().getString();
        }else if(type == 3){
            Component component = packet.chatType().targetName();
            if(component != null){
                ReplyMod.lastMessenger = component.getString();
            }
        }
    }
}
