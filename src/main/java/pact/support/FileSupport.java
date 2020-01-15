package pact.support;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * File Utilities.
 *
 * @author XyParaCrim
 */
public final class FileSupport {

    /**
     * Create a child file instance from a parent abstract path-name and a child path-name string.
     *
     * @param parent The parent abstract path-name
     * @param child  The child path-name string
     * @return The child abstract path-name
     */
    public static File childFile(File parent, String child) {
        return new File(parent, child);
    }

    /**
     * Attempts to acquire an exclusive lock in process level. Create a new file if it does not exist.
     * Also try only once to lock the specified file.
     *
     * @param file The abstract path-name to lock
     * @return A lock object or null if the lock could not be acquired
     */
    public static FileLock acquireFileLockWithCreate(File file) {
        return acquireFileLock(file, 1, CollectionsSupport.asSet(WRITE, CREATE));
    }

    /**
     * Attempts to acquire an exclusive lock in process level. Custom retries and open file strategy.
     *
     * @param file         The abstract path-name to lock
     * @param tryLockCount Retry count if the lock could not be acquired
     * @param options      Options specifying how the file is opened
     * @return A lock object or null if the lock could not be acquired
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public static FileLock acquireFileLock(File file, int tryLockCount, Set<OpenOption> options) {
        FileLock fileLock = null;
        try {
            FileChannel fileChannel = FileChannel.
                    open(file.toPath(), options);

            while ((fileLock = fileChannel.tryLock()) == null &&
                    --tryLockCount > 0) {
            }
        } catch (IOException collected) {
            ExceptionSupport.collectIgnored(collected);
        }
        return fileLock;
    }

    public static void mkdir(File file) throws IOException {
        mkdir(file, true);
    }

    public static void mkdir(File file, boolean ignoreExisted) throws IOException {
        if (!file.mkdir()) {
            if (!file.isDirectory() || !ignoreExisted) {
                throw new IOException("Failed to create directory: " + file.toString());
            }
        }
    }

    public static void atomicMove(File source, File target) throws IOException {
        Files.move(source.toPath(), target.toPath(), StandardCopyOption.ATOMIC_MOVE);
    }
}
