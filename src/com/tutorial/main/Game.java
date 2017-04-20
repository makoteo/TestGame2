package com.tutorial.main;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Game {

	public static void main(String [] args){
		JFrame window = new JFrame("Blobs");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.setSize(new Dimension(606, 629));
		window.setContentPane(new GamePanel());
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
	
}
