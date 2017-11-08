package a3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Driver {
	public static void main(String args[]) throws Exception {
		Object outputObj = null;
		System.out.println("Sender Interface");
		System.out.println("Please chose the number that corresponds to the object you would like to serialize");
		printObjectsSelection();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String selection = in.readLine();
		int selectionInt = Integer.parseInt(selection);
		//A simple object with only primitives for instance variables.
		if (selectionInt ==1) {
			SimpleObject simpleObj = new SimpleObject();
			ObjectCreator.initializeObject(simpleObj); 
			outputObj = simpleObj;
		}
		//An object that contains references to other objects.
		else if (selectionInt == 2) {
			ReferencesObject referenceObj = new ReferencesObject();
			ObjectCreator.initializeObject(referenceObj.aSO);
			outputObj = referenceObj;
		}
		//An object that contains an array of primitives. Set the array values in here
		else if (selectionInt == 3) {
			ArrayPrimitives arrayPObj = new ArrayPrimitives();
			arrayPObj.arrayPrimitives = new int[5];
			for(int i = 0; i < 5; i++) {
				arrayPObj.arrayPrimitives[i] = i;	
				System.out.println("Index of " + i + " current value " + arrayPObj.arrayPrimitives[i]); 
				System.out.println("Change value to: ");
				String input = in.readLine();
				arrayPObj.arrayPrimitives[i] = Integer.parseInt(input); 
			}

			outputObj = arrayPObj;
		}
		//An object that contains an array of object references.
		else if ( selectionInt==4) {
			ArrayReferences arrayRObj = new ArrayReferences();
			outputObj = arrayRObj;
		}
		//An object that uses an instance of one of Java's collection classes to refer to several other objects.
		else if (selectionInt== 5) { 
			ObjectCollection collectionObj = new ObjectCollection();
			outputObj = collectionObj;
		}
	}


	
		private static void printObjectsSelection() {
			System.out.println("1. A simple object with only primitives for instance variables. You will be able to set values for these fields");
			System.out.println("2. An object that contains references to other objects. The primitive instance variables will be able to be set by you.");
			System.out.println("3. An object that contains an array of primitives. You will set the values in this array.");
			System.out.println("4. An object that contains an array of object references.");
			System.out.println("5. An object that uses an instance of one of Java's collection classes to refer to several other objects");
		}
}