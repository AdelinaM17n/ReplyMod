/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.github.maheevil.replymod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.commands.arguments.MessageArgument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReplyMod implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("replymod");
	public static String lastMessenger;
	public static String clientUsername = "";

	@Override
	public void onInitializeClient() {
		//ClientCommandManager.literal()
		ClientCommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess) -> dispatcher.register(
						ClientCommandManager.literal("r")
								.then(
										ClientCommandManager.argument("message",MessageArgument.message())
								)
				)
		);
	}
}
