import sheffield.*;

public class Picture{

	public enum Colour{BLUE, GREEN, DARK_GREEN, BROWN}
	private static Colour colour;
	
	public static void main(String[] args){
		
		//array of enums to store the colours of all the pixels
		Colour[][] colourArray = new Colour[200][200];

		//file initally read into a string so each digit can be turned into a set of colour values.
		EasyReader fileInput = new EasyReader("picture.txt");
		String input = fileInput.readString();

		//image is a square of 40,000 pixels - i.e. side length 200 (or sqrt(40,000)).
		final int IMAGE_SIZE = (int)Math.sqrt(input.length());
		//screen size is 600, because each block is 3 pixels on screen
		final int SCREEN_SIZE = 600;
		//sketch screen size just needs to be smaller so the colour image is still viewable.
		final int SKETCH_SCREEN_SIZE = 200;
		
		renderColourImage(input, colourArray, SCREEN_SIZE, IMAGE_SIZE);
		renderSketch(colourArray, SKETCH_SCREEN_SIZE, IMAGE_SIZE);
		
	}

	private static void renderColourImage(String input, Colour[][] colourArray, final int SCREEN_SIZE, final int IMAGE_SIZE){
		
		final int BLOCK_SIZE = 3;
		EasyGraphics mainWindow = new EasyGraphics(SCREEN_SIZE, SCREEN_SIZE);

		//counter that increments up to the size of the data making the image (40,000 digits in this case)
		int digitNumber = 0;

		for(int y = 0; y < IMAGE_SIZE; y++){

			for(int x = 0; x < IMAGE_SIZE; x++){
				//works out the current digit in the input file
				int currentDigit = Integer.parseInt(input.substring(digitNumber, digitNumber+1));
				//populates an array of enums with the Colour returned by checkColour for that digit in the file
				colourArray[x][y] = checkColour(currentDigit);
				
				//sets the colour for drawing based on the enum value for that pixel
				mainWindow.setColor(getRGB(colourArray[x][y])[0], getRGB(colourArray[x][y])[1], getRGB(colourArray[x][y])[2]);
				//each rectangle is scaled up by the block size, with the y coordinate flipped so the picture is the right orientation

				mainWindow.fillRectangle(x * BLOCK_SIZE, SCREEN_SIZE - (y * BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
				
				digitNumber++;

			}	
		}

	}

	private static void renderSketch(Colour[][] colourArray, final int SKETCH_SCREEN_SIZE, final int IMAGE_SIZE){

		EasyGraphics sketchWindow = new EasyGraphics(SKETCH_SCREEN_SIZE, SKETCH_SCREEN_SIZE);

		/* nested for loop to plot points where colours change between blocks in the image
		 * starts at 1 and ends at IMAGE_SIZE - 1 so it doesn't either go outside the range of the array
		 * or detect the edges of the screen. 
		 */
		for(int y = 1; y < IMAGE_SIZE - 1; y++){

			for(int x = 1; x < IMAGE_SIZE - 1; x++){
				
				//checks up, down, left and right in the image (200x200) to see whether the enum value for that block differs
				if(colourArray[x][y] != colourArray[x][y-1]){
					//plots a dot at that point if it does differ
					sketchWindow.plot(x, SKETCH_SCREEN_SIZE - y);
				}
				if(colourArray[x][y] != colourArray[x][y+1]){
					sketchWindow.plot(x, SKETCH_SCREEN_SIZE - y);
				}
				if(colourArray[x][y] != colourArray[x-1][y]){
					sketchWindow.plot(x, SKETCH_SCREEN_SIZE - y);
				}
				if(colourArray[x][y] != colourArray[x+1][y]){
					sketchWindow.plot(x, SKETCH_SCREEN_SIZE - y);
				}

			}

		}

	}



	private static int[] getRGB(Colour colour){

		//assigns red, green and blue integer values to an array
		//depending on the colour the method is fed as a parameter.
		int[] rgbValues = new int[3];
		switch(colour){
			case BLUE:
				rgbValues[0] = 0;
				rgbValues[1] = 0;
				rgbValues[2] = 200;
				break;
			case GREEN:
				rgbValues[0] = 0;
				rgbValues[1] = 200;
				rgbValues[2] = 0;
				break;
			case DARK_GREEN:
				rgbValues[0] = 0;
				rgbValues[1] = 180;
				rgbValues[2] = 0;
				break;
			case BROWN:
				rgbValues[0] = 150;
				rgbValues[1] = 100;
				rgbValues[2] = 90;
		}

		return rgbValues;		

	}


	private static Colour checkColour(int digit){

			//turns each integer digit in the text file into an enum value. 

			if(digit >= 0 && digit <= 3){
				return colour.BLUE;
            }
            if(digit >= 4 && digit <= 5){
                return colour.GREEN;
            }
            if(digit >= 6 && digit <= 7){
                return colour.DARK_GREEN;
            }else{
            	return colour.BROWN;
            }
	}

}