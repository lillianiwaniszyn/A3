package a3;

import static org.junit.Assert.*;

import java.lang.reflect.Field;


import org.junit.Before;
import org.junit.Test;

public class InspectorTest {
    Object simpleObject;
    Object referencesObject;
    Inspector testClass;
    @Before
    public void setUp() throws Exception
    {
    		simpleObject = new SimpleObject();
    		referencesObject = new ReferencesObject();
            testClass = new Inspector();
            
    }
	@Test
	public void testInspectClass() throws NoSuchFieldException, SecurityException {
		Class classObj = simpleObject.getClass();
		Class classObjRef = referencesObject.getClass();
		Field aField = classObjRef.getField("aSO");
		String typeField = aField.getType().getSimpleName();
		assertEquals( classObj.getSimpleName(), "SimpleObject");
		assertEquals( typeField, "SimpleObject");
	}
	
	
	

}
