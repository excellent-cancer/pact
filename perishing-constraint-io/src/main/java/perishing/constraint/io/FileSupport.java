package perishing.constraint.io;

import perishing.constraint.note.Unqualified;
import perishing.constraint.treasure.chest.CollectionsTreasureChest;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * File Utilities.
 *
 * @author XyParaCrim
 */
@Unqualified
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
        return acquireFileLock(file, 1, CollectionsTreasureChest.asSet(WRITE, CREATE));
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
            // ExceptionSupport.collectIgnored(collected);
        }
        return fileLock;
    }

    public static void mkdir(File file) throws IOException {
        mkdir(file, true);
    }

    public static void mkdir(File parent, File file) throws IOException {
        mkdir(new File(parent, file.toString()), true);
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

    public static void atomicMove(File source, File parent, File target) throws IOException {
        Files.move(source.toPath(), parent.toPath().resolve(target.toPath()), StandardCopyOption.ATOMIC_MOVE);
    }

    public static File createTempFile(String suffix) throws IOException {
        return File.createTempFile("temp", suffix);
    }

    public static void deleteFile(File location) throws IOException {
        deleteFile(location, false);
    }

    public static void deleteFile(File location, boolean completed) throws IOException {
        if (location.isDirectory()) {
            File[] children = location.listFiles();
            if (children != null) {
                if (completed) {
                    for (File child : children) {
                        deleteFile(child, true);
                    }
                    Files.delete(location.toPath());
                }
                return;
            }
        }

        Files.delete(location.toPath());
    }

    public static File resolve(File parent, File child) {
        return new File(parent, child.toString());
    }

    /**
     * 遍历文件夹的文件树，寻找所有相同扩展名的文件。
     *
     * @param location 开始遍历的文件夹位置
     * @param extension 扩展名
     * @param ignored 是否忽略前缀是点的文件和文件夹
     * @return 所有相同扩展名的文件
     * @throws IOException 遍历文件夹不正确或者无法遍历
     */
    public static List<Path> collectFilesFromFileTree(Path location, String extension, boolean ignored) throws IOException {
        ExtensionFileVisitor visitor = new ExtensionFileVisitor(extension, ignored);

        Files.walkFileTree(location, visitor);

        return visitor.getFiles();
    }
}
