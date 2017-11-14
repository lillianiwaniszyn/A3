package a3;

import static org.junit.Assert.*;

import java.io.File;

import org.jdom.Document;
import org.junit.Before;
import org.junit.Test;

public class TestDriver {
	@Before
    public void setUp() throws Exception
    {
		String server = "localhost";
		int port = 8087;
		SimpleObject simpleObj = new SimpleObject();
		Driver.serialize(simpleObj, server, port);
		Document doc = Serializer.serialize(simpleObj);
		File aFile = Driver.createFile(doc);
            
    }
	

	@Test
	//test if file is created called Sent_Data
	public void test() {
		File shouldExist = new File("Sent_Data.xml");
		assertTrue(shouldExist.exists());
	}

}
