import sheffield.*;

public class Picture{
    
    //read in a text file
    //store the digits in the text file in an array of length 40,000 of type int
    //create a screen 200x200
    //for each digit, plot a dot on the screen - every 200 pixels, move down a row
        //if the digit is between 0 and 3, it's blue,
        //if it's between 8 and 9 it's brown
        //if it's between 4 and 5 it's one shade of green
        //if it's between 6 and 7 it's a different shade of green
       
    
    private EasyGraphics window = new EasyGraphics(SCREEN_SIZE,SCREEN_SIZE);
    
   
    
    public static void main(String[] args){
            	final int PIXEL_SIZE = 3;
	    	final int SCREEN_SIZE = 600;
		final int actualImageSize = actualImageSize;
		int[] digitArray = new int[40000];

            	EasyReader textInput = new EasyReader("picture.txt");
            	String digits = textInput.readString();
           
            	for(int i = 0; i < 40000; i++){
               
                	digitArray[i] = Integer.parseInt(digits.substring(i, i+1));
	        
			//finds the current row, by dividing the current digit by the size of the screen so that any digit from 0-599 is on row 0, 600-1199 is on row 1, etc.
			//adjusts for larger pixel size by dividing screen size by that - "actual" size of the picture is 200x200, just scaled up because of 3x3 pixels        
               		int y = i / (actualImageSize);
			
			//pixels need to be drawn in rows on top of one another. As we go down the image, the array index gets larger, but the x position must stay the same, so
			//here the x position is adjusted to make everything line up.
                	int x = i - ((actualImageSize) * y);
			
			changeColour(digitArray[i]);
			//renders the pixel, adjusting for pixel size and flipping the image to deal with EasyGraphics' bottom left coordinate system
			window.fillRectangle(x * PIXEL_SIZE, SCREEN_SIZE  - (y * PIXEL_SIZE), PIXEL_SIZE, PIXEL_SIZE);
                	
               
            	}
        
    }

    private static void changeColour(int pixelNum){
        
            if(pixelNum >= 0 && pixelNum <= 3){
                
                window.setColor(0,0,200);
        
            }
            if(pixelNum >= 4 && pixelNum <= 5){
                
                window.setColor(0,200,0);
                
            }
            if(pixelNum >= 6 && pixelNum <= 7){
                
                window.setColor(0,180,0);

            }
            if(pixelNum >= 8 && pixelNum <= 9){
                
                window.setColor(150,100,90);
                
            }
        
    }
    
    
}
