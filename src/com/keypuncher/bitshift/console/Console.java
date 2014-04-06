package com.keypuncher.bitshift.console;

import java.awt.*;

import com.keypuncher.bitshift.Main;
import com.keypuncher.bitshift.interpreter.ScriptAnalyser;

public class Console {

	private static long[][] text = new long[Main.CHARSPERHEIGHT - 1][Main.CHARSPERWIDTH];
	private static String input = "";
	private static int tick = 0;
	private static boolean cursor = false;

	private static int currentLine = 0;

	public static void input(char c) {
		input += c;
	}

	public static void backspace() {
		if (input.length() > 0)
			input = input.substring(0, input.length() - 1);
	}

	public static String getInput() {
		return input;
	}

	public static void clearInput() {
		input = "";
	}

	public static void update() {
		tick++;
	}

	public static void render(Graphics2D g) {
		for (int y = 0; y < text.length; y++) {
			for (int x = 0; x < text[y].length; x++) {
				g.setColor(new Color((int) (text[y][x] >> 16)));
				g.drawChars(new char[] { (char) (text[y][x] & 0X000000FFFF) },
						0, 1, x * Main.CHARWIDTH, (y + 1) * Main.CHARHEIGHT);
			}
		}

		g.setColor(new Color(0XC0C0C0));

		if (tick % 20 == 0)
			cursor = !cursor;

		g.drawChars((">" + input).toCharArray(), 0,
				(">" + input).toCharArray().length, 0, (Main.CHARSPERHEIGHT)
						* Main.CHARHEIGHT);

		if (cursor) {
			g.fillRect((">" + input).toCharArray().length * Main.CHARWIDTH,
					(Main.CHARSPERHEIGHT) * Main.CHARHEIGHT - 1, 8, 3);
		}

	}

	public static void print(String input) {
		print(input, 0XC0C0C0);
	}

	public static void print(String input, int color) {
		if (input.contains("_script:")) {
			ScriptAnalyser.executeScript(input.substring(input
					.indexOf("_script:") + "_script:".length()));
		} else {
			for (int i = 0; i < text[currentLine].length; i++) {
				if (text[currentLine][i] == 0) {
					for (int z = 0; z < input.toCharArray().length
							&& i + z < text[currentLine].length; z++) {
						text[currentLine][i + z] = input.toCharArray()[z]
								| ((long) (color) << 16);
					}
					break;
				}
			}
		}
	}

	public static void println(String input) {
		newLine();
		print(input);
	}

	public static void println(String input, int color) {
		newLine();
		print(input, color);
	}

	public static void newLine() {
		currentLine++;
		if (currentLine >= text.length) {
			currentLine = text.length - 1;
			for (int i = 0; i < text.length - 1; i++) {
				text[i] = text[i + 1];
			}

			text[currentLine] = new long[Main.CHARSPERWIDTH];
		}
	}

	public static void clear() {
		currentLine = 0;
		text = new long[Main.CHARSPERHEIGHT - 1][Main.CHARSPERWIDTH];
	}

}
