package control.procedure.validator;

import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.types.DeleteProcedure;

/**
 * Implementation of the {@link ProcedureValidator} interface for validating {@link DeleteProcedure} instances.
 * This class provides validation logic specifically for procedures related to deleting files, ensuring that
 * the parameters provided in a {@code DeleteProcedure} are valid and complete.
 *
 * @see DeleteProcedure
 * @see ProcedureValidator
 * @see Listener
 */
public class DeleteProcedureValidator implements ProcedureValidator<DeleteProcedure>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code DeleteProcedureValidator}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final DeleteProcedure procedure)
    {
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void register(final Listener listener)
    {
        this.listeners.register(listener);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void unregister(final Listener listener)
    {
        this.listeners.unregister(listener);
    }
}
