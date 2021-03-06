package com.tutorial.main;

import java.awt.*;

public class Bullet {
	
	//FIELDS 
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private Color color1;
	
	private int type;
	
	
	//CONSTRUCTOR
	public Bullet(double angle, int x, int y, int type){
		
		this.x = x;
		this.y = y;
		this.type = type;
		r = 3;
		
		rad = Math.toRadians(angle);
		speed = 12;
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		color1 = Color.WHITE;
		
	}
	
	//FUNCTIONS
	
	public double getx(){ return x; }
	public double gety(){ return y; }
	public double getr(){ return r; }
	
	public double getType(){ return type; }
	public void setType(int i){this.type = i;}
	
	public boolean update(){
		
		if(type == 2){
			color1=Color.RED;
		}
		
		x += dx;
		y += dy;
		
		if(x < -r || x > GamePanel.WIDTH || y < -r || y > GamePanel.HEIGHT){
			return true;
		}
		return false;
	}
	public void draw(Graphics2D g){
		
		g.setColor(color1);
		g.fillOval((int)(x-r), (int) (y-r), 2*r, 2*r);
		
	}
	
}
