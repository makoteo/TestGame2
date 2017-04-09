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
				g.setFont(new Font("Century Ghotic", Font.PLAIN, 20));
				g.drawString("X",(int)x, (int)y);
			}
		}else{
			g.setColor(new Color(red, green, 0, alpha));
			if(this.type == 1){
				g.fillOval((int)x - 93, (int)y - 105, 200, 200);
			}else if(this.type == 2){
				g.fillOval((int)x - 123, (int)y - 135, 260, 260);
			}else if(this.type == 3){
				g.fillOval((int)x - 293, (int)y - 300, 600, 600);
			}else if(this.type == 4){
				g.fillOval((int)x - 2493, (int)y - 2505, 5000, 5000);
			}
		}
	}
}