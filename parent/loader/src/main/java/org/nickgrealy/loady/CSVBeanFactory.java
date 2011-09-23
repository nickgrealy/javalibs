/**
 *
 */
package org.nickgrealy.loady;

import au.com.bytecode.opencsv.CSVReader;
import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.commons.util.NotNullableMap;
import org.nickgrealy.conversion.SmartBeanBuilder;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

/**
 * @author nickgrealy@gmail.com
 */
public class CSVBeanFactory {

    private static final int NOT_INSTANTIABLE_MODIFIER = Modifier.ABSTRACT | Modifier.INTERFACE;

    public Map<Class<?>, List<?>> loadCsvFolder(File csvFolder) {
        return loadFolder(csvFolder, new CSVFileFilter());
    }

    private Map<Class<?>, List<?>> loadFolder(File csvFolder, FileFilter filter) {
        check("csvFolder", csvFolder).isNotNull();
        check("csvFolder exists", csvFolder.exists()).isTrue();
        check("csvFolder is directory and readable", csvFolder.isDirectory() && csvFolder.canRead()).isTrue();
        // setup
        NotNullableMap<Class<?>, List<?>> aggregatedMap = new NotNullableMap<Class<?>, List<?>>();
        File[] files = csvFolder.listFiles(filter);
        for (File file : files) {
            loadCsv(file, aggregatedMap);
        }
        return aggregatedMap;
    }

    private void loadCsv(File csvFile, Map<Class<?>, List<?>> aggregatedMap) {
        check("csvFile", csvFile).isNotNull();
        check("csvFile is readable", csvFile.exists() && csvFile.isFile() && csvFile.canRead()).isTrue();
        String name = csvFile.getName();
        try {
            Class<?> clazz = Class.forName(name.substring(0, name.length() - 4));
//            Modifier.isAbstract(clazz.getModifiers())
            if ((clazz.getModifiers() & NOT_INSTANTIABLE_MODIFIER) == 0) {
                aggregatedMap.put(clazz, loadCsv(csvFile, clazz));
            }
        } catch (ClassNotFoundException e) {
            throw new CSVException(e);
        }
    }

    private <T> List<T> loadCsv(File csvFile, Class<T> clazz) {
        // setup reader
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(csvFile);
            CSVReader csvReader = new CSVReader(fileReader, ',', '\"', 0);
            String[] columnHeaders = csvReader.readNext();
            check("columnHeaders", columnHeaders).isNotNull();

            // setup strategy
            List<String[]> readAll = csvReader.readAll();
            SmartBeanBuilder<T> builder = new SmartBeanBuilder<T>(clazz, columnHeaders);
            return builder.buildBeans(readAll);
        } catch (FileNotFoundException e) {
            throw new CSVException(e);
        } catch (IOException e) {
            throw new CSVException(e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    throw new CSVException(e);
                }
            }
        }
    }
}
