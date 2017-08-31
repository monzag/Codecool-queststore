package models.containers;

import java.util.ArrayList;
import java.util.Iterator;


public abstract class CodecoolerList<Codecooler> implements Storable {

    private ArrayList<Codecooler> codecoolers;

    public CodecoolerList() {
        this.codecoolers = new ArrayList<Codecooler>();
    }

    public ArrayList<Codecooler> getAll() {
        return this.codecoolers;
    }

    public abstract void load();

    public abstract void save();

    public boolean add(Codecooler codecooler) {
        if (!this.codecoolers.contains(codecooler)) {
            codecoolers.add(codecooler);
            return true;
        }
        return false;
    }

    public Iterator getIterator() {
        return this.codecoolers.iterator();
    }

    public boolean remove(Codecooler codecooler) {
        return this.codecoolers.remove(codecooler);
    }

}
