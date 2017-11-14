package a3;
import org.jdom.Document;
import org.jdom.Element;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Deserializer {

	public static Object deserialize(Document document) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		List objectList = document.getRootElement().getChildren();
		Map table = new HashMap();
		createInstances(table, objectList);
		assignFieldValues(table, objectList);
		return table.get("0");
	}
	public static Object deserializeValue(Element valueE, Class fieldType,Map table) {
		Object returnVal;
		String valtype = valueE.getName();
		if (valtype.equals("null"))
			return null;
		else if (valtype.equals("reference"))
			return table.get(valueE.getText());
		else {
			returnVal = testType(fieldType, valueE);
			
			
		}
		return returnVal;
	}


	private static Object testType(Class fieldType, Element valueE) {
		if (fieldType.equals(boolean.class)) {
			if (valueE.getText().equals("true"))
				return Boolean.TRUE;
			else
				return Boolean.FALSE;
		}
		
		else if (fieldType.equals(long.class))
			return Long.valueOf(valueE.getText());
		else if (fieldType.equals(float.class))
			return Float.valueOf(valueE.getText());
		else if (fieldType.equals(double.class))
			return Double.valueOf(valueE.getText());
		else if (fieldType.equals(char.class))
			return new Character(valueE.getText().charAt(0));
		else if (fieldType.equals(byte.class))
			return Byte.valueOf(valueE.getText());
		else if (fieldType.equals(short.class))
			return Short.valueOf(valueE.getText());
		else if (fieldType.equals(int.class))
			return Integer.valueOf(valueE.getText());
		else
			return valueE.getText();
		
	}
	public static void assignFieldValues(Map table, List objectList) throws ClassNotFoundException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		for (Object e: objectList) {
			Element objectElement = (Element) e;
			Object instance = table.get(objectElement.getAttributeValue("id"));
			List fieldElements = objectElement.getChildren();
			if (!instance.getClass().isArray()) {
				for (Object x : fieldElements) {
					Element fieldElement = (Element) x;
					String className = fieldElement.getAttributeValue("declaringclass");
					Class fieldDC = Class.forName(className);
					String fieldName = fieldElement.getAttributeValue("name");
					Field f = fieldDC.getDeclaredField(fieldName);
					f.setAccessible(true);
					Element vElt = (Element) fieldElement.getChildren().get(0);
					f.set(instance, deserializeValue(vElt, f.getType(), table));
				}
			} else {
				Class comptype = instance.getClass().getComponentType();
				for (int j=0; j<fieldElements.size(); j++)
					Array.set(instance, j, deserializeValue((Element)fieldElements.get(j), comptype, table));
			}
		}
		
	}

	

	public static void createInstances(Map table, List objectList) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for (int i=0; i<objectList.size(); i++) {
			Element objectElement = (Element) objectList.get(i);
			Class myClass = Class.forName(objectElement.getAttributeValue("class"));
			Object instance = null;
			if (!myClass.isArray()) {
				Constructor c = myClass.getDeclaredConstructor(null);
				c.setAccessible(true);
				instance = c.newInstance(null);
			} 
			else
				instance = Array.newInstance(myClass.getComponentType(),Integer.parseInt(objectElement.getAttributeValue("length")));
				table.put(objectElement.getAttributeValue("id"), instance);
		}
		
	}

}
