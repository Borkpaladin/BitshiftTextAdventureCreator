package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import com.keypuncher.bitshift.GlobalVariables;
import com.keypuncher.bitshift.interpreter.Command;
import com.keypuncher.bitshift.rooms.Room;

public class RoomWindow extends JFrame {
	private static final long serialVersionUID = -4304533416151009071L;

	private CommandWindow commandWindow;

	private JPanel info;
	private JPanel commands;

	private int selectedCommand;
	private Label commandsLabel;
	private List commandsList;
	private Button newCommand, removeCommand, editCommand;
	private ScrollPane commandsScroller;

	private TextField infoRoomTitle;
	private Label infoRoomLabel, introRoomLabel, firstEntranceRoomLabel,
			outroLabel;
	private TextArea introText;

	private TextArea entranceText;

	private TextArea outroText;

	private int selectedRoom = 0;
	private ScrollPane roomsScroller;
	private List roomsList;
	private Label roomsLabel;
	private Button addRoom, deleteRoom;

	private Button apply, close;

	public RoomWindow() {
		info = new JPanel();
		commands = new JPanel();

		commandWindow = new CommandWindow(this);

		setUpInfoUI();
		setUpCommandsAndRoomsUI();

		setLayout(new BorderLayout());
		add(info, BorderLayout.CENTER);
		add(commands, BorderLayout.WEST);

		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void updateCommands() {
		commandsList.removeAll();
		if (GlobalVariables.rooms.get(selectedRoom).commands.size() > 0) {
			for (Command command : GlobalVariables.rooms.get(selectedRoom).commands) {
				commandsList.add(command.name);
			}
		}
	}
	
	public void reload() {
		roomsList.removeAll();
		for(Room r : GlobalVariables.rooms)
			roomsList.add(r.name);
		updateCommands();
	}

	public void setUpCommandsAndRoomsUI() {
		commands.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		roomsList = new List();
		roomsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedRoom = roomsList.getSelectedIndex();
				introText.setText(arrayListToString(GlobalVariables.rooms
						.get(selectedRoom).intro));
				outroText.setText(arrayListToString(GlobalVariables.rooms
						.get(selectedRoom).description));
				entranceText.setText(arrayListToString(GlobalVariables.rooms
						.get(selectedRoom).firstEntranceIntro));
				infoRoomTitle.setText(GlobalVariables.rooms.get(selectedRoom).name);
				updateCommands();
			}

		});

		roomsScroller = new ScrollPane();
		roomsScroller.add(roomsList);
		roomsLabel = new Label("Rooms");
		addRoom = new Button("new");
		addRoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Room r = new Room();
				GlobalVariables.rooms.add(r);
				infoRoomTitle.setText("room");
				introText.setText("");
				entranceText.setText("");
				outroText.setText("");
				roomsList.removeAll();
				for (Room room : GlobalVariables.rooms) {
					roomsList.add(room.name);
				}
				selectedRoom = indexOf(roomsList, "room");
			}
		});

		deleteRoom = new Button("delete");
		deleteRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomsList.getItemCount() > 0) {
					GlobalVariables.rooms.remove(selectedRoom);
					roomsList.removeAll();
					for (Room room : GlobalVariables.rooms) {
						roomsList.add(room.name);
					}
				}
			}
		});

		roomsScroller.setPreferredSize(new Dimension(130, 200));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		commands.add(roomsLabel, c);
		c.gridy = 1;
		c.gridwidth = 4;
		commands.add(roomsScroller, c);
		c.gridwidth = 1;
		c.gridy = 2;
		commands.add(addRoom, c);
		c.gridx = 1;
		c.gridy = 2;
		commands.add(deleteRoom, c);

		commandsLabel = new Label("Commands");
		commandsScroller = new ScrollPane();
		commandsList = new List();
		commandsList.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				selectedCommand = commandsList.getSelectedIndex();
			}

		});
		newCommand = new Button("new");
		newCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomsList.getItemCount() > 0) {
					commandWindow.setVisible(true);
					commandWindow.setCurrentRoom(GlobalVariables.rooms
							.get(selectedRoom));
				}
			}

		});
		removeCommand = new Button("delete");
		removeCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commandsList.getItemCount() > 0) {
					GlobalVariables.rooms.get(selectedRoom).removeCommand(
							selectedCommand);
					updateCommands();
				}
			}

		});
		editCommand = new Button("edit");
		editCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (commandsList.getItemCount() > 0) {
					commandWindow.setVisible(true);
					commandWindow.setCurrentRoom(GlobalVariables.rooms
							.get(selectedRoom));
					commandWindow.loadCommand(GlobalVariables.rooms
							.get(selectedRoom).commands.get(selectedCommand));
				}
			}

		});
		commandsScroller.add(commandsList);
		commandsScroller.setPreferredSize(new Dimension(130, 200));

		c.gridx = 0;
		c.gridy = 3;
		commands.add(commandsLabel, c);
		c.gridy = 4;
		c.gridwidth = 4;
		commands.add(commandsScroller, c);
		c.gridwidth = 1;
		c.gridy = 5;
		c.gridx = 0;
		commands.add(newCommand, c);
		c.gridx = 1;
		commands.add(removeCommand, c);
		c.gridx = 2;
		commands.add(editCommand, c);

	}

	public void setUpInfoUI() {
		info.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		infoRoomLabel = new Label("Room Title: ");
		infoRoomTitle = new TextField("room");
		infoRoomTitle.setColumns(12);
		introRoomLabel = new Label("Intro");
		firstEntranceRoomLabel = new Label("First Entrance");
		outroLabel = new Label("Description");

		introText = new TextArea(7, 55);
		entranceText = new TextArea(7, 55);
		outroText = new TextArea(7, 55);

		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		info.add(infoRoomLabel, c);
		c.gridx = 1;
		info.add(infoRoomTitle, c);
		c.gridx = 0;
		c.gridy = 1;
		info.add(introRoomLabel, c);
		c.gridy = 2;
		c.gridwidth = 5;
		info.add(introText, c);
		c.gridy = 3;
		info.add(firstEntranceRoomLabel, c);
		c.gridy = 4;
		info.add(entranceText, c);
		c.gridy = 5;
		info.add(outroLabel, c);
		c.gridy = 6;
		info.add(outroText, c);

		apply = new Button("apply");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (roomsList.getItemCount() > 0) {
					Room r = GlobalVariables.rooms.get(selectedRoom);
					r.description = new ArrayList<String>(Arrays
							.asList(outroText.getText().split("\n")));
					r.intro = new ArrayList<String>(Arrays.asList(introText
							.getText().split("\n")));
					r.firstEntranceIntro = new ArrayList<String>(Arrays
							.asList(entranceText.getText().split("\n")));
					r.name = infoRoomTitle.getText();
					roomsList.removeAll();
					for (Room room : GlobalVariables.rooms) {
						roomsList.add(room.name);
					}
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

		c.gridy = 7;
		c.gridwidth = 1;
		info.add(apply, c);
		c.gridx = 1;
		info.add(close, c);

		info.setPreferredSize(new Dimension(460, 500));
	}

	public String arrayListToString(ArrayList<String> as) {
		String a = "";
		for (String s : as) {
			a += s + "\n";
		}
		return a;
	}

	public void edit(Room room) {
		selectedRoom = indexOf(roomsList, room.name);
		introText.setText(arrayListToString(GlobalVariables.rooms
				.get(selectedRoom).intro));
		outroText.setText(arrayListToString(GlobalVariables.rooms
				.get(selectedRoom).description));
		entranceText.setText(arrayListToString(GlobalVariables.rooms
				.get(selectedRoom).firstEntranceIntro));
		infoRoomTitle.setText(GlobalVariables.rooms.get(selectedRoom).name);
		updateCommands();
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
