package org.gagauz.shop.utils;

import java.util.Iterator;

public class TreeIterator<T extends Parent<T>> implements Iterator<T> {

    private IteratorWrapper<T> wrapper;
    private T next;

    protected TreeIterator(Iterator<T> original) {
        this.wrapper = new IteratorWrapper<>(original, null);
    }

    public void enter() {

    }

    public void exit() {

    }

    @Override
    public boolean hasNext() {
        boolean b = wrapper.current.hasNext();
        while (!b && wrapper.parent != null) {
            wrapper = wrapper.parent;
            exit();
            b = wrapper.current.hasNext();
        }
        return b;
    }

    @Override
    public T next() {
        next = wrapper.current.next();
        if (next.getChildren().size() > 0) {
            wrapper = new IteratorWrapper<>(next.getChildren().iterator(), wrapper);
            enter();
        }
        return next;
    }

    private static class IteratorWrapper<T> {
        final Iterator<T> current;
        final IteratorWrapper<T> parent;

        IteratorWrapper(Iterator<T> current, IteratorWrapper<T> parent) {
            this.current = current;
            this.parent = parent;
        }

    }

}
