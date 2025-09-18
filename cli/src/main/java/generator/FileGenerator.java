package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class FileGenerator
{

    private static final int NUM_FILES = 500;       // Anzahl Dateien

    private static final int MAX_DEPTH = 5;        // maximale Tiefe von Unterordnern

    private static final int MAX_SUBFOLDERS = 2;   // max. Unterordner pro Verzeichnis

    private final Random random = new Random();

    private static final String BASE_DIR = "C:/Users/Lasse/Desktop/source/";

    private int filesCreated = 0;


    public void run()
    {
        try
        {
            final File baseDir = new File(BASE_DIR);
            if (!baseDir.exists() && !baseDir.mkdir())
            {
                throw new IOException("Konnte Basisverzeichnis nicht erstellen.");
            }

            // Dateien und Unterordner rekursiv erzeugen
            this.createFilesAndFolders(baseDir, 0);

            System.out.println(this.filesCreated + " Dateien erfolgreich erstellt in '" + BASE_DIR + "'.");
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }


    private void createFilesAndFolders(final File dir, final int depth) throws IOException
    {
        // Erstelle zufällig Dateien im aktuellen Verzeichnis
        final int filesHere = this.random.nextInt(50) + 1; // 1–3 Dateien pro Ordner
        for (int i = 0; i < filesHere && this.filesCreated < NUM_FILES; i++)
        {
            this.createRandomFile(dir);
        }

        // Falls maximale Tiefe noch nicht erreicht -> Unterordner erstellen
        if (depth < MAX_DEPTH)
        {
            final int numSubFolders = this.random.nextInt(MAX_SUBFOLDERS) + 1; // 1–MAX_SUBFOLDERS
            for (int i = 0; i < numSubFolders; i++)
            {
                final File subDir = new File(dir, "subfolder_" + (depth + 1) + "_" + (i + 1));
                if (!subDir.exists() && !subDir.mkdir())
                {
                    throw new IOException("Konnte Unterordner nicht erstellen: " + subDir.getAbsolutePath());
                }
                // Rekursiv tiefer gehen
                this.createFilesAndFolders(subDir, depth + 1);
            }
        }
    }


    private void createRandomFile(final File dir) throws IOException
    {
        final String fileName = "file_" + UUID.randomUUID() + ".txt";
        final File file = new File(dir, fileName);
        try (final FileWriter writer = new FileWriter(file))
        {
            this.filesCreated++;
            writer.write("Dies ist Datei #" + this.filesCreated + " im Ordner " + dir.getAbsolutePath());
        }
    }
}
