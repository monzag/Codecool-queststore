package models.containers;

import java.util.ArrayList;
import java.util.Iterator;


public interface Storable<T> {

    public void load();
    public void save();
    // add returns false if object already was on list
    public boolean add(T storableObject);
    // remove returns false if there was no such object on list
    public boolean remove(T storableObject);
    public ArrayList<T> getAll();
    public Iterator getIterator();

}
