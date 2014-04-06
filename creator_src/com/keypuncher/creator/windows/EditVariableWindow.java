package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.keypuncher.bitshift.GlobalVariables;

public class EditVariableWindow extends JFrame {
	private static final long serialVersionUID = -5336985186970624342L;
	private String key;
	private boolean bool;

	private Label nameLabel;
	private Label valueLabel;
	private TextField value;
	private TextField name;

	private Button apply;
	private Button close;

	public EditVariableWindow() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		nameLabel = new Label("Name: ");
		valueLabel = new Label("Value: ");
		value = new TextField(30);
		name = new TextField(30);

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

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		add(nameLabel, c);
		c.gridx = 1;
		c.gridwidth = 4;
		add(name, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		add(valueLabel, c);
		c.gridx = 1;
		c.gridwidth = 4;
		add(value, c);
		c.gridwidth = 1;

		c.gridy = 2;
		c.gridx = 0;
		add(apply, c);
		c.gridx = 1;
		add(close, c);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void apply() {
		if (bool) {
			GlobalVariables.defaultGlobalTriggers.remove(key);
			GlobalVariables.globalTriggers.remove(key);
			GlobalVariables.defaultGlobalTriggers.put(name.getText(),
					Boolean.parseBoolean(value.getText()));
			GlobalVariables.globalTriggers.put(name.getText(),
					Boolean.parseBoolean(value.getText()));
		} else {
			GlobalVariables.defaultGlobalVariables.remove(key);
			GlobalVariables.globalVariables.remove(key);
			GlobalVariables.defaultGlobalVariables.put(name.getText(),
					Double.parseDouble(value.getText()));
			GlobalVariables.globalVariables.put(name.getText(),
					Double.parseDouble(value.getText()));
		}
		GlobalVariableWindow.updateTriggers();
		GlobalVariableWindow.updateVariables();
		setVisible(false);
	}

	public void edit(boolean bool, String key) {
		name.setText(key);
		if (bool) {
			this.value.setText(GlobalVariables.defaultGlobalTriggers.get(key)
					.toString());
		} else {
			this.value.setText(GlobalVariables.defaultGlobalVariables.get(key)
					.toString());

		}
		this.bool = bool;
		this.key = key;
		setVisible(true);
	}

}
