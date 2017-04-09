package com.tutorial.main;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Game {

	public static void main(String [] args){
		
		JFrame window = new JFrame("First Game");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setContentPane(new GamePanel());
		
		window.pack();
		window.setVisible(true);
		window.setResizable(false);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//window.setSize(new Dimension(606, 629));
	
		window.setLocationRelativeTo(null);
	}
	
}
