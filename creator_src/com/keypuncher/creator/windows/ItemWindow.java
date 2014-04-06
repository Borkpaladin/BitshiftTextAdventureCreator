package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.items.*;

public class ItemWindow extends JFrame {
	private static final long serialVersionUID = 1667990856626028861L;

	private JPanel info;
	private JPanel descriptions;

	private int selectedItem;
	private Label itemsLabel;
	private List itemsList;
	private Button newItem, removeItem;
	private ScrollPane itemsScroller;

	private Label nameLabel;
	private Label idLabel;
	private TextField name;
	private TextField id;

	private Label descriptionLabel;
	private Label roomDescriptionLabel;
	private Label placedRoomDescriptionLabel;

	private TextArea description;
	private TextArea roomDescription;
	private TextArea placedRoomDescription;

	private Button apply;
	private Button close;

	public ItemWindow() {
		info = new JPanel();
		descriptions = new JPanel();

		setUpInfoUI();
		setUpDescriptionsUI();

		setLayout(new BorderLayout());
		add(info, BorderLayout.WEST);
		add(descriptions, BorderLayout.CENTER);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);

	}

	public void reload() {
		updateItems();
	}
	
	public void updateItems() {
		itemsList.removeAll();
		if (GlobalVariables.items.size() > 0) {
			for (Item item : GlobalVariables.items.values()) {
				itemsList.add(item.name);
			}
		}
	}

	public void setUpInfoUI() {
		info.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		nameLabel = new Label("Name:");
		name = new TextField(12);
		idLabel = new Label("ID:");
		id = new TextField(12);
		id.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent ke) {
				char c = ke.getKeyChar();
				if ((!(Character.isDigit(c))) && // Only digits
						(c != '\b')) {
					ke.consume();
				}
			}

			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		info.add(nameLabel, c);
		c.gridx = 1;
		info.add(name, c);
		c.gridx = 0;

		c.gridy = 1;
		info.add(idLabel, c);
		c.gridx = 1;
		info.add(id, c);
		c.gridx = 0;

		itemsLabel = new Label("Items");
		itemsScroller = new ScrollPane();
		itemsList = new List();
		itemsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedItem = itemsList.getSelectedIndex();
				name.setText(itemsList.getItem(selectedItem));
				id.setText(Integer.toString(GlobalVariables.items.get(itemsList.getItem(selectedItem)).id));
				description.setText(arrayToString(
						GlobalVariables.items.get(itemsList.getItem(selectedItem)).description));
				roomDescription.setText(arrayToString(
						GlobalVariables.items.get(itemsList.getItem(selectedItem)).roomDescription));
				placedRoomDescription.setText(arrayToString(
						GlobalVariables.items.get(itemsList.getItem(selectedItem)).placedRoomDescription));
				
				updateItems();
			}

		});
		itemsScroller.add(itemsList);
		itemsScroller.setPreferredSize(new Dimension(130, 375));

		c.gridy = 2;
		info.add(itemsLabel, c);
		c.gridy = 3;
		c.gridwidth = 2;
		info.add(itemsScroller, c);
		c.gridwidth = 1;

		newItem = new Button("new");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GlobalVariables.items.put("item", new Item(
						new String[] {},
						new String[] {},
						new String[] {},
						"item",
						0));
				itemsList.add("item");
				name.setText("item");
				id.setText("0");
				updateItems();
				selectedItem = indexOf(itemsList, "item");
			}
		});
		removeItem = new Button("delete");
		removeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (itemsList.getItemCount() > 0) {
					GlobalVariables.items.remove(itemsList
							.getItem(selectedItem));
					updateItems();
				}
			}
		});

		c.gridy = 4;
		info.add(newItem, c);
		c.gridx = 1;
		info.add(removeItem, c);
	}

	public void setUpDescriptionsUI() {
		descriptions.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		descriptionLabel = new Label("Description");
		roomDescriptionLabel = new Label("Room Description");
		placedRoomDescriptionLabel = new Label("Placed Room Description");

		description = new TextArea(7, 55);
		roomDescription = new TextArea(7, 55);
		placedRoomDescription = new TextArea(7, 55);

		apply = new Button("apply");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (itemsList.getItemCount() > 0) {
					GlobalVariables.items.remove(itemsList.getItem(selectedItem));
					GlobalVariables.items.put(
							name.getText(),
							new Item(
									description.getText().split("\n"),
									roomDescription.getText().split("\n"),
									placedRoomDescription.getText().split("\n"),
									name.getText(), Integer.parseInt(id
											.getText())));
					updateItems();
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

		c.gridwidth = 8;
		descriptions.add(descriptionLabel, c);
		c.gridy = 1;
		descriptions.add(description, c);
		c.gridy = 2;
		descriptions.add(roomDescriptionLabel, c);
		c.gridy = 3;
		descriptions.add(roomDescription, c);
		c.gridy = 4;
		descriptions.add(placedRoomDescriptionLabel, c);
		c.gridy = 5;
		descriptions.add(placedRoomDescription, c);
		c.gridy = 6;
		c.gridwidth = 1;
		descriptions.add(apply, c);
		c.gridx = 1;
		descriptions.add(close, c);
	}
	
	public String arrayToString(String[] as) {
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
	
}
