package io.github.maheevil.replymod.mixin;

import com.mojang.authlib.GameProfile;
import io.github.maheevil.replymod.ReplyMod;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.ChatTypeDecoration;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatListener.class)
public class ChatListenerMixin {
    @Inject(
            method = "handlePlayerChatMessage",
            at = @At(
                    value = "HEAD"
            )
    )
    private void replymod$handlePlayerChatMessage(
            PlayerChatMessage chatMessage, GameProfile gameProfile, ChatType.Bound boundChatType, CallbackInfo ci
    ){
        ChatTypeDecoration chat = boundChatType.chatType().chat();
        String name = chat.parameters().get(0).getSerializedName();

        if(!chat.style().isEmpty()){
            if(name.equalsIgnoreCase("sender")){
                ReplyMod.lastMessenger = gameProfile.getName();
            } else {
                assert boundChatType.targetName() != null;
                ReplyMod.lastMessenger = boundChatType.targetName().getString();
                System.out.println(ReplyMod.lastMessenger);
            }
        }
    }

    @Inject(
            method = "handleSystemMessage",
            at = @At(
                    value = "HEAD"
            )
    )
    private void replymod$handleSystemMessage(Component message, boolean isOverlay, CallbackInfo ci){
        String string = message.getString();

        if(string.contains("whisper")){
            String[] messageParts = string.split(" ");

            if(messageParts.length >= 4){
                if(messageParts[1].equalsIgnoreCase("whisper")){
                    ReplyMod.lastMessenger = messageParts[3].replace(":", "");
                }else if(messageParts[1].equalsIgnoreCase("whispers")){
                    ReplyMod.lastMessenger = messageParts[0];
                }
            }
        }
    }
}
