package com.tutorial.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tutorial.main.GamePanel.STATE;

public class Menu extends MouseAdapter{
	
	private Color color1;
	private Color color2;
	private Color color3;
	private Color color1Upgrades;
	private Color color1CharSelect;
	private Color colorRed;
	
	private int boxx = -1000;
	private int boxy = -1000;
	private String boxtext = "Hi, if you see this, you've found a bug";
	
	GamePanel gamepanel;
	
	public static int mxg;
	public static int myg;
	
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
			//Play
			if(mouseOver(mx, my, GamePanel.WIDTH/80, GamePanel.WIDTH/13, GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
				GamePanel.player.setLives(3); //Because we have to use GamePanels Player(As it has all the data)
				gamepanel.setWaveNumber(0);
				GamePanel.enemies.clear();
				GamePanel.player.setRecovering(false);
				GamePanel.player.setx(GamePanel.WIDTH/2);
				GamePanel.player.sety(GamePanel.HEIGHT/2);
				GamePanel.player.setPower(0);
				GamePanel.player.setPowerLevel(0);
				GamePanel.player.setPowerLevelChecker(0);
				Player.currentWeapon=1;
				//gamepanel.setBombAmount(10); //10 no longer needed as you have to buy bombs
				//gamepanel.setRocketAmount(20); //20
				//gamepanel.setBouncerAmount(50);  //50
				//gamepanel.setCanonAmount(10);  //10
				//gamepanel.setLaserAmount(10);  //10
				GamePanel.player.setScore(0);
				GamePanel.bullets.clear();
				GamePanel.powerups.clear();
				GamePanel.bombs.clear();
				GamePanel.explosions.clear();
				GamePanel.particles.clear();
				GamePanel.texts.clear();
				gamepanel.setslowDownTimer(0);
				gamepanel.gameState = STATE.Game;
			}
			//Upgrades
			if(mouseOver(mx, my, GamePanel.WIDTH/80, (int) (GamePanel.WIDTH/6.5), GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
				gamepanel.gameState = STATE.Upgrade;
			}
			//Character
			if(mouseOver(mx, my, GamePanel.WIDTH/80, (int) (GamePanel.HEIGHT/7.5 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64), GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
				gamepanel.gameState = STATE.CharSelect;
			}
		}else if(gamepanel.gameState == STATE.Game){
			//Bomb
			if(gamepanel.getDetonateButton()){
				if(mouseOver(mx, my, GamePanel.WIDTH-(GamePanel.WIDTH/13)-(GamePanel.WIDTH/13/6), GamePanel.HEIGHT-(GamePanel.WIDTH/13)-(GamePanel.WIDTH/13/6), GamePanel.WIDTH/13, GamePanel.WIDTH/13)){
					for(int i = 0; i < GamePanel.bombs.size(); i++){
						pressingDetonate=true;
						GamePanel.bombs.get(i).detonate();	
					}
					Player.currentWeapon = 1;
					GamePanel.alpha = 255;
					pressingDetonate=false;
				}else{
					pressingDetonate=false;
				}
			}
			if(gamepanel.powerLevelUpgrade1 != 0 && gamepanel.powerLevelUpgrade2 != 0){
				double imageheightwidth = GamePanel.WIDTH/3.2;
				double space = GamePanel.WIDTH/80;
				int widthdiv3pt2 = (int) ((int)GamePanel.WIDTH/3.2);
				
				if(mouseOver(mx, my, (int) (GamePanel.WIDTH/2 - imageheightwidth - space) , (GamePanel.HEIGHT/2)-(widthdiv3pt2/2), widthdiv3pt2, widthdiv3pt2)){
					
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
					
					GamePanel.alpha = 255;
					
				}
				else if(mouseOver(mx, my, (GamePanel.WIDTH/2 + (GamePanel.WIDTH/80)), (int) ((int) (GamePanel.HEIGHT/2)-((GamePanel.WIDTH/3.2)/2)), (int) (GamePanel.WIDTH/3.2), (int) (GamePanel.WIDTH/3.2))){
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
						for(int j = 0; j < GamePanel.enemies.size(); j++){
							GamePanel.enemies.get(j).setSlow(true);
						}
					}
					Menu.firstwindowselected=false;
					Menu.secondwindowselected=false;
					
					gamepanel.powerLevelUpgrade1 = 0;
					gamepanel.powerLevelUpgrade2 = 0;
					
					GamePanel.alpha = 255;
				}
			}
		}else if(gamepanel.gameState == STATE.Dead){
			if(mouseOver(mx, my, GamePanel.WIDTH/2-GamePanel.WIDTH/12, (int) ((GamePanel.HEIGHT/3)*1.45), GamePanel.WIDTH/6, GamePanel.HEIGHT/14)){
				//Back to Menu
				gamepanel.gameState = STATE.Menu;
			}
			if(mouseOver(mx, my, GamePanel.WIDTH/2-GamePanel.WIDTH/12, (int) ((GamePanel.HEIGHT/3)*1.5) + GamePanel.HEIGHT/12, GamePanel.WIDTH/6, GamePanel.HEIGHT/14)){
				gamepanel.gameState = STATE.Upgrade;
			}
		}else if(gamepanel.gameState == STATE.Upgrade){
			int length = (int) (GamePanel.WIDTH/4);
			int tallness = (int) (GamePanel.HEIGHT/3.5);
			//Bombs
			if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
				if(gamepanel.getMasterScore()>20 /*cost per 2 bombs*/&& gamepanel.getBombAmount()<20){
					gamepanel.setBombAmount(gamepanel.getBombAmount()+2);
					gamepanel.setMasterScore(gamepanel.getMasterScore()-20);
				}else{
					colorRed = Color.red;
				}
			}
			//Rockets
			if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
				if(gamepanel.getMasterScore()>30/*cost per 2 rockets*/ && gamepanel.getRocketAmount()<40){
					gamepanel.setRocketAmount(gamepanel.getRocketAmount()+2);
					gamepanel.setMasterScore(gamepanel.getMasterScore()-30);
				}else{
					colorRed = Color.red;
				}
			}
			//Lasers
			if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
				if(gamepanel.getMasterScore()>40/*cost per 2 lasers*/ && gamepanel.getLaserAmount()<20){
					gamepanel.setLaserAmount(gamepanel.getLaserAmount()+2);
					gamepanel.setMasterScore(gamepanel.getMasterScore()-40);
				}else{
					colorRed = Color.red;
				}
			}
			//Canons
			if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
				if(gamepanel.getMasterScore()>50/*cost per 2 canons*/ && gamepanel.getCanonAmount()<20){
					gamepanel.setCanonAmount(gamepanel.getCanonAmount()+2);
					gamepanel.setMasterScore(gamepanel.getMasterScore()-50);
				}else{
					colorRed = Color.red;
				}
			}
			//Bouncers
			if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
				if(gamepanel.getMasterScore()>45/*cost per 5 bouncers*/ && gamepanel.getBouncerAmount()<100){
					gamepanel.setBouncerAmount(gamepanel.getBouncerAmount()+5);
					gamepanel.setMasterScore(gamepanel.getMasterScore()-45);
				}else{
					colorRed = Color.red;
				}
			}
			//BACK TO MENU
			if(mouseOver(mx, my, GamePanel.WIDTH-GamePanel.WIDTH/6, GamePanel.HEIGHT-GamePanel.HEIGHT/6, GamePanel.WIDTH/8, GamePanel.HEIGHT/12)){
				gamepanel.gameState=STATE.Menu;
			}
		}else if(gamepanel.gameState == STATE.CharSelect){
			for(int i = 0; i < 6; i++){
				//Buttons
				if(mouseOver(mx, my, GamePanel.WIDTH/12 + GamePanel.WIDTH/9*i + GamePanel.WIDTH/32*i, GamePanel.HEIGHT/16, GamePanel.WIDTH/9, (int) (GamePanel.HEIGHT/6.5))){
					GamePanel.CharColorSelected = i;
				}
			}	
			//BACK TO MENU
			if(mouseOver(mx, my, GamePanel.WIDTH-GamePanel.WIDTH/6, GamePanel.HEIGHT-GamePanel.HEIGHT/6, GamePanel.WIDTH/8, GamePanel.HEIGHT/12)){
				gamepanel.gameState=STATE.Menu;
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
		
		//Play
		if(mouseOver(mx, my, GamePanel.WIDTH/80, GamePanel.WIDTH/13, GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
			color1 = Color.GREEN;
		}else{
			color1 = Color.WHITE;
		}
		//Upgrades
		if(mouseOver(mx, my, GamePanel.WIDTH/80, (int) (GamePanel.WIDTH/6.5), GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
			color2 = Color.GREEN;
		}else{
			color2 = Color.WHITE;
		}
		//Characters
		if(mouseOver(mx, my, GamePanel.WIDTH/80, (int) (GamePanel.HEIGHT/7.5 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64), GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
			color3 = Color.GREEN;
		}else{
			color3 = Color.WHITE;
		}
		//Back To Menu
		if(mouseOver(mx, my, GamePanel.WIDTH/2-GamePanel.WIDTH/12, (int) ((GamePanel.HEIGHT/3)*1.45), GamePanel.WIDTH/6, GamePanel.HEIGHT/14)){
			gamepanel.colordead1 = Color.GREEN;
		}else{
			gamepanel.colordead1 = Color.WHITE;
		}
		//Upgrades
		if(mouseOver(mx, my, GamePanel.WIDTH/2-GamePanel.WIDTH/12, (int) ((GamePanel.HEIGHT/3)*1.5) + GamePanel.HEIGHT/12, GamePanel.WIDTH/6, GamePanel.HEIGHT/14)){
			gamepanel.colordead2 = Color.GREEN;
		}else{
			gamepanel.colordead2 = Color.WHITE;
		}
		
		if(mouseOver(mx, my, GamePanel.WIDTH-GamePanel.WIDTH/6, GamePanel.HEIGHT-GamePanel.HEIGHT/6, GamePanel.WIDTH/8, GamePanel.HEIGHT/12)){
			color1Upgrades = Color.GREEN;
		}else{
			color1Upgrades = Color.WHITE;
		}
		
		if(mouseOver(mx, my, GamePanel.WIDTH-GamePanel.WIDTH/6, GamePanel.HEIGHT-GamePanel.HEIGHT/6, GamePanel.WIDTH/8, GamePanel.HEIGHT/12)){
			color1CharSelect = Color.GREEN;
		}else{
			color1CharSelect = Color.WHITE;
		}
		
		int length = (int) (GamePanel.WIDTH/4);
		int tallness = (int) (GamePanel.HEIGHT/3.5);
		//Bombs
		if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
			boxx = e.getX();
			boxy = e.getY();
			boxtext = "Cost: 20";
			if(gamepanel.getMasterScore()>20/*cost per 2 bombs*/){
				colorRed = Color.WHITE;
			}else{
				colorRed = Color.red;
			}
		}
		//Rockets
		else if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
			boxx = e.getX();
			boxy = e.getY();
			boxtext = "Cost: 30";
			if(gamepanel.getMasterScore()>30/*cost per 2 bombs*/){
				colorRed = Color.WHITE;
			}else{
				colorRed = Color.red;
			}
		}
		//Lasers
		else if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
			boxx = e.getX();
			boxy = e.getY();
			boxtext = "Cost: 40";
			if(gamepanel.getMasterScore()>40/*cost per 2 bombs*/){
				colorRed = Color.WHITE;
			}else{
				colorRed = Color.red;
			}
		}
		//Canons
		else if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
			boxx = e.getX();
			boxy = e.getY();
			boxtext = "Cost: 50";
			if(gamepanel.getMasterScore()>50/*cost per 2 bombs*/){
				colorRed = Color.WHITE;
			}else{
				colorRed = Color.red;
			}
		}
		//Bouncers
		else if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
			boxx = e.getX();
			boxy = e.getY();
			boxtext = "Cost: 45";
			if(gamepanel.getMasterScore()>45/*cost per 2 bombs*/){
				colorRed = Color.WHITE;
			}else{
				colorRed = Color.red;
			}
		}
		else{
			boxx = -1000;
			boxy = -1000;
			boxtext = "Hi, if you see this, you've found a bug";
			colorRed = Color.WHITE;
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
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/14));
			g.setColor(Color.GRAY);
			g.drawString("M E N U", GamePanel.WIDTH/66, GamePanel.WIDTH/15);
			g.setColor(Color.WHITE);
			g.drawString("M E N U", GamePanel.WIDTH/80, GamePanel.WIDTH/16);
			g.setStroke(new BasicStroke(5));
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/26));
			g.setColor(color1);
			g.drawRoundRect(GamePanel.WIDTH/80, (int) (GamePanel.HEIGHT/7.5), GamePanel.WIDTH/4, GamePanel.WIDTH/16, GamePanel.WIDTH/53, GamePanel.WIDTH/53);
			g.drawString("Play", GamePanel.WIDTH/12, (int) (GamePanel.HEIGHT/4.7));
			g.setColor(color2);
			g.drawRoundRect(GamePanel.WIDTH/80, (int) (GamePanel.HEIGHT/7.5 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64), GamePanel.WIDTH/4, GamePanel.WIDTH/16, GamePanel.WIDTH/53, GamePanel.WIDTH/53);
			g.drawString("Upgrades", GamePanel.WIDTH/20, (int) (GamePanel.HEIGHT/2.8));
			g.setColor(color3);
			g.drawRoundRect(GamePanel.WIDTH/80, (int) (GamePanel.HEIGHT/7.5 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64), GamePanel.WIDTH/4, GamePanel.WIDTH/16, GamePanel.WIDTH/53, GamePanel.WIDTH/53);
			g.drawString("Characters", GamePanel.WIDTH/25, (int) (GamePanel.HEIGHT/2));
			g.setStroke(new BasicStroke(1));
		}else if(gamepanel.gameState == STATE.Upgrade){
			g.setStroke(new BasicStroke(4));
			int length = (int) (GamePanel.WIDTH/4);
			int tallness = (int) (GamePanel.HEIGHT/3.5);
			//Classical Bullets
			g.setColor(Color.gray);
			g.fillRoundRect(GamePanel.WIDTH/5-(length/2), GamePanel.HEIGHT/5-(tallness/2), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.setColor(Color.black);
			g.drawRoundRect(GamePanel.WIDTH/5-(length/2), GamePanel.HEIGHT/5-(tallness/2), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10, GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/8, GamePanel.HEIGHT/7);
			g.setColor(Color.GREEN);
			g.fillRect(GamePanel.WIDTH/5-(length/2)+length/10, (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness*0.7), (int) (GamePanel.WIDTH/5-(GamePanel.WIDTH/5-(GamePanel.WIDTH/5*0.33))), GamePanel.HEIGHT/20);
			g.setColor(Color.black);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10, (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness*0.7), GamePanel.WIDTH/5, GamePanel.HEIGHT/20);
			g.setColor(Color.GREEN);
			g.fillRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness/10+GamePanel.HEIGHT/7-(GamePanel.HEIGHT/7*1)), GamePanel.WIDTH/28, (int) (GamePanel.HEIGHT/7*1));
			g.setColor(Color.black);
			g.drawRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7);
			//Bombs
			g.setColor(Color.gray);
			g.fillRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5), GamePanel.HEIGHT/5-(tallness/2), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.setColor(Color.black);
			g.drawRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5), GamePanel.HEIGHT/5-(tallness/2), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/8, GamePanel.HEIGHT/7);
			g.setColor(Color.GREEN);
			g.fillRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness*0.7), (int) (GamePanel.WIDTH/5-(GamePanel.WIDTH/5-(GamePanel.WIDTH/5*0.33))), GamePanel.HEIGHT/20);
			g.setColor(Color.black);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness*0.7), GamePanel.WIDTH/5, GamePanel.HEIGHT/20);
			g.setColor(Color.GREEN);
			g.fillRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness/10+GamePanel.HEIGHT/7-(GamePanel.HEIGHT/7*((double)gamepanel.getBombAmount()/20))), GamePanel.WIDTH/28, (int) (GamePanel.HEIGHT/7*((double)gamepanel.getBombAmount()/20)));
			g.setColor(Color.black);
			g.drawRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7);
			//Rockets
			g.setColor(Color.gray);
			g.fillRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5)+length+(length/5), GamePanel.HEIGHT/5-(tallness/2), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.setColor(Color.black);
			g.drawRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5)+length+(length/5), GamePanel.HEIGHT/5-(tallness/2), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5)+length+(length/5), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/8, GamePanel.HEIGHT/7);
			g.setColor(Color.GREEN);
			g.fillRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness*0.7), (int) (GamePanel.WIDTH/5-(GamePanel.WIDTH/5-(GamePanel.WIDTH/5*0.33))), GamePanel.HEIGHT/20);
			g.setColor(Color.black);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness*0.7), GamePanel.WIDTH/5, GamePanel.HEIGHT/20);
			g.setColor(Color.GREEN);
			g.fillRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)+length+(length/5)), (int) (GamePanel.HEIGHT/5-(tallness/2)+tallness/10+GamePanel.HEIGHT/7-(GamePanel.HEIGHT/7*((double)gamepanel.getRocketAmount()/40))), GamePanel.WIDTH/28, (int) (GamePanel.HEIGHT/7*((double)gamepanel.getRocketAmount()/40)));
			g.setColor(Color.black);
			g.drawRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7);
			//Lasers
			g.setColor(Color.gray);
			g.fillRoundRect(GamePanel.WIDTH/5-(length/2), (int) (GamePanel.HEIGHT/5+(tallness/1.5)), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.setColor(Color.black);
			g.drawRoundRect(GamePanel.WIDTH/5-(length/2), (int) (GamePanel.HEIGHT/5+(tallness/1.5)), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10, (int) (GamePanel.HEIGHT/5+(tallness/1.5))+tallness/10, GamePanel.WIDTH/8, GamePanel.HEIGHT/7);
			g.setColor(Color.GREEN);
			g.fillRect(GamePanel.WIDTH/5-(length/2)+length/10, (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness*0.7), (int) (GamePanel.WIDTH/5-(GamePanel.WIDTH/5-(GamePanel.WIDTH/5*0.33))), GamePanel.HEIGHT/20);
			g.setColor(Color.black);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10, (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness*0.7), GamePanel.WIDTH/5, GamePanel.HEIGHT/20);
			g.setColor(Color.GREEN);
			g.fillRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10+GamePanel.HEIGHT/7-(GamePanel.HEIGHT/7*((double)gamepanel.getLaserAmount()/20))), GamePanel.WIDTH/28, (int) (GamePanel.HEIGHT/7*((double)gamepanel.getLaserAmount()/20)));
			g.setColor(Color.black);
			g.drawRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7);
			//Canons
			g.setColor(Color.gray);
			g.fillRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.setColor(Color.black);
			g.drawRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5))+tallness/10, GamePanel.WIDTH/8, GamePanel.HEIGHT/7);
			g.setColor(Color.GREEN);
			g.fillRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness*0.7), (int) (GamePanel.WIDTH/5-(GamePanel.WIDTH/5-(GamePanel.WIDTH/5*0.33))), GamePanel.HEIGHT/20);
			g.setColor(Color.black);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness*0.7), GamePanel.WIDTH/5, GamePanel.HEIGHT/20);
			g.setColor(Color.GREEN);
			g.fillRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10+GamePanel.HEIGHT/7-(GamePanel.HEIGHT/7*((double)gamepanel.getCanonAmount()/20))), GamePanel.WIDTH/28, (int) (GamePanel.HEIGHT/7*((double)gamepanel.getCanonAmount()/20)));
			g.setColor(Color.black);
			g.drawRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7);
			//Bouncers
			g.setColor(Color.gray);
			g.fillRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.setColor(Color.black);
			g.drawRoundRect(GamePanel.WIDTH/5-(length/2)+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)), length, tallness, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5))+tallness/10, GamePanel.WIDTH/8, GamePanel.HEIGHT/7);
			g.setColor(Color.GREEN);
			g.fillRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness*0.7), (int) (GamePanel.WIDTH/5-(GamePanel.WIDTH/5-(GamePanel.WIDTH/5*0.33))), GamePanel.HEIGHT/20);
			g.setColor(Color.black);
			g.drawRect(GamePanel.WIDTH/5-(length/2)+length/10+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness*0.7), GamePanel.WIDTH/5, GamePanel.HEIGHT/20);
			g.setColor(Color.GREEN);
			g.fillRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10+GamePanel.HEIGHT/7-(GamePanel.HEIGHT/7*((double)gamepanel.getBouncerAmount()/100))), GamePanel.WIDTH/28, (int) (GamePanel.HEIGHT/7*((double)gamepanel.getBouncerAmount()/100)));
			g.setColor(Color.black);
			g.drawRect((int) (GamePanel.WIDTH/5-(length/2)+length*0.75)+length+(length/5)+length+(length/5), (int) (GamePanel.HEIGHT/5+(tallness/1.5)+tallness/10), GamePanel.WIDTH/28, GamePanel.HEIGHT/7);
			//Score
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/40));
			g.setColor(Color.WHITE);
			g.drawString("Score: " + gamepanel.getMasterScore(), (int) (GamePanel.WIDTH-GamePanel.WIDTH*0.9), GamePanel.HEIGHT-GamePanel.HEIGHT/6+GamePanel.HEIGHT/18);
			
			//Info Box
			String s = boxtext;
			g.setColor(colorRed);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/80));
			long length1 = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			g.drawRect(boxx + GamePanel.WIDTH/120, boxy - GamePanel.HEIGHT/40, (int) (length1+GamePanel.WIDTH/80), GamePanel.HEIGHT/40);
			g.setColor(Color.gray);
			g.fillRect(boxx + GamePanel.WIDTH/120, boxy - GamePanel.HEIGHT/40, (int) (length1+GamePanel.WIDTH/80), GamePanel.HEIGHT/40);
			g.setColor(colorRed);
			g.drawString(s, boxx + GamePanel.WIDTH/140 + GamePanel.WIDTH/120, boxy + GamePanel.WIDTH/80 - GamePanel.HEIGHT/40);
			
			//backtomenubutton
			g.setColor(color1Upgrades);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/50));
			g.drawRoundRect(GamePanel.WIDTH-GamePanel.WIDTH/6, GamePanel.HEIGHT-GamePanel.HEIGHT/6, GamePanel.WIDTH/8, GamePanel.HEIGHT/12, GamePanel.WIDTH/100, GamePanel.HEIGHT/100);
			g.drawString("Back to Menu", GamePanel.WIDTH-GamePanel.WIDTH/6+GamePanel.WIDTH/170, GamePanel.HEIGHT-GamePanel.HEIGHT/6+GamePanel.HEIGHT/18);
			g.setStroke(new BasicStroke(1));
		}else if(gamepanel.gameState == STATE.CharSelect){
			for(int i = 0; i < 6; i++){
				//Buttons
				g.setColor(Color.GRAY);
				g.setStroke(new BasicStroke(3));
				g.fillRoundRect(GamePanel.WIDTH/12 + GamePanel.WIDTH/9*i + GamePanel.WIDTH/32*i, GamePanel.HEIGHT/16, GamePanel.WIDTH/9, (int) (GamePanel.HEIGHT/6.5), GamePanel.WIDTH/80, GamePanel.HEIGHT/80);
				g.setColor(Color.BLACK);
				g.drawRoundRect(GamePanel.WIDTH/12 + GamePanel.WIDTH/9*i + GamePanel.WIDTH/32*i, GamePanel.HEIGHT/16, GamePanel.WIDTH/9, (int) (GamePanel.HEIGHT/6.5), GamePanel.WIDTH/80, GamePanel.HEIGHT/80);
				if(GamePanel.CharColorSelected == i){
					g.setColor(Color.YELLOW);
					g.drawRoundRect(GamePanel.WIDTH/12 + GamePanel.WIDTH/9*i + GamePanel.WIDTH/32*i, GamePanel.HEIGHT/16, GamePanel.WIDTH/9, (int) (GamePanel.HEIGHT/6.5), GamePanel.WIDTH/80, GamePanel.HEIGHT/80);
				}
				if(GamePanel.CharColorPage == 1){
					Color tmpColor = Color.BLACK;
					if(GamePanel.CharColors1[i] == "white"){
						tmpColor = Color.WHITE;
					}else if(GamePanel.CharColors1[i] == "blue"){
						tmpColor = Color.BLUE;
					}
					else if(GamePanel.CharColors1[i] == "red"){
						tmpColor = Color.RED;
					}
					g.setColor(tmpColor);
					g.fillOval(GamePanel.WIDTH/12 + GamePanel.WIDTH/9*i + GamePanel.WIDTH/32*i + (GamePanel.WIDTH/9/2) - GamePanel.WIDTH/80, GamePanel.HEIGHT/16 + (int) (GamePanel.HEIGHT/6.5/2) - GamePanel.WIDTH/80, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
					g.setColor(tmpColor.darker());
					g.setStroke(new BasicStroke(5));
					g.drawOval(GamePanel.WIDTH/12 + GamePanel.WIDTH/9*i + GamePanel.WIDTH/32*i + (GamePanel.WIDTH/9/2) - GamePanel.WIDTH/80, GamePanel.HEIGHT/16 + (int) (GamePanel.HEIGHT/6.5/2) - GamePanel.WIDTH/80, GamePanel.WIDTH/40, GamePanel.WIDTH/40);
					g.setStroke(new BasicStroke(1));
				}
			}
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(GamePanel.WIDTH/50, GamePanel.HEIGHT/15, (int) (GamePanel.WIDTH/26.7), (int) (GamePanel.HEIGHT/6.9), (GamePanel.WIDTH/80), (GamePanel.WIDTH/80));
			g.fillPolygon(new int[] {GamePanel.WIDTH/40, GamePanel.WIDTH/20, GamePanel.WIDTH/20}, new int[] {(int) (GamePanel.HEIGHT/6.9), (int) (GamePanel.HEIGHT/11.25), GamePanel.HEIGHT/5}, 3);
			g.setColor(Color.BLACK);
			g.drawRoundRect(GamePanel.WIDTH/50, GamePanel.HEIGHT/15, (int) (GamePanel.WIDTH/26.7), (int) (GamePanel.HEIGHT/6.9), (GamePanel.WIDTH/80), (GamePanel.WIDTH/80));
			g.drawPolygon(new int[] {GamePanel.WIDTH/40, GamePanel.WIDTH/20, GamePanel.WIDTH/20}, new int[] {(int) (GamePanel.HEIGHT/6.9), (int) (GamePanel.HEIGHT/11.25), GamePanel.HEIGHT/5}, 3);
			g.setColor(Color.DARK_GRAY);
			g.fillRoundRect(GamePanel.WIDTH - GamePanel.WIDTH/50 - (int) (GamePanel.WIDTH/26.7), GamePanel.HEIGHT/15, (int) (GamePanel.WIDTH/26.7), (int) (GamePanel.HEIGHT/6.9), (GamePanel.WIDTH/80), (GamePanel.WIDTH/80));
			g.fillPolygon(new int[] {GamePanel.WIDTH - GamePanel.WIDTH/40, GamePanel.WIDTH - GamePanel.WIDTH/20, GamePanel.WIDTH - GamePanel.WIDTH/20}, new int[] {(int) (GamePanel.HEIGHT/6.9), (int) (GamePanel.HEIGHT/11.25), GamePanel.HEIGHT/5}, 3);
			g.setColor(Color.BLACK);
			g.drawRoundRect(GamePanel.WIDTH - GamePanel.WIDTH/50 - (int) (GamePanel.WIDTH/26.7), GamePanel.HEIGHT/15, (int) (GamePanel.WIDTH/26.7), (int) (GamePanel.HEIGHT/6.9), (GamePanel.WIDTH/80), (GamePanel.WIDTH/80));
			g.drawPolygon(new int[] {GamePanel.WIDTH - GamePanel.WIDTH/40, GamePanel.WIDTH - GamePanel.WIDTH/20, GamePanel.WIDTH - GamePanel.WIDTH/20}, new int[] {(int) (GamePanel.HEIGHT/6.9), (int) (GamePanel.HEIGHT/11.25), GamePanel.HEIGHT/5}, 3);
			g.setColor(color1CharSelect);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/50));
			g.setStroke(new BasicStroke(3));
			g.drawRoundRect(GamePanel.WIDTH-GamePanel.WIDTH/6, GamePanel.HEIGHT-GamePanel.HEIGHT/6, GamePanel.WIDTH/8, GamePanel.HEIGHT/12, GamePanel.WIDTH/100, GamePanel.HEIGHT/100);
			g.drawString("Back to Menu", GamePanel.WIDTH-GamePanel.WIDTH/6+GamePanel.WIDTH/170, GamePanel.HEIGHT-GamePanel.HEIGHT/6+GamePanel.HEIGHT/18);
			g.setStroke(new BasicStroke(1));
		}
	
	}
	
}
