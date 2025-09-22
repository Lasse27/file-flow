package commands;

import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Scanner;

import static consts.CommandConsts.FILE_FLOW_1_0;
import static consts.CommandConsts.SEPARATOR;
import static picocli.CommandLine.Command;

/**
 * Represents a command-line wizard designed to guide users
 * through the process of cleaning empty directories in a specified path.
 * This class is part of a suite of tools based on the Picocli library,
 * providing a user-friendly interface for directory management tasks.
 * It includes default configurations to display command help options,
 * show default values, and includes version information for the tool.
 * The {@code CleanWizard} utilizes Picocli's {@code @Command} annotation
 * to define its behavior and structure as a CLI command.
 * Command Options:
 * - Provides a wizard-like interface for performing clean operations.
 * - Includes standard help functionality to guide the user.
 */
@Command(name = "clean-wizard",
        description = "Wizard: Cleans the specified path of empty directories interactively.",
        showDefaultValues = true,
        mixinStandardHelpOptions = true,
        version = FILE_FLOW_1_0)
public class CleanWizard implements Runnable
{
    private final Scanner scanner = new Scanner(System.in);


    @Override
    public void run()
    {
        System.out.println("Wizard: Clean Procedure");
        System.out.println(SEPARATOR);

        // Zielpfad
        final Path sourcePath = this.promptForSourcePath();

        if (this.confirmSetup(sourcePath))
        {
            System.out.println("Setup finished. Executing clean operation...");
        }
        else
        {
            System.out.println("Setup cancelled.");
        }
    }


    private Path promptForSourcePath()
    {
        System.out.printf("Target Path [%s]: ", System.getProperty("user.dir"));
        final String input = this.scanner.nextLine().trim();
        return input.isEmpty() ? Path.of(System.getProperty("user.dir")) : Path.of(input);
    }


    private boolean confirmSetup(final Path sourcePath)
    {
        System.out.println("\nWizard: Clean Procedure - Summary:");
        System.out.println(SEPARATOR);
        System.out.println("Target Path: " + sourcePath.toAbsolutePath());

        System.out.print("\nProceed with setup? [Y/n]: ");
        final String response = this.scanner.nextLine().trim().toLowerCase(Locale.ROOT);
        return response.isEmpty() || response.equals("y") || response.equals("yes");
    }


    public static void main(String[] args)
    {
        final int exitCode = new CommandLine(new CleanWizard()).execute(args);
        System.exit(exitCode);
    }
}
