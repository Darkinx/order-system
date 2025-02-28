package com.darkin.electronicordersystem.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * This is designed for the Spinner to carry the min and max setup.
 *
 * @see: https://stackoverflow.com/questions/37810745/tableview-and-spinner-in-javafx
 */
public class Item {
    private final IntegerProperty itemCount = new SimpleIntegerProperty();
    private final IntegerProperty itemMaxCount = new SimpleIntegerProperty();

    @Override
    public String toString() {
        return Integer.toString(getItemCount());
    }

    public Item(int disp, int max) {
        this.itemCount.setValue(disp);
        this.itemMaxCount.setValue(max);
    }

    public final int getItemCount() {
        return this.itemCount.get();
    }

    public final void setItemCount(int value) {
        this.itemCount.set(value);
    }

    public final IntegerProperty itemCountProperty() {
        return this.itemCount;
    }

    public final int getItemMaxCount() {
        return this.itemMaxCount.get();
    }

    public final void setItemMaxCount(int value) {
        this.itemMaxCount.set(value);
    }

    public final IntegerProperty itemMaxCountProperty() {
        return this.itemMaxCount;
    }

}