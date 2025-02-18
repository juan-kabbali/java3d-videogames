/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uao.compu.appgames;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class CheckList<T> {

    private LinkedList<T> storage = new LinkedList<T>();
    public int actual = 0;

    public void add(T item) {
        storage.add(item);
    }

    public void next() {
        if (actual < storage.size() - 1) {
            ++actual;
        } else {
            actual = 0;
        }
    }

    public void add(List<T> items) {
        storage.addAll(items);
    }

    public T select() {
        return storage.get(actual);
    }
}
