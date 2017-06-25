package com.tutorial.main;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Game {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static double width = screenSize.getWidth();
	public static double height = screenSize.getHeight();
	static Rectangle r;
	static int h;
	static int w;
	//public static double width = 1200;
	//public static double height = 675;
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
