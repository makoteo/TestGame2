package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particles {
	
	private int amount;
	private double x;
	private double y;
	private int type;
	private int range;
	private int random1;
	private int random2;
	private int timer = 8;
	private int lifeTimer;
	private int alphaColor;
	private int redHue;
	private int greenHue;
	
	public Particles(double x, double y, int amount, int type, int range){
		
		this.amount = amount;
		this.x = x;
		this.y = y;
		this.type = type;
		this.range = range;
		
		alphaColor = 255;
		redHue = 255;
		greenHue = 255;
		lifeTimer = 7;
		
		this.random1 = new Random().nextInt(range + 1 + range) - range; 
		this.random2 = new Random().nextInt(range + 1 + range) - range; 
		
	}
	
	public void update(){
		timer--;
		lifeTimer--;
		/*Flame Presets*/
		if(type == 1){
			this.y-=5;/*1 by defualt, the higher you go, the more it looks like flames*/
		}else{
			
		}
		if(redHue >= 10){
			redHue-=10;
		}
		if(greenHue >= 30){
			greenHue-=30;
		}
		alphaColor-=20; /*37 by default*/
		/*End here*/
		if(alphaColor < 0){
			alphaColor = 0;
			GamePanel.particles.remove(this);
		}
		if(GamePanel.particles.size() < 20){
			if(timer == 0){
				GamePanel.particles.add(new Particles(x, y, 1, 1, range));
			}
		}

	}
	public double getx(){
		return this.x;
	}
	public double gety(){
		return this.y;
	}
	public int getr(){
		return this.range;
	}
	public void draw(Graphics g){
		g.setColor(new Color(redHue, greenHue, 0, alphaColor));
		g.fillRect((int) (x + random1),(int) (y + random2), GamePanel.WIDTH/350, GamePanel.WIDTH/350);
	}

}
