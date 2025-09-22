package model.file.move;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import model.conflict.FileMove;
import model.move.FileMoveStrategy;
import model.move.FlatMoveStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class FlatMoveStrategyTest
{

    private static void createOneLayerTestingEnvironment(final FileSystem fileSystem, final String source, final String target) throws IOException
    {
        final Path sourceDir = fileSystem.getPath(source);
        Files.createDirectories(sourceDir);
        assertTrue(Files.isDirectory(sourceDir), "Source directory not created.");

        final Path targetDir = fileSystem.getPath(target);
        Files.createDirectories(targetDir);
        assertTrue(Files.isDirectory(targetDir), "Target directory not created.");
    }


    private static boolean compareBasicFileAttributes(final BasicFileAttributes before, final BasicFileAttributes after)
    {
        if (before == after) return true;
        if (before == null || after == null) return false;

        final boolean creationTimeEqual = before.creationTime().equals(after.creationTime());
        final boolean lastAccessTimeEqual = before.lastAccessTime().equals(after.lastAccessTime());
        final boolean lastModifiedTimeEqual = before.lastModifiedTime().equals(after.lastModifiedTime());
        final boolean sizeEqual = before.size() == after.size();
        final boolean fileKeyEqual = before.fileKey().equals(after.fileKey());
        final boolean isRegularFileEqual = before.isRegularFile() == after.isRegularFile();
        final boolean isDirectoryEqual = before.isDirectory() == after.isDirectory();
        final boolean isSymbolicLinkEqual = before.isSymbolicLink() == after.isSymbolicLink();
        return creationTimeEqual && lastAccessTimeEqual && lastModifiedTimeEqual && sizeEqual && fileKeyEqual && isRegularFileEqual && isDirectoryEqual && isSymbolicLinkEqual;
    }


    private static boolean comparePosixFileAttributes(final PosixFileAttributes before, final PosixFileAttributes after)
    {
        if (before == after) return true;
        if (before == null || after == null) return false;

        final boolean groupEqual = before.group().equals(after.group());
        final boolean ownerEqual = before.owner().equals(after.owner());
        final boolean permissionsEqual = before.permissions().equals(after.permissions());
        return groupEqual && ownerEqual && permissionsEqual;
    }


    @Nested
    @DisplayName("Unix Tests")
    final class UnixTests
    {
        @Test
        @Order(1)
        @DisplayName("Move: Target file created and at expected location - Unix")
        void move_checkTargetFileCreated_unix() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var2/");

                // 4. create the source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertTrue(Files.exists(result.targetFile()), "Target file not created.");
            }
        }


        @Test
        @Order(2)
        @DisplayName("Move: Check Source File Deleted - Unix")
        void move_checkSourceFileDeleted_unix() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var2/");

                // 4. create the source file
                Files.writeString(source, source.toAbsolutePath().toString(), StandardOpenOption.CREATE);

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertTrue(Files.notExists(result.sourceFile()), "Source file not deleted.");
            }
        }


        @Test
        @Order(3)
        @DisplayName("Move: Check Resolved On Success - Unix")
        void move_checkResolvedOnSuccess_unix() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var2/");

                // 4. create the source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertTrue(result.isResolved(), "Result not as expected.");
            }
        }


        @Test
        @Order(4)
        @DisplayName("Move: Return unresolved on source path not exists - Unix")
        void move_unresolvedOnSourcePathNotExists_unix() throws IOException
        {
            // create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var2/");

                // 4. create source file
                // Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertFalse(result.isResolved(), "Result not as expected.");
            }
        }


        @Test
        @Order(5)
        @DisplayName("Move: Return unresolved on target directory not exists - Unix")
        void move_unresolvedOnTargetDirNotExists_unix() throws IOException
        {
            // create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var3/");

                // 4. create source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertFalse(result.isResolved(), "Result not as expected.");
            }
        }


        @Test
        @Order(6)
        @DisplayName("Move: Check if attributes restored if wanted - Unix")
        void move_checkAttributesRestored_unix() throws IOException
        {
            // create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var2/");

                // 4. create source file
                Files.writeString(source, source.toString());
                final BasicFileAttributes beforeBasic = Files.readAttributes(source, BasicFileAttributes.class);

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy(true);
                final FileMove result = strategy.move(source, target);
                final BasicFileAttributes afterBasic = Files.readAttributes(result.targetFile(), BasicFileAttributes.class);

                // Assert
                assertTrue(compareBasicFileAttributes(beforeBasic, afterBasic), "Basic file attributes not equal.");

                if (fileSystem.supportedFileAttributeViews().contains("posix"))
                {
                    final PosixFileAttributes afterPosix = Files.readAttributes(result.targetFile(), PosixFileAttributes.class);
                    final PosixFileAttributes beforePosix = Files.readAttributes(source, PosixFileAttributes.class);
                    assertTrue(comparePosixFileAttributes(beforePosix, afterPosix), "Posix file attributes not equal.");
                }
            }
        }


        @Test
        @Order(7)
        @DisplayName("Move: Check move to the same file - should return isResolved - Unix")
        void move_checkMoveToSameFileIsResolved_unix() throws IOException
        {
            // create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.unix()))
            {
                createOneLayerTestingEnvironment(fileSystem, "/var1", "/var2");

                // 3. define paths
                final Path source = fileSystem.getPath("/var1/source.txt");
                final Path target = fileSystem.getPath("/var2/");

                // 4. create the source file
                Files.writeString(source, source.toString());
                final BasicFileAttributes beforeBasic = Files.readAttributes(source, BasicFileAttributes.class);

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy(true);
                final FileMove result = strategy.move(source, target);

                // Assert
                assertTrue(result.isResolved(), "Result not as expected.");
            }
        }
    }

    @Nested
    @DisplayName("Windows Tests")
    final class WindowsTests
    {
        @Test
        @Order(1)
        @DisplayName("Move: Target file created - Windows")
        void move_checkTargetFileCreated_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var2\\");

                // 4. create the source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertTrue(Files.exists(target), "Target file not created.");
            }
        }


        @Test
        @Order(2)
        @DisplayName("Move: Check Source File Deleted - Windows")
        void move_checkSourceFileDeleted_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var2\\");

                // 4. create source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertTrue(Files.notExists(source), "Source file not deleted.");
            }
        }


        @Test
        @Order(3)
        @DisplayName("Move: Check Resolved On Success - Windows")
        void move_checkResolvedOnSuccess_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var2\\");

                // 4. create the source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertTrue(result.isResolved(), "Result not as expected.");
            }
        }


        @Test
        @Order(4)
        @DisplayName("Move: Return unresolved on source path not exists - Windows")
        void move_unresolvedOnSourcePathNotExists_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var2\\");

                // 4. create source file
                // Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertFalse(result.isResolved(), "Result not as expected.");
            }
        }


        @Test
        @Order(5)
        @DisplayName("Move: Return unresolved on target directory not exists - Windows")
        void move_unresolvedOnTargetDirNotExists_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var3\\");

                // 4. create source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy();
                final FileMove result = strategy.move(source, target);

                assertFalse(result.isResolved(), "Result not as expected.");
            }
        }


        @Test
        @Order(6)
        @DisplayName("Move: Check if attributes restored if wanted - Windows")
        void move_checkAttributesRestored_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var2\\");

                // 4. create source file
                Files.writeString(source, source.toString());
                final BasicFileAttributes beforeBasic = Files.readAttributes(source, BasicFileAttributes.class);

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy(true);
                final FileMove result = strategy.move(source, target);
                final BasicFileAttributes afterBasic = Files.readAttributes(result.targetFile(), BasicFileAttributes.class);

                // Assert
                assertTrue(compareBasicFileAttributes(beforeBasic, afterBasic), "Basic file attributes not equal.");

                if (fileSystem.supportedFileAttributeViews().contains("posix"))
                {
                    final PosixFileAttributes afterPosix = Files.readAttributes(result.targetFile(), PosixFileAttributes.class);
                    final PosixFileAttributes beforePosix = Files.readAttributes(source, PosixFileAttributes.class);
                    assertTrue(comparePosixFileAttributes(beforePosix, afterPosix), "Posix file attributes not equal.");
                }
            }
        }


        @Test
        @Order(7)
        @DisplayName("Move: Check move to the same file - should return isResolved - Windows")
        void move_checkMoveToSameFileIsResolved_win() throws IOException
        {
            // 1. create the file system
            try (final FileSystem fileSystem = Jimfs.newFileSystem(Configuration.windows()))
            {
                // 2. source dir
                createOneLayerTestingEnvironment(fileSystem, "C:\\var1", "C:\\var2");

                // 3. define paths
                final Path source = fileSystem.getPath("C:\\var1\\source.txt");
                final Path target = fileSystem.getPath("C:\\var2\\");

                // 4. create source file
                Files.writeString(source, source.toString());

                // 5. Move file
                final FileMoveStrategy strategy = new FlatMoveStrategy(true);
                final FileMove result = strategy.move(source, target);

                // Assert
                assertTrue(result.isResolved(), "Result not as expected.");
            }
        }
    }
}