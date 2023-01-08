/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package io.github.maheevil.replymod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MessageArgument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.commands.Commands.literal;

public class ReplyMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("modid");
	public static String lastMessenger;

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((
				dispatcher, registryAccess, environment) -> dispatcher.register(literal("r")
						.then(
								Commands.argument("message",  MessageArgument.message())
						)
				)
		);
		LOGGER.info("Hello Fabric world!");
	}
}
