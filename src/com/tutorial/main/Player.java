package com.tutorial.main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {

	//Fields
	private int x;
	private int y;
	private int r;
	
	private int dx;
	private int dy;
	private int speed;
	
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private boolean firing;
	private long firingTimer;
	private long firingDelay;
	public static int currentWeapon = 1;
	
	private boolean recovering;
	private long recoveryTimer;
	
	private int lives;
	private Color color1;
	private Color color2;
	
	private BufferedImage HatImg = null;
	
	GamePanel gamepanel;
	
	public static int powerLevelChecker = 0;
	
	public enum SHOOTERTYPE {
		oneGun,
		twoGun,
		threeGun
	};
	
	public SHOOTERTYPE shooterType = SHOOTERTYPE.oneGun;
	
	private int score = 0;
	
	private int powerLevel;
	private int power;
	private int [] requiredPower = {
			
		1,2,2,2,3,3,3,3,3,3,4,4,4,4,4,4,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9
			
	};
	
	//Constructor
	public Player(GamePanel gamepanel){
		this.gamepanel = gamepanel;
		x = GamePanel.WIDTH / 2;
		y = GamePanel.HEIGHT / 2;
		r = GamePanel.WIDTH/200;
		
		dx = 0;
		dy = 0;
		speed = GamePanel.WIDTH/200;
		
		lives = 3;
		
		firing = false;
		firingTimer = System.nanoTime();
		setFiringDelay(200);
		
		recovering = false;
		recoveryTimer = 0;
	}
	
	//Functions
	
	public int getx(){ return x;}
	public int gety(){ return y;}
	public void setx(int i){x=i;}
	public void sety(int i){y=i;}
	public int getr(){ return r;}
	public int getscore(){return score;}
	
	
	public int getLives(){return lives;}
	
	public int getCurrentWeapon(){return currentWeapon;}
	
	public void setCurrentWeapon(int i){currentWeapon = i;}
	
	public boolean isrecovering(){ return recovering;}
	public void loseLife(){
		lives--;
		recovering = true;
		recoveryTimer = System.nanoTime();
	}
	public void gainlife(){
		lives++;
	}
	
	public void setLives(int i){
		lives = i;
	}
	
	public void addScore(int i){
		score += i;
	}
	public void setScore(int i){
		score = i;
	}
	public void setRecovering(boolean b){
		recovering=b;
	}
	
	public void incresePower(int i){
		power += i;
		if(power >= requiredPower[powerLevel]){
			power -= requiredPower[powerLevel];
			powerLevel++;
			powerLevelChecker++;
		}
	}
	
	public int getPowerLevel(){return powerLevel;}
	public int getPower(){return power;}
	public int getRequiredPower(){return requiredPower[powerLevel];}
	
	public void setPowerLevel(int i){powerLevel = i;}//should be 0
	public void setPower(int i){power = i;}//should be 0
	public void setPowerLevelChecker(int i){powerLevelChecker = i;}//should be 0
	
	public void setLeft(boolean b){
		left = b;
	}
	public void setRight(boolean b){
		right = b;
	}
	public void setUp(boolean b){
		up = b;
	}
	public void setDown(boolean b){
		down = b;
	}
	public void setFiring(boolean b){
		firing = b;
	}
	public boolean getFiring(){
		return firing;
	}
	public long getFiringTimer(){
		return firingTimer;
	}
	public void setFiringTimer(long l){
		firingTimer = l;
	}
	public void update(){
		if(GamePanel.CharColorPage == 1){//If on first page
			if(GamePanel.CharColorSelected == 0){
				color1 = Color.WHITE;
				color2 = Color.RED.darker();
			}else if(GamePanel.CharColorSelected == 1){
				color1 = Color.BLUE;
				color2 = Color.RED.darker();
			}else if(GamePanel.CharColorSelected == 5){
				color1 = Color.RED;
				color2 = Color.RED.darker();
			}else{
				color1 = Color.DARK_GRAY;
				color2 = Color.DARK_GRAY;
			}
		}else if(GamePanel.CharColorPage == 2){
			if(GamePanel.CharColorSelected == 6){
				color1 = Color.YELLOW;
				color2 = Color.RED.darker();
			}else if(GamePanel.CharColorSelected == 7){
				color1 = Color.GREEN;
				color2 = Color.RED.darker();
			}else if(GamePanel.CharColorSelected == 8){
				color1 = Color.BLACK;
				color2 = Color.RED.darker();
			}else{
				color1 = Color.DARK_GRAY;
				color2 = Color.DARK_GRAY;
			}
		}
		if(GamePanel.CharHatPage == 1){
			if(GamePanel.CharHatSelected == 0){
				HatImg = null;
			}else if(GamePanel.CharHatSelected == 1){
				HatImg = GamePanel.Hats_ClassicalHat;
			}else if(GamePanel.CharHatSelected == 2){
				HatImg = GamePanel.Hats_Wizard;
			}else{
				
			}
		}
		if(left){
			dx = -speed;
		}
		if(right){
			dx = speed;
		}
		if(up){
			dy = -speed;
		}
		if(down){
			dy = speed;
		}
		
		x += dx;
		y += dy;
		
		if(x < r) x = r;
		if(y < r) y = r;
		if(x > GamePanel.WIDTH - r) x = GamePanel.WIDTH - r;
		if(y > GamePanel.HEIGHT - r) y = GamePanel.HEIGHT - r;

		dx = 0;
		dy = 0;
		//Firing
		
		if(firing){
			if(!recovering){
				long elapsed = (System.nanoTime() - firingTimer) / 1000000;
				if(elapsed > getFiringDelay()){
					firingTimer = System.nanoTime();
					
					//System.out.println(Menu.mxg);
					//System.out.println(Menu.myg);
					int px = getx();
					int py = gety();
	
					double angle = (180 - 90 -(Math.toDegrees(Math.atan((double)Math.abs(Menu.mxg - px) / ((double)Math.abs(Menu.myg - py))))));
					//System.out.println(Math.abs(Menu.mxg - px) + "///" + Math.abs(Menu.myg - py));
					
					if((Menu.mxg >= px) && (py >= Menu.myg)){
						angle = 360 - angle;
					}else if((Menu.mxg >= px) && (py <= Menu.myg)){
						
					}else if((Menu.mxg <= px) && (py <= Menu.myg)){
						angle = 180 - angle;
					}else if((Menu.mxg <= px) && (py >= Menu.myg)){
						angle = 180 + angle;
					}
					if(currentWeapon == 1){
						if(shooterType == SHOOTERTYPE.oneGun){
							GamePanel.bullets.add(new Bullet(angle, x, y,  1));
						}
						else if(shooterType == SHOOTERTYPE.twoGun){
							int bulletGap = 100;
							int nx1 = (int)(x + ((double)Math.cos(Math.toRadians(angle + bulletGap)) * 5));
							int ny1 = (int)(y + ((double)Math.sin(Math.toRadians(angle + bulletGap)) * 5));	
							int nx2 = (int)(x + ((double)Math.cos(Math.toRadians(angle - bulletGap)) * 5));
							int ny2 = (int)(y + ((double)Math.sin(Math.toRadians(angle - bulletGap)) * 5));	
							
							GamePanel.bullets.add(new Bullet(angle/*90 = down || 0 = right || 180 = left*/, nx1, ny1, 1));
							GamePanel.bullets.add(new Bullet(angle/*90 = down || 0 = right || 180 = left*/, nx2, ny2, 1));
						}
						else if(shooterType == SHOOTERTYPE.threeGun){   
							int bulletGap = 100;
							int nx1 = (int)(x + ((double)Math.cos(Math.toRadians(angle + bulletGap)) * 5));
							int ny1 = (int)(y + ((double)Math.sin(Math.toRadians(angle + bulletGap)) * 5));	
							int nx2 = (int)(x + ((double)Math.cos(Math.toRadians(angle - bulletGap)) * 5));
							int ny2 = (int)(y + ((double)Math.sin(Math.toRadians(angle - bulletGap)) * 5));	
							GamePanel.bullets.add(new Bullet(angle+5/*90 = down || 0 = right || 180 = left*/, nx1, ny1, 1));
							GamePanel.bullets.add(new Bullet(angle-5/*90 = down || 0 = right || 180 = left*/, nx2, ny2, 1));
							GamePanel.bullets.add(new Bullet(angle/*90 = down || 0 = right || 180 = left*/, x, y, 1));
						}
					}else if(currentWeapon == 3){
						if(gamepanel.getRocketAmount() > 0){
							GamePanel.bullets.add(new Bullet(angle, x, y, 3));
							gamepanel.setRocketAmount(gamepanel.getRocketAmount()-1);
						}
					}else if(currentWeapon == 4){
						if(gamepanel.getLaserAmount() > 0){
							GamePanel.bullets.add(new Bullet(angle, x, y, 4));
							gamepanel.setLaserAmount(gamepanel.getLaserAmount()-1);
						}
						
					}else if(currentWeapon == 5){
						if(gamepanel.getCanonAmount() > 0){
							GamePanel.bullets.add(new Bullet(angle, x, y, 5));
							gamepanel.setCanonAmount(gamepanel.getCanonAmount()-1);
						}
					}
					else if(currentWeapon == 6){
						if(gamepanel.getBouncerAmount() > 0){
							GamePanel.bullets.add(new Bullet(angle, x, y, 6));
							gamepanel.setBouncerAmount(gamepanel.getBouncerAmount()-1);
						}
					}
				}		
			}
		}
		if(recovering){
			long elapsed =(System.nanoTime() - recoveryTimer) / 1000000;
			if(elapsed > 2000){
				recovering = false;
				recoveryTimer = 0;
			}
		}
		
		
	}
	public void draw(Graphics2D g){
		
		if(recovering){
			g.setColor(color2);
			g.fillOval(x - r, y - r, 2*r, 2*r);
			
			g.setStroke(new BasicStroke(4));
			g.setColor(color2.darker());
			g.drawOval(x - r, y - r, 2*r, 2*r);
			g.setStroke(new BasicStroke(1));
		}else{
			g.setColor(color1);
			g.fillOval(x - r, y - r, 2*r, 2*r);
			
			g.setStroke(new BasicStroke(4)); 
			g.setColor(color1.darker());
			g.drawOval(x - r, y - r, 2*r, 2*r);
			g.setStroke(new BasicStroke(1));
			if(HatImg != null){
				g.drawImage(HatImg, this.x-(GamePanel.WIDTH/160), this.y-(GamePanel.WIDTH/67), GamePanel.WIDTH/80, GamePanel.WIDTH/80, null);
			}
		}
	
	}

	public long getFiringDelay() {
		return firingDelay;
	}

	public void setFiringDelay(long firingDelay) {
		this.firingDelay = firingDelay;
	}
}
