package control.procedure.dispatcher;

/**
 * Represents a generic dispatcher interface for dispatching objects of type T.
 *
 * @param <T> the type of objects to be dispatched
 */
public interface Dispatcher<T, H>
{
    /**
     * Dispatches a given object of type T for further processing.
     *
     * @param t the object to be dispatched. This parameter represents the item that needs to be processed and is provided as input to the dispatching mechanism.
     */
    void dispatch(T t);


    /**
     * Retrieves the appropriate handler for the specified object of type T.
     *
     * @param t the input object for which the handler is to be retrieved. Must not be null and should have a valid type.
     * @return the corresponding handler of type H for the given input.
     * @throws IllegalArgumentException if the input object is null or if an appropriate handler cannot be found.
     */
    H getHandler(T t);
}
