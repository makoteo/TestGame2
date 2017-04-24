package com.tutorial.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tutorial.main.GamePanel.STATE;

public class Menu extends MouseAdapter{
	
	private Color color1;
	private Color color2;
	
	GamePanel gamepanel;
	public static Player player;
	
	public static int mxg;
	public static int myg;
	
	private int Timer = 2;
	
	public static boolean firstwindowselected = false;
	public static boolean secondwindowselected = false;
	
	public static boolean pressingDetonate = false;
	public static boolean hoveringDetonate = false;
	
	public Menu(GamePanel gamepanel){
		this.gamepanel = gamepanel;
	}
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		if(gamepanel.gameState == STATE.Menu){
			if(mouseOver(mx, my, -20, 120, 400, 100)){
				gamepanel.gameState = STATE.Game;
			}
			
			if(mouseOver(mx, my, -20, 240, 400, 100)){
				gamepanel.gameState = STATE.CharSelect;
			}
		}else if(gamepanel.gameState == STATE.Game){
			if(gamepanel.getDetonateButton()){
				if(mouseOver(mx, my, 1470, 750, 100, 100)){
					for(int i = 0; i < GamePanel.bombs.size(); i++){
						pressingDetonate=true;
						GamePanel.bombs.get(i).detonate();	
					}
					Player.currentWeapon = 1;
					pressingDetonate=false;
				}else{
					pressingDetonate=false;
				}
			}
			if(gamepanel.powerLevelUpgrade1 != 0 && gamepanel.powerLevelUpgrade2 != 0){
				if(mouseOver(mx, my, 287, 180, 500, 500)){
					
					if(gamepanel.powerLevelUpgrade1 == 1){
						//Refill Bullets
						gamepanel.setBombAmount(gamepanel.getBombAmount() + 10);
						gamepanel.setRocketAmount(gamepanel.getRocketAmount() + 20);
						gamepanel.setBouncerAmount(gamepanel.getBouncerAmount() + 50);	
						gamepanel.setCanonAmount(gamepanel.getCanonAmount() + 10);	
						gamepanel.setLaserAmount(gamepanel.getLaserAmount() + 10);	
						
					}else if(gamepanel.powerLevelUpgrade1 == 2){
						//Life
						gamepanel.addLife();
					}
					else if(gamepanel.powerLevelUpgrade1 == 3){
						//Slowdown
						gamepanel.setslowDownLength(25000);
						gamepanel.setslowDownTimer(System.nanoTime());
						for(int j = 0; j < GamePanel.enemies.size(); j++){
							GamePanel.enemies.get(j).setSlow(true);
						}
					}
					Menu.firstwindowselected=false;
					Menu.secondwindowselected=false;
					
					gamepanel.powerLevelUpgrade1 = 0;
					gamepanel.powerLevelUpgrade2 = 0;
				}
				else if(mouseOver(mx, my, 815, 180, 500, 500)){
					if(gamepanel.powerLevelUpgrade2 == 1){
						//Refill Bullets
						gamepanel.setBombAmount(gamepanel.getBombAmount() + 10);
						gamepanel.setRocketAmount(gamepanel.getRocketAmount() + 20);
						gamepanel.setBouncerAmount(gamepanel.getBouncerAmount() + 50);	
						gamepanel.setCanonAmount(gamepanel.getCanonAmount() + 10);	
						gamepanel.setLaserAmount(gamepanel.getLaserAmount() + 10);	
						
					}else if(gamepanel.powerLevelUpgrade2 == 2){
						//Life
						gamepanel.addLife();
					}
					else if(gamepanel.powerLevelUpgrade2 == 3){
						//Slowdown
						gamepanel.setslowDownLength(25000);
						gamepanel.setslowDownTimer(System.nanoTime());
						for(int j = 0; j < gamepanel.enemies.size(); j++){
							gamepanel.enemies.get(j).setSlow(true);
						}
					}
					Menu.firstwindowselected=false;
					Menu.secondwindowselected=false;
					
					gamepanel.powerLevelUpgrade1 = 0;
					gamepanel.powerLevelUpgrade2 = 0;
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		
	}
	
	public void mouseMoved(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		mxg = mx;
		myg = my;
		
		if(mouseOver(mx, my, -20, 120, 400, 100)){
			color1 = Color.GREEN;
		}else{
			color1 = Color.WHITE;
		}
		
		if(mouseOver(mx, my, -20, 240, 400, 100)){
			color2 = Color.GREEN;
		}else{
			color2 = Color.WHITE;
		}
		
		if(gamepanel.powerLevelUpgrade1 != 0){
			if(mouseOver(mx, my, 287, 180, 500, 500)){
				firstwindowselected=true;
				secondwindowselected=false;
			}else if(mouseOver(mx, my, 815, 180, 500, 500)){
				secondwindowselected=true;
				firstwindowselected=false;
			}else{
				firstwindowselected=false;
				secondwindowselected=false;
			}
		}

	}
	
	public void update(){
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		if(mx > x && mx < x + width){
			if(my > y && my < y + height){
				return true;
			}else return false;
		}else return false;
	}
	
	public void draw(Graphics2D g){
		if(gamepanel.gameState == STATE.Menu){
			g.setColor(new Color(0,100,255));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, 120));
			g.setColor(Color.GRAY);
			g.drawString("M E N U", 24, 104);
			g.setColor(Color.WHITE);
			g.drawString("M E N U", 20, 100);
			g.setStroke(new BasicStroke(5));
			g.setFont(new Font("Century Ghotic", Font.PLAIN, 60));
			g.setColor(color1);
			g.drawRoundRect(-20, 120, 400, 100, 30, 30);
			g.drawString("Play", 100, 190);
			g.setColor(color2);
			g.drawRoundRect(-20, 240, 400, 100, 30, 30);
			g.drawString("Characters", 30, 310);
			g.setColor(Color.WHITE);
			g.drawRoundRect(-20, 360, 400, 100, 30, 30);
			g.drawString("Options", 60, 430);
			g.setStroke(new BasicStroke(1));
		}else if(gamepanel.gameState == STATE.CharSelect){
			g.setColor(new Color(0,100,255));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			
		}
	
	}
	
}
