/**
 * 
 */
package comp6731.patternRecognition.Assignment2;

import java.util.LinkedList;
import java.util.List;

/**
 * @author p_sokke
 *
 */
public class ZhangSuenThinning {

	public int[][] applyZhangSuenThinning(int[][] binaryMatrix, int row, int column) {
		
		List<Pixel> setPixel = new LinkedList<Pixel>();
		int trueNeighbourCount = 0; 
		int count01transition = 0;
		boolean isPixelChange ;
		do{
			isPixelChange = false;
			
			// Condition 1
			for (int i = 1; i < row - 1; i++) {
				for (int j = 1; j < column - 1; j++) {
					trueNeighbourCount = getNonZeroNeighbour(binaryMatrix, i, j);
					count01transition  = get01transition(binaryMatrix, i, j);
					
					boolean pixel1 = (binaryMatrix[i - 1][j] == 1) ? true : false;
					boolean pixel2 = (binaryMatrix[i - 1][j + 1] == 1) ? true : false;
					boolean pixel3 = (binaryMatrix[i][j + 1] == 1) ? true : false;
					boolean pixel4 = (binaryMatrix[i + 1][j + 1] == 1) ? true : false;
					boolean pixel5 = (binaryMatrix[i + 1][j] == 1) ? true : false;
					boolean pixel6 = (binaryMatrix[i + 1][j - 1] == 1) ? true : false;
					boolean pixel7 = (binaryMatrix[i][j - 1] == 1) ? true : false;
					boolean pixel8 = (binaryMatrix[i - 1][j - 1] == 1) ? true : false;
					
					if( ( binaryMatrix[i][j] == 1 ) && ( trueNeighbourCount >= 2 && trueNeighbourCount <= 6) &&
							( count01transition == 1 ) &&
							!( pixel1 & pixel3 & pixel5 ) &&
							!( pixel3 & pixel5 & pixel7 ) ){
						isPixelChange = true ;
						setPixel.add(new Pixel(i,j));
					}
				}
			}
				
			for (Pixel pixel : setPixel) {
				binaryMatrix[pixel.getxIndex()][pixel.getyIndex()] = 0;
			}
			
			setPixel.clear();
				
			// Condition 2
			for ( int i = 1; i < row - 1; i++) {
				for (int j = 1; j < column - 1; j++) {
					trueNeighbourCount = getNonZeroNeighbour(binaryMatrix, i, j);
					count01transition  = get01transition(binaryMatrix, i, j);
					
					boolean pixel1 = (binaryMatrix[i - 1][j] == 1) ? true : false;
					boolean pixel2 = (binaryMatrix[i - 1][j + 1] == 1) ? true : false;
					boolean pixel3 = (binaryMatrix[i][j + 1] == 1) ? true : false;
					boolean pixel4 = (binaryMatrix[i + 1][j + 1] == 1) ? true : false;
					boolean pixel5 = (binaryMatrix[i + 1][j] == 1) ? true : false;
					boolean pixel6 = (binaryMatrix[i + 1][j - 1] == 1) ? true : false;
					boolean pixel7 = (binaryMatrix[i][j - 1] == 1) ? true : false;
					boolean pixel8 = (binaryMatrix[i - 1][j - 1] == 1) ? true : false;
					
					if( ( binaryMatrix[i][j] == 1 ) && ( trueNeighbourCount >= 2 && trueNeighbourCount <= 6) &&
							( count01transition == 1 ) &&
							!( pixel1 & pixel3 & pixel7 ) &&
							!( pixel1 & pixel5 & pixel7 ) ){
						isPixelChange = true ;
						setPixel.add(new Pixel(i,j));
					}
				}
			}
			
			for (Pixel pixel : setPixel) {
				binaryMatrix[pixel.getxIndex()][pixel.getyIndex()] = 0;
			}
				
			setPixel.clear();
			
		}while(isPixelChange);
		
		return binaryMatrix;
	}

	private int get01transition(int[][] binaryMatrix, int x, int y) {
		int count = 0;
		// P1 -> P2
		if (binaryMatrix[x - 1][y] == 0 && binaryMatrix[x - 1][y + 1] == 1) count++;
		// P2 -> P3
		if (binaryMatrix[x - 1][y + 1] == 0 && binaryMatrix[x][y + 1] == 1) count++;
		// P3 -> P4
		if (binaryMatrix[x][y + 1] == 0 && binaryMatrix[x + 1][y + 1] == 1) count++;
		// P4 -> P5
		if (binaryMatrix[x + 1][y + 1] == 0 && binaryMatrix[x + 1][y] == 1) count++;
		// P5 -> P6
		if (binaryMatrix[x + 1][y] == 0 && binaryMatrix[x + 1][y - 1] == 1) count++;
		// P6 -> P7
		if (binaryMatrix[x + 1][y - 1] == 0 && binaryMatrix[x][y - 1] == 1) count++;
		// P7 -> P8
		if (binaryMatrix[x][y - 1] == 0 && binaryMatrix[x - 1][y - 1] == 1) count++;
		// P8 -> P1
		if (binaryMatrix[x - 1][y - 1] == 0 && binaryMatrix[x - 1][y] == 1) count++;
		return count;
	}

	private int getNonZeroNeighbour(int[][] binaryMatrix, int x, int y) {
		return binaryMatrix[x - 1][y] + binaryMatrix[x - 1][y + 1] + 
				binaryMatrix[x][y + 1] +binaryMatrix[x][y - 1] + 
						binaryMatrix[x + 1][y + 1] + binaryMatrix[x + 1][y] + binaryMatrix[x + 1][y - 1] + 
							binaryMatrix[x - 1][y - 1] ;
	}

}
