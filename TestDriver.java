package a3;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.junit.Before;
import org.junit.Test;

public class TestDriver {
	Element e = null; 
	@Before
    public void setUp() throws Exception
    {
		String server = "localhost";
		int port = 8087;
		SimpleObject simpleObj = new SimpleObject();
		Driver.initializeSerializer(simpleObj, server, port);
		Document doc = Serializer.serialize(simpleObj);
		File aFile = Driver.createFile(doc);
		e = doc.getRootElement();
            
    }
	

	@Test
	//test if file is created called Sent_Data
	public void testDriver() {
		File shouldExist = new File("Sent_Data.xml");
		assertTrue(shouldExist.exists());
		//check to see if root element is called serialized
		assertEquals(e.getName(), "serialized");
		
	}

}
