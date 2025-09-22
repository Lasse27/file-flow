package control.procedure.handler;

import control.procedure.executor.CleanProcedureExecutor;
import control.procedure.executor.ProcedureExecutor;
import control.procedure.validator.CleanProcedureValidator;
import control.procedure.validator.ProcedureValidator;
import exception.ProcedureHandlerException;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.CleanProcedure;

/**
 * The CleanProcedureHandler class is responsible for managing the lifecycle of {@link CleanProcedure} instances.
 * It implements the {@link ProcedureHandler} interface, providing validation, execution, and listener registration
 * functionalities specific to clean procedures.
 *
 * @see CleanProcedure
 * @see ProcedureHandler
 * @see CleanProcedureValidator
 * @see CleanProcedureExecutor
 * @see Listener
 */
public class CleanProcedureHandler implements ProcedureHandler<CleanProcedure>
{
    /**
     * A validator responsible for ensuring that {@link CleanProcedure} instances are properly constructed
     * and meet all required criteria before execution.
     *
     * @see CleanProcedureValidator
     * @see ProcedureValidator
     */
    private final ProcedureValidator<CleanProcedure> validator = new CleanProcedureValidator();

    /**
     * Responsible for executing {@link CleanProcedure} instances. This functional interface implementation
     * provides the execution logic required to handle cleaning of the source path.
     *
     * @see CleanProcedure
     * @see CleanProcedureExecutor
     * @see ProcedureExecutor
     */
    private final ProcedureExecutor<CleanProcedure> executor = new CleanProcedureExecutor();

    /**
     * A collection of {@link Listener} objects associated with the {@code CleanProcedureHandler}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final CleanProcedure procedure) throws ProcedureHandlerException
    {
        this.validator.register(this.listeners);
        this.validator.validate(procedure);
        this.executor.register(this.listeners);
        this.executor.execute(procedure);
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
