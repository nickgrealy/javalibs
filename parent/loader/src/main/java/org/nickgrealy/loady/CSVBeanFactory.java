/**
 * 
 */
package org.nickgrealy.loady;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.nickgrealy.commons.reflect.BeanUtil;
import org.nickgrealy.commons.util.NotNullableMap;
import org.nickgrealy.conversion.ConverterFactory;
import org.nickgrealy.conversion.PrimitiveConverters;
import org.nickgrealy.conversion.SmartBeanBuilder;

import au.com.bytecode.opencsv.CSVReader;

/**
 * 
 * @author nick.grealy
 */
public class CSVBeanFactory {

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
            aggregatedMap.put(clazz, loadCsv(csvFile, clazz));
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
            SmartBeanBuilder<T> builder = new SmartBeanBuilder<T>(clazz, columnHeaders);
            builder.setBeanUtil(new BeanUtil());
            List<String[]> readAll = csvReader.readAll();
            return builder.buildBeans(readAll);
            // Didn't work :(
            // ColumnPositionMappingStrategy<T> strat = new
            // ColumnPositionMappingStrategy<T>();
            // strat.setType(clazz);
            // strat.setColumnMapping(columnHeaders);
            // CsvToBean<T> csv = new CsvToBean<T>();
            // return csv.parse(strat, csvReader);
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
