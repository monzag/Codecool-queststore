package models.containers;


public interface Storable<T> {

    public void load();
    public void save();
    public void add(T storableObject);
    public boolean remove(T storableObject);
    public ArrayList<T> getAll();
    public Iterator getIterator();

}
