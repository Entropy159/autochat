package com.entropy;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

import org.lwjgl.glfw.GLFW;

import com.mojang.brigadier.Command;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;


public class AutoChatClient implements ClientModInitializer {
	public static KeyBinding key;
	@Override
	public void onInitializeClient() {
		key = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.autochat.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, "category.autochat"));
		
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while(key.wasPressed()) {
				client.setScreen(new ChatScreen(Config.get()));
			}
		});
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(literal("autochat").then(argument("text", greedyString()).executes(arguments -> {
				Config.set(getString(arguments, "text"));
				return Command.SINGLE_SUCCESS;
			})));
		});
	}
}