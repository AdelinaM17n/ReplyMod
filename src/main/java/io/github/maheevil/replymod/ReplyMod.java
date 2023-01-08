package io.github.maheevil.replymod;

import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.arguments.MessageArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static net.minecraft.commands.Commands.*;

public class ReplyMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("modid");
	public static String lastMessenger;

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("r")));
		LOGGER.info("Hello Fabric world!");
	}
}
