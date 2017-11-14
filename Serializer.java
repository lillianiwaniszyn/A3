package a3;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
public class Serializer {

	public static org.jdom.Document serialize(Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		return serializeHelper(obj, new Document(new Element("serialized")), new IdentityHashMap());
	}
	private static Element serializeVariable(Class<?> fieldtype, Object child, Document target, Map table) throws Exception {
		if (child == null) {
			return new Element("null");
		}

		else if (!fieldtype.isPrimitive()) {
			Element reference = new Element("reference");
			if (table.containsKey(child))
				reference.setText(table.get(child).toString());
			else {
				reference.setText(Integer.toString(table.size()));
				serializeHelper(child, target, table);
			}
			return reference;
		} else {
			Element value = new Element("value");
			value.setText(child.toString());
			return value;
		}
	}

	private static Document serializeHelper(Object source, Document target, Map table) throws ArrayIndexOutOfBoundsException, IllegalArgumentException, Exception {
		String id = Integer.toString(table.size());
		table.put(source, id);
		Class sourceclass = source.getClass();
		Element objectElement = new Element("object");
		objectElement.setAttribute("class", sourceclass.getName());
		objectElement.setAttribute("id", id);
		target.getRootElement().addContent(objectElement);
		//if not an array
		if (!sourceclass.isArray()) {
			Field[] fields = sourceclass.getDeclaredFields();
			for (Field f: fields) {
				if (!Modifier.isPublic(f.getModifiers())) {
					f.setAccessible(true);
				}
				Element fieldElement = new Element("field");
				fieldElement.setAttribute("name", f.getName());
				Class declClass = f.getDeclaringClass();
				fieldElement.setAttribute("declaringclass", declClass.getName());
				Class fieldtype = f.getType();
				Object child = f.get(source);
				if (Modifier.isTransient(f.getModifiers())) {
					child = null;
				}
				fieldElement.addContent(serializeVariable(fieldtype, child, target,table));
				objectElement.addContent(fieldElement);
			}
		} 
		else {
			Class componentType = sourceclass.getComponentType();
			int length = Array.getLength(source);
			objectElement.setAttribute("length", Integer.toString(length));
			for (int i = 0; i < length; i++)
				objectElement.addContent(serializeVariable(componentType, Array.get(source, i), target, table));
		}
		return target;
	}


}
