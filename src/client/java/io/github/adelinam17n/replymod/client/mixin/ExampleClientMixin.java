package io.github.adelinam17n.replymod.client.mixin;

import com.mojang.authlib.GameProfile;
import io.github.adelinam17n.replymod.client.ReplyModClient;
import net.minecraft.client.Minecraft;
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
public class ExampleClientMixin {
	@Inject(
			method = "handlePlayerChatMessage",
			at = @At(
					value = "HEAD"
			)
	)
	private void replymod$handlePlayerChatMessage(
			PlayerChatMessage chatMessage, GameProfile gameProfile, ChatType.Bound boundChatType, CallbackInfo ci
	){
		ChatTypeDecoration chat = boundChatType.chatType().value().chat();
		String name = chat.parameters().getFirst().getSerializedName();

		if(!chat.style().isEmpty()){
			if(name.equalsIgnoreCase("sender")){
				ReplyModClient.lastMessenger = gameProfile.name();
			} else {
				assert boundChatType.targetName().isPresent();
				ReplyModClient.lastMessenger = boundChatType.targetName().get().getString();//.getString();
				System.out.println(ReplyModClient.lastMessenger);
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
					ReplyModClient.lastMessenger = messageParts[3].replace(":", "");
				}else if(messageParts[1].equalsIgnoreCase("whispers")){
					ReplyModClient.lastMessenger = messageParts[0];
				}
			}
		}
	}
}