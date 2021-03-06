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
	
	public Menu(GamePanel gamepanel){
		this.gamepanel = gamepanel;
	}
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		if(gamepanel.gameState == STATE.Menu){
			if(mouseOver(mx, my, -20, 100, 200, 50)){
				gamepanel.gameState = STATE.Game;
			}
			
			if(mouseOver(mx, my, -5, 160, 200, 50)){
				gamepanel.gameState = STATE.CharSelect;
			}
		}if(gamepanel.gameState == STATE.Game){
			if(gamepanel.getDetonateButton()){
				if(mouseOver(mx, my, 1470, 750, 100, 100)){
					for(int i = 0; i < GamePanel.bombs.size(); i++){
						GamePanel.bombs.get(i).detonate();	
					}
					Player.currentWeapon = 1;
				}
			}
			if(gamepanel.powerLevelUpgrade1 != 0 && gamepanel.powerLevelUpgrade2 != 0){
				if(mouseOver(mx, my, 272, 180, 500, 500)){
					
					if(gamepanel.powerLevelUpgrade1 == 1){
						//Bomb
						gamepanel.setBombAmount(gamepanel.getBombAmount() + 3);
					}else if(gamepanel.powerLevelUpgrade1 == 2){
						//Life
						gamepanel.addLife();
					}
					else if(gamepanel.powerLevelUpgrade1 == 3){
						//Slowdown
						gamepanel.setslowDownTimer(System.nanoTime());
						for(int j = 0; j < GamePanel.enemies.size(); j++){
							GamePanel.enemies.get(j).setSlow(true);
						}
					}
					
					gamepanel.powerLevelUpgrade1 = 0;
					gamepanel.powerLevelUpgrade2 = 0;
				}
				else if(mouseOver(mx, my, 800, 180, 500, 500)){
					if(gamepanel.powerLevelUpgrade2 == 1){
						//Bomb
						gamepanel.setBombAmount(gamepanel.getBombAmount() + 3);
					}else if(gamepanel.powerLevelUpgrade2 == 2){
						//Life
						gamepanel.addLife();
					}
					else if(gamepanel.powerLevelUpgrade2 == 3){
						//Slowdown
						gamepanel.setslowDownTimer(System.nanoTime());
						for(int j = 0; j < gamepanel.enemies.size(); j++){
							gamepanel.enemies.get(j).setSlow(true);
						}
					}
					
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
		
		if(mouseOver(mx, my, -20, 100, 200, 50)){
			color1 = Color.GREEN;
		}else{
			color1 = Color.WHITE;
		}
		
		if(mouseOver(mx, my, -5, 160, 200, 50)){
			color2 = Color.GREEN;
		}else{
			color2 = Color.WHITE;
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
			g.setStroke(new BasicStroke(3));
			g.setFont(new Font("Century Ghotic", Font.PLAIN, 35));
			g.setColor(color1);
			g.drawRoundRect(-20, 100, 200, 50, 30, 30);
			g.drawString("Play", 40, 140);
			g.setColor(color2);
			g.drawRoundRect(-20, 160, 200, 50, 30, 30);
			g.drawString("Characters", 0, 200);
			g.setColor(Color.WHITE);
			g.drawRoundRect(-20, 220, 200, 50, 30, 30);
			g.drawString("Options", 10, 260);
			g.setStroke(new BasicStroke(1));
		}else if(gamepanel.gameState == STATE.CharSelect){
			g.setColor(new Color(0,100,255));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
			
		}
	
	}
	
}
