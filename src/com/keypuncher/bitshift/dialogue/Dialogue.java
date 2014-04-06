package com.keypuncher.bitshift.dialogue;

import java.io.Serializable;
import java.util.ArrayList;

import com.keypuncher.bitshift.Game;
import com.keypuncher.bitshift.console.Console;

public class Dialogue implements Serializable {
	private static final long serialVersionUID = 826882700412282726L;
	public String title;
	public DialogueOption option;
	public DialogueOption[] options;
	public ArrayList<DialogueOption> allDialogueOptions = new ArrayList<DialogueOption>();
	
	private int dialogueChoice = 0;

	public Dialogue(String title, int dialogueChoice) {
		this.dialogueChoice = dialogueChoice;
		if(dialogueChoice < allDialogueOptions.size())
			option = allDialogueOptions.get(dialogueChoice);
		this.title = title;
	}

	public void setDialogueChoice(int dialogueChoice) {
		this.dialogueChoice = dialogueChoice;
		if(dialogueChoice < allDialogueOptions.size())	
			option = allDialogueOptions.get(dialogueChoice);
	}

	public int getDialogueChoice() {
		return dialogueChoice;
	}

	public void displayOptions() {
		Console.newLine();
		for(String s : option.response.split("\n")) {
			Console.println(s);
		}
		Console.newLine();
		for (int q = 0; q < option.options.length; q++) {
			Console.println((q + 1) + ": " + option.options[q].option);
		}
	}

	public void input(char c) {
		if (Character.isDigit(c)) {
			int choice = Integer.parseInt(new String(Character.toString(c))) - 1;
			for (int i = 0; i < option.options.length; i++) {
				if (i == choice) {
					option = option.options[i];
					option.triggerOption();

					if (option.options == null || option.options.length <= 0) {
 						Console.newLine();
						for(String s : option.response.split("\n")) {
							Console.println(s);
						}
						Game.setState(Game.State.GAME);
					} else {
						displayOptions();
					}
					break;
				}
			}
		}
	}
	
	public Dialogue clone() {
		Dialogue clone = new Dialogue(title, dialogueChoice);
		clone.options = options;
		clone.option = option;
		clone.allDialogueOptions = allDialogueOptions;
		return clone;
	}

}
