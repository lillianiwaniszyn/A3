package a3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class ObjectCreator {
	public static void initializeObject(Object obj) throws Exception {
		System.out.println("Please set up the above fields for these objects");
		Class classObj = obj.getClass();
		Field [] fieldObj = classObj.getFields();
		for(Field x : fieldObj) {
			x.setAccessible(true);
			System.out.println("Name : " + x.getName());
			Field currentField = classObj.getDeclaredField(x.getName());
			System.out.println("Type : " + currentField.getType().toString());
			System.out.println("Type in value: ");

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String input = in.readLine();
			if(currentField.getType().getName().equals("int")) {
				currentField.set(obj, Integer.parseInt(input));
			}
			else if(currentField.getType().getName().equals("char")) {
				currentField.set(obj, input.charAt(0));
			}

		}
	}

}
