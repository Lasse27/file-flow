package commands;

import consts.CommandConsts;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

/**
 * A command-line tool designed to clean the specified directory path of empty directories.
 * This command scans the provided target directory and performs operations to identify and optionally display its contents.
 * <p>
 * The {@code CleanCommand} is executed by invoking it with specific options that define its behavior. By default,
 * the command targets the user's current working directory if no path is explicitly provided.
 * <p>
 * This class uses the Picocli command-line library for argument parsing and option handling. It implements the
 * {@link Runnable} interface to enable its execution directly as a command task.
 * <p>
 * Command Options:
 * - {@code -t}, {@code -target}: Specifies the directory to clean. If no value is provided, defaults to the current working directory.
 * <p>
 * Features:
 * - Scans the directory (target) and lists all contents.
 * - Handles exceptions related to file I/O operations within the directory.
 */
@Command(
        name = "clean",
        description = "Cleans the specified path of empty directories.",
        showDefaultValues = true,
        mixinStandardHelpOptions = true,
        version = CommandConsts.FILE_FLOW_1_0)
public class CleanCommand implements Runnable
{
    @Option(names = {"-t", "--target"},
            description = "The path to the target directory to be cleaned.",
            defaultValue = "${sys:user.dir}")
    private Path targetPath = Paths.get("");


    /**
     * {@inheritDoc}
     */
    @Override
    public void run()
    {
        final Path absoluteWorkDir = this.targetPath.toAbsolutePath();
        System.out.println("Scanning directory: " + absoluteWorkDir);

        // Dateien im Arbeitsverzeichnis auflisten
        try
        {
            Files.list(absoluteWorkDir).forEach(System.out::println);
        }
        catch (final IOException e)
        {
            System.err.println("Error reading directory: " + e.getMessage());
        }
    }


    public static void main(final String[] args)
    {
        final int exitCode = new CommandLine(new CleanCommand()).execute(args);
        System.exit(exitCode);
    }
}
