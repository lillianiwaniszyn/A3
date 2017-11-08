package a3;

import java.util.Collection;
import java.util.HashSet;

public class ObjectCollection {
	public Collection<SimpleObject> simpleObj;

	public ObjectCollection(){
		simpleObj = new HashSet<SimpleObject>();

		for (int i=0; i < 5; i++){
			simpleObj.add(new SimpleObject()); 
		}
	}

}
