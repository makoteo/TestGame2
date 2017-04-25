package com.tutorial.main;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Game {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double width = screenSize.getWidth();
	public static double height = screenSize.getHeight();
	//public static double width = 800;
	//public static double height = 450;
	public static void main(String [] args){
		JFrame window = new JFrame("Blobs");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setContentPane(new GamePanel());

		window.pack();
		window.setVisible(true);
		window.setResizable(false);
		window.setSize(new Dimension((int)width, (int)height));
		window.setLocationRelativeTo(null);
	}
	
}
