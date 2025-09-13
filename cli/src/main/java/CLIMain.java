import control.procedure.ProcedureDispatcher;
import model.procedure.Procedure;
import model.procedure.ProcedureOption;
import model.procedure.ProcedureOptionType;
import model.procedure.ProcedureType;

import java.util.ArrayList;
import java.util.List;


/**
 * The CLIMain class serves as the starting point of the application. It contains the main method which is the entry point for execution. This class cannot be instantiated.
 */
public final class CLIMain
{

    /**
     * Private constructor to prevent instantiation of the CLIMain class. The CLIMain class is a utility class containing the main entry point and should not be instantiated.
     */
    private CLIMain()
    {
    }


    /**
     * The entry point of the application.
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(final String[] args)
    {
        final ProcedureOption sourceOption = ProcedureOption.builder()
                .type(ProcedureOptionType.SOURCE)
                .value("C:/Users/Lasse/Desktop/source")
                .build();

        final ProcedureOption targetOption = ProcedureOption.builder()
                .type(ProcedureOptionType.TARGET)
                .value("C:/Users/Lasse/Desktop/target")
                .build();

        final List<ProcedureOption> options = new ArrayList<>();
        options.add(sourceOption);
        options.add(targetOption);

        final Procedure procedure = Procedure.builder()
                .name("Test")
                .id("Test-1")
                .type(ProcedureType.MOVE)
                .options(options)
                .build();

        final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
        dispatcher.dispatch(procedure);
    }
}
