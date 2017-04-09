package com.tutorial.main;

import java.awt.*;

public class Enemy {

	//FIELDS
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private int health;
	private int type;
	private int rank;
	
	private Color color1;
	
	private GamePanel gamepanel;
	
	private boolean ready;
	private boolean dead;
	
	private boolean hit;
	private long hitTimer;
	
	private boolean slow;
	
	//CONSTRUCTOR
	
	public Enemy(int type, int rank){
		
		this.type = type;//The type variable is equal to the variable the programmer inputs into the constructor
		this.rank = rank;
		
		//deafult enemy  --- BLUE
		if(type == 1){
			color1 = new Color(0,0,255,/*128*/ 128);
			if(rank == 1){
				speed = 3;
				r = 14;
				health = 1;
			}
			if(rank == 2){
				speed = 3;
				r = 26;
				health = 2;
			}
			if(rank == 3){
				speed = 2;
				r = 50;
				health = 3;
			}
			if(rank == 4){
				speed = 2;
				r = 70;
				health = 4;
			}
			if(rank == 7){
				speed = 3;
				r = 8;
				health = 1;
			}
		}
		//stronger, faster deafult --- RED
		if(type == 2){
			color1 = new Color(255,0,0,128);
			if(rank == 1){
				speed = 4;
				r = 14;
				health = 2;
			}
			if(rank == 2){
				speed = 4;
				r = 26;
				health = 3;
			}
			if(rank == 3){
				speed = 3;
				r = 50;
				health = 3;
			}
			if(rank == 4){
				speed = 3;
				r = 70;
				health = 4;
			}
		}
		//slow , hard to kill
		if(type == 3){
			color1 = new Color(0,255,0,128);
			if(rank == 1){
				speed = 2;
				r = 14;
				health = 5;
			}
			if(rank == 2){
				speed = 2;
				r = 34;
				health = 6;
			}
			if(rank == 3){
				speed = 2;
				r = 60;
				health = 7;
			}
			if(rank == 4){
				speed = 2;
				r = 100;
				health = 8;
			}
		}
		
		x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		y = -r;
		
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);
		
		dx = Math.cos(rad) * speed;
		dy = Math.sin(rad) * speed;
		
		ready = false;
		dead = false;
		
		hit = false;
		hitTimer = 0;
	}
	
	//FUNCTIONS
	
	public double getx(){ return x; }
	public double gety(){ return y; }
	public void setx(int x){ this.x = x; }
	public void sety(int y){ this.y = y; }
	public int getr(){ return r; }
	public boolean isDead() {return dead;}
	
	public void setDead() { dead = true; }
	
	public void setSlow(boolean b){slow = b;}
	
	public int getRank(){return rank;}
	public int getType(){return type;}
	
	public void healthChange(String moreorless, int amount){
		if(moreorless == "+"){
			health += amount;
		}else{
			health -= amount;
		}
		if(health <= 0){
			dead = true;
		}
	}

	public void hit(){
		health--;
		if(health <= 0){
			dead = true;
		}
		hit = true;
		hitTimer = System.nanoTime();
	}
	public void explode(){
		
		if(rank > 1){
			int amount = 0;
			if(type == 1){
				amount = 3;
			}
			if(type == 2){
				amount = 3;
			}
			if(type == 3){
				amount = 4;
			}
			for(int i = 0; i < amount; i++){
				
				Enemy e = new Enemy(getType(), getRank() - 1);
				e.x = this.x;
				e.y = this.y;
				double angle = 0;
				if(!ready){
					angle = Math.random() * 140 + 20;
				}else{
					angle = Math.random() * 360;
				}
				e.rad = Math.toRadians(angle);
				
				GamePanel.enemies.add(e);
				
			}
		}
		
	}
	
	public void update(){
		if(dead){
			GamePanel.enemies.remove(this);
		}
		if(slow){
			x += dx * 0.3;
			y += dy *0.3;
		}else{
			x += dx;
			y += dy;
		}
		
		if(!ready){
			if(x > r && x < GamePanel.WIDTH - r && y > r && y < GamePanel.HEIGHT - r){
				ready = true;
			}
		}
		
		if(x < r && dx < 0){dx = -dx;}
		if(y < r && dy < 0){dy = -dy;}
		if(x > GamePanel.WIDTH -r && dx > 0){ dx = -dx;}
		if(y > GamePanel.HEIGHT -r && dy > 0){ dy = -dy;}
		
		if(hit){
			long elapsed = (System.nanoTime() - hitTimer) / 1000000;
			if(elapsed > 50){
				hit = false;
				hitTimer = 0;
			}
		}
	}
	public void draw(Graphics2D g){
		
		if(hit){
			g.setColor(Color.WHITE);
			g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
			
			g.setStroke(new BasicStroke(5));
			g.setColor(Color.WHITE.darker());
			g.drawOval((int)(x-r), (int)(y-r), 2*r, 2*r);
			g.setStroke(new BasicStroke(1));
		}else{
			g.setColor(color1);
			g.fillOval((int)(x-r), (int)(y-r), 2*r, 2*r);
			
			g.setStroke(new BasicStroke(5));
			g.setColor(color1.darker());
			g.drawOval((int)(x-r), (int)(y-r), 2*r, 2*r);
			g.setStroke(new BasicStroke(1));
		}
		

	}

}
