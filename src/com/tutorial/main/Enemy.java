package com.tutorial.main;

import java.awt.*;

import com.tutorial.main.Player.SHOOTERTYPE;

public class Enemy {

	//FIELDS
	private double x;
	private double y;
	private int r;
	
	private double dx;
	private double dy;
	private double rad;
	private double speed;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	
	private int health;
	private int type;
	private int rank;
	
	private Color color1;
	
	private boolean ready;
	private boolean dead;
	
	private boolean hit;
	private long hitTimer;
	
	private boolean slow;
	private int Timer = -1;
	
	private boolean selected = false;
	
	private int id = 0;
	
	//TRAITS
	/*
	Speed
	Health
	HealthRegen
	Size
	 */
	//CONSTRUCTOR
	
	public Enemy(int type, int rank){
		
		this.type = type;//The type variable is equal to the variable the programmer inputs into the constructor
		this.rank = rank;
		
		//deafult enemy  --- BLUE
		if(type == 1){
			color1 = new Color(0,0,255,/*128*/ 128);
			if(rank == 1){
				speed = GamePanel.WIDTH/533;
				r = GamePanel.WIDTH/114;
				health = 1;
			}
			if(rank == 2){
				speed = GamePanel.WIDTH/533;
				r = GamePanel.WIDTH/61;
				health = 2;
			}
			if(rank == 3){
				speed = GamePanel.WIDTH/800;
				r = GamePanel.WIDTH/32;
				health = 3;
			}
			if(rank == 4){
				speed = GamePanel.WIDTH/800;
				r = GamePanel.WIDTH/23;
				health = 4;
			}
		}
		//stronger, faster deafult --- RED
		if(type == 2){
			color1 = new Color(255,0,0,128);
			if(rank == 1){
				speed = GamePanel.WIDTH/400;
				r = GamePanel.WIDTH/114;
				health = 2;
			}
			if(rank == 2){
				speed = GamePanel.WIDTH/400;
				r = GamePanel.WIDTH/63;
				health = 3;
			}
			if(rank == 3){
				speed = GamePanel.WIDTH/533;
				r = GamePanel.WIDTH/32;
				health = 3;
			}
			if(rank == 4){
				speed = GamePanel.WIDTH/533;
				r = GamePanel.WIDTH/23;
				health = 4;
			}
		}
		//slow , hard to kill
		if(type == 3){
			color1 = new Color(0,255,0,128);
			if(rank == 1){
				speed = GamePanel.WIDTH/800;
				r = GamePanel.WIDTH/114;
				health = 5;
			}
			if(rank == 2){
				speed = GamePanel.WIDTH/800;
				r = GamePanel.WIDTH/47;
				health = 6;
			}
			if(rank == 3){
				speed = GamePanel.WIDTH/800;
				r = GamePanel.WIDTH/27;
				health = 7;
			}
			if(rank == 4){
				speed = GamePanel.WIDTH/800;
				r = GamePanel.WIDTH/16;
				health = 8;
			}
		}
		
		x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
		y = -r;
		
		double angle = Math.random() * 140 + 20;
		rad = Math.toRadians(angle);
		if(this.Timer < 1){
			dx = Math.cos(rad) * speed;
			dy = Math.sin(rad) * speed;
		}
		ready = false;
		dead = false;
		
		hit = false;
		hitTimer = 0;
		
		firing = false;
		firingTimer = System.nanoTime();
		firingDelay = 2500;
		
		GamePanel.EnemyId += 1;
		this.id = GamePanel.EnemyId;
	}
	
	//FUNCTIONS
	
	public void setSelected(boolean b){
		selected = b;
	}
	public boolean getSelected(){
		return selected;
	}
	public double getx(){ return x; }
	public double gety(){ return y; }
	public void setx(int x){ this.x = x; }
	public void sety(int y){ this.y = y; }
	public int getr(){ return r; }
	public boolean isDead() {return dead;}
	
	public void setDead() { dead = true; }
	
	public void setSlow(boolean b){slow = b;}
	public boolean getSlow(){return slow;}
	
	public int getid(){ return id; }
	public void setid(int i){ this.id = i; }
	
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
				amount = 2;
			}
			for(int i = 0; i < amount; i++){
				
				Enemy e = new Enemy(getType(), getRank() - 1);
				e.x = this.x;
				e.y = this.y;
				GamePanel.EnemyId += 1;
				this.id = GamePanel.EnemyId;
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
	public void setTimer(int i){
		this.Timer = i;
	}
	public int getTimer(){
		return this.Timer;
	}
	public void update(){
		if(dead){
			GamePanel.enemies.remove(this);
		}
		if(!(this.Timer<=0)){
			this.Timer--;
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
		if(type==1){
			if(x < r && dx < 0){dx = -dx;}
			if(y < r && dy < 0){dy = -dy;}
			if(x > GamePanel.WIDTH -r && dx > 0){ dx = -dx;}
			if(y > GamePanel.HEIGHT -r && dy > 0){ dy = -dy;}
		}else if(type==2){
			if(this.Timer >= 1){
				if(this.Timer == 50){	
					double angle = 0;
					angle = Math.random() * 360;
					this.rad = Math.toRadians(angle);
				}
				dx = Math.cos(rad) * 2;
				dy = Math.sin(rad) * 2;
				if(x < r && dx < 0){dx = -dx;}
				if(y < r && dy < 0){dy = -dy;}
				if(x > GamePanel.WIDTH -r && dx > 0){ dx = -dx;}
				if(y > GamePanel.HEIGHT -r && dy > 0){ dy = -dy;}
			}else{
				dx = GamePanel.getPlayerX() - this.x;
				dy = GamePanel.getPlayerY() - this.y;
				float norm = (float) Math.sqrt(dx * dx + dy * dy);
				dx *= (this.speed / norm);
				dy *= (this.speed / norm);
			}
		}else{
			if(x < r && dx < 0){dx = -dx;}
			if(y < r && dy < 0){dy = -dy;}
			if(x > GamePanel.WIDTH -r && dx > 0){ dx = -dx;}
			if(y > GamePanel.HEIGHT -r && dy > 0){ dy = -dy;}
			long elapsed = (System.nanoTime() - firingTimer) / 1000000;
			if(elapsed > firingDelay){
				firingTimer = System.nanoTime();
				
				//System.out.println(Menu.mxg);
				//System.out.println(Menu.myg);
				int px = GamePanel.getPlayerX();
				int py = GamePanel.getPlayerY();

				double angle = (180 - 90 -(Math.toDegrees(Math.atan((double)Math.abs(px - this.x) / ((double)Math.abs(py - this.y))))));
				//System.out.println(Math.abs(Menu.mxg - px) + "///" + Math.abs(Menu.myg - py));
				
				if((px >= this.x) && (this.y >= py)){
					angle = 360 - angle;
				}else if((px >= this.x) && (this.y <= py)){
					
				}else if((px <= this.x) && (this.y <= py)){
					angle = 180 - angle;
				}else if((px <= this.x) && (this.y >= py)){
					angle = 180 + angle;
				}
				GamePanel.bullets.add(new Bullet(angle, (int)this.x, (int)this.y, 2));
			}
		}
		
		
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
		if((selected) && (Player.currentWeapon == 3)){
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(5));
			g.drawRect((int)(x-r), (int)(y-r), 2*r, 2*r);
			g.setStroke(new BasicStroke(1));
		}

	}

}
