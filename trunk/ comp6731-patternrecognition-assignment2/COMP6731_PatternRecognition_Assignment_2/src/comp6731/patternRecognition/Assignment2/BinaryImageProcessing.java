/**
 * 
 */
package comp6731.patternRecognition.Assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author p_sokke
 *
 */
public class BinaryImageProcessing {

	/**
	 * @param args
	 */

	// Static variable to store row size
	public static int row;
	// Static variable to store column size
	public static int column;
	// Static variable to store normalized row size
	public static int normalizedRow;
	// Static variable to store normalized column size
	public static int normalizedColumn;

	public static void main(String[] args) {

		// Function Call to Input Binary Image from Text file 
		// inputMatrix - Two dimensional integer array - used to store binary values of binary image
		int inputMatrix[][] = loadBinaryImage();

		System.out.println("Binary Image Input:");
		System.out.println("_______________BINARY IMAGE INPUT_______________");
		printBinaryImage(inputMatrix, row, column);
		System.out.println("__________________________________________________");

		int resultMatrix[][] = inputMatrix;

		// Q (A)(i) Pattern pre-processing by Cleaning and Smoothing
		// Function Call to Binary Image Smoothing using 3x3 filter mask
		resultMatrix = binaryImageSmoothing(resultMatrix, row, column);

		System.out.println("\nResult of Binary Image Smoothing using 3x3 filter mask:");
		System.out.println("_______________BINARY IMAGE AFTER SMOOTHING_______________");
		printBinaryImage(resultMatrix, row, column);
		System.out.println("__________________________________________________________");

		// Function Call to 4 Neighbors filling
		for (int k = 1; k <= 5; k++) 
		resultMatrix = fill4NeighborsBinaryImage(resultMatrix);
		
		System.out.println("\nResult of 4 Neigbors filling :");
		System.out.println("_______________BINARY IMAGE AFTER 4 NEIGHBORS FILLING_______________");
		printBinaryImage(resultMatrix, row, column);
		System.out.println("____________________________________________________________________");
	
		// Function Call to 8 Neighbors filling
		for (int k = 1; k <= 5; k++) 
		resultMatrix = fill8NeighborsBinaryImage(resultMatrix);
		
		System.out.println("\nResult of 8 Neigbors filling :");
		System.out.println("_______________BINARY IMAGE AFTER 8 NEIGHBORS FILLING_______________");
		printBinaryImage(resultMatrix, row, column);
		System.out.println("____________________________________________________________________");
		
		// (A)(ii) Size Normalization
		// Function Call for Size Normalization
		int resultNormaizedMatrix[][] = sizeNormalization(resultMatrix, row, column);
		
		System.out.println("\nResult of Size Normalization Binary Image:");
		System.out.println("\nRow Size before size normalization  : " + row);
		System.out.println("Row Size before size normalization : " + column);
		System.out.println("\nNormalized Row Size    :  " + normalizedRow);
		System.out.println("Normalized Column Size :  " + normalizedColumn);
		System.out.println("_______________BINARY IMAGE AFTER SIZE NORMALIZATION_______________");
		printBinaryImage(resultNormaizedMatrix, normalizedRow, normalizedColumn);
		System.out.println("___________________________________________________________________");
		
		// (A)(iii) Center of Gravity
		// Function call to get Center of Gravity of Binary Image
		Pixel pixel = centerOfGravity(resultMatrix, row, column);
		System.out.println("Binary Image's \"Center Of Gravity\" is at Row Index: " + pixel.getxIndex() + "\tColumn Index: " + pixel.getyIndex());
		System.out.println("Center of Gravity pixel denoted by NUMBER '8'");
		resultMatrix[pixel.getxIndex()][pixel.getyIndex()] = 8 ;
		printBinaryImage(resultMatrix, row, column);
		resultMatrix[pixel.getxIndex()][pixel.getyIndex()] = 8 ;

		// (A)(iii) Center of Gravity 
		// Size normalized Image's Center of Gravity
		// Function call to get Center of Gravity of Binary Image
		pixel = centerOfGravity(resultNormaizedMatrix, normalizedRow, normalizedRow);
		System.out.println("Size Normalized Binary Image's \"Center Of Gravity\" is at Row Index: " + pixel.getxIndex() + "\tColumn Index: " + pixel.getyIndex());
		System.out.println("Center of Gravity pixel denoted by NUMBER '8'");
		int resultNormaizedMatrixCOG[][] = resultNormaizedMatrix;
		resultNormaizedMatrixCOG[pixel.getxIndex()][pixel.getyIndex()] = 8 ;
		printBinaryImage(resultNormaizedMatrixCOG, normalizedRow, normalizedRow);
		resultNormaizedMatrixCOG[pixel.getxIndex()][pixel.getyIndex()] = 0 ;
		
		// Slkeleton extraction
		// Function call for Zhang-Suen Skeletonization
		ZhangSuenThinning zhangSuenThinning = new ZhangSuenThinning();
		resultMatrix = zhangSuenThinning.applyZhangSuenThinning(resultNormaizedMatrix, normalizedRow, normalizedColumn);
		System.out.println("_____________________________________________________________________");
		System.out.println("\nResult of Zhang-Suen Skeletonization Binary Image:");
		System.out.println("_______________BINARY IMAGE AFTER ZHANG-SUEN THINNING_______________");
		printBinaryImage(resultMatrix, normalizedRow, normalizedColumn);
		System.out.println("_____________________________________________________________________");
		
		// Function Call for Thinning Binary Image
		int resultMatrix2[][] = thinningBinaryImage(resultNormaizedMatrix);
		
		System.out.println("\nResult of Thinning Formulae Binary Image:");
		System.out.println("_______________BINARY IMAGE SKELETONIZATION USING THINNING_______________");
		printBinaryImage(resultNormaizedMatrix, normalizedRow, normalizedColumn);
		System.out.println("_________________________________________________________________________");
		
	}

