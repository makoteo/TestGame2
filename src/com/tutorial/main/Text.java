package com.tutorial.main;

import java.awt.*;

public class Text {

	//FIELDS
	private double x;
	private double y;
	private long time;
	private String s;
	
	private int alphaColor;
	
	private long start;
	
	//CONSTRUCTOR
	public Text(double x, double y, long time, String s){
		this.x = x;
		this.y = y;
		this.time = time;
		this.s = s;
		start = System.nanoTime();
		alphaColor  = 255;
	}
	
	public boolean update(){
		alphaColor-= 4;
		y-=2;
		long elapsed = (System.nanoTime() - start) / 1000000;
		if(elapsed > time){
			return true;
		}
		return false;
	}
	public void draw(Graphics2D g){
		g.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		g.setColor(new Color(255,255,255,alphaColor));
		g.drawString(s, (int)x, (int)y);
	}
	
}
