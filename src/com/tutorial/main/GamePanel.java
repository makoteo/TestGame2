package com.tutorial.main;

import javax.swing.JPanel;

import com.tutorial.main.GamePanel.STATE;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.*;

public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	//FEILDS
	public static int WIDTH = 1598;
	public static int HEIGHT = 876;
	
	private Thread thread;
	
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private double averageFPS;
	
	public static Player player;
	private Menu menu;
	public static ArrayList<Bullet> bullets;
	public static ArrayList<Enemy> enemies;
	public static ArrayList<Bomb> bombs;
	public static ArrayList<PowerUp> powerups;
	public static ArrayList<Explosion> explosions;
	public static ArrayList<Text> texts;
	
	private long waveStartTimer;
	private long WaveStartTimerDiff;
	private int waveNumber;
	private boolean waveStart;
	private int waveDelay = 2000;
	
	private int bombAmount = 10;
	
	private long slowDownTimer;
	private long slowDownTimerDiff;
	private int slowDownLength = 6000;
	
	private boolean dead;
	
	public static boolean bombExplode = false;
	
	private boolean detonateButton = false;
	
	public int powerLevelUpgrade1 = 0;
	public int powerLevelUpgrade2 = 0;
	PowerUp powerup;
	
	public static boolean paused = false;
	
	public int bombType = 1;
	
	public enum STATE {
		Menu,
		Game,
		Dead,
		CharSelect
	};
	
	public STATE gameState = STATE.Menu; 
	
	//CONSTRUCTOR
	public GamePanel(){
		super();
		//setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		menu = new Menu(this);
	}
	//FUNCTIONS
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
		addKeyListener(this);
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
	}
	public static int getPlayerX(){
		return player.getx();
	}
	public static int getPlayerY(){
		return player.gety();
	}
	public void run(){
		
		running = true;
		
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics(); 
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		player = new Player();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		bombs = new ArrayList<Bomb>();
		powerups = new ArrayList<PowerUp>();
		explosions = new ArrayList<Explosion>();
		texts = new ArrayList<Text>();
		
		waveStartTimer = 0;
		WaveStartTimerDiff = 0;
		waveStart = true;
		waveNumber = 0;
		
		dead = false;
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		int targetTime = 1000 / FPS;
		//GAME LOOP
		while(running){
			
			startTime = System.nanoTime();
			
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			
			try{
				Thread.sleep(waitTime);
			}
			catch(Exception e){
				
			}
			
			totalTime += System.nanoTime() - startTime;
			
			frameCount++;
			
			if(frameCount == maxFrameCount){
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
		
	}
	
	public void setPlayerWeapon(int i){
		player.setCurrentWeapon(i);
	}
	
	private void gameUpdate(){
		if(slowDownTimer > 0){
			for(int j = 0; j < enemies.size(); j++){
				enemies.get(j).setSlow(true);
			}
		}
		
		if(bombs.size() > 0){
			detonateButton = true;
		}else{
			//Destroy Bomb after explosion
			detonateButton = false;
		}
		if(gameState == STATE.Game && 
			powerLevelUpgrade1 == 0 && 
			powerLevelUpgrade2 == 0 && 
			paused == false && 
			player.getCurrentWeapon() != 2){
			//New Wave
			if(waveStartTimer == 0 && enemies.size() == 0){
				waveNumber++;
				waveStart = false;
				waveStartTimer = System.nanoTime();
			}else{
				WaveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
				if(WaveStartTimerDiff > waveDelay){
					waveStart = true;
					waveStartTimer = 0;
					WaveStartTimerDiff = 0;
				}
			}
			//Create enemies
			if(waveStart && enemies.size() == 0){
				createNewEnemies();
			}
			
			//PLAYER
			player.update();
			
			//BULLET
			for(int i = 0; i < bullets.size(); i++){
				boolean remove = bullets.get(i).update();
				if(remove){
					bullets.remove(i);
					i--;
				}
			}
			//ENEMY
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).update();
				if(enemies.get(i).getTimer() == -1){
					enemies.get(i).setTimer(50);
				}
			}
			
			for(int i = 0; i < bombs.size(); i++){
				bombs.get(i).update();
				int bombAlpha = bombs.get(i).getAlpha();
				if(bombAlpha <= 0){
					if(bombType != 4){
						bombs.remove(i);
					}else{
						bombs.remove(i);//FALLOUT TIMER
					}
				}
			}
			
			//Powerup
			for(int i = 0; i < powerups.size(); i++){
				powerups.get(i).update();
				if(powerups.get(i).getFadeTimer() == 0){
					powerups.remove(i);
				}
			}
			//Explosion update
			
			for(int i = 0; i < explosions.size(); i++){
				boolean remove = explosions.get(i).update();
				if(remove){
					explosions.remove(i);
					i--;
				}
			}
			//Text Update
			for(int i = 0; i < texts.size(); i++){
				boolean remove = texts.get(i).update();
				if(remove){
					texts.remove(i);
					i--;
				}
			}
			//BULLET ENEMY COLLISION
			for(int i = 0; i < bullets.size(); i++){
				
				Bullet b = bullets.get(i);
				double bx = b.getx();
				double by = b.gety();
				double br = b.getr();
				
				for(int j = 0; j < enemies.size(); j++){
					
					Enemy e = enemies.get(j);
					double ex = e.getx();
					double ey = e.gety();
					double er = e.getr();
					
					double dx = bx - ex;
					double dy = by - ey;
					double dist = Math.sqrt(dx * dx + dy*  dy);
					
					if(dist < br + er){
						if(b.getType()==1){
							e.hit();
							bullets.remove(i);
							i--;
						}else{
							
						}	
						break;
					}
					
				}
				
			}
			//BULLET PLAYER COLLISION
			for(int i = 0; i < bullets.size(); i++){
				
				Bullet b = bullets.get(i);
				double bx = b.getx();
				double by = b.gety();
				double br = b.getr();		
					double px = player.getx();
					double py = player.gety();
					double pr = player.getr();
					
					double dx = bx - px;
					double dy = by - py;
					double dist = Math.sqrt(dx * dx + dy*  dy);
					
					if(dist < br + pr){
						if(b.getType()==2){
							if(!player.isrecovering()){
								player.loseLife();
								bullets.remove(i);
								i--;
							}	
						}else{
							
						}	
						break;
					}
				
			}
			//BOMB ENEMY COLLISION
			for(int i = 0; i < bombs.size(); i++){
				if(bombs.get(i).getState() == 2){
					Bomb b = bombs.get(i);
					double bx = b.getx();
					double by = b.gety();
					double br = 48;
					if(b.gettype() == 1){
						br = 48;
					}else if(b.gettype() == 2){
						br = 63;
					}else if(b.gettype() == 3){
						br = 148;
					}else if(b.gettype() == 4){
						br = 498;
					}
					
					for(int j = 0; j < enemies.size(); j++){
						Enemy e = enemies.get(j);
						double ex = e.getx();
						double ey = e.gety();
						double er = e.getr();
						
						double dx = bx - ex;
						double dy = by - ey;
						double dist = Math.sqrt(dx * dx + dy*  dy);
						
						if(dist < br + er){
							int bombAlpha = b.getAlpha();
							int timePick = 200;
							if(bombType == 1){
								timePick = 200;
							}else if(bombType == 2){
								timePick = 180;
							}else if(bombType == 3){
								timePick = 150;
							}else if(bombType == 4){
								timePick = 100;
							}
							if(bombAlpha > timePick){//200 is really cool but 0 would be crazy!
								if(bombType < 3){
									enemies.get(j).healthChange("-", 3);//5 is deadly
								}else if(bombType == 3){
									enemies.get(j).healthChange("-", 5);//5 is deadly
								}else if(bombType == 4){
									enemies.get(j).healthChange("-", 10);//5 is deadly
								}
								j--;
								break; //Stops for loop
							}
	
						}
					}	
				}
				
			}
			//CHECK DEAD ENEMIES
			for(int i = 0; i < enemies.size(); i++){
				if(enemies.get(i).isDead()){
					Enemy e = enemies.get(i);
					
					//chance for powerup
					double rand = Math.random();
					if(rand < .001){
						powerups.add(new PowerUp(1, e.getx(), e.gety(), 500));
					}
					else if(rand < .060){
						powerups.add(new PowerUp(3, e.getx(), e.gety(), 500));
					}
					else if(rand < .100){
						powerups.add(new PowerUp(2, e.getx(), e.gety(), 500));
					}
					else if(rand < .110){
						powerups.add(new PowerUp(4, e.getx(), e.gety(), 500));
					}else{
						//powerups.add(new PowerUp(3, e.getx(), e.gety()));
					}
					
					player.addScore(e.getType() + e.getRank());
					enemies.remove(i);
					i--;
					
					e.explode();
					explosions.add(new Explosion(e.getx(), e.gety(), e.getr(), e.getr()+30));
				}
			}
			if(Player.powerLevelChecker == 5){
				Random r = new Random();
				Random r1 = new Random();
				
				powerLevelUpgrade1 = r.nextInt(3) + 1;
				powerLevelUpgrade2 = r1.nextInt(3) + 1;
				if(powerLevelUpgrade1 == powerLevelUpgrade2){
					powerLevelUpgrade2 ++;
				}
				if(powerLevelUpgrade2 > 3){
					powerLevelUpgrade2 = 1;
				}
				Player.powerLevelChecker = 0;
			}
			//Player Enemy Collision
			if(!player.isrecovering()){
				double px = player.getx();
				double py = player.gety();
				double pr = player.getr();
				for(int i = 0; i < enemies.size(); i++){
					Enemy e = enemies.get(i);
					double ex = e.getx();
					double ey = e.gety();
					double er = e.getr();
					
					double dx = px - ex;
					double dy = py - ey;
					double dist = Math.sqrt(dx * dx + dy *  dy);
					
					if(dist < pr + er){
						player.loseLife();
					}
				}
			}
			
			//Player Powerup Collision
			double px = player.getx();
			double py = player.gety();
			double pr = player.getr();
			for(int i = 0; i < powerups.size(); i++){

				PowerUp p = powerups.get(i);
				double x = p.getx();
				double y = p.gety();
				double r = p.getr();
				
				double dx = px - x;
				double dy = py - y;
				double dist = Math.sqrt(dx * dx + dy *  dy);
				
				//Colect		
				if(dist < pr + r){
					
					int type = p.gettype();
					
					if(type == 1){
						player.gainlife();
						texts.add(new Text(player.getx(), player.gety(), 2000, "Extra Life"));
					}
					if(type == 2){
						player.incresePower(1);
						texts.add(new Text(player.getx(), player.gety(), 2000, "Power"));
					}
					if(type == 3){
						player.incresePower(2);
						texts.add(new Text(player.getx(), player.gety(), 2000, "Double Power"));
					}
					if(type == 4){
						slowDownTimer = System.nanoTime();
						for(int j = 0; j < enemies.size(); j++){
							enemies.get(j).setSlow(true);
						}
						texts.add(new Text(player.getx(), player.gety(), 2000, "Slow Down"));
						
					}
					powerups.remove(i);
					
					
				}
			}
			
			if(slowDownTimer != 0){
				slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000000;
				if(slowDownTimerDiff > slowDownLength){
					slowDownTimer = 0;
					for(int j = 0; j < enemies.size(); j++){
						enemies.get(j).setSlow(false);
						slowDownLength = 6000;
					}
				}
			}
			if(player.getLives() <= 0){
				gameState = STATE.Dead;
			}
		}else if(gameState == STATE.Menu || gameState == STATE.CharSelect){
			menu.update();
		}else if(gameState == STATE.Dead){
			
		}
		
	}
	
	public void setslowDownTimer(long l){
		slowDownTimer = l;
	}
	public void setslowDownLength(int l){
		slowDownLength = l;
	}
	public int getBombAmount(){
		return bombAmount;
	}
	public void setBombAmount(int i){
		bombAmount = i;
	}
	private void gameRender(){
		if(gameState == STATE.Game){
			//Background
			g.setColor(new Color(0,100,255));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			//draw slow down screen
			if(slowDownTimer != 0){
				g.setColor(new Color(255, 255, 255, 64));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
			
			//Player
			player.draw(g);
			//Bullets
			for(int i = 0; i < bullets.size(); i++){
				bullets.get(i).draw(g);
			}
			//Enemies
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).draw(g);
			}
			//PowerUps
			for(int i = 0; i < powerups.size(); i++){
				powerups.get(i).draw(g);
			}
			
			//Draw explosions
			for(int i = 0; i < explosions.size(); i++){
				explosions.get(i).draw(g);
			}
			//Draw Text
			
			for(int i = 0; i < texts.size(); i++){
				texts.get(i).draw(g);
			}
			
			//Wave Number
			if(waveStartTimer != 0){
				
				g.setFont(new Font("Century Ghotic", Font.PLAIN, 40));
				String s = "- W A V E  " + waveNumber + "  -";
				int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				int alpha = (int)(255 * Math.sin(3.14 * WaveStartTimerDiff / waveDelay));
				if(alpha > 255){
					alpha = 255;
				}
				g.setColor(new Color(255, 255, 255, alpha));
				g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
			}
			if(player.getCurrentWeapon() == 2){
				g.setFont(new Font("Century Ghotic", Font.PLAIN, 30));
				g.setColor(new Color(50, 50, 50, 200));
				g.fillRect(0, 0, WIDTH, HEIGHT);
				if(bombType == 1){
					if(bombAmount > 0){
						g.setColor(Color.WHITE);
					}else{
						g.setColor(new Color(20, 20, 20, 230));
					}
				}else if(bombType == 2){
					if(bombAmount > 1){
						g.setColor(Color.WHITE);
					}else{
						g.setColor(new Color(20, 20, 20, 230));
					}
				}else if(bombType == 3){
					if(bombAmount > 4){
						g.setColor(Color.WHITE);
					}else{
						g.setColor(new Color(20, 20, 20, 230));
					}
				}else if(bombType == 4){
					if(bombAmount > 9){
						g.setColor(Color.WHITE);
					}else{
						g.setColor(new Color(20, 20, 20, 230));
					}
				}
				
				g.drawString("x", Menu.mxg-7, Menu.myg+7);
				g.setColor(Color.WHITE);
				g.drawString("Place Bomb", 715, 850);
				g.setFont(new Font("Century Ghotic", Font.PLAIN, 20));
				g.drawString("Bomb Points Left: " + bombAmount, 1200, 40);
				if(detonateButton){
					g.setColor(Color.YELLOW);
					g.fillRect(1470, 750, 100, 100);
				}
			}
			for(int i = 0; i < bombs.size(); i++){
				bombs.get(i).draw(g);
			}
			//Player Lives
			for(int i = 0; i < player.getLives(); i++){
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(3));
				g.fillOval((int) 25 + (25 * i), 20, 18, 18);
				g.setColor(Color.WHITE.darker());
				g.drawOval((int) 25 + (25 * i), 20, 18, 18);
				g.setStroke(new BasicStroke(1));
			}
			g.setFont(new Font("Century Ghotic", Font.PLAIN, 18));
			//g.drawString(String.valueOf(powerLevelUpgrade1), 10, 100);
			//g.drawString(String.valueOf(powerLevelUpgrade2), 10, 120);
			
			//DrawPlayer Power
			g.setColor(Color.YELLOW);
			g.fillRect(25, 50, player.getPower() * 20, 20);
			g.setColor(Color.YELLOW.darker());
			g.setStroke(new BasicStroke(2));
			for(int i = 0; i < player.getRequiredPower(); i++){
				g.drawRect(25 + 20*i, 50, 20, 20);
			}
			g.setStroke(new BasicStroke(2));
			//Draw Player Score
			g.setColor(Color.WHITE);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, 30));
			g.drawString("Score: " + player.getscore(), WIDTH - 180, 40);
			
			//Draw Slowdown
			
			if(slowDownTimer != 0){
				g.setColor(Color.WHITE);
				g.drawRect(20, 60, 100, 8);
				g.fillRect(20, 60, (int)(100 - 100.0 * slowDownTimerDiff / slowDownLength), 8);
			}
			
			if(powerLevelUpgrade1 != 0){
				g.setColor(new Color(50, 50, 50, 200));
				g.fillRect(0, 0, WIDTH, HEIGHT);
			}
			if(powerLevelUpgrade1 == 0){
				//nothing
			}else if(powerLevelUpgrade1 == 1){
				//Bomb
				g.setColor(Color.BLACK);
			}else if(powerLevelUpgrade1 == 2){
				//Recharge life
				g.setColor(Color.RED);
				
			}else if(powerLevelUpgrade1 == 3){
				//Slowdown
				g.setColor(Color.BLUE);
			}
			if(powerLevelUpgrade1 != 0){
				g.fillRoundRect(272, 180, 500, 500, 50, 50);
			}
			
			if(powerLevelUpgrade2 == 0){
				//nothing
			}else if(powerLevelUpgrade2 == 1){
				//Bomb
				g.setColor(Color.BLACK);
			}else if(powerLevelUpgrade2 == 2){
				//Recharge life
				g.setColor(Color.RED);
			}else if(powerLevelUpgrade2 == 3){
				//Slowdown
				g.setColor(Color.BLUE);
			}
			if(powerLevelUpgrade2 != 0){
				g.fillRoundRect(800, 180, 500, 500, 50, 50);
			}
		}else if(gameState == STATE.Dead){
				g.setColor(new Color(0,100,255));
				g.fillRect(0, 0, WIDTH, HEIGHT);
				g.setColor(Color.WHITE);
				g.setFont(new Font("Century Ghotic", Font.PLAIN, 40));
				String s = "- G A M E  O V E R -";
				int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2 - 20);
				g.setFont(new Font("Century Ghotic", Font.PLAIN, 14));
				g.drawString("F I N A L  S C O R E = " + player.getscore(), WIDTH / 2 - 80, HEIGHT / 2 + 20);
				
		}else if(gameState == STATE.Menu || gameState == STATE.CharSelect){
			menu.draw(g);	
		}
		
		if(paused == true){
			g.setColor(new Color(50, 50, 50, 200));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setFont(new Font("Century Ghotic", Font.PLAIN, 40));
			String s = "Game Paused";
			int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			g.setColor(new Color(220, 220, 220));
			g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);
		}
	}
	
	public void addLife(){
		player.gainlife();
	}
	public boolean getDetonateButton(){
		return detonateButton;
	}
	private void addBomb(){
		if(bombType == 1){
			if(bombAmount > 0){
				GamePanel.bombs.add(new Bomb(Menu.mxg - 7, Menu.myg + 7, 1, 1));
				bombAmount--;
			}
		}else if(bombType == 2){
			if(bombAmount > 1){
				GamePanel.bombs.add(new Bomb(Menu.mxg - 7, Menu.myg + 7, 2, 1));
				bombAmount-=2;
			}
		}else if(bombType == 3){
			if(bombAmount > 4){
				GamePanel.bombs.add(new Bomb(Menu.mxg - 7, Menu.myg + 7, 3, 1));
				bombAmount-=5;
			}
		}else if(bombType == 4){
			if(bombAmount > 9){
				GamePanel.bombs.add(new Bomb(Menu.mxg - 7, Menu.myg + 7, 4, 1));
				bombAmount-=10;
			}
		}
	}
	private void gameDraw(){
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	private void createNewEnemies(){
		enemies.clear();
		
		if(waveNumber == 1){
			for(int i = 0; i < 2; i++){
				enemies.add(new Enemy(1,1));
			}
			for(int i = 0; i < 2; i++){
				enemies.add(new Enemy(1,2));
			}
		}
		if(waveNumber == 2){
			for(int i = 0; i < 1; i++){
				enemies.add(new Enemy(2,1));
			}
			for(int i = 0; i < 3; i++){
				enemies.add(new Enemy(1,1));
			}
			for(int i = 0; i < 2; i++){
				enemies.add(new Enemy(1,2));
			}
		}
		if(waveNumber == 3){
			enemies.add(new Enemy(1,3));
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(1,4));
		}
		if(waveNumber == 4){
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(3,2));
			enemies.add(new Enemy(1,4));
		}
		if(waveNumber == 5){
			enemies.add(new Enemy(2,4));
			enemies.add(new Enemy(3,3));
			enemies.add(new Enemy(1,4));
		}
		if(waveNumber == 6){
			enemies.add(new Enemy(3,3));
			enemies.add(new Enemy(3,3));
			enemies.add(new Enemy(3,4));
		}
		if(waveNumber == 7){
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(3,4));
		}
		if(waveNumber == 8){
			enemies.add(new Enemy(3,3));
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(3,4));
		}
		if(waveNumber == 9){
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(2,3));
			enemies.add(new Enemy(3,4));
		}
		if(waveNumber == 10){
			enemies.add(new Enemy(3,3));
			enemies.add(new Enemy(3,3));
			enemies.add(new Enemy(3,4));
		}
		
	}
	public void keyTyped(KeyEvent key){
		
	}
	public void keyPressed(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_A){
			player.setLeft(true);
		}
		if(keyCode == KeyEvent.VK_D){
			player.setRight(true);
		}
		if(keyCode == KeyEvent.VK_W){
			player.setUp(true);
		}
		if(keyCode == KeyEvent.VK_S){
			player.setDown(true);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			player.setFiring(true);
			if(player.getCurrentWeapon() == 2){	
				addBomb();
			}
		}
		if(keyCode == KeyEvent.VK_P){
			if(paused == false){
				paused = true;
			}else{
				paused = false;
			}
		}
		if(keyCode == KeyEvent.VK_1){
			player.setCurrentWeapon(1);
		}
		if(keyCode == KeyEvent.VK_2){
			player.setCurrentWeapon(2);
			bombExplode = false;
		}
	}
	public void keyReleased(KeyEvent key){
		int keyCode = key.getKeyCode();
		if(keyCode == KeyEvent.VK_A){
			player.setLeft(false);
		}
		if(keyCode == KeyEvent.VK_D){
			player.setRight(false);
		}
		if(keyCode == KeyEvent.VK_W){
			player.setUp(false);
		}
		if(keyCode == KeyEvent.VK_S){
			player.setDown(false);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			player.setFiring(false);
		}
	}
	
}
