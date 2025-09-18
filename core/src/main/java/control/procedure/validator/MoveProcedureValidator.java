package control.procedure.validator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.listener.Listener;
import model.listener.ListenerCollection;
import model.procedure.types.MoveProcedure;

/**
 * A specific implementation of the {@link ProcedureValidator} interface that validates the parameters required for executing a file move procedure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveProcedureValidator implements ProcedureValidator<MoveProcedure>
{

    /**
     * A collection of {@link Listener} objects associated with the {@code MoveProcedureValidator}. This field manages
     * the registration and notification of {@link Listener} objects for lifecycle events of the file-moving procedure.
     */
    @Builder.Default
    private final ListenerCollection listeners = ListenerCollection.builder().build();


    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final MoveProcedure procedure)
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
