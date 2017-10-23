package com.miaotian;

import java.util.Random;

import com.hfp.youtie.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy {

	private int x, y, speed;
	private byte dir;// 方向
	private byte frameX, frameY;
	private byte index;
	private byte state;// 0活着；1死亡；2死亡后的分数；3从list中删除
	private int count, time;
	private Bitmap[] bit;
	private Bitmap bit_dead;
	private Random random;

	public Enemy(Context context) {
		Resources res = context.getResources();
		random = new Random();
		bit = new Bitmap[4];
		bit[0] = BitmapFactory.decodeResource(res, R.drawable.npc0);
		bit[1] = BitmapFactory.decodeResource(res, R.drawable.npc1);
		bit[2] = BitmapFactory.decodeResource(res, R.drawable.npc2);
		bit[3] = BitmapFactory.decodeResource(res, R.drawable.npc3);
		bit_dead = BitmapFactory.decodeResource(res, R.drawable.npc_death);
	}

	public void draw(Canvas canvas, Paint paint, int offx, int offy) {
		canvas.save();
		canvas.clipRect(x + offx, y + offy, x + offx + Map.W, y + offy + Map.H);
		switch (state) {
		case 0:
			canvas.drawBitmap(bit[index], x + offx - frameX * Map.W, y + offy
					- frameY * Map.H, paint);
			break;
		case 1:
			canvas.drawBitmap(bit_dead, x + offx - frameX * Map.W, y + offy
					- frameY * Map.H, paint);
			break;
		case 2:
			canvas.drawBitmap(bit[index], x + offx - 4 * Map.W, y + offy - 1
					* Map.H, paint);
			break;
		}
		canvas.restore();
	}

	public void logic() {
		switch (state) {
		case 0:
			if (frameX < 3) {
				move();
				changeDir();
			}
			frameRun();
			break;
		case 1:
		case 2:
			dead();
			break;
		}
	}

	public void move() {
		int row = 0;
		int col = 0;
		switch (dir) {
		case 0:// 向上
			if (y - speed >= 0) {
				row = (y - speed) / Map.H;
				col = (x + (Map.W >> 1)) / Map.W;
				if (Map.map[row][col] == 1) {
					y -= speed;
					x = col * Map.W;
				} else {
					dir = (byte) random.nextInt(4);
				}
			} else {
				dir = (byte) random.nextInt(4);
			}
			frameY = 0;
			break;
		case 1:// 向下
			if (y + Map.H + speed < Map.rowNum * Map.H) {
				row = (y + Map.H + speed) / Map.H;
				col = (x + (Map.W >> 1)) / Map.W;
				if (Map.map[row][col] == 1) {
					y += speed;
					x = col * Map.W;
				} else {
					dir = (byte) random.nextInt(4);
				}
			} else {
				dir = (byte) random.nextInt(4);
			}
			frameY = 1;
			break;
		case 2:// 向左
			if (x - speed >= 0) {
				row = (y + (Map.H >> 1)) / Map.H;
				col = (x - speed) / Map.W;
				if (Map.map[row][col] == 1) {
					x -= speed;
					y = row * Map.W;
				} else {
					dir = (byte) random.nextInt(4);
				}
			} else {
				dir = (byte) random.nextInt(4);
			}
			frameY = 0;
			break;
		case 3:// 向右
			if (x + Map.W + speed < Map.colNum * Map.W) {
				row = (y + (Map.H >> 1)) / Map.H;
				col = (x + Map.W + speed) / Map.W;
				if (Map.map[row][col] == 1) {
					x += speed;
					y = row * Map.W;
				} else {
					dir = (byte) random.nextInt(4);
				}
			} else {
				dir = (byte) random.nextInt(4);
			}
			frameY = 1;
			break;
		}
	}

	public void changeDir() {
		if (count < 20) {
			count++;
		} else {
			dir = (byte) random.nextInt(4);
			count = 0;
		}
	}

	public void frameRun() {
		if (frameX < 3) {
			frameX++;
		} else if (frameX == 4) {
			frameY = 0;
			if (time < 10) {
				time++;
			} else {
				time = 0;
				state = 1;
				frameX = 0;
			}
		} else {
			frameX = 0;
		}
	}

	public void dead() {
		if (frameX < 3) {
			frameX++;
		} else {
			state = 2;
		}
		if (state == 2) {
			if (time < 10) {
				time++;
			} else {
				state = 3;
				time = 0;
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

	public byte getIndex() {
		return index;
	}

	public void setIndex(byte index) {
		this.index = index;
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

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

}
