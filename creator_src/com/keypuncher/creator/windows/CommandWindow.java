package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.keypuncher.bitshift.interpreter.Command;
import com.keypuncher.bitshift.rooms.*;

public class CommandWindow extends JFrame {
	private static final long serialVersionUID = 1405394724282205642L;

	private Room room;

	private Label nameLabel, arg0Label, arg1Label, arg2Label, scriptLabel;
	private TextField arg0, arg1, arg2, name;
	private TextArea script;
	private Button apply, close;

	private Command currentCommand;

	public CommandWindow(final RoomWindow roomWindow) {
		nameLabel = new Label("Name:");
		arg0Label = new Label("arg0");
		arg1Label = new Label("arg1");
		arg2Label = new Label("arg2");
		scriptLabel = new Label("Script:");

		name = new TextField(12);
		arg0 = new TextField(12);
		arg1 = new TextField(12);
		arg2 = new TextField(12);

		script = new TextArea(8, 45);

		apply = new Button("apply");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentCommand == null)
					room.addCommand(name.getText(), script.getText(),
							arg0.getText(), arg1.getText(), arg2.getText());
				else {
					currentCommand.name = name.getText();
					currentCommand.script = script.getText();
					currentCommand.arg0 = arg0.getText();
					currentCommand.arg1 = arg1.getText();
					currentCommand.arg2 = arg2.getText();
					room.addCommandKeyWords(arg0.getText(), arg1.getText(), arg2.getText());
				}
				
				roomWindow.updateCommands();
				setVisible(false);
				currentCommand = null;
			}

		});
		close = new Button("close");
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentCommand = null;
				setVisible(false);
			}

		});

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		c.gridx = 0;
		add(nameLabel, c);
		c.gridx = 1;
		add(name, c);

		c.gridy = 1;
		c.gridx = 0;
		add(arg0Label, c);
		c.gridx = 1;
		add(arg1Label, c);
		c.gridx = 2;
		add(arg2Label, c);

		c.gridy = 2;
		c.gridx = 0;
		add(arg0, c);
		c.gridx = 1;
		add(arg1, c);
		c.gridx = 2;
		add(arg2, c);

		c.gridy = 3;
		c.gridx = 0;
		add(scriptLabel, c);
		c.gridy = 4;
		c.gridwidth = 8;
		add(script, c);
		c.gridwidth = 1;
		c.gridy = 5;
		c.gridx = 0;
		add(apply, c);
		c.gridx = 1;
		add(close, c);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void setCurrentRoom(Room room) {
		this.room = room;
	}

	public void loadCommand(Command command) {
		name.setText(command.name);
		arg0.setText(command.arg0);
		arg1.setText(command.arg1);
		arg2.setText(command.arg2);
		script.setText(command.script);
		currentCommand = command;

	}

}
