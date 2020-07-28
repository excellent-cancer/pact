package perishing.constraint.io;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

/**
 * 寻找相同扩展名的文件，通过{@link ExtensionFileVisitor#ignored}设置是否忽略
 * 前缀为点的文件和文件夹
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
class ExtensionFileVisitor implements FileVisitor<Path> {

    private static final String IGNORED_CHARACTOR = ".";

    private final String extension;

    private final boolean ignored;

    @Getter
    private final List<Path> files = new LinkedList<>();

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        String fileName = filename(dir);

        return ignored(fileName) ? FileVisitResult.SKIP_SUBTREE : FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        String fileName = filename(file);
        if (!ignored(fileName) && isMatch(fileName)) {
            files.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return FileVisitResult.TERMINATE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
        return FileVisitResult.CONTINUE;
    }

    private static String filename(Path path) {
        return path.getFileName().toString();
    }

    private boolean ignored(String filename) {
        return ignored && filename.startsWith(IGNORED_CHARACTOR);
    }

    private boolean isMatch(String filename) {
        int index = filename.lastIndexOf(".");
        return index > -1 && filename.substring(index + 1).equals(extension);
    }
}
