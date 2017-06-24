package com.tutorial.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Particles {
	
	private int amount;
	private int x;
	private int y;
	private int type;
	private int range;
	private int random1;
	private int random2;
	private int timer = 8;
	private int lifeTimer;
	private int alphaColor;
	
	public Particles(int x, int y, int amount, int type, int range){
		
		this.amount = amount;
		this.x = x;
		this.y = y;
		this.type = type;
		this.range = range;
		
		alphaColor = 255;
		lifeTimer = 7;
		
		this.random1 = new Random().nextInt(range + 1 + range) - range; 
		this.random2 = new Random().nextInt(range + 1 + range) - range; 
		
	}
	
	public void update(){
		timer--;
		this.y--;
		lifeTimer--;
		alphaColor-=37;
		if(alphaColor < 1){
			alphaColor = 0;
			GamePanel.particles.remove(this);
		}
		if(GamePanel.particles.size() < 20){
			if(timer == 0){
				GamePanel.particles.add(new Particles(x, y, 1, 1, range));
			}
		}

	}
	public int getx(){
		return this.x;
	}
	public int gety(){
		return this.y;
	}
	public int getr(){
		return this.range;
	}
	public void draw(Graphics g){
		g.setColor(new Color(255, 255, 0, alphaColor));
		g.fillRect(x + random1, y + random2, GamePanel.WIDTH/350, GamePanel.WIDTH/350);
	}

}
