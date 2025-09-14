package control.procedure.handler;


import control.procedure.executor.MoveProcedureExecutor;
import control.procedure.executor.ProcedureExecutor;
import control.procedure.validator.MoveProcedureValidator;
import control.procedure.validator.ProcedureValidator;
import model.procedure.ProcedureType;
import model.procedure.types.MoveProcedure;


/**
 * Implementation of {@link ProcedureHandler} that handles procedures of type {@link ProcedureType#MOVE}.
 */
public final class MoveProcedureHandler implements ProcedureHandler<MoveProcedure>
{

    /**
     * A validator responsible for ensuring that {@link MoveProcedure} instances are properly constructed
     * and meet all required criteria before execution.
     *
     * @see MoveProcedureValidator
     * @see ProcedureValidator
     */
    private final ProcedureValidator<MoveProcedure> validator = new MoveProcedureValidator();

    /**
     * Responsible for executing {@link MoveProcedure} instances. This functional interface implementation
     * provides the execution logic required to handle the movement of files between a source path and a
     * target path as defined in the associated procedure configuration.
     *
     * @see MoveProcedure
     * @see MoveProcedureExecutor
     * @see ProcedureExecutor
     */
    private final ProcedureExecutor<MoveProcedure> moveProcedureExecutor = new MoveProcedureExecutor();


    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final MoveProcedure procedure)
    {
        this.validator.validate(procedure);
        this.moveProcedureExecutor.execute(procedure);
    }
}
