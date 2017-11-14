package a3;

import java.io.*;
import java.net.*;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.JDOMException;

public class Reciever {
	public static void main(String[] args) throws IOException, IllegalArgumentException, IllegalAccessException {
		int port = 8090;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + port);
			System.exit(1);
		}
		while (true) {
			System.out.println("Waiting for client request...");
			File aFile = new File("Recieved_Data.xml");
			Socket sock = null;
			try {
				sock = serverSocket.accept();
				System.out.println("Client connected!");
			} catch (IOException e) {
				System.err.println("Accept failed");
				System.exit(1);
			}
			receiveFile(aFile, sock);
			Object obj = buildObject(aFile);
			Inspector inspector = new Inspector();
			inspector.inspect(obj, true);
		}
	}
	public static Object buildObject(File aFile) {
		SAXBuilder builder = new SAXBuilder();
		Object obj = null;
		try {
			Document doc = (Document) builder.build(aFile);
			obj = Deserializer.deserialize(doc);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void receiveFile(File aFile, Socket sock) throws IOException, FileNotFoundException {
		InputStream input = sock.getInputStream();
		FileOutputStream out = new FileOutputStream(aFile);
		byte[] buffer = new byte[1024 * 1024];
		int bytesReceived = 0;
		System.out.println("receiving file");
		while ((bytesReceived = input.read(buffer)) > 0) {
			out.write(buffer, 0, bytesReceived);
			System.out.println(bytesReceived + " Bytes received.");
			break;
		}
	}


}