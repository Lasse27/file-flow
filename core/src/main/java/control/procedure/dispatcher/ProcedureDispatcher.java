package control.procedure.dispatcher;


import control.procedure.handler.CleanProcedureHandler;
import control.procedure.handler.DeleteProcedureHandler;
import control.procedure.handler.MoveProcedureHandler;
import control.procedure.handler.ProcedureHandler;
import exception.ProcedureDispatcherException;
import exception.ProcedureHandlerException;
import lombok.NoArgsConstructor;
import lombok.ToString;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.listener.ListenerEvent;
import model.procedure.*;
import shared.Registrable;
import shared.Contracts;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;


/**
 * This class provides the ability to execute a given {@link Procedure}.
 */
@ToString
@NoArgsConstructor
public class ProcedureDispatcher implements Registrable<Listener>, Dispatcher<Procedure, ProcedureHandler<? extends Procedure>>
{

    /**
     * A static, immutable map that associates each {@link ProcedureType} with its corresponding {@link ProcedureHandler}.
     */
    private static final Map<Class<? extends Procedure>, Supplier<ProcedureHandler<? extends Procedure>>> handlers = Map.of(
            MoveProcedure.class, MoveProcedureHandler::new,
            DeleteProcedure.class, DeleteProcedureHandler::new,
            CleanProcedure.class, CleanProcedureHandler::new
//            ZipProcedureHandler.class, ZipProcedureHandler::new,
//            UnzipProcedureHandler.class, UnzipProcedureHandler::new,
//            RenameProcedureHandler.class, RenameProcedureHandler::new
    );

    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * Dispatches a given {@link Procedure} to the appropriate handler for execution.
     *
     * @param procedure the procedure to be dispatched. This object contains the type of procedure and its associated options. The procedure's type is used to determine the
     *                  appropriate handler for execution.
     */
    @Override
    public void dispatch(final Procedure procedure)
    {
        // Execute handler
        this.executeHandler(
                this.getHandler(procedure),
                procedure
        );
    }


    /**
     * Retrieves the appropriate {@link ProcedureHandler} for a given {@link Procedure}, based on its type. If the procedure type is unsupported or null, a
     * {@link ProcedureDispatcherException} is thrown.
     *
     * @param procedure the {@link Procedure} for which the handler is to be retrieved. Must not be null and must have a valid type defined.
     * @return the corresponding {@link ProcedureHandler} for the specified procedure type.
     * @throws ProcedureDispatcherException if the procedure is null, its type is null, or the type does not have a corresponding handler.
     */
    @Override
    public ProcedureHandler<? extends Procedure> getHandler(final Procedure procedure)
    {
        // Contracts
        Contracts.notNull(procedure, () -> new ProcedureDispatcherException("No procedure specified."));

        // Get handler
        return Optional.ofNullable(handlers.get(procedure.getClass()))
                .map(Supplier::get)
                .orElseThrow(() -> new ProcedureDispatcherException(
                        String.format("Unsupported procedure type: %s", procedure.getClass().getSimpleName())
                ));
    }


    /**
     * Executes the specified {@link ProcedureHandler} on a given {@link Procedure}. If the handler fails to process the procedure, a {@link ProcedureDispatcherException} is
     * thrown with details of the failure.
     *
     * @param handler   the {@link ProcedureHandler} responsible for processing the {@link Procedure}. Must not be null.
     * @param procedure the {@link Procedure} to be handled. Must contain valid attributes necessary for its execution.
     * @throws ProcedureDispatcherException if an error occurs during the procedure handling, wrapping the original {@link ProcedureHandlerException}.
     */
    private void executeHandler(final ProcedureHandler handler, final Procedure procedure)
    {
        // Contracts
        Contracts.notNull(handler, () -> new ProcedureDispatcherException("No handler specified."));
        Contracts.notNull(procedure, () -> new ProcedureDispatcherException("No procedure specified."));

        try
        {
            this.listeners.onStart(ListenerEvent.builder()
                    .message(String.format("Executing procedure: %s", procedure.getName()))
                    .build());

            //noinspection unchecked
            handler.register(this.listeners);
            //noinspection unchecked
            handler.handle(procedure);

            this.listeners.onEnd(ListenerEvent.builder()
                    .message(String.format("Procedure %s execution finished.", procedure.getName()))
                    .build());
        }
        catch (final ProcedureHandlerException exception)
        {
            throw new ProcedureDispatcherException(
                    String.format("Error executing procedure: %s", procedure.getName()),
                    exception
            );
        }
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
