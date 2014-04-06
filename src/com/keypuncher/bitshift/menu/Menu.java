package com.keypuncher.bitshift.menu;

import java.awt.event.*;

import com.keypuncher.bitshift.console.*;

public abstract class Menu {

	public static final MainMenu mainMenu = new MainMenu();
	public static final LoadMenu loadMenu = new LoadMenu();
	public static final AboutMenu aboutMenu = new AboutMenu();
	
	private String[] info;
	
	public Menu(String[] info) {
		this.info = info;
	}
	
	public Menu() {
		
	}
	
	public void printInfo() {
		for(String output : info) {
			Console.println(output);
		}
	}
	
	public abstract void input(KeyEvent e);

}
