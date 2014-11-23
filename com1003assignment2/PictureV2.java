import sheffield.*;

public class PictureV2{

	public enum Colour{BLUE, GREEN, DARK_GREEN, BROWN}
	private static Colour colour;

	private static final int PIXEL_SIZE = 3;
	//actual size of the image (i.e. 200x200 in this case)
	private static final int IMAGE_SIZE = 200;
	//screen size that the image is to be displayed at (scaled up based on pixel size)
	private static final int SCREEN_SIZE = IMAGE_SIZE * PIXEL_SIZE;
	private static final int SKETCH_SCREEN_SIZE = 200;
	
	private static EasyGraphics mainWindow = new EasyGraphics(SCREEN_SIZE, SCREEN_SIZE);
	private static EasyGraphics sketchWindow = new EasyGraphics(SKETCH_SCREEN_SIZE, SKETCH_SCREEN_SIZE);

	public static void main(String[] args){

		//array of enums to store the colours of all the pixels
		Colour[][] colourArray = new Colour[IMAGE_SIZE][IMAGE_SIZE];
		EasyReader fileInput = new EasyReader("picture.txt");
		String input = fileInput.readString();

		renderImage(input, colourArray);
		renderSketch(colourArray);

	}

	private static void renderSketch(Colour[][] colourArray){

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

	private static void renderImage(String input, Colour[][] colourArray){

		//counter that increments up to the size of the data making the image (40,000 digits in this case)
		int digitNumber = 0;

		for(int y = 0; y < IMAGE_SIZE; y++){

			for(int x = 0; x < IMAGE_SIZE; x++){
				digitNumber++;

				if(digitNumber == IMAGE_SIZE * IMAGE_SIZE){
					break;
				}else{
					colourArray[x][y] = checkColour(Integer.parseInt(input.substring(digitNumber, digitNumber+1)));
				}
				//sets the colour for drawing based on the enum value for that pixel
				setRGB(colourArray[x][y]);
				mainWindow.drawRectangle(x * PIXEL_SIZE, SCREEN_SIZE - (y * PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);
				mainWindow.fillRectangle(x * PIXEL_SIZE, SCREEN_SIZE - (y * PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);

			}	
		}

	}

	private static void setRGB(Colour colour){

		switch(colour){
			case BLUE:
				mainWindow.setColor(0,0,200);
				break;
			case GREEN:
				mainWindow.setColor(0,200,0);
				break;
			case DARK_GREEN:
				mainWindow.setColor(0,180,0);
				break;
			case BROWN:
				mainWindow.setColor(150,100,90);
				break;
			default:
				mainWindow.setColor(0,0,0);
		}		

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