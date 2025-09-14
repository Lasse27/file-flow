package model.shared;

public interface Registrable<T>
{
    void register(T t);

    void unregister(T t);
}