	/*
	 * Function to Input Binary Image from Text file
	 */
	private static int[][] loadBinaryImage() {
		String a2Image = "resource/BinaryImage_1.txt";
		String a2Image2 = "resource/BinaryImage_2.txt";
		int tempRow = 0;
		int tempCol = 0;
		int a2Array[][] = new int[1000][1000];
		try {
			Scanner input = new Scanner(new File(a2Image));
			while (input.hasNextLine()) {
				String line = input.nextLine();
				tempCol = line.length();
				for (int i = 0; i < tempCol; i++) {
					a2Array[tempRow][i] = Integer.parseInt(String.valueOf(line
							.charAt(i)));
				}
				++tempRow;
			}
			input.close();
			row = tempRow;
			column = tempCol;
		} catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
		}
		return a2Array;
	}

	/*
	 * Function to Print the resultant Binary image matrix
	*/
	private static void printBinaryImage(int resultMatrix[][], int row, int column) {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				if (resultMatrix[i][j] == 0)
					System.out.print(" ");
				else
					System.out.print(resultMatrix[i][j]);
			}
			System.out.println();
		}
	}
	
	/*
	 * Function to Binary Image Smoothing
	*/
	private static int[][] binaryImageSmoothing(int[][] binaryMatrix, int row, int column) {
		boolean isPixelChange;
		isPixelChange = false;
		for (int x = row - 2; x > 0; x--) {
			for (int y = column - 2; y > 0; y--) {
				isPixelChange = isPixelChange || smoothBinaryPoint(binaryMatrix, x, y);
			}
		}
		return binaryMatrix;
	}
	
	/*
	 * Function call to smoothen every pixel
	*/
	private static boolean smoothBinaryPoint(int[][] binaryMatrix, int x, int y)//, int filterValue) 
	{
		if (binaryMatrix[x][y - 1] == binaryMatrix[x - 1][y - 1]
				&& binaryMatrix[x - 1][y - 1] == binaryMatrix[x - 1][y]
				&& binaryMatrix[x - 1][y] == binaryMatrix[x - 1][y + 1]
				&& binaryMatrix[x - 1][y + 1] == binaryMatrix[x][y + 1]
				&& binaryMatrix[x][y] != binaryMatrix[x][y - 1]) {
			binaryMatrix[x][y] = binaryMatrix[x][y - 1];
			return true;
		}
		if (binaryMatrix[x - 1][y] == binaryMatrix[x - 1][y + 1]
				&& binaryMatrix[x - 1][y + 1] == binaryMatrix[x][y + 1]
				&& binaryMatrix[x][y + 1] == binaryMatrix[x + 1][y + 1]
				&& binaryMatrix[x + 1][y + 1] == binaryMatrix[x + 1][y]
				&& binaryMatrix[x][y] != binaryMatrix[x - 1][y]) {
			binaryMatrix[x][y] = binaryMatrix[x - 1][y];
			return true;
		}
		if (binaryMatrix[x][y + 1] == binaryMatrix[x + 1][y + 1]
				&& binaryMatrix[x + 1][y + 1] == binaryMatrix[x + 1][y]
				&& binaryMatrix[x + 1][y] == binaryMatrix[x + 1][y - 1]
				&& binaryMatrix[x + 1][y - 1] == binaryMatrix[x][y - 1]
				&& binaryMatrix[x][y] != binaryMatrix[x][y + 1]) {
			binaryMatrix[x][y] = binaryMatrix[x][y + 1];
			return true;
		}
		if (binaryMatrix[x + 1][y] == binaryMatrix[x + 1][y - 1]
				&& binaryMatrix[x + 1][y - 1] == binaryMatrix[x][y - 1]
				&& binaryMatrix[x][y - 1] == binaryMatrix[x - 1][y - 1]
				&& binaryMatrix[x - 1][y - 1] == binaryMatrix[x - 1][y]
				&& binaryMatrix[x][y] != binaryMatrix[x + 1][y]) {
			binaryMatrix[x][y] = binaryMatrix[x + 1][y];
			return true;
		}
		return false;
	}
	
	
	/*
	 * Function to fill using 4 neighbors filling 
	*/
	private static int[][] fill4NeighborsBinaryImage(int[][] binaryMatrix) {
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < column - 1; j++) {
				int target = binaryMatrix[i][j];
				boolean targetBit = (binaryMatrix[i][j] == 1) ? true : false;
					boolean leftBit  = (binaryMatrix[i][j - 1] == 1) ? true : false;
					boolean rightBit = (binaryMatrix[i][j + 1] == 1) ? true : false;
					boolean upperBit = (binaryMatrix[i - 1][j] == 1) ? true : false;
					boolean lowerBit = (binaryMatrix[i + 1][j] == 1) ? true : false;

					boolean resultBit = ((targetBit)
										| ((upperBit & lowerBit) & (leftBit  | rightBit)) 
										| ((leftBit  & rightBit) & (upperBit | lowerBit)));
					binaryMatrix[i][j] = (resultBit) ? 1 : 0;
			}
		}
		return binaryMatrix;
	}
	
	/*
	 * Function to fill using84 neighbors filling 
	*/
	private static int[][] fill8NeighborsBinaryImage(int[][] binaryMatrix) {
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < column - 1; j++) {
				int target = binaryMatrix[i][j];
				boolean targetBit = (binaryMatrix[i][j] == 1) ? true : false;
					
				boolean leftBit  = (binaryMatrix[i][j - 1] == 1) ? true : false;
				boolean rightBit = (binaryMatrix[i][j + 1] == 1) ? true : false;
				boolean upperBit = (binaryMatrix[i - 1][j] == 1) ? true : false;
				boolean lowerBit = (binaryMatrix[i + 1][j] == 1) ? true : false;
				
				boolean leftUpperBit = (binaryMatrix[i - 1][j - 1] == 1) ? true : false;
				boolean leftLowerBit = (binaryMatrix[i + 1][j - 1] == 1) ? true : false;
				boolean rightUpperBit = (binaryMatrix[i - 1][j + 1] == 1) ? true : false;
				boolean rightLowerBit = (binaryMatrix[i + 1][j + 1] == 1) ? true : false;
	
				boolean resultBit = ( ( targetBit ) 
									| ( leftUpperBit  & rightLowerBit ) 
									| ( upperBit      & lowerBit ) 
									| ( rightUpperBit & leftLowerBit ) 
									| ( leftBit       & rightBit ) );
				
				binaryMatrix[i][j] = (resultBit) ? 1 : 0;
			}
		}
		return binaryMatrix;
	}
	
	/*
	 * Function for Size Normalization
	*/
	private static int[][] sizeNormalization(int[][] binaryMatrix, int row, int column) {
		int[][] resultMatrix = new int[1000][1000];
		for(int x = 0; x < row; x++){
			for(int y = 0; y < column; y++){
				normalizedRow = x / 2 ;
				normalizedColumn = y / 2;
				resultMatrix[normalizedRow][normalizedColumn] = binaryMatrix[x][y];
			}
		}
		return resultMatrix;
	}
	
	/*
	 * Function for Thinning Binary Image
	*/
	private static int[][] thinningBinaryImage(int[][] binaryMatrix) {
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < column - 1; j++) {
				boolean targetBit = (binaryMatrix[i][j] == 1) ? true : false;
				
				boolean leftBit  = (binaryMatrix[i][j - 1] == 1) ? true : false;
				boolean rightBit = (binaryMatrix[i][j + 1] == 1) ? true : false;
				boolean upperBit = (binaryMatrix[i - 1][j] == 1) ? true : false;
				boolean lowerBit = (binaryMatrix[i + 1][j] == 1) ? true : false;
				
				boolean leftUpperBit = (binaryMatrix[i - 1][j - 1] == 1) ? true : false;
				boolean leftLowerBit = (binaryMatrix[i + 1][j - 1] == 1) ? true : false;
				boolean rightUpperBit = (binaryMatrix[i - 1][j + 1] == 1) ? true : false;
				boolean rightLowerBit = (binaryMatrix[i + 1][j + 1] == 1) ? true : false;

				boolean resultBit = targetBit | ( ( leftUpperBit  & leftBit  & upperBit ) 
												& ( rightUpperBit & rightBit & upperBit ) 
												& ( leftLowerBit  & leftBit  & lowerBit ) 
												& ( rightLowerBit & rightBit & lowerBit ) );
				binaryMatrix[i][j] = (resultBit) ? 1 : 0;
			}
		}
		return binaryMatrix;
	}
	
	/*
	 * Function to get Center of Gravity of Binary Image
	*/
	private static Pixel centerOfGravity(int [][] binaryMatrix, int row, int column){
		int sumX = 0;
		int sumY = 0;
		int pixelCount = 0;
		for(int i = 0 ; i < row; i++ ){
			for(int j = 0 ; j < column ; j++ ){
				if( binaryMatrix[i][j] == 1 ){
					sumX = sumX + i ;
					sumY = sumY + j ;
					++pixelCount;
				}
			}
		}
		sumX = sumX / pixelCount ;
		sumY = sumY / pixelCount ;
		return new Pixel( sumX, sumY);
	}

}
