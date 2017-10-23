package com.miaotian;

import com.hfp.youtie.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bomb {

	private int row, col;
	private int[] length;
	private byte state;
	private int time;
	private byte frame;

	private Bitmap[] bit_bomb;

	public Bomb(Context context, int row, int col, int force) {
		Resources res = context.getResources();
		bit_bomb = new Bitmap[3];
		bit_bomb[0] = BitmapFactory.decodeResource(res, R.drawable.bomb);
		bit_bomb[1] = BitmapFactory.decodeResource(res, R.drawable.boom);
		bit_bomb[2] = BitmapFactory.decodeResource(res, R.drawable.fire);
		this.row = row;
		this.col = col;
		state = 0;
		time = 0;
		length = new int[4];
		for (int i = 0; i < length.length; i++) {
			length[i] = force;
		}
	}

	public void drawBomb(Canvas canvas, Paint paint, int offx, int offy) {
		int tempRow = 0;
		int tempCol = 0;
		switch (state) {
		case 1:// ����״̬
			canvas.save();
			canvas.clipRect(col * Map.W + offx, row * Map.H + offy, col * Map.W
					+ offx + Map.W, row * Map.H + offy + Map.H);
			canvas.drawBitmap(bit_bomb[0], col * Map.W + offx - frame * Map.W,
					row * Map.H + offy, paint);
			canvas.restore();
			break;
		case 2:// ��Ҫ��ը��״̬
			canvas.drawBitmap(bit_bomb[1], col * Map.W + offx, row * Map.H
					+ offy, paint);
			break;
		case 3:// ��ը״̬
			// ����
			canvas.save();
			canvas.clipRect(col * Map.W + offx, row * Map.H + offy, col * Map.W
					+ offx + Map.W, row * Map.H + offy + Map.H);
			canvas.drawBitmap(bit_bomb[2], col * Map.W + offx, row * Map.H
					+ offy - frame * Map.H, paint);
			canvas.restore();
			// ����
			for (int i = 1; i <= length[0]; i++) {
				tempRow = row - i;
				tempCol = col;
				canvas.save();
				canvas.clipRect(tempCol * Map.W + offx, tempRow * Map.H + offy,
						tempCol * Map.W + offx + Map.W, tempRow * Map.H + offy
								+ Map.H);
				if (i == length[0]) {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 5
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				} else {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 1
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				}
				canvas.restore();
			}
			// ����
			for (int i = 1; i <= length[1]; i++) {
				tempRow = row + i;
				tempCol = col;
				canvas.save();
				canvas.clipRect(tempCol * Map.W + offx, tempRow * Map.H + offy,
						tempCol * Map.W + offx + Map.W, tempRow * Map.H + offy
								+ Map.H);
				if (i == length[1]) {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 6
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				} else {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 2
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				}
				canvas.restore();
			}
			// ����
			for (int i = 1; i <= length[2]; i++) {
				tempRow = row;
				tempCol = col - i;
				canvas.save();
				canvas.clipRect(tempCol * Map.W + offx, tempRow * Map.H + offy,
						tempCol * Map.W + offx + Map.W, tempRow * Map.H + offy
								+ Map.H);
				if (i == length[2]) {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 7
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				} else {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 3
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				}
				canvas.restore();
			}
			// ����
			for (int i = 1; i <= length[3]; i++) {
				tempRow = row;
				tempCol = col + i;
				canvas.save();
				canvas.clipRect(tempCol * Map.W + offx, tempRow * Map.H + offy,
						tempCol * Map.W + offx + Map.W, tempRow * Map.H + offy
								+ Map.H);
				if (i == length[3]) {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 8
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				} else {
					canvas.drawBitmap(bit_bomb[2], tempCol * Map.W + offx - 4
							* Map.W, tempRow * Map.H + offy - frame * Map.H,
							paint);
				}
				canvas.restore();
			}
			break;
		}
	}

	public void bombLogic() {
		switch (state) {
		case 1:
			time++;
			if (frame < 2) {
				if (time % 5 == 0) {
					frame++;
				}
			} else {
				frame = 0;
			}
			if (time > 25) {
				time = 0;
				if (MainView.detonate) {
					return;
				}
				state++;
			}
			break;
		case 2:
			if (time < 2) {
				time++;
			} else {
				time = 0;
				state++;
			}
			break;
		case 3:
			changeMap();
			if (frame < 3) {
				frame++;
			} else {
				frame = 0;
				state++;
			}
			break;
		}
	}

	public void setBombLength() {
		int tempRow = 0;
		int tempCol = 0;
		// ����
		for (int i = 1; i <= length[0]; i++) {
			tempRow = row - i;
			tempCol = col;
			if (tempRow >= 0 && tempRow < Map.map.length) {
				if (Map.map[tempRow][tempCol] == 2) {
					length[0] = i;
					break;
				}
				if (Map.map[tempRow][tempCol] == 3) {
					length[0] = i - 1;
					break;
				}
			}
		}
		// ����
		for (int i = 1; i <= length[1]; i++) {
			tempRow = row + i;
			tempCol = col;
			if (tempRow >= 0 && tempRow < Map.map.length) {
				if (Map.map[tempRow][tempCol] == 2) {
					length[1] = i;
					break;
				}
				if (Map.map[tempRow][tempCol] == 3) {
					length[1] = i - 1;
					break;
				}
			}
		}
		// ����
		for (int i = 1; i <= length[2]; i++) {
			tempRow = row;
			tempCol = col - i;
			if (tempCol >= 0 && tempCol < Map.map[0].length) {
				if (Map.map[tempRow][tempCol] == 2) {
					length[2] = i;
					break;
				}
				if (Map.map[tempRow][tempCol] == 3) {
					length[2] = i - 1;
					break;
				}
			}
		}
		// ����
		for (int i = 1; i <= length[3]; i++) {
			tempRow = row;
			tempCol = col + i;
			if (tempCol >= 0 && tempCol < Map.map[0].length) {
				if (Map.map[tempRow][tempCol] == 2) {
					length[3] = i;
					break;
				}
				if (Map.map[tempRow][tempCol] == 3) {
					length[3] = i - 1;
					break;
				}
			}
		}
	}

	public void changeMap() {
		int tempRow = 0;
		int tempCol = 0;
		for (int i = 0; i <= length[0]; i++) {// ը������������ͼ
			tempRow = row - i;
			tempCol = col;
			if (tempRow >= 0 && tempRow < Map.map.length) {
				if (Map.map[tempRow][tempCol] == 2) {
					Map.count++;
					if (Map.count == Map.prop) {
						Map.map[tempRow][tempCol] = getProp();
					} else if (Map.count == Map.door) {
						Map.map[tempRow][tempCol] = 4;
					} else {
						Map.map[tempRow][tempCol] = 1;
					}
				}
			}
		}
		for (int i = 0; i <= length[1]; i++) {// ը������������ͼ
			tempRow = row + i;
			tempCol = col;
			if (tempRow >= 0 && tempRow < Map.map.length) {
				if (Map.map[tempRow][tempCol] == 2) {
					Map.count++;
					if (Map.count == Map.prop) {
						Map.map[tempRow][tempCol] = getProp();
					} else if (Map.count == Map.door) {
						Map.map[tempRow][tempCol] = 4;
					} else {
						Map.map[tempRow][tempCol] = 1;
					}
				}
			}
		}
		for (int i = 0; i <= length[2]; i++) {// ը������������ͼ
			tempRow = row;
			tempCol = col - i;
			if (tempCol >= 0 && tempCol < Map.map[0].length) {
				if (Map.map[tempRow][tempCol] == 2) {
					Map.count++;
					if (Map.count == Map.prop) {
						Map.map[tempRow][tempCol] = getProp();
					} else if (Map.count == Map.door) {
						Map.map[tempRow][tempCol] = 4;
					} else {
						Map.map[tempRow][tempCol] = 1;
					}
				}
			}
		}
		for (int i = 0; i <= length[3]; i++) {// ը������������ͼ
			tempRow = row;
			tempCol = col + i;
			if (tempCol >= 0 && tempCol < Map.map[0].length) {
				if (Map.map[tempRow][tempCol] == 2) {
					Map.count++;
					if (Map.count == Map.prop) {
						Map.map[tempRow][tempCol] = getProp();
					} else if (Map.count == Map.door) {
						Map.map[tempRow][tempCol] = 4;
					} else {
						Map.map[tempRow][tempCol] = 1;
					}
				}
			}
		}
	}

	public int getProp() {
		int index = 0;
		switch (MainView.level % 4) {
		case 1:
			index = 5;
			break;
		case 2:
			index = 6;
			break;
		case 3:
			index = 7;
			break;
		case 0:
			index = 8;
			break;
		}
		return index;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int[] getLength() {
		return length;
	}

	public void setLength(int[] length) {
		this.length = length;
	}

	public byte getState() {
		return state;
	}

	public void setState(byte state) {
		this.state = state;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}