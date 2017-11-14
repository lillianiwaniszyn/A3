package a3;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

import org.jdom.Document;
import org.junit.Before;
import org.junit.Test;

public class TestReciever implements Runnable{
	@Before
    public void setUp() throws ArrayIndexOutOfBoundsException, Exception 
    {
        Runnable r = new Runnable1();
        Thread t = new Thread(r);
        Runnable r2 = new Runnable2();
        Thread t2 = new Thread(r2);
        t.start();
        t2.start();
 
    
} 

            
    

	@Test
	public void recieverTest() {
		File shouldExist = new File("Recieved_Data.xml");
		assertTrue(shouldExist.exists());
	}




	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
class Runnable2 implements Runnable{
    public void run(){
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			Driver.main(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

class Runnable1 implements Runnable{
    public void run(){
        try {
			Reciever.main(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
