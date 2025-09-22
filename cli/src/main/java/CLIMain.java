import control.procedure.dispatcher.ProcedureDispatcher;
import generator.FileGenerator;
import listener.ConsoleListener;
import model.filter.PatternFilterStrategy;
import model.conflict.RenameConflictStrategy;
import model.delete.SoftDeleteStrategy;
import model.discover.RecursiveDiscoverStrategy;
import model.move.StructuralMoveStrategy;
import model.procedure.Procedure;
import model.procedure.DeleteProcedure;
import model.procedure.MoveProcedure;

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

        final Path targetDirectory = Path.of("C:/Users/Lasse/Desktop/target/");
        final Path sourceDirectory = Path.of("C:/Users/Lasse/Desktop/source/");
        final Procedure moveProcedure = MoveProcedure.builder()
                .name("Move Procedure Test")
                .id("MPT-1")
                .sourcePath(sourceDirectory)
                .targetDirectory(targetDirectory)
                .discoverStrategy(new RecursiveDiscoverStrategy())
                .filterStrategy(new PatternFilterStrategy())
                .fileMoveStrategy(StructuralMoveStrategy.builder()
                        .restoreAttributes(true)
                        .sourceDirectory(sourceDirectory)
                        .build())
                .fileConflictStrategy(new RenameConflictStrategy())
                .build();

        final Procedure deleteProcedure = DeleteProcedure.builder()
                .name("Delete Procedure Test")
                .id("DPT-1")
                .sourcePath(sourceDirectory)
                .discoverStrategy(new RecursiveDiscoverStrategy())
                .filterStrategy(new PatternFilterStrategy())
                .deleteStrategy(new SoftDeleteStrategy())
                .build();

        final ProcedureDispatcher dispatcher = new ProcedureDispatcher();
        dispatcher.register(new ConsoleListener());

        final FileGenerator generator = new FileGenerator();
        generator.run();
        dispatcher.dispatch(moveProcedure);

//        generator.run();
//        dispatcher.dispatch(deleteProcedure);
    }
}
