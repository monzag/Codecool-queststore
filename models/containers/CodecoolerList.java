package models.containers;

import java.util.ArrayList;
import java.util.Iterator;

import models.account.Codecooler;


public abstract class CodecoolerList<T extends Codecooler> implements Storable<T> {

    protected ArrayList<T> codecoolers;

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
