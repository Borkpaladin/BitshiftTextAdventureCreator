package com.keypuncher.creator.windows;

import javax.swing.*;

import com.keypuncher.bitshift.GlobalVariables;
import com.keypuncher.bitshift.dialogue.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class DialogueWindow extends JFrame {
	private static final long serialVersionUID = 4332879310898874914L;

	private DialogueOptionWindow editOption;
	private JPanel top, bottom;

	private Label topDialogueLabel;
	private TextField topDialogueTitle;
	private Label topDialogueChoiceLabel;
	private TextField topDialogueChoice;

	private static int selectedDialogue;
	private Button addDialogue, deleteDialogue;
	private static List dialogueList;
	private ScrollPane dialogueScroller;
	private Label dialogueLabel;

	private int selectedDialogueOptions;
	private Button addDialogueOptions, deleteDialogueOptions,
			editDialogueOptions;
	private static List dialogueOptionsList;
	private ScrollPane dialogueOptionsScroller;
	private Label dialogueOptionsLabel;

	private Button apply, close;

	public DialogueWindow() {
		editOption = new DialogueOptionWindow();
		top = new JPanel();
		bottom = new JPanel();

		setUpTop();
		setUpBottom();

		setLayout(new BorderLayout());
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.CENTER);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void reload() {
		updateDialogue();
		updateDialogueOptions();
		updateDialogueOptionsOptions();
	}

	public void updateDialogue() {
		dialogueList.removeAll();
		if (GlobalVariables.dialogue.size() > 0) {
			for (Dialogue dialogue : GlobalVariables.dialogue.values()) {
				dialogueList.add(dialogue.title);
			}
		}
	}

	public static void updateDialogueOptions() {
		dialogueOptionsList.removeAll();
		if (GlobalVariables.dialogue.size() > 0) {
			if (GlobalVariables.dialogue.get(dialogueList
					.getItem(selectedDialogue)).allDialogueOptions.size() > 0) {
				for (DialogueOption dialogue : GlobalVariables.dialogue
						.get(dialogueList.getItem(selectedDialogue)).allDialogueOptions) {
					dialogueOptionsList.add(dialogue.option);
				}
			}
		}
	}

	public static void updateDialogueOptionsOptions() {
		if (GlobalVariables.dialogue.size() > 0) {
			Dialogue dialogue = GlobalVariables.dialogue.get(dialogueList
					.getItem(selectedDialogue));
			for (DialogueOption option : dialogue.allDialogueOptions) {
				ArrayList<DialogueOption> options = new ArrayList<DialogueOption>();
				for (int i : option.optionIDs) {
					if (i < dialogue.allDialogueOptions.size()) {
						options.add(dialogue.allDialogueOptions.get(i));
					}
				}
				DialogueOption[] buffer = new DialogueOption[options.size()];
				option.options = options.toArray(buffer);
			}
		}
	}

	private void setUpTop() {
		top.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		topDialogueLabel = new Label("Dialogue Title: ");
		topDialogueTitle = new TextField("dialogue");

		topDialogueChoiceLabel = new Label("Dialogue Choice: ");
		topDialogueChoice = new TextField("0");

		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		top.add(topDialogueLabel, c);
		c.gridx = 1;
		top.add(topDialogueTitle, c);
		c.gridx = 0;
		c.gridy = 1;
		top.add(topDialogueChoiceLabel, c);
		c.gridx = 1;
		top.add(topDialogueChoice, c);
	}

	private void setUpBottom() {
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		dialogueList = new List();
		dialogueList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedDialogue = dialogueList.getSelectedIndex();
				topDialogueTitle.setText(dialogueList.getItem(selectedDialogue));
				topDialogueChoice.setText(Integer
						.toString(GlobalVariables.dialogue.get(
								dialogueList.getItem(selectedDialogue))
								.getDialogueChoice()));
				updateDialogue();
				updateDialogueOptions();
			}

		});

		dialogueScroller = new ScrollPane();
		dialogueScroller.add(dialogueList);
		dialogueLabel = new Label("Dialogue");
		addDialogue = new Button("new");
		addDialogue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalVariables.dialogue.put("dialogue", new Dialogue(
						"dialogue", 0));
				dialogueList.add("dialogue");
				updateDialogue();
				updateDialogueOptions();
			}
		});

		deleteDialogue = new Button("delete");
		deleteDialogue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialogueList.getItemCount() > 0) {
					GlobalVariables.dialogue.remove(dialogueList
							.getItem(selectedDialogue));
					updateDialogue();
					updateDialogueOptions();
				}
			}
		});

		dialogueScroller.setPreferredSize(new Dimension(130, 200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		bottom.add(dialogueLabel, c);
		c.gridy = 1;
		c.gridwidth = 2;
		bottom.add(dialogueScroller, c);
		c.gridwidth = 1;
		c.gridy = 2;
		bottom.add(addDialogue, c);
		c.gridx = 1;
		c.gridy = 2;
		bottom.add(deleteDialogue, c);

		dialogueOptionsList = new List();
		dialogueOptionsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedDialogueOptions = dialogueOptionsList
						.getSelectedIndex();
				updateDialogueOptions();
			}

		});

		dialogueOptionsScroller = new ScrollPane();
		dialogueOptionsScroller.add(dialogueOptionsList);
		dialogueOptionsLabel = new Label("Dialogue Options");

		addDialogueOptions = new Button("new");
		addDialogueOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GlobalVariables.dialogue.size() > 0) {
					GlobalVariables.dialogue.get(dialogueList
							.getItem(selectedDialogue)).allDialogueOptions
							.add(new DialogueOption("option", "response"));
					dialogueOptionsList.add("option");
					updateDialogueOptions();

					selectedDialogueOptions = indexOf(dialogueOptionsList,
							"option");

					editOption.edit(GlobalVariables.dialogue.get(dialogueList
							.getItem(selectedDialogue)).allDialogueOptions
							.get(selectedDialogueOptions));
				}
			}
		});

		deleteDialogueOptions = new Button("delete");
		deleteDialogueOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialogueOptionsList.getItemCount() > 0) {
					GlobalVariables.dialogue.get(dialogueList
							.getItem(selectedDialogue)).allDialogueOptions
							.remove(selectedDialogueOptions);
					updateDialogueOptions();
				}
			}
		});

		editDialogueOptions = new Button("edit");
		editDialogueOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (GlobalVariables.dialogue.size() > 0
						&& GlobalVariables.dialogue.get(dialogueList
								.getItem(selectedDialogue)).allDialogueOptions
								.size() > 0) {
					editOption.edit(GlobalVariables.dialogue.get(dialogueList
							.getItem(selectedDialogue)).allDialogueOptions
							.get(selectedDialogueOptions));
				}
			}
		});

		dialogueOptionsScroller.setPreferredSize(new Dimension(130, 200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		bottom.add(dialogueOptionsLabel, c);
		c.gridy = 1;
		c.gridwidth = 3;
		bottom.add(dialogueOptionsScroller, c);
		c.gridwidth = 1;
		c.gridy = 2;
		bottom.add(addDialogueOptions, c);
		c.gridx = 3;
		c.gridy = 2;
		bottom.add(deleteDialogueOptions, c);
		c.gridx = 4;
		bottom.add(editDialogueOptions, c);

		apply = new Button("apply");

		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialogueList.getItemCount() > 0) {
					Dialogue dialogue = GlobalVariables.dialogue
							.get(dialogueList.getItem(selectedDialogue));

					dialogue.title = topDialogueTitle.getText();
					dialogue.setDialogueChoice(Integer
							.parseInt(topDialogueChoice.getText()));
					GlobalVariables.dialogue.put(topDialogueTitle.getText(),
							dialogue);

					updateDialogueOptionsOptions();

					if (!dialogueList.getItem(selectedDialogue).equals(
							topDialogueTitle.getText()))
						GlobalVariables.dialogue.remove(dialogueList
								.getItem(selectedDialogue));

					updateDialogue();
					updateDialogueOptions();
				}
			}
		});
		close = new Button("close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}

		});

		c.gridy = 6;
		c.gridwidth = 1;
		c.gridx = 0;
		bottom.add(apply, c);
		c.gridx = 1;
		bottom.add(close, c);

	}

	private int indexOf(List list, String item) {
		String[] items = list.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(item))
				return i;
		}
		return 0;
	}

}
