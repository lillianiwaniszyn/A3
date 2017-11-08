package a3;

public class ArrayReferences {
	public SimpleObject [] arraySimple;

	public ArrayReferences() {
		arraySimple = new SimpleObject[5];

		for(int i = 0; i < arraySimple.length; i++) {
			arraySimple[i] = new SimpleObject();
		}

	}

}
