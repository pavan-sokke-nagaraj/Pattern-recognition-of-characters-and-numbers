/**
 * 
 */
package comp6731.patternRecognition.Assignment2;

/**
 * @author p_sokke
 *
 *Class to save pixel values
 */
public class Pixel {

	private int xIndex, yIndex;

	public Pixel(int xIndex, int yIndex) {
		this.xIndex = xIndex;
		this.yIndex = yIndex;
	}

	public int getxIndex() {
		return xIndex;
	}

	public void setxIndex(int xIndex) {
		this.xIndex = xIndex;
	}

	public int getyIndex() {
		return yIndex;
	}

	public void setyIndex(int yIndex) {
		this.yIndex = yIndex;
	}

}
