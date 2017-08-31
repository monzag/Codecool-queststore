package models.containers;

import java.util.ArrayList;
import java.util.Iterator;


public abstract class CodecoolerList<T> implements Storable {

    private ArrayList<T> codecoolers;

    public CodecoolerList() {
        this.codecoolers = new ArrayList<T>();
    }

    public ArrayList<T> getAll() {
        return this.codecoolers;
    }

    public abstract void load();

    public abstract void save();

    public boolean add(T codecooler) {
        if (!this.codecoolers.contains(codecooler)) {
            codecoolers.add(codecooler);
            return true;
        }
        return false;
    }

    public Iterator getIterator() {
        return this.codecoolers.iterator();
    }

    public boolean remove(T codecooler) {
        return this.codecoolers.remove(codecooler);
    }

}
