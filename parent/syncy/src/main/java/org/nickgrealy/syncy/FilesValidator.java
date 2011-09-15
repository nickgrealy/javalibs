/**
 * 
 */
package org.nickgrealy.syncy;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.io.File;
import java.util.zip.Checksum;

/**
 * 
 * @author nick.grealy
 */
public class FilesValidator {

    public void reportMissingFiles(File directory1, File directory2, boolean ignoreDifferentFilePaths,
            String ignoreRegex) {

        check("directory1", directory1).isNotNull();
        check("directory2", directory2).isNotNull();
        // Rsync varumz verbose attributes recursive update missEmptyDirs
        // compress

        // Scan dir1 - build checksums
        // Scan dir2 - build checksums
        // Get intersection
        // Get outersection's
        // Report outersection's for dir1 (i.e. missing files from dir 2)
        // Report outersection's for dir2 (i.e. missing files from dir 1)
        // Report differing checksums, filesizes, for intersection (i.e. corrupt
        // files)
    }

    class FileReport {
        private File file;
        private Checksum checksum;
        private long size;
    }

}
