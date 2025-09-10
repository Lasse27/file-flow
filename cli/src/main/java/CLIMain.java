import control.procedure.ProcedureDispatcher;
import model.procedure.Procedure;
import model.procedure.ProcedureOption;
import model.procedure.ProcedureOptionType;
import model.procedure.ProcedureType;


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
        final ProcedureOption sourceOption = new ProcedureOption.Builder()
                .withType(ProcedureOptionType.SOURCE)
                .withValue("C:/Users/Lasse/Desktop/source")
                .build();

        final ProcedureOption targetOption = new ProcedureOption.Builder()
                .withType(ProcedureOptionType.TARGET)
                .withValue("C:/Users/Lasse/Desktop/target")
                .build();

        final Procedure procedure = new Procedure.Builder()
                .withName("Test")
                .withId("Test-1")
                .withType(ProcedureType.MOVE)
                .withOption(sourceOption)
                .withOption(targetOption)
                .build();

        final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
        dispatcher.dispatch(procedure);
    }
}
