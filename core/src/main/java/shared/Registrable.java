package shared;

/**
 * Represents a generic interface for managing the registration and unregistration of objects of type T.
 * This interface defines a contract for classes that need to maintain a collection of registrable entities
 * or listeners and provides methods to add or remove them from the registration system.
 *
 * @param <T> the type of objects that can be registered and unregistered
 */
public interface Registrable<T>
{
    /**
     * Registers an instance of the specified type T for further use or handling.
     *
     * @param t the instance of type T to be registered. It must not be null and represents the object or listener to be added to the registration system.
     */
    void register(final T t);

    /**
     * Unregisters an instance of the specified type T, removing it from the registration system.
     *
     * @param t the instance of type T to be unregistered. It represents the object or listener that should no longer receive updates or events.
     */
    void unregister(final T t);
}
