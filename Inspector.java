package a3;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.Vector;

public class Inspector {
		public void inspect(Object obj, boolean recursive) throws IllegalArgumentException, IllegalAccessException {
			//name of declaring class
			System.out.println("Visualizing created object....");
			System.out.println();
			Class classObj = obj.getClass();
			Vector objects = new Vector();
			String declaringClass = obj.getClass().getSimpleName();
			System.out.print("The name of the declaring class is: ");
			System.out.println(declaringClass);
			//name of immediate superclass
			String immediateSuperClass = obj.getClass().getSuperclass().getSimpleName();
			System.out.print("The name of the immediate superclass is: ");
			System.out.println(immediateSuperClass);
			//name of interfaces implemented
			inspectInterfaces(classObj);
			inspectMethods(classObj);
			inspectConstructors(classObj);
			inspectFields(obj, classObj, objects);
			if (recursive==true) {
				inspectFieldClasses(obj, classObj, objects, recursive);
			}
			while ((classObj.getSuperclass() != null)&& (classObj.getSuperclass() != Object.class)) {
				inspectSuperclass(obj, classObj, objects, recursive);
				classObj = classObj.getSuperclass();
			}
			System.out.println("Finished Inspecting Object!");
			System.out.println();
		}
		private void inspectSuperclass(Object obj, Class classObj, Vector objects, Boolean recursive) throws IllegalArgumentException, IllegalAccessException {
			System.out.println();
			Class superClass =classObj.getSuperclass();
			System.out.println("'" + classObj.getSimpleName() + "' Superclass: " + classObj.getSuperclass().getSimpleName());
			Class superclass = classObj.getSuperclass();
			System.out.println(classObj.getSuperclass().getSimpleName() + "'s" + " SuperClass is " +superClass.getSuperclass().getSimpleName());
			inspectInterfaces(superclass); 
			inspectMethods(superclass);
			inspectConstructors(superclass);
			inspectFields(obj, superclass, new Vector());
			if (recursive==true) {
				inspectFieldClasses(obj, classObj, objects, recursive);
			}
			
			
		}
		private void inspectFieldClasses(Object obj, Class classObj, Vector objects, boolean recursive) {
			if (objects.size() > 0) {
			}
			Enumeration e = objects.elements();
			while (e.hasMoreElements()) {
				Field f = (Field) e.nextElement();
				System.out.println("Inspecting Field: " + f.getName());
				try {
					System.out.println("---");
					inspect(f.get(obj), recursive);
					System.out.println("---");
				} catch (NullPointerException nullExp) {
					System.out.println("Field not instantiated at runtime");
					System.out.println("---");
				} catch (Exception exp) {
					exp.printStackTrace();
				}
			}
			System.out.println("End of " + classObj.getSimpleName()+ " field classes");
		}
			
		
		public Vector<String> inspectInterfaces(Class classObj) {
			Vector v = new Vector();
			Class[] interfaces = classObj.getInterfaces();
			if (interfaces.length > 0) {
				System.out.print("The interfaces implemented by " + classObj.getSimpleName() +" are: ");
				for (Class j : interfaces) {
					System.out.println(j.getName());
					v.add(j.getName());
				}
			}
			else {
				System.out.println("No interfaces found");
			}
			return v;	
		}

