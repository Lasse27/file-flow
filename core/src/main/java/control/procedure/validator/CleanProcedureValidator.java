package control.procedure.validator;

import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.CleanProcedure;

/**
 * A specific implementation of the {@link ProcedureValidator} interface for validating instances of {@link CleanProcedure}.
 * This class defines the necessary logic to validate parameters for clean procedures, ensuring that the
 * provided configuration data is complete and accurate.
 *
 * @see CleanProcedure
 * @see ProcedureValidator
 * @see Listener
 */
public class CleanProcedureValidator implements ProcedureValidator<CleanProcedure>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code CleanProcedureValidator}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final CleanProcedure procedure)
    {
        // TODO: implement validation of clean procedure
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
