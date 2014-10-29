/*Written by: Simon Turner (aca14st)
* First written: 15/10/2014
* Last updated: 28/10/2014
*/ 

import sheffield.*;

public class Assignment{

	public static void main(String[] args){
		
		//Create new objects to take input and output to the screen.
		EasyReader keyboard = new EasyReader();
		EasyReader file = new EasyReader("rates.txt");
		EasyWriter screen = new EasyWriter();
		
		//Take user input to calculate VAT from.
		final double TOTAL_PRICE = keyboard.readDouble("Enter the total price: ");

		//Calculate VAT using 20% VAT rate.
		final double UK_VAT = 20.0;
		double ukVatAmount = TOTAL_PRICE / ((100.0 + UK_VAT) / UK_VAT);
		
		/*Find Foreign VAT rate in file, based on the position of the space 
		before the VAT rate, and the position of the percent sign. */
		String vatString = file.readString();
		int vatStart = vatString.lastIndexOf(" ") + 1;
		int vatEnd = vatString.indexOf("%");
		final double FOREIGN_VAT = Double.parseDouble(vatString.substring(vatStart, vatEnd));
		
		//Calculate the amount of the total price that is VAT.
		double foreignVatAmount = TOTAL_PRICE / ((100.0 + FOREIGN_VAT) / FOREIGN_VAT);

		/* Find country's name in file, based on the space next to the country name 
		(also next to the word "in"), and the space next to the vat rate 
		(i.e. the last space on the first line, and the space next to the word "is"). */
		final String FOREIGN_COUNTRY = vatString.substring(vatString.indexOf("in ") + 3, vatStart - 4);
		
		//Find currency names.
		String currencyString = file.readString();
		
		/*Finds where the currency names start and finish in the string, 
		using the spaces next to the words near the currency names in the file. */
		int mainCurrencyStart = currencyString.indexOf("uses ") + 5;
		int mainCurrencyEnd= currencyString.indexOf(" and");
		int subCurrencyStart = currencyString.lastIndexOf(" ") + 1;
        
       		//Uses the positions of the currency names to extract the currency names from the string.
		final String FOREIGN_MAIN_CURRENCY = currencyString.substring(mainCurrencyStart, mainCurrencyEnd);
		final String FOREIGN_SUB_CURRENCY = currencyString.substring(subCurrencyStart);
        
	   	/*Calculate which part of the vat amount is the main currency (i.e. pounds in the UK)
		and which part is the sub currency (i.e. pence in the UK). */
		int foreignMainAmount = (int)foreignVatAmount;
		int ukMainAmount = (int)ukVatAmount;

		//calculates subcurrency amount in whole amounts of subcurrency not fractions of maincurrency
		int foreignSubAmount = (int)Math.round((foreignVatAmount - foreignMainAmount)*100);
		int ukSubAmount = (int)Math.round((ukVatAmount - ukMainAmount)*100);
		
		//Print VAT and original input.
		screen.println(foreignVatAmount);
		screen.println("The VAT on " + TOTAL_PRICE + " is "); 
		screen.print(ukVatAmount, 2, 8);
		screen.println(" in the UK");
		screen.print(foreignVatAmount, 2, 8);
		screen.println(" in " + FOREIGN_COUNTRY);
		
		//Print out vat amounts with currencies.
		screen.println("The VAT on " + TOTAL_PRICE + " is ");
		screen.println(ukMainAmount + " pounds and " + ukSubAmount + " pence in the UK.");
		screen.println(foreignMainAmount + " " + FOREIGN_MAIN_CURRENCY + " and " 
				+ foreignSubAmount + " " + FOREIGN_SUB_CURRENCY + " in " + FOREIGN_COUNTRY + ".");
		
	
	}
	
}