		public Vector<String> inspectMethods(Class classObj) {
			Vector m = new Vector();
			Method[] methods = classObj.getDeclaredMethods();
			System.out.println("The methods in " + classObj.getSimpleName() + " are: ");
			if (methods.length >0) {
				for (Method i : methods) {
					//get exception types
					Class [] exception = i.getExceptionTypes();	
					//get parameter types
					Class[] parameterTypes = i.getParameterTypes();
					//get return type
					Class returnType = i.getReturnType();
					//get modifiers
					int modifiers = i.getModifiers();
					String sModifiers = Modifier.toString(modifiers);
					System.out.print("Method: ");
					System.out.print(i.getName());
					m.add(i.getName());
					//loop through exceptions
					if(exception.length >0 ) {
						for (Class j : exception) { // fix this to say no exceptions
							System.out.print(", Exception Name: ");
							System.out.print(j.getName());
							m.add(j.getName());
						}
					}
					else {
						System.out.print(", No exceptions found");
					}

					System.out.print(", Parameter Types: ");
					if(parameterTypes.length > 0) {
						for (Class j : parameterTypes) {
							System.out.print(j.getName() + ",");
							m.add(j.getName());
						}
					}
					else {
						System.out.print("No parameters");
					}
					System.out.print(" Return Type: " +  returnType.getName());
					m.add(returnType.getName());
					System.out.println(" Modifiers: " +  sModifiers);
					m.add(sModifiers);
				}
			}
			else {
				System.out.println("No methods found");
			}
			return m;	
		}
		public Vector<String> inspectConstructors(Class classObj) {
			Vector cons = new Vector();
			System.out.println("\nThe constructors in " + classObj.getSimpleName() + " are: ");
			Constructor[] constructors = classObj.getDeclaredConstructors();
			if (constructors.length >0 ) {
				for (Constructor x : constructors) {
					System.out.print("Constructor Name: " + x.getName());
					cons.add(x.getName());
					//get parameter types
					Class [] parameterTypes = x.getParameterTypes();
					//get modifiers
					int modifiers = x.getModifiers();
					String sModifiers = Modifier.toString(modifiers);
					System.out.print(" Parameter types: ");
					if (parameterTypes.length > 0) {
						for (Class c : parameterTypes) {
							System.out.print(c.getSimpleName());
							cons.add(c.getSimpleName());
						}
					}
					else {
						System.out.print(" No parameters");
					}

					System.out.println(" Modifiers: " +  sModifiers);
					cons.addElement(sModifiers);
				}
			}
			else {
				System.out.println("No constructors found");
			}
			return cons;

			
		}
		public void inspectFields(Object obj, Class classObj, Vector objects) throws IllegalArgumentException, IllegalAccessException {
			System.out.println("\n\nThe fields in this class are:");
			Field[] fields = classObj.getDeclaredFields();
			System.out.println("Number of fields: " +fields.length);
			for (Field x : fields) {
				System.out.println();
				x.setAccessible(true);
				if (!x.getType().isPrimitive()) {
					objects.addElement(x);
				}
				if (x.getType().isArray()) {
					System.out.println("Field: '" + x.getName() + "'\n\tType: " + x.getType().getComponentType() + "\n\tModifier: " + Modifier.toString(x.getModifiers()));
					System.out.println("Array found! Length = " + Array.getLength(x.get(obj)));
					System.out.println("Printing array content...");
					Object array = x.get(obj);
					  int length = Array.getLength(array);
					  for (int i = 0; i < length; i++) {
					    System.out.print(Array.get(array, i) + " ");
					  }
					  System.out.println();

				} else {
					System.out.println("Field: '" + x.getName() + "' = " + x.get(obj) + "\n\tType: " + x.getType()+ "\n\tModifier: "+ Modifier.toString(x.getModifiers()));
				}		
			}
			
			
			
		}

		public void inspectFieldsRecursive(Object obj) throws IllegalArgumentException, IllegalAccessException {
			Class classObj = obj.getClass();
			System.out.println("\n\nThe fields in this class are:");
			System.out.println();
			Field[] fields = classObj.getDeclaredFields();
			for (Field x : fields) {
				System.out.println();
				System.out.print(x.getName());
				int modifiers = x.getModifiers();
				Type type = x.getGenericType();
				String sModifiers = Modifier.toString(modifiers);
				System.out.print(", " + type.getTypeName());
				System.out.print(", " + sModifiers);
				if (x.getType().isPrimitive()){
					System.out.println(", " + x.getType());
				}
				else {
					inspectFieldsRecursive(x);
				}
				
			
			}
			
		}

	

}
