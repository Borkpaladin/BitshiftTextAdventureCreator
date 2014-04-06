package com.keypuncher.creator;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.channels.FileChannel;

import javax.swing.*;

import com.keypuncher.bitshift.Game;
import com.keypuncher.bitshift.GlobalVariables;
import com.keypuncher.bitshift.GlobalVariablesSaveData;
import com.keypuncher.bitshift.Main;
import com.keypuncher.creator.windows.*;

public class Creator extends JFrame {

	private static final long serialVersionUID = -5283836604868244057L;

	private JPanel topBar;

	public static RoomWindow roomWindow;
	private OverViewWindow overViewWindow;
	private ItemWindow itemWindow;
	private DialogueWindow dialogueWindow;
	private GlobalVariableWindow globalVariableWindow;
	private GameOptionsWindow gameOptionsWindow;

	private MenuBar menuBar;

	private Menu file;
	private MenuItem save, open, export;

	private Button gameOptions;
	private Button roomEditor;
	private Button itemEditor;
	private Button dialogueEditor;
	private Button globalVariables;
	private Button help;

	private Button runGame;

	public Creator() {

		setName("Gamme - Level Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		roomWindow = new RoomWindow();
		overViewWindow = new OverViewWindow();
		itemWindow = new ItemWindow();
		dialogueWindow = new DialogueWindow();
		globalVariableWindow = new GlobalVariableWindow();
		gameOptionsWindow = new GameOptionsWindow();

		menuBar = new MenuBar();

		gameOptions = new Button("Game Options");
		gameOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOptionsWindow.setVisible(true);
			}
		});

		roomEditor = new Button("Rooms");
		roomEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				roomWindow.setVisible(true);
			}
		});

		itemEditor = new Button("Items");
		itemEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				itemWindow.setVisible(true);
			}
		});

		dialogueEditor = new Button("Dialogue");
		dialogueEditor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialogueWindow.setVisible(true);
			}
		});

		globalVariables = new Button("Global Variables");
		globalVariables.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				globalVariableWindow.setVisible(true);
			}
		});
		help = new Button("Help");

		runGame = new Button(">");
		runGame.setForeground(Color.green);
		runGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Main(true);
			}
		});

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		file = new Menu("File");
		save = new MenuItem("Save");
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					Game.save(new File(chooser.getSelectedFile() + ".gce"));
				}
			}
		});
		open = new MenuItem("Open");
		open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int retrival = chooser.showOpenDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					Game.load(chooser.getSelectedFile());
					roomWindow.reload();
					itemWindow.reload();
					dialogueWindow.reload();
					globalVariableWindow.reload();
					gameOptionsWindow.reload();
				}
			}
		});
		export = new MenuItem("Export");
		export.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int retrival = chooser.showSaveDialog(null);
				if (retrival == JFileChooser.APPROVE_OPTION) {
					File dir = chooser.getSelectedFile();
					if (!dir.exists())
						dir.mkdir();
					if (dir.exists()) {
						InputStream stream = getClass().getResourceAsStream(
								"basegame.jar");
						if (stream != null) {
							try {
								OutputStream resStreamOut = null;
								int readBytes;
								byte[] buffer = new byte[4096];
								Game.save(new File(dir + "/data.dat"));

								File gameFile = new File(dir + "/game.jar");
								if (!gameFile.exists()) {
									gameFile.createNewFile();
								}

								resStreamOut = new FileOutputStream(gameFile);
								while ((readBytes = stream.read(buffer)) > 0) {
									resStreamOut.write(buffer, 0, readBytes);
								}
								stream.close();
								resStreamOut.close();
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});

		file.add(save);
		file.add(open);
		file.add(export);

		menuBar.add(file);

		topBar = new JPanel();

		topBar.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.NORTH;
		c.gridx = 0;
		topBar.add(gameOptions, c);
		c.gridx = 1;
		topBar.add(roomEditor, c);
		c.gridx = 2;
		topBar.add(itemEditor, c);
		c.gridx = 3;
		topBar.add(dialogueEditor, c);
		c.gridx = 4;
		topBar.add(globalVariables, c);
		c.gridx = 5;
		topBar.add(help, c);
		c.gridx = 6;
		topBar.add(runGame, c);

		addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				overViewWindow.WIDTH = getWidth();
				overViewWindow.HEIGHT = getHeight();
			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {

			}

			@Override
			public void componentShown(ComponentEvent e) {

			}
		});

		setLayout(new BorderLayout());
		setMenuBar(menuBar);
		add(topBar, BorderLayout.NORTH);
		add(overViewWindow, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		overViewWindow.start();

	}

	public static void main(String[] args) {
		new Creator();
	}

}
