import sheffield.*;

public class PictureV2{

	public enum Colour{BLUE, GREEN, DARK_GREEN, BROWN}
	private static Colour colour;
	
	public static void main(String[] args){
		
		//array of enums to store the colours of all the pixels
		Colour[][] colourArray = new Colour[200][200];

		EasyReader fileInput = new EasyReader("picture.txt");
		String input = fileInput.readString();

		final int IMAGE_SIZE = (int)Math.sqrt(input.length());
		final int PIXEL_SIZE = 3;
		final int SCREEN_SIZE = IMAGE_SIZE * PIXEL_SIZE;
		final int SKETCH_SCREEN_SIZE = 200;
		
		renderColourImage(input, colourArray, SCREEN_SIZE, PIXEL_SIZE, IMAGE_SIZE);
		renderSketch(colourArray, SKETCH_SCREEN_SIZE, IMAGE_SIZE);
		
	}

	private static void renderColourImage(String input, Colour[][] colourArray, final int SCREEN_SIZE, final int PIXEL_SIZE, final int IMAGE_SIZE){
		
		
		EasyGraphics mainWindow = new EasyGraphics(SCREEN_SIZE, SCREEN_SIZE);

		//counter that increments up to the size of the data making the image (40,000 digits in this case)
		int digitNumber = 0;

		for(int y = 0; y < IMAGE_SIZE; y++){

			for(int x = 0; x < IMAGE_SIZE; x++){

				if(digitNumber < (IMAGE_SIZE * IMAGE_SIZE) - 1){
					digitNumber++;
				}
				
				colourArray[x][y] = checkColour(Integer.parseInt(input.substring(digitNumber, digitNumber+1)));
				
				//sets the colour for drawing based on the enum value for that pixel
				mainWindow.setColor(getRGB(colourArray[x][y])[0], getRGB(colourArray[x][y])[1], getRGB(colourArray[x][y])[2]);
				mainWindow.drawRectangle(x * PIXEL_SIZE, SCREEN_SIZE - (y * PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);
				mainWindow.fillRectangle(x * PIXEL_SIZE, SCREEN_SIZE - (y * PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);

			}	
		}

	}

	private static void renderSketch(Colour[][] colourArray, final int SKETCH_SCREEN_SIZE, final int IMAGE_SIZE){

		EasyGraphics sketchWindow = new EasyGraphics(SKETCH_SCREEN_SIZE, SKETCH_SCREEN_SIZE);

		/* nested for loop to plot points where colours change between pixels
		 * starts at 1 and ends at IMAGE_SIZE - 1 so it doesn't either go outside the range of the array
		 * or detect the edges of the screen. 
		 */
		for(int y = 1; y < IMAGE_SIZE - 1; y++){

			for(int x = 1; x < IMAGE_SIZE - 1; x++){
				
				if(colourArray[x][y] != colourArray[x][y-1]){
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
				break;
			default:
				rgbValues[0] = 0;
				rgbValues[1] = 0;
				rgbValues[2] = 0;
		}

		return rgbValues;		

	}


	private static Colour checkColour(int digit){

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