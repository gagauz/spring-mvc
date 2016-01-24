package org.gagauz.shop.utils;

import java.util.Iterator;

public class TreeIterator<T extends Parent<T>> implements Iterator<T> {

    private IteratorWrapper<T> wrapper;
    private T next;

    TreeIterator(Iterator<T> original) {
        this.wrapper = new IteratorWrapper<>(original, null);
    }

    @Override
    public boolean hasNext() {
        boolean b = wrapper.current.hasNext();
        while (!b && wrapper.parent != null) {
            wrapper = wrapper.parent;
            b = wrapper.current.hasNext();
        }
        return b;
    }

    @Override
    public T next() {
        next = wrapper.current.next();
        if (next.getChildren().size() > 0) {
            wrapper = new IteratorWrapper<>(next.getChildren().iterator(), wrapper);
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
