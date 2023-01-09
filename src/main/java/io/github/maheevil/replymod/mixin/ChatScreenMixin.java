/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.github.maheevil.replymod.mixin;

import io.github.maheevil.replymod.ReplyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public abstract class ChatScreenMixin extends Screen {
	protected ChatScreenMixin(Component component){
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
	private void replymod$handleChatInput(String input, boolean addToRecentChat, CallbackInfoReturnable<Boolean> cir){
		if(input.length() >= 4 && input.substring(1,3).equals("r ")){
			this.minecraft.gui.getChat().getRecentChat().forEach(s -> System.out.println(s));
			this.minecraft.player.connection.sendCommand("msg " + ReplyMod.lastMessenger + " " + input.substring(3));
			cir.setReturnValue(true);
			cir.cancel();
		}
	}

	// I realise how cursed this is
	@Inject(
			method = "init",
			at = @At("HEAD")
	)
	private void replymod$init(CallbackInfo ci){
		if(ReplyMod.clientUsername.equals("")){
			assert Minecraft.getInstance().player != null;
			ReplyMod.clientUsername = Minecraft.getInstance().player.getName().getString();
		}
	}

}
