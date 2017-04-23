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
	
	private int following = 0;
	
	private Color color1;
	
	private int type;
	/*Types:
	1. Basic -- done
	2. Enemy Bullets -- done
	3. Follower -- done
	4. Laser
	5. Canon
	6. Bouncer -- done
	Bomb is a separate thing
	*/
	private float rotation = 0;
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
	
	public double getRot(){ return rotation; }
	public void setRot(float f){this.rotation = f;}
	
	public double getFol(){ return following; }
	public void setFol(int i){this.following = i;}
	
	public void setDx(double d){this.dx = d;}
	public void setDy(double d){this.dy = d;}
	public double getDx(){ return dx; }
	public double getDy(){ return dy; }
	
	public boolean update(){
		if(type == 1){
			color1=Color.WHITE;
			r=3;
			GamePanel.setFiringDelay(200);
		}
		
		if(type == 2){
			color1=Color.RED;
			r=5;
		}
		if(type == 3){
			color1=Color.GREEN;
			r=5;
			GamePanel.setFiringDelay(1500);
		}
		
		if(type == 6){
			color1=Color.YELLOW;
			r=3;
			GamePanel.setFiringDelay(200);
		}
		if(this.type == 3){
			for(int i = 0; i < GamePanel.enemies.size(); i++){
				if(GamePanel.enemies.get(i).getSelected()){
					if(GamePanel.player.getFiring()){
						double bx = this.getx();
						double by = this.gety();
						double br = this.getr();		
						double px = GamePanel.player.getx();
						double py = GamePanel.player.gety();
						double pr = GamePanel.player.getr();
							
						double dx = bx - px;
						double dy = by - py;
						double dist = Math.sqrt(dx * dx + dy*  dy);
							
						if(dist < br + pr){
							this.setFol(GamePanel.enemies.get(i).getid());
						}
						break;
					}
				}
			}
		}
		//GET FOLLOWING
		if(type == 3){
			for(int j = 0; j < GamePanel.enemies.size(); j++){
				if(this.following == GamePanel.enemies.get(j).getid()){
					dx = (GamePanel.enemies.get(j).getx()) - (this.getx());
					dy = (GamePanel.enemies.get(j).gety()) - (this.gety());
					float norm = (float) Math.sqrt(dx * dx + dy * dy);
					dx *= (this.speed / norm);
					dy *= (this.speed / norm);
					break;
				}else{
					if(GamePanel.enemies.get(j).isDead()){
						
					}
				}
			}
		}
		if(type == 6){
			if(x < r && dx < 0){dx = -dx;}
			if(y < r && dy < 0){dy = -dy;}
			if(x > GamePanel.WIDTH -r && dx > 0){ dx = -dx;}
			if(y > GamePanel.HEIGHT -r && dy > 0){ dy = -dy;}
		}
		x += dx;
		y += dy;
		
		if(x < -r -20 || x > GamePanel.WIDTH -r +20 || y < -r -20 || y > GamePanel.HEIGHT -r +20){
			return true;
		}
		return false;
	}
	public void draw(Graphics2D g){
		
		g.setColor(color1);
		g.fillOval((int)(x-r), (int) (y-r), 2*r, 2*r);
		
	}
	
}
