import sheffield.*;

public class PictureV2{

	public enum Colour{BLUE, GREEN, DARK_GREEN, BROWN}
	private static Colour colour;

	private static final int SCREEN_SIZE = 600;
	private static final int SKETCH_SCREEN_SIZE = 200;
	private static final int BLOCK_SIZE = 3;
	private static EasyGraphics mainWindow = new EasyGraphics(SCREEN_SIZE, SCREEN_SIZE);
	private static EasyGraphics sketchWindow = new EasyGraphics(SKETCH_SCREEN_SIZE, SKETCH_SCREEN_SIZE);

	public static void main(String[] args){

		EasyReader fileInput = new EasyReader("picture.txt");
		String input = fileInput.readString();
		int[] digitArray = new int[40000];

		for(int i = 0; i < input.length(); i++){

			digitArray[i] = Integer.parseInt(input.substring(i, i+1));

		}

		int[][] imageArray = new int[200][200];
		Colour[][] colourArray = new Colour[200][200];

		for(int y = 0; y < 200; y++){

			for(int x = 0; x < 200; x++){
				imageArray[x][y] = digitArray[x + (y*200)];
				colourArray[x][y] = checkColour(imageArray[x][y]);
			
				setRGB(colourArray[x][y]);
				mainWindow.fillRectangle(x*BLOCK_SIZE,SCREEN_SIZE - (y * BLOCK_SIZE),BLOCK_SIZE,BLOCK_SIZE);
				mainWindow.drawRectangle(x*BLOCK_SIZE,SCREEN_SIZE - (y*BLOCK_SIZE), BLOCK_SIZE, BLOCK_SIZE);
			}	

		}

		for(int y = 1; y < 199; y++){

			for(int x = 1; x < 199; x++){
				
				if(colourArray[x][y] != colourArray[x][y-1]){
					sketchWindow.plot(x,SKETCH_SCREEN_SIZE - y);
				}
				if(colourArray[x][y] != colourArray[x][y+1]){
					sketchWindow.plot(x,SKETCH_SCREEN_SIZE - y);
				}
				if(colourArray[x][y] != colourArray[x-1][y]){
					sketchWindow.plot(x,SKETCH_SCREEN_SIZE - y);
				}
				if(colourArray[x][y] != colourArray[x+1][y]){
					sketchWindow.plot(x, SKETCH_SCREEN_SIZE - y);
				}
				
				
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