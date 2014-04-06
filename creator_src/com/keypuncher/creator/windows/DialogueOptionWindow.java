package com.keypuncher.creator.windows;

import javax.swing.*;

import com.keypuncher.bitshift.dialogue.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogueOptionWindow extends JFrame {
	private static final long serialVersionUID = 4332879310898874914L;

	private JPanel top, bottom;

	private Label optionTextLabel;
	private TextField optionText;
	private Label responseTextLabel;
	private TextArea responseText;

	private TextArea script;
	private Label scriptLabel;

	private TextArea dialogueOptions;
	private Label dialogueOptionsLabel;

	private Button apply, close;

	private DialogueOption option;

	public DialogueOptionWindow() {
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

	private void setUpTop() {
		top.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		optionTextLabel = new Label("Option Text: ");
		optionText = new TextField("option");
		optionText.setColumns(40);

		responseTextLabel = new Label("Response Text: ");
		responseText = new TextArea("response");
		responseText.setColumns(40);
		responseText.setRows(10);

		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		top.add(optionTextLabel, c);
		c.gridx = 1;
		top.add(optionText, c);
		c.gridx = 0;
		c.gridy = 1;
		top.add(responseTextLabel, c);
		c.gridx = 1;
		top.add(responseText, c);
	}

	private void setUpBottom() {
		bottom.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		script = new TextArea(12, 40);
		scriptLabel = new Label("Script");

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		bottom.add(scriptLabel, c);
		c.gridy = 1;
		c.gridwidth = 2;
		bottom.add(script, c);

		dialogueOptions = new TextArea(12, 10);
		dialogueOptionsLabel = new Label("Dialogue Options");

		dialogueOptions.setPreferredSize(new Dimension(130, 200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		bottom.add(dialogueOptionsLabel, c);
		c.gridy = 1;
		c.gridwidth = 3;
		bottom.add(dialogueOptions, c);
		c.gridwidth = 1;
		c.gridy = 2;

		apply = new Button("apply");

		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				apply();
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

	public void edit(DialogueOption option) {
		this.option = option;
		script.setText(arrayListToString(option.script));
		dialogueOptions.setText(arrayListIntegerToString(option.optionIDs));
		responseText.setText(option.response);
		optionText.setText(option.option);
		setVisible(true);
	}

	private void apply() {
		option.script.clear();
		for (String s : script.getText().split("\n")) {
			option.script.add(s);
		}
		option.optionIDs.clear();
		if (dialogueOptions.getText().length() > 0) {
			for (String s : dialogueOptions.getText().split("\n")) {
				option.optionIDs.add(Integer.parseInt(s));
			}
		}
		option.response = responseText.getText();
		option.option = optionText.getText();
		DialogueWindow.updateDialogueOptions();
		DialogueWindow.updateDialogueOptionsOptions();
	}

	public String arrayListIntegerToString(ArrayList<Integer> as) {
		String a = "";
		for (Integer s : as) {
			a += s.toString() + "\n";
		}
		return a;
	}

	public String arrayListToString(ArrayList<String> as) {
		String a = "";
		for (String s : as) {
			a += s + "\n";
		}
		return a;
	}

}
