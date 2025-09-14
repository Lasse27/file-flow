import control.procedure.ProcedureDispatcher;
import listener.ConsoleListener;
import model.file.FlatDiscoverStrategy;
import model.file.FlatMoveStrategy;
import model.file.PatternFilterStrategy;
import model.procedure.Procedure;
import model.procedure.types.MoveProcedure;

import java.nio.file.Path;


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
        final Procedure procedure = MoveProcedure.builder()
                .name("Test")
                .id("Test-1")
                .sourcePath(Path.of("C:/Users/Lasse/Desktop/source"))
                .targetPath(Path.of("C:/Users/Lasse/Desktop/target"))
                .discoverStrategy(new FlatDiscoverStrategy())
                .filterStrategy(new PatternFilterStrategy())
                .fileMoveStrategy(new FlatMoveStrategy())
                .build();

        final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
        dispatcher.register(new ConsoleListener());
        dispatcher.dispatch(procedure);
    }
}
