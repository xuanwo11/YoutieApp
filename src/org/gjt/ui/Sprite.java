package org.gjt.ui;

public class Sprite extends Layer {
	private float rotate;
	public Sprite(float x, float y, float width, float height) {
		super(x, y, width, height);
	}
	public float getRotate() {
		return rotate;
	}
	public void setRotate(float rotate) {
		this.rotate = rotate;
	}
	
}
