package com.entropy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.fabricmc.loader.api.FabricLoader;

public class Config {
	
	public static File file = FabricLoader.getInstance().getConfigDir().resolve("autochat.txt").toFile();
	
	public static void set(String text) {
		if(file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get() {
		String text = "<rainbow>";
		if(file.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				text = reader.readLine();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			set(text);
		}
		return text;
	}

}
