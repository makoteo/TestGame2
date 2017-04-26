package com.tutorial.main;

import java.awt.*;

public class Bomb {
	//FIELDS
	private double x;
	private double y;
	private int r;
	private GamePanel gamepanel;
	private int state = 1;
	
	private Player player;
	
	private int type;
	
	private int alpha = 255;
	private int red = 255;
	private int green = 255;
	
	//CONSTRUCTOR
	
	public Bomb(double x, double y, int type, int state){
		this.type = type; //The type variable is equal to the variable the programmer inputs into the constructor
		this.x = x;
		this.y = y;
		this.state = state;
		
	}
	
	//FUNCTIONS
	
	public double getx(){ return x; }
	public double gety(){ return y; }
	public int gettype(){return type;}
	
	public void update(){
		if(this.state == 2){
			alpha-=3;
			red-=2;
			green-=2;
		}
	}
	public int getAlpha(){return alpha;}
	
	void detonate(){
		state = 2;
	}
	int getState(){
		return this.state;
	}
	public void draw(Graphics2D g){	
		if(state == 1){
			if(Player.currentWeapon == 2){
				g.setColor(Color.RED);
				int fontSize = GamePanel.HEIGHT/30;
				g.setFont(new Font("Century Ghotic", Font.PLAIN, fontSize/*40*/));
				g.drawString("x",(int)x, (int)y);
			}
		}else{
			g.setColor(new Color(red, green, 0, alpha));
			if(this.type == 1){
				g.fillOval((int)x - GamePanel.WIDTH/17, (int)y - GamePanel.WIDTH/15, GamePanel.WIDTH/8, GamePanel.WIDTH/8);
			}else if(this.type == 2){
				g.fillOval((int)x - GamePanel.WIDTH/13, (int)y - GamePanel.WIDTH/12, GamePanel.WIDTH/6, GamePanel.WIDTH/6);
			}else if(this.type == 3){
				g.fillOval((int) ((int)x - GamePanel.WIDTH/5.8), (int) ((int)y - GamePanel.WIDTH/5.2), GamePanel.WIDTH/3, GamePanel.WIDTH/3);
			}else if(this.type == 4){
				g.fillOval((int) ((int)x - GamePanel.WIDTH*1.56), (int) ((int)y - GamePanel.WIDTH*1.56), GamePanel.WIDTH*3, GamePanel.WIDTH*3);
			}
		}
	}
}