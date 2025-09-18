package model.file.delete;

import model.listener.Listener;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 * Implementation of the {@link FileDeleteStrategy} interface that provides a soft delete mechanism
 * for files or directories. This strategy moves the specified target to a designated location
 * (e.g., trash or recycle bin) instead of permanently deleting it. Soft deletion allows for the
 * possibility of recovering deleted files or directories.
 */
public class SoftDeleteStrategy implements FileDeleteStrategy
{
    /**
     * {@inheritDoc}
     */
    @Override
    public FileDeletion delete(final Path targetPath, final Listener listener)
    {
        try
        {
            final String operatingSystemName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            if (operatingSystemName.contains("win"))
            {
                moveToTrashWindows(targetPath);
                return FileDeletion.RESOLVED(targetPath);
            }
            moveToTrashLinux(targetPath);
            return FileDeletion.RESOLVED(targetPath);
        }
        catch (final Exception exception)
        {
            return FileDeletion.UNRESOLVED(targetPath);
        }
    }


    private static void moveToTrashWindows(final Path path) throws IOException
    {
        if (Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().moveToTrash(path.toFile());
        }
        else
        {
            throw new IOException("Desktop API not supported for moving to trash on Windows.");
        }
    }


    private static void moveToTrashLinux(final Path path) throws IOException, InterruptedException
    {
        // Versuche zuerst 'gio trash'
        final ProcessBuilder builder = new ProcessBuilder("gio", "trash", path.toAbsolutePath().toString());
        final Process process = builder.start();
        final int exitCode = process.waitFor();
        if (exitCode != 0)
        {
            // Fallback: verschiebe manuell in XDG Trash
            final Path home = Paths.get(System.getProperty("user.home"));
            final Path trashDir = home.resolve(".local/share/Trash/files");
            final Path infoDir = home.resolve(".local/share/Trash/info");

            Files.createDirectories(trashDir);
            Files.createDirectories(infoDir);

            final Path target = trashDir.resolve(path.getFileName());
            Files.move(path, target, StandardCopyOption.REPLACE_EXISTING);

            // Info-Datei schreiben
            final String infoContent = "[Trash Info]\n" +
                    "Path=" + path.toAbsolutePath() + "\n" +
                    "DeletionDate=" + LocalDateTime.now() + "\n";

            final Path infoFile = infoDir.resolve(path.getFileName().toString() + ".trashinfo");
            Files.writeString(infoFile, infoContent);
        }
    }
}
