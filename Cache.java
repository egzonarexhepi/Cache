/**
 * 
 * Cache class (implementing linked list)
 * to store data (of generic type) in memory in a one level
 * and two level cache operating on multilevel inclusion property
 * 
 * Created 01/29/2019
 * 
 * @author egzonarexhepi
 *
 */

import java.util.LinkedList;

public class Cache<T> {
	
	private LinkedList<Object> list;
	
	int firstLevelCount = 0;
	int secondLevelCount = 0;
	
	int maxSize;
	
	public Cache(int levelSize) {
		list = new LinkedList<Object>();
		maxSize = levelSize;
	}
	
	public int getCurrentSize() {
		 return list.size();
	}
	
	public boolean hasObject(T object) {
		if (list.contains(object)) {
			return true;
		} else {
			return false;
		}
	}
	
	public T getObject(T object){
		return object;
		
	}
	
	public boolean isFull() {
		if (list.size() < maxSize) {
			return false;
		} else {
			return true;
		}
	}
	
	
	public void addObject(T object){
		list.addFirst(object);
	}
	
	public void removeObject(T object) {
		list.remove(object);
	}
	
	public void removeLastObject() {
		list.removeLast();
	}
	
	public void clearCache() {
		list.removeAll(list);
	}

}
