package io.keepcoding.madridguide.model;

public interface IElementsUpdate<T> {
    public void add(T element);
    public void delete(T element);
    public void edit(T element, long index);
}
