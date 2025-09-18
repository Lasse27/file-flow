package control.procedure.handler;

import control.procedure.executor.DeleteProcedureExecutor;
import control.procedure.executor.ProcedureExecutor;
import control.procedure.validator.DeleteProcedureValidator;
import control.procedure.validator.ProcedureValidator;
import exception.ProcedureHandlerException;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.types.DeleteProcedure;

/**
 * Handles the execution of {@link DeleteProcedure} instances, including their validation and execution processes.
 * This class acts as the controller for managing the entire lifecycle of the delete procedure by combining
 * validation and execution with listener notifications.
 *
 * @see ProcedureHandler
 * @see DeleteProcedure
 * @see DeleteProcedureValidator
 * @see DeleteProcedureExecutor
 * @see Listener
 */
public class DeleteProcedureHandler implements ProcedureHandler<DeleteProcedure>
{
    /**
     * A collection of {@link Listener} objects associated with the {@code DeleteProcedureHandler}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    private final ListenerCollection listeners = ListenerCollection.builder().build();

    /**
     * A validator responsible for ensuring that {@link DeleteProcedure} instances are properly constructed
     * and meet all required criteria before execution.
     *
     * @see DeleteProcedureValidator
     * @see ProcedureValidator
     */
    private final ProcedureValidator<DeleteProcedure> validator = new DeleteProcedureValidator();

    /**
     * Responsible for executing {@link DeleteProcedure} instances. This functional interface implementation
     * provides the execution logic required to handle the movement of files between a source path and a
     * target path as defined in the associated procedure configuration.
     *
     * @see DeleteProcedure
     * @see DeleteProcedureExecutor
     * @see ProcedureExecutor
     */
    private final ProcedureExecutor<DeleteProcedure> executor = new DeleteProcedureExecutor();


    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final DeleteProcedure procedure) throws ProcedureHandlerException
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
