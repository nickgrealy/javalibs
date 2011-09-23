/**
 * 
 */
package org.nickgrealy.loady;

import java.io.File;
import java.io.FileFilter;

/**
 * 
 * @author nickgrealy@gmail.com
 */
public class CSVFileFilter implements FileFilter {

    private static final String CSV_EXTENSION = ".csv";

    /** {@inheritDoc} */
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().toLowerCase().endsWith(CSV_EXTENSION);
    }

}
