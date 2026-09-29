package io.github.adelinam17n.replymod.client;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.MessageArgument;

public class ReplyModClient implements ClientModInitializer {
	public static String lastMessenger;

	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess) -> dispatcher.register(
						ClientCommands.literal("r").then(
								ClientCommands.argument("message", MessageArgument.message()).executes(
										ReplyModClient::commandLogic
								)
						)
				)
		);

		ClientCommandRegistrationCallback.EVENT.register(
				(dispatcher, registryAccess) -> dispatcher.register(
						ClientCommands.literal("reply").then(
								ClientCommands.argument("message",MessageArgument.message()).executes(
										ReplyModClient::commandLogic
								)
						)
				)
		);
	}

	private static int commandLogic(CommandContext<FabricClientCommandSource> context){
		assert Minecraft.getInstance().player != null;

		String message = context.getArgument(
				"message",
				MessageArgument.Message.class
		).text();
		String commandToSend = "msg " + lastMessenger + " " + message;

		Minecraft.getInstance().player.connection.sendCommand(
				commandToSend
		);

		return 1;
	}
}