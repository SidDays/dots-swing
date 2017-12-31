package dotsgui.dots;

import dotsgui.DotsConstants;

public class Square implements DotsConstants {
	private char data;
	private int i, j;

	Square(int i, int j) {
		data = MASK;
		this.i = i;
		this.j = j;
	}

	char getData() {
		return data;
	}

	int getX() {
		return i;
	}

	int getY() {
		return j;
	}

	void setData(char data) {
		this.data = data;
	}

	public String toString() {
		return String.valueOf(data);
	}
}