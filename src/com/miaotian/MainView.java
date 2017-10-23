package com.miaotian;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hfp.youtie.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements Runnable, Callback {

	private Context myContext;
	private SurfaceHolder myHolder;

	private Canvas myCanvas;
	private Resources res;
	private Paint myPaint;

	private Thread myThread;
	private boolean going;

	public static int SW = 480;
	public static int SH = 320;
	public static final int SPEED = 7;

	public static byte gameState;
	private byte gameSubState;

	public final byte GAME_LOGO = 0;
	public final byte GAME_ANYKEY = 1;
	public final byte GAME_MENU = 2;
	public final byte GAME_LOADING = 3;
	public final byte GAME_START = 4;
	public final byte GAME_CONTINE = 5;
	public final byte GAME_SET = 6;
	public final byte GAME_HELP = 7;
	public final byte GAME_ABOUT = 8;
	public final byte GAME_EXIT = 9;
	public static final byte GAME_PASS = 10;
	public final byte GAME_OVER = 11;

	private Bitmap bit_logo, bit_anykey;// LOGO�������ͼƬ
	private Bitmap[] bit_e;// ����ͼƬ
	private int x, y;
	private String[] str_info;// ����
	private int i_load;// ����ֵ
	private int time;// ʱ����Ʊ���

	public static int level;// ��Ϸ�ؿ�
	public static boolean next;// ���Թ���
	private int score;// ��Ϸ�÷�
	public static int force;// ը������
	public static int num;// ը������
	public static boolean detonate;// ����ը��
	public static int life;// ������
	public static int speed;// ����ٶ�

	private boolean isMove;// ��������Ƿ��ƶ�

	private int KEY;
	private boolean keyPress, keyReless;

	private Map map;
	private List<Bomb> bombList;
	private List<Enemy> enemyList;
	private Player player;

	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MainView(Context context) {
		super(context);
		myContext = context;
		myHolder = getHolder();
		myHolder.addCallback(this);
		setFocusable(true);
		myPaint = new Paint();
		res = myContext.getResources();

		gameState = 0;

		bit_logo = BitmapFactory.decodeResource(res, R.drawable.logo1);
		x = SW - bit_logo.getWidth() >> 1;
		y = SH - bit_logo.getHeight() >> 1;
	}

	public void draw() {
		try {
			myCanvas = myHolder.lockCanvas();

			switch (gameState) {
			case GAME_LOGO:
				myPaint.setColor(0xffffffff);
				myCanvas.drawRect(0, 0, SW, SH, myPaint);
				myCanvas.drawBitmap(bit_logo, x, y, myPaint);
				break;
			case GAME_ANYKEY:
				myPaint.setColor(0xffffffff);
				myCanvas.drawRect(0, 0, SW, SH, myPaint);
				myCanvas.drawBitmap(bit_logo, 0, 0, myPaint);
				if (time > 2) {
					myCanvas.drawBitmap(bit_anykey, x, y, myPaint);
				}
				break;
			case GAME_LOADING:
				myPaint.setColor(0xffffffff);
				myCanvas.drawRect(0, 0, SW, SH, myPaint);
				myCanvas.drawBitmap(bit_logo, x, y, myPaint);
				myPaint.setColor(0xff000000);
				myPaint.setTextAlign(Align.CENTER);
				int text_x = SW >> 1;
				int text_y = SH - 50;
				myCanvas.drawText(str_info[0], text_x, text_y, myPaint);
				myCanvas.drawText(i_load + str_info[1], text_x, text_y + 20,
						myPaint);
				break;
			case GAME_START:
				map.drawMap(myCanvas, myPaint, player.getOffx(), player
						.getOffy());
				for (int i = 0; i < bombList.size(); i++) {
					bombList.get(i).drawBomb(myCanvas, myPaint,
							player.getOffx(), player.getOffy());
				}
				for (int i = 0; i < enemyList.size(); i++) {
					enemyList.get(i).draw(myCanvas, myPaint, player.getOffx(),
							player.getOffy());
				}
				player.draw(myCanvas, myPaint);
				// ������
				myCanvas.drawBitmap(bit_e[0], 0, SH - bit_e[0].getHeight(),
						myPaint);
				myCanvas.drawBitmap(bit_e[1], SW - bit_e[1].getWidth(), SH
						- bit_e[1].getHeight(), myPaint);
				break;
			case GAME_PASS:
				myPaint.setColor(0xff000000);
				myCanvas.drawRect(0, 0, SW, SH, myPaint);
				myPaint.setColor(0xffffffff);
				myPaint.setTextAlign(Align.CENTER);
				myCanvas.drawText(str_info[0] + level, x, y, myPaint);
				myCanvas.drawText(str_info[1] + life, x, y + 30, myPaint);
				break;
			case GAME_OVER:
				myPaint.setColor(0xff000000);
				myCanvas.drawRect(0, 0, SW, SH, myPaint);
				myPaint.setColor(0xffffffff);
				myPaint.setTextAlign(Align.CENTER);
				myCanvas.drawText(str_info[0], x, y, myPaint);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myCanvas != null) {
				myHolder.unlockCanvasAndPost(myCanvas);
			}
		}
	}

	public void logic() {
		switch (gameState) {
		case GAME_LOGO:
			switch (gameSubState) {
			case 0:
				if (time < 30) {
					time++;
				} else {
					bit_logo = BitmapFactory.decodeResource(res,
							R.drawable.logo2);
					x = SW - bit_logo.getWidth() >> 1;
					y = SH - bit_logo.getHeight() >> 1;
					time = 0;
					gameSubState++;
				}
				break;
			case 1:
				if (time < 30) {
					time++;
				} else {
					bit_logo = BitmapFactory.decodeResource(res,
							R.drawable.cgbk);
					bit_anykey = BitmapFactory.decodeResource(res,
							R.drawable.anykey);
					x = SW - bit_anykey.getWidth() >> 1;
					y = SH - bit_anykey.getHeight() - 30;
					time = 0;
					gameState = GAME_ANYKEY;
				}
				break;
			}
			break;
		case GAME_ANYKEY:
			time = time < 5 ? time + 1 : 0;
			break;
		case GAME_LOADING:
			if (i_load < 100) {
				i_load++;
				if (i_load == 50) {
					initGame();
				}
			} else {
				gameState = GAME_START;
				i_load = 0;
				str_info = null;
				bit_logo = null;
			}
			break;
		case GAME_START:
			for (int i = 0; i < bombList.size(); i++) {
				bombList.get(i).bombLogic();
				if (bombList.get(i).getState() == 3) {
					checkBomb(i);
				} else if (bombList.get(i).getState() == 4) {
					bombList.remove(i);
				}
			}
			for (int i = 0; i < enemyList.size(); i++) {
				if (player.getState() < 3) {
					enemyList.get(i).logic();
					changeDir(i);
					checkNpcDead(i);
					if (enemyList.get(i).getState() == 0) {
						checkPlayerDead(i);
					}
					if (enemyList.get(i).getState() == 3) {
						enemyList.remove(i);
					}
				}
			}
			if (player.getState() == 1) {
				if (isMove) {
					player.move();
				}
				// if (enemyList.size() == 0) {
				// str_info = new String[] { "STAGE    ", "LIFE    " };
				// x = SW >> 1;
				// y = SH >> 1;
				// next = true;
				// }
				for (int i = 0; i < enemyList.size(); i++) {
					if (enemyList.get(i).getState() == 0) {
						next = false;
						return;
					} else if (i == enemyList.size() - 1) {
						str_info = new String[] { "STAGE    ", "LIFE    " };
						x = SW >> 1;
						y = SH >> 1;
						next = true;
					}
				}
			} else if (player.getState() == 2) {
				player.dead();
			} else if (player.getState() == 3) {
				if (time < 10) {
					time++;
				} else {
					x = SW >> 1;
					y = SH >> 1;
					if (life > 0) {
						str_info = new String[] { "STAGE    ", "LIFE    " };
						life--;
						gameState = GAME_PASS;
					} else {
						str_info = new String[] { "GAME   OVER" };
						gameState = GAME_OVER;
					}
					map = null;
					player = null;
					bombList = null;
					enemyList = null;
					time = 0;
				}
			}
			break;
		case GAME_PASS:
			if (time < 10) {
				time++;
			} else {
				loadGame();
				gameState = GAME_START;
				str_info = null;
				time = 0;
			}
			break;
		case GAME_OVER:
			if (time < 10) {
				time++;
			} else {
				initGame();
				gameState = GAME_START;
				str_info = null;
				time = 0;
			}
			break;
		}
	}

	// ��ʼ����Ϸ����
	public void initGame() {
		level = 1;
		score = 0;
		force = 1;
		num = 1;
		detonate = false;
		life = 3;
		speed = 5;
		isMove = false;
		loadGame();
	}

	// ������Ϸ����
	public void loadGame() {
		map = new Map(myContext);
		bombList = new ArrayList<Bomb>();
		enemyList = new ArrayList<Enemy>();
		player = new Player(myContext);
		player.setX(32);
		player.setY(32);
		player.setSpeed(speed);
		player.setState((byte) 1);
		initEnemy();
		next = false;
		bit_e = new Bitmap[2];
		bit_e[0] = BitmapFactory.decodeResource(res, R.drawable.e1);
		bit_e[1] = BitmapFactory.decodeResource(res, R.drawable.e2);

		// Enemy enemy = new Enemy(myContext);
		// enemy.setX(32);
		// enemy.setY(128);
		// enemy.setState((byte) 0);
		// enemyList.add(enemy);
		// detonate = true;
	}

	// ��ʼ�����˷������ڵ�ͼ���漴������
	public void initEnemy() {
		Random random = new Random();
		int row = 0;
		int col = 0;
		while (enemyList.size() < 10) {
			Enemy enemy = new Enemy(myContext);
			col = random.nextInt((Map.colNum - 5) * Map.W) / Map.W + 5;
			row = random.nextInt((Map.rowNum - 5) * Map.H) / Map.H + 5;
			if (Map.map[row][col] == 1) {
				enemy.setX(col * Map.W);
				enemy.setY(row * Map.H);
				enemy.setState((byte) 0);
				if (level == 1) {
					enemy.setSpeed(SPEED);
					enemy.setIndex((byte) 0);
				} else if (level == 2) {
					if (enemyList.size() < 5) {
						enemy.setSpeed(SPEED);
						enemy.setIndex((byte) 0);
					} else {
						enemy.setSpeed(SPEED + 1);
						enemy.setIndex((byte) 1);
					}
				} else if (level == 3) {
					if (enemyList.size() < 3) {
						enemy.setSpeed(SPEED);
						enemy.setIndex((byte) 0);
					} else if (enemyList.size() < 6) {
						enemy.setSpeed(SPEED + 1);
						enemy.setIndex((byte) 1);
					} else {
						enemy.setSpeed(SPEED + 2);
						enemy.setIndex((byte) 2);
					}
				} else if (level == 4) {
					if (enemyList.size() < 2) {
						enemy.setSpeed(SPEED);
						enemy.setIndex((byte) 0);
					} else if (enemyList.size() < 4) {
						enemy.setSpeed(SPEED + 1);
						enemy.setIndex((byte) 1);
					} else if (enemyList.size() < 6) {
						enemy.setSpeed(SPEED + 2);
						enemy.setIndex((byte) 2);
					} else {
						enemy.setSpeed(SPEED + 3);
						enemy.setIndex((byte) 3);
					}
				} else {
					enemy.setIndex((byte) random.nextInt(4));
					switch (enemy.getIndex()) {
					case 0:
						enemy.setSpeed(SPEED);
						break;
					case 1:
						enemy.setSpeed(SPEED + 1);
						break;
					case 2:
						enemy.setSpeed(SPEED + 2);
						break;
					case 3:
						enemy.setSpeed(SPEED + 3);
						break;
					}
				}
				enemyList.add(enemy);
			} else {
				enemy = null;
			}
		}
	}

	// ը���໥�����ķ���
	public void checkBomb(int i) {
		// ��⵱ǰը�������ը��
		if (i + 1 < bombList.size()) {
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[0]; j++) {
				for (int j2 = i + 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow() - j, bombList.get(i)
							.getCol(), bombList.get(j2).getRow(), bombList.get(
							j2).getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[1]; j++) {
				for (int j2 = i + 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow() + j, bombList.get(i)
							.getCol(), bombList.get(j2).getRow(), bombList.get(
							j2).getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[2]; j++) {
				for (int j2 = i + 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow(), bombList.get(i)
							.getCol()
							- j, bombList.get(j2).getRow(), bombList.get(j2)
							.getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[3]; j++) {
				for (int j2 = i + 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow(), bombList.get(i)
							.getCol()
							+ j, bombList.get(j2).getRow(), bombList.get(j2)
							.getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
		}
		// ��⵱ǰը��ǰ���ը��
		if (i - 1 >= 0) {
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[0]; j++) {
				for (int j2 = i - 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow() - j, bombList.get(i)
							.getCol(), bombList.get(j2).getRow(), bombList.get(
							j2).getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[1]; j++) {
				for (int j2 = i - 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow() + j, bombList.get(i)
							.getCol(), bombList.get(j2).getRow(), bombList.get(
							j2).getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[2]; j++) {
				for (int j2 = i - 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow(), bombList.get(i)
							.getCol()
							- j, bombList.get(j2).getRow(), bombList.get(j2)
							.getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
			// ����
			for (int j = 1; j <= bombList.get(i).getLength()[3]; j++) {
				for (int j2 = i - 1; j2 < bombList.size(); j2++) {
					if (checkBomb(bombList.get(i).getRow(), bombList.get(i)
							.getCol()
							+ j, bombList.get(j2).getRow(), bombList.get(j2)
							.getCol())) {
						bombList.get(j2).setState((byte) 3);
						bombList.get(j2).setTime(bombList.get(i).getTime());
					}
				}
			}
		}
	}

	public boolean checkBomb(int row1, int col1, int row2, int col2) {
		if (row1 == row2 && col1 == col2) {
			return true;
		}
		return false;
	}

	// �жϵ����Ƿ�����
	public void checkNpcDead(int i) {
		int row = 0;
		int col = 0;
		int w = Map.W - 5;
		int h = Map.H - 5;
		for (int j = 0; j < bombList.size(); j++) {
			row = bombList.get(j).getRow();
			col = bombList.get(j).getCol();
			if (bombList.get(j).getState() == 3) {
				// ����
				if (isCollision(enemyList.get(i).getX(), enemyList.get(i)
						.getY(), w, h, col * Map.W, row * Map.H, w, h)) {
					enemyList.get(i).setFrameX((byte) 4);
				}
				// ����
				for (int j2 = 0; j2 <= bombList.get(j).getLength()[0]; j2++) {
					if (isCollision(enemyList.get(i).getX(), enemyList.get(i)
							.getY(), w, h, col * Map.W, (row - j2) * Map.H, w,
							h)) {
						enemyList.get(i).setFrameX((byte) 4);
					}
				}
				// ����
				for (int j2 = 0; j2 <= bombList.get(j).getLength()[1]; j2++) {
					if (isCollision(enemyList.get(i).getX(), enemyList.get(i)
							.getY(), w, h, col * Map.W, (row + j2) * Map.H, w,
							h)) {
						enemyList.get(i).setFrameX((byte) 4);
					}
				}
				// ����
				for (int j2 = 0; j2 <= bombList.get(j).getLength()[2]; j2++) {
					if (isCollision(enemyList.get(i).getX(), enemyList.get(i)
							.getY(), w, h, (col - j2) * Map.W, row * Map.H, w,
							h)) {
						enemyList.get(i).setFrameX((byte) 4);
					}
				}
				// ����
				for (int j2 = 0; j2 <= bombList.get(j).getLength()[3]; j2++) {
					if (isCollision(enemyList.get(i).getX(), enemyList.get(i)
							.getY(), w, h, (col + j2) * Map.W, row * Map.H, w,
							h)) {
						enemyList.get(i).setFrameX((byte) 4);
					}
				}
			}
		}
	}

	// ��������ը���ı䷽��ķ���
	public void changeDir(int i) {
		int row = 0;
		int col = 0;
		int row_eny = 0;
		int col_eny = 0;
		for (int j = 0; j < bombList.size(); j++) {
			row = bombList.get(j).getRow();
			col = bombList.get(j).getCol();
			if (bombList.get(j).getState() == 1) {
				switch (enemyList.get(i).getDir()) {
				case 0:// ����
					row_eny = (enemyList.get(i).getY() - enemyList.get(i)
							.getSpeed())
							/ Map.H;
					col_eny = (enemyList.get(i).getX() + (Map.W >> 1)) / Map.W;
					if (row_eny == row && col_eny == col) {
						enemyList.get(i).setDir((byte) 1);
					}
					break;
				case 1:// ����
					row_eny = (enemyList.get(i).getY() + Map.H + enemyList.get(
							i).getSpeed())
							/ Map.H;
					col_eny = (enemyList.get(i).getX() + (Map.W >> 1)) / Map.W;
					if (row_eny == row && col_eny == col) {
						enemyList.get(i).setDir((byte) 0);
					}
					break;
				case 2:// ����
					row_eny = (enemyList.get(i).getY() + (Map.H >> 1)) / Map.H;
					col_eny = (enemyList.get(i).getX() - enemyList.get(i)
							.getSpeed())
							/ Map.W;
					if (row_eny == row && col_eny == col) {
						enemyList.get(i).setDir((byte) 3);
					}
					break;
				case 3:// ����
					row_eny = (enemyList.get(i).getY() + (Map.H >> 1)) / Map.H;
					col_eny = (enemyList.get(i).getX() + Map.W + enemyList.get(
							i).getSpeed())
							/ Map.W;
					if (row_eny == row && col_eny == col) {
						enemyList.get(i).setDir((byte) 2);
					}
					break;
				}
			}
		}
	}

	// �ж�����Ƿ������������������˼���ը��ը����
	public void checkPlayerDead(int i) {
		int row = 0;
		int col = 0;
		int w = Map.W >> 1;
		int h = Map.H >> 1;
		if (player.getState() == 1) {
			// ��Ҹ�npc
			if (isCollision(player.getX(), player.getY(), w, h, enemyList
					.get(i).getX(), enemyList.get(i).getY(), w, h)) {
				player.setState((byte) 2);
				player.setFrameY((byte) 4);
				speed = SPEED;
				detonate = false;
			}
			// ��Ҹ�ը��
			for (int j = 0; j < bombList.size(); j++) {
				row = bombList.get(j).getRow();
				col = bombList.get(j).getCol();
				if (bombList.get(j).getState() == 3) {
					// ����
					if (isCollision(player.getX(), player.getY(), w, h, col
							* Map.W, row * Map.H, w, h)) {
						player.setState((byte) 2);
						player.setFrameY((byte) 4);
						speed = SPEED;
						detonate = false;
					}
					// ����
					for (int j2 = 0; j2 <= bombList.get(j).getLength()[0]; j2++) {
						if (isCollision(player.getX(), player.getY(), w, h, col
								* Map.W, (row + j2) * Map.H, w, h)) {
							player.setState((byte) 2);
							player.setFrameY((byte) 4);
							speed = SPEED;
							detonate = false;
						}
					}
					// ����
					for (int j2 = 0; j2 <= bombList.get(j).getLength()[1]; j2++) {
						if (isCollision(player.getX(), player.getY(), w, h, col
								* Map.W, (row - j2) * Map.H, w, h)) {
							player.setState((byte) 2);
							player.setFrameY((byte) 4);
							speed = SPEED;
							detonate = false;
						}
					}
					// ����
					for (int j2 = 0; j2 <= bombList.get(j).getLength()[2]; j2++) {
						if (isCollision(player.getX(), player.getY(), w, h,
								(col - j2) * Map.W, row * Map.H, w, h)) {
							player.setState((byte) 2);
							player.setFrameY((byte) 4);
							speed = SPEED;
							detonate = false;
						}
					}
					// ����
					for (int j2 = 0; j2 <= bombList.get(j).getLength()[3]; j2++) {
						if (isCollision(player.getX(), player.getY(), w, h,
								(col + j2) * Map.W, row * Map.H, w, h)) {
							player.setState((byte) 2);
							player.setFrameY((byte) 4);
							speed = SPEED;
							detonate = false;
						}
					}
				}
			}
		}
	}

	public boolean isCollision(int x1, int y1, int w1, int h1, int x2, int y2,
			int w2, int h2) {
		if (x1 + w1 < x2 || x1 > x2 + w2 || y1 + h1 < y2 || y1 > y2 + h2) {
			return false;
		}
		return true;
	}

	// ��ը���ķ���
	public void addBomb() {
		int row = (int) ((player.getY() + (Map.W >> 1)) / Map.W);
		int col = (int) ((player.getX() + (Map.H >> 1)) / Map.H);
		if (Map.map[row][col] == 1 && bombList.size() < num) {
			for (int i = 0; i < bombList.size(); i++) {
				if ((row == bombList.get(i).getRow() && col == bombList.get(i)
						.getCol())) {
					return;
				}
			}
			Bomb bomb = new Bomb(myContext, row, col, force);
			bomb.setState((byte) 1);
			bomb.setBombLength();
			bombList.add(bomb);
		}
	}

	// ����ը���ķ���
	public void detonateBomt() {
		if (detonate) {
			for (int i = 0; i < bombList.size(); i++) {
				if (bombList.get(i).getState() == 1) {
					bombList.get(i).setState((byte) 3);
					return;
				}
			}
		}
	}

	long startTime, endTime, tempTime;

	@Override
	public void run() {
		while (going) {
			try {
				draw();
				logic();
				keyPross();
				endTime = System.currentTimeMillis();
				if (startTime - endTime > 80) {
					tempTime = startTime - endTime;
					if (tempTime < 0) {
						tempTime = 0;
					} else {
						Thread.sleep(tempTime);
					}
				}
				Thread.sleep(80);
				startTime = System.currentTimeMillis();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		SW = getWidth();
		SH = getHeight();
		myThread = new Thread(this);
		going = true;
		myThread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		going = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (gameState) {
		case GAME_ANYKEY:
			str_info = new String[] { "�����С���", "%" };
			i_load = 0;
			bit_logo = BitmapFactory.decodeResource(res, R.drawable.logo03);
			this.x = SW - bit_logo.getWidth() >> 1;
			this.y = SH - bit_logo.getHeight() >> 1;
			gameState = GAME_LOADING;
			bit_anykey = null;
			break;
		case GAME_START:
			int w = 40;
			int h = 40;
			if (event.getAction() == event.ACTION_DOWN) {
				if (isCollision(x, y, 0, 0, 0 + w, SH - h * 3, w, h)) { // �Ϸ���
					player.setDir((byte) 0);
					isMove = true;
				} else if (isCollision(x, y, 0, 0, 0 + w, SH - h, w, h)) {// �·���
					player.setDir((byte) 1);
					isMove = true;
				} else if (isCollision(x, y, 0, 0, 0, SH - h * 2, w, h)) {// ����
					player.setDir((byte) 2);
					isMove = true;
				} else if (isCollision(x, y, 0, 0, 0 + w * 2, SH - h * 2, w, h)) {// �ҷ���
					player.setDir((byte) 3);
					isMove = true;
				} else if (isCollision(x, y, 0, 0, SW - bit_e[1].getWidth(), SH
						- bit_e[1].getHeight(), w, h)) {// ��ը��
					addBomb();
				} else if (isCollision(x, y, 0, 0, SW - bit_e[1].getWidth(), SH
						- h, w, h)) {// ��ը��
					addBomb();
				} else if (isCollision(x, y, 0, 0, SW - w, SH
						- bit_e[1].getHeight(), w, h)) {// ����ը��
					detonateBomt();
				} else if (isCollision(x, y, 0, 0, SW - w, SH - h, w, h)) {// ����ը��
					detonateBomt();
				}
			} else if (event.getAction() == event.ACTION_UP) {
				isMove = false;
			}
			break;
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		KEY = keyCode;
		keyPress = true;
		keyReless = true;
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		keyReless = false;
		return true;
	}

	public void keyPross() {
		if (keyPress && keyReless) {
			switch (gameState) {
			case GAME_ANYKEY:
				str_info = new String[] { "�����С���", "%" };
				i_load = 0;
				bit_logo = BitmapFactory.decodeResource(res, R.drawable.logo03);
				x = SW - bit_logo.getWidth() >> 1;
				y = SH - bit_logo.getHeight() >> 1;
				gameState = GAME_LOADING;
				bit_anykey = null;
				break;
			case GAME_START:
				switch (KEY) {
				case KeyEvent.KEYCODE_DPAD_UP:
					player.setDir((byte) 0);
					player.move();
					break;
				case KeyEvent.KEYCODE_DPAD_DOWN:
					player.setDir((byte) 1);
					player.move();
					break;
				case KeyEvent.KEYCODE_DPAD_LEFT:
					player.setDir((byte) 2);
					player.move();
					break;
				case KeyEvent.KEYCODE_DPAD_RIGHT:
					player.setDir((byte) 3);
					player.move();
					break;
				case KeyEvent.KEYCODE_DPAD_CENTER:
				case 66:
					int row = (int) ((player.getY() + (Map.W >> 1)) / Map.W);
					int col = (int) ((player.getX() + (Map.H >> 1)) / Map.H);
					if (Map.map[row][col] == 1 && bombList.size() < num) {
						for (int i = 0; i < bombList.size(); i++) {
							if ((row == bombList.get(i).getRow() && col == bombList
									.get(i).getCol())) {
								return;
							}
						}
						Bomb bomb = new Bomb(myContext, row, col, force);
						bomb.setState((byte) 1);
						bomb.setBombLength();
						bombList.add(bomb);
					}
					break;
				}
				break;
			}
			if (KEY == KeyEvent.KEYCODE_BACK) {
				BombGame.instence.finish();
			}
		}
	}
}
