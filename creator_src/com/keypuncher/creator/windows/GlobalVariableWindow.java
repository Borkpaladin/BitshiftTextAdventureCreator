package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import com.keypuncher.bitshift.GlobalVariables;

public class GlobalVariableWindow extends JFrame {
	private static final long serialVersionUID = -7615283248013360500L;

	private EditVariableWindow editVariable;

	private Label variableLabel;
	private Label triggerLabel;
	private static List variableList;
	private static List triggerList;
	private ScrollPane variableScroller;
	private ScrollPane triggerScroller;
	private int selectedVariable = 0;
	private int selectedTrigger = 0;

	private Button varNew, varEdit, varDelete;
	private Button trigNew, trigEdit, trigDelete;
	private Button apply, close;

	public GlobalVariableWindow() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		editVariable = new EditVariableWindow();

		variableLabel = new Label("Variables");
		triggerLabel = new Label("Triggers");
		variableList = new List();
		variableList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedVariable = variableList.getSelectedIndex();
			}

		});
		triggerList = new List();
		triggerList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedTrigger = triggerList.getSelectedIndex();
			}

		});
		variableScroller = new ScrollPane();
		variableScroller.add(variableList);
		triggerScroller = new ScrollPane();
		triggerScroller.add(triggerList);
		variableScroller.setPreferredSize(new Dimension(130, 200));
		triggerScroller.setPreferredSize(new Dimension(130, 200));

		varNew = new Button("new");
		varNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalVariables.globalVariables.put("var", 0.0);
				GlobalVariables.defaultGlobalVariables.put("var", 0.0);
				updateVariables();
				
				selectedVariable = indexOf(variableList, "var");
				
				editVariable.edit(false,
						variableList.getItem(selectedVariable));
				
			}
		});
		varEdit = new Button("edit");
		varEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (variableList.getItemCount() > 0) {
					editVariable.edit(false,
							variableList.getItem(selectedVariable));
				}
			}
		});
		varDelete = new Button("delete");
		varDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (variableList.getItemCount() > 0) {
					GlobalVariables.globalVariables.remove(variableList
							.getItem(selectedVariable));
					GlobalVariables.defaultGlobalVariables.remove(variableList
							.getItem(selectedVariable));
					updateVariables();
				}
			}
		});

		trigNew = new Button("new");
		trigNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalVariables.globalTriggers.put("trig", false);
				GlobalVariables.defaultGlobalTriggers.put("trig", false);
				updateTriggers();
				
				selectedTrigger = indexOf(triggerList, "trig");
				
				editVariable.edit(true,
						triggerList.getItem(selectedTrigger));
				
			}
		});
		trigEdit = new Button("edit");
		trigEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (triggerList.getItemCount() > 0) {
					editVariable.edit(true,
							triggerList.getItem(selectedTrigger));
				}
			}
		});
		trigDelete = new Button("delete");
		trigDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (triggerList.getItemCount() > 0) {
					GlobalVariables.globalTriggers.remove(triggerList
							.getItem(selectedTrigger));
					GlobalVariables.defaultGlobalTriggers.remove(triggerList
							.getItem(selectedTrigger));
					updateTriggers();
				}
			}
		});

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
		add(variableLabel, c);
		c.gridx = 3;
		add(triggerLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		add(variableScroller, c);
		c.gridx = 3;
		add(triggerScroller, c);
		c.gridwidth = 1;
		c.gridy = 2;
		c.gridx = 0;
		add(varNew, c);
		c.gridx = 2;
		add(varEdit, c);
		c.gridx = 1;
		add(varDelete, c);
		c.gridx = 3;
		add(trigNew, c);
		c.gridx = 5;
		add(trigEdit, c);
		c.gridx = 4;
		add(trigDelete, c);
		c.gridy = 3;
		c.gridx = 0;
		add(apply, c);
		c.gridx = 1;
		add(close, c);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public static void updateVariables() {
		variableList.removeAll();
		if (GlobalVariables.defaultGlobalVariables.size() > 0) {
			for (String string : GlobalVariables.defaultGlobalVariables
					.keySet()) {
				variableList.add(string);
			}
		}
	}

	public static void updateTriggers() {
		triggerList.removeAll();
		if (GlobalVariables.defaultGlobalTriggers.size() > 0) {
			for (String string : GlobalVariables.defaultGlobalTriggers.keySet()) {
				triggerList.add(string);
			}
		}
	}

	public void apply() {

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

	private int indexOf(List list, String item) {
		String[] items = list.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].equals(item))
				return i;
		}
		return 0;
	}

	public void reload() {
		updateTriggers();
		updateVariables();
	}
	
}
