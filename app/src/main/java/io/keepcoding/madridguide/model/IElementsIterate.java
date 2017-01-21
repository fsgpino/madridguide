package io.keepcoding.madridguide.model;

import java.util.List;

public interface IElementsIterate<T> {
    public long size();
    public T get(long index);
    public int indexOf(T element);
    public List<T> allElements();
}
