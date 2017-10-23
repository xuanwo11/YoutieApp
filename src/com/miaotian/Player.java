package com.miaotian;

import com.hfp.youtie.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Player {

	private int x, y, speed, offx, offy;
	private byte dir;
	private byte frameX, frameY;
	private byte state;// 1 活着；2死亡；3不画

	private Bitmap bit;

	public Player(Context context) {
		Resources res = context.getResources();
		bit = BitmapFactory.decodeResource(res, R.drawable.player);
		dir = 1;
		state = 1;
		offx = 0;
		offy = 0;
	}

	public void draw(Canvas canvas, Paint paint) {
		if (state < 3) {
			canvas.save();
			canvas.clipRect(x + offx, y + offy, x + offx + Map.W, y + offy
					+ Map.H);
			canvas.drawBitmap(bit, x + offx - frameX * Map.W, y + offy - frameY
					* Map.H, paint);
			canvas.restore();
		}
	}

	public void move() {
		if (state != 1) {
			return;
		}
		int row = 0;
		int col = 0;
		switch (dir) {
		case 0:// 向上
			if (y - speed >= 0) {
				row = (y - speed) / Map.H;
				col = (x + (Map.W >> 1)) / Map.W;
				switch (Map.map[row][col]) {
				case 1:
					y -= speed;
					x = col * Map.W;
					break;
				case 5:
					y -= speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.force++;
					break;
				case 6:
					y -= speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.num++;
					break;
				case 7:
					y -= speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.speed = MainView.SPEED + 2;
					break;
				case 8:
					y -= speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.detonate = true;
					break;
				case 4:
					y -= speed;
					x = col * Map.W;
					if (MainView.next) {
						MainView.level++;
						MainView.life++;
						MainView.gameState = MainView.GAME_PASS;
					}
					break;
				}
			}
			frameY = 1;
			break;
		case 1:// 向下
			if (y + Map.H + speed < Map.rowNum * Map.H) {
				row = (y + Map.H + speed) / Map.H;
				col = (x + (Map.W >> 1)) / Map.W;
				switch (Map.map[row][col]) {
				case 1:
					y += speed;
					x = col * Map.W;
					break;
				case 5:
					y += speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.force++;
					break;
				case 6:
					y += speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.num++;
					break;
				case 7:
					y += speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.speed = MainView.SPEED + 2;
					break;
				case 8:
					y += speed;
					x = col * Map.W;
					Map.map[row][col] = 1;
					MainView.detonate = true;
					break;
				case 4:
					y += speed;
					x = col * Map.W;
					if (MainView.next) {
						MainView.level++;
						MainView.life++;
						MainView.gameState = MainView.GAME_PASS;
					}
					break;
				}
			}
			frameY = 0;
			break;
		case 2:// 向左
			if (x - speed >= 0) {
				row = (y + (Map.H >> 1)) / Map.H;
				col = (x - speed) / Map.W;
				switch (Map.map[row][col]) {
				case 1:
					x -= speed;
					y = row * Map.H;
					break;
				case 5:
					x -= speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.force++;
					break;
				case 6:
					x -= speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.num++;
					break;
				case 7:
					x -= speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.speed = MainView.SPEED + 2;
					break;
				case 8:
					x -= speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.detonate = true;
					break;
				case 4:
					x -= speed;
					y = row * Map.H;
					if (MainView.next) {
						MainView.level++;
						MainView.life++;
						MainView.gameState = MainView.GAME_PASS;
					}
					break;
				}
			}
			frameY = 2;
			break;
		case 3:// 向右
			if (x + Map.W + speed < Map.colNum * Map.W) {
				row = (y + (Map.H >> 1)) / Map.H;
				col = (x + Map.W + speed) / Map.W;
				switch (Map.map[row][col]) {
				case 1:
					x += speed;
					y = row * Map.H;
					break;
				case 5:
					x += speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.force++;
					break;
				case 6:
					x += speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.num++;
					break;
				case 7:
					x += speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.speed = MainView.SPEED + 2;
					break;
				case 8:
					x += speed;
					y = row * Map.H;
					Map.map[row][col] = 1;
					MainView.detonate = true;
					break;
				case 4:
					x += speed;
					y = row * Map.H;
					if (MainView.next) {
						MainView.level++;
						MainView.life++;
						MainView.gameState = MainView.GAME_PASS;
					}
					break;
				}
			}
			frameY = 3;
			break;
		}

		if (x < Map.colNum * Map.W - (MainView.SW - MainView.SW / 3)) {
			offx = -(x - MainView.SW / 3);
			if (offx > 0) {
				offx = 0;
			}
		}

		if (y < Map.rowNum * Map.H - (MainView.SH - MainView.SH / 3)) {
			offy = -(y - MainView.SH / 3);
			if (offy > 0) {
				offy = 0;
			}
		}

		frameRun();
	}

	public void frameRun() {
		if (frameX < 3) {
			frameX++;
		} else {
			frameX = 0;
		}
	}

	public void dead() {
		if (frameY == 4) {
			if (frameX < 3) {
				frameX++;
			} else {
				frameY = 5;
				frameX = 0;
			}
		} else if (frameY == 5) {
			if (frameX < 3) {
				frameX++;
			} else {
				state = 3;
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public byte getDir() {
		return dir;
	}

	public void setDir(byte dir) {
		this.dir = dir;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public byte getFrameX() {
		return frameX;
	}

	public void setFrameX(byte frameX) {
		this.frameX = frameX;
	}

	public byte getFrameY() {
		return frameY;
	}

	public void setFrameY(byte frameY) {
		this.frameY = frameY;
	}

	public int getOffx() {
		return offx;
	}

	public void setOffx(int offx) {
		this.offx = offx;
	}

	public int getOffy() {
		return offy;
	}

	public void setOffy(int offy) {
		this.offy = offy;
	}
}
