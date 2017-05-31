package com.tutorial.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.tutorial.main.GamePanel.STATE;

public class Menu extends MouseAdapter{
	
	private Color color1;
	private Color color2;
	private Color color1CharSelect;
	
	GamePanel gamepanel;
	
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
			//Play
			if(mouseOver(mx, my, GamePanel.WIDTH/80, GamePanel.WIDTH/13, GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
				GamePanel.player.setLives(3); //Because we have to use GamePanels Player(As it has all the data)
				gamepanel.setWaveNumber(0);
				gamepanel.enemies.clear();
				gamepanel.player.setRecovering(false);
				gamepanel.player.setx(gamepanel.WIDTH/2);
				gamepanel.player.sety(gamepanel.HEIGHT/2);
				gamepanel.player.setPower(0);
				gamepanel.player.setPowerLevel(0);
				gamepanel.player.setPowerLevelChecker(0);
				Player.currentWeapon=1;
				//gamepanel.setBombAmount(10); //10 no longer needed as you have to buy bombs
				gamepanel.setRocketAmount(20); //20
				gamepanel.setBouncerAmount(50);  //50
				gamepanel.setCanonAmount(10);  //10
				gamepanel.setLaserAmount(10);  //10
				gamepanel.player.setScore(0);
				gamepanel.bullets.clear();
				gamepanel.powerups.clear();
				gamepanel.bombs.clear();
				gamepanel.explosions.clear();
				gamepanel.texts.clear();
				gamepanel.setslowDownTimer(0);
				gamepanel.gameState = STATE.Game;
			}
			//Charcter
			if(mouseOver(mx, my, GamePanel.WIDTH/80, (int) (GamePanel.WIDTH/6.5), GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
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
						for(int j = 0; j < gamepanel.enemies.size(); j++){
							gamepanel.enemies.get(j).setSlow(true);
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
				gamepanel.gameState = STATE.CharSelect;
			}
		}else if(gamepanel.gameState == STATE.CharSelect){
			int length = (int) (GamePanel.WIDTH/4);
			int tallness = (int) (GamePanel.HEIGHT/3.5);
			if(mouseOver(mx, my, (int) (GamePanel.WIDTH/5-(length/2)+length*0.75+length+(length/5)), GamePanel.HEIGHT/5-(tallness/2)+tallness/10, GamePanel.WIDTH/28, GamePanel.HEIGHT/7)){
				gamepanel.setBombAmount(gamepanel.getBombAmount()+2);
			}

			//BACK TO MENU
			if(mouseOver(mx, my, gamepanel.WIDTH-gamepanel.WIDTH/6, gamepanel.HEIGHT-gamepanel.HEIGHT/6, gamepanel.WIDTH/8, gamepanel.HEIGHT/12)){
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
		//Character/upgrades
		if(mouseOver(mx, my, GamePanel.WIDTH/80, (int) (GamePanel.WIDTH/6.5), GamePanel.WIDTH/4, GamePanel.WIDTH/16)){
			color2 = Color.GREEN;
		}else{
			color2 = Color.WHITE;
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
		
		if(mouseOver(mx, my, gamepanel.WIDTH-gamepanel.WIDTH/6, gamepanel.HEIGHT-gamepanel.HEIGHT/6, gamepanel.WIDTH/8, gamepanel.HEIGHT/12)){
			color1CharSelect = Color.GREEN;
		}else{
			color1CharSelect = Color.WHITE;
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
			g.setColor(new Color(0,100,50));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
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
			g.drawString("Characters", GamePanel.WIDTH/25, (int) (GamePanel.HEIGHT/2.8));
			g.setColor(Color.WHITE);
			g.drawRoundRect(GamePanel.WIDTH/80, (int) (GamePanel.HEIGHT/7.5 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64 + GamePanel.WIDTH/16 + GamePanel.WIDTH/64), GamePanel.WIDTH/4, GamePanel.WIDTH/16, GamePanel.WIDTH/53, GamePanel.WIDTH/53);
			g.drawString("Options", GamePanel.WIDTH/18, (int) (GamePanel.HEIGHT/2));
			g.setStroke(new BasicStroke(1));
		}else if(gamepanel.gameState == STATE.CharSelect){
			g.setColor(new Color(0,100,50));
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
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
			
			//backtomenubutton
			g.setColor(color1CharSelect);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, GamePanel.WIDTH/50));
			g.drawRoundRect(gamepanel.WIDTH-gamepanel.WIDTH/6, gamepanel.HEIGHT-gamepanel.HEIGHT/6, gamepanel.WIDTH/8, gamepanel.HEIGHT/12, GamePanel.WIDTH/100, GamePanel.HEIGHT/100);
			g.drawString("Back to Menu", gamepanel.WIDTH-gamepanel.WIDTH/6+gamepanel.WIDTH/170, gamepanel.HEIGHT-gamepanel.HEIGHT/6+gamepanel.HEIGHT/18);
			g.setStroke(new BasicStroke(1));
		}
	
	}
	
}
