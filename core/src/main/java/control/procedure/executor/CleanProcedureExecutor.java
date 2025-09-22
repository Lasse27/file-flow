package control.procedure.executor;

import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.CleanProcedure;

/**
 * The CleanProcedureExecutor class is an implementation of the {@link ProcedureExecutor} interface
 * specifically for handling {@link CleanProcedure} executions. It provides execution logic for cleaning
 * procedures while supporting listener management for lifecycle events.
 */
public class CleanProcedureExecutor implements ProcedureExecutor<CleanProcedure>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code CleanProcedureExecutor}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final CleanProcedure procedure)
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
