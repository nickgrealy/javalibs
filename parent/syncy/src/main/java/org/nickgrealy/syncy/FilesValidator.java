/**
 * 
 */
package org.nickgrealy.syncy;

import java.io.File;

/**
 * 
 * @author nick.grealy
 */
public class FilesValidator {

    public void reportMissingFiles(File directory1, File directory2, boolean ignoreDifferentFilePaths) {
        // Rsync varumz verbose attributes recursive update missEmptyDirs
        // compress

        // Scan dir1 - build checksums
        // Scan dir2 - build checksums
        // Get intersection
        // Get outersection's
        // Report outersection's for dir1 (i.e. missing files from dir 2)
        // Report outersection's for dir2 (i.e. missing files from dir 1)
        // Report differing checksums for intersection (i.e. corrupt files)
    }

}
