/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.github.maheevil.replymod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.MessageArgument;

public class ReplyMod implements ClientModInitializer {

	public static String lastMessenger;

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess) -> dispatcher.register(
						ClientCommandManager.literal("r").then(
								ClientCommandManager.argument("message",MessageArgument.message()).executes(
										context -> {
											assert Minecraft.getInstance().player != null;

											String message = context.getArgument(
													"message",
													MessageArgument.Message.class
											).getText();
											String commandToSend = "msg " + lastMessenger + " " + message;

											Minecraft.getInstance().player.connection.sendCommand(
													commandToSend
											);

											return 1;
										}
								)
						)
				)
		);
	}
}
