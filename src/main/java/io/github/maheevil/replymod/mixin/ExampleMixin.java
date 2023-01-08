package io.github.maheevil.replymod.mixin;

import io.github.maheevil.replymod.ReplyMod;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ExampleMixin extends Screen {
	protected ExampleMixin(Component component){
		super(component);
	}

	@SuppressWarnings("all")
	@Inject(
			method = "handleChatInput",
			at = @At(
					value = "FIELD",
					target = "net/minecraft/client/gui/screens/ChatScreen.minecraft : Lnet/minecraft/client/Minecraft;",
					shift = At.Shift.BEFORE, ordinal = 1
			),
			cancellable = true
	)
	private void replymod$normalizeChatMessages(String input, boolean addToRecentChat, CallbackInfoReturnable<Boolean> cir){
		if(input.length() > 4 && input.substring(1,3).equals("r ")){
			this.minecraft.gui.getChat().getRecentChat().forEach(s -> System.out.println(s));
			this.minecraft.player.connection.sendCommand("msg " + ReplyMod.lastMessenger + " " + input.substring(3));
			cir.setReturnValue(true);
			cir.cancel();
		}
	}

}
