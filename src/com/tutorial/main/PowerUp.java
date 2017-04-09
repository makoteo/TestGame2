package com.tutorial.main;

import java.awt.*;

public class PowerUp {

	//FIELDS 
	private double x;
	private double y;
	private int r;
	private int type;
	private int alpha = 255;
	
	private int FadeTimer;
	
	//1 == +1 life
	//2 == +1 power
	//3 == +2 power
	//4 == slow down time
	
	private Color color1;
	
	//Constructor
	public PowerUp(int type,double x, double y, int FadeTimer){
		
		this.type = type;
		this.x = x;
		this.y = y;
		this.FadeTimer = FadeTimer;
		if(type == 1){
			color1 = new Color(255, 182, 193, alpha);
			r = 5;
		}
		if(type == 2){
			color1 = new Color(255, 255, 0, alpha);
			r = 5;
		}
		if(type == 3){
			color1 = new Color(255, 255, 0, alpha);
			r = 7;
		}
		if(type == 4){
			color1 = new Color(255, 255, 255, alpha);
			r = 5;
		}
	}
	
	//FUNCTIONS
	public double getx(){ return x;}
	public double gety(){ return y;}
	public int gettype(){ return type;}
	public double getr(){ return r;}
	
	public void update(){
		FadeTimer-=2;
		if(FadeTimer <= 255){
			alpha = FadeTimer;
		}
		if(type == 1){
			color1 = new Color(255, 182, 193, alpha);
		}
		if(type == 2){
			color1 = new Color(255, 255, 0, alpha);
		}
		if(type == 3){
			color1 = new Color(255, 255, 0, alpha);
		}
		if(type == 4){
			color1 = new Color(255, 255, 255, alpha);
		}
	}
	public void setAlpha(int i){this.alpha = i;}
	public int getAlpha(){return alpha;}
	public int getFadeTimer(){return FadeTimer;}
	public void draw(Graphics2D g){
		
		g.setColor(color1);
		g.fillRect((int)(x-r), (int)(y-r), 2*r, 2*r);
		
		g.setStroke(new BasicStroke(3));
		g.setColor(color1.darker());
		g.drawRect((int)(x-r), (int)(y-r), 2*r, 2*r);
		g.setStroke(new BasicStroke(1));
		
	}
}
