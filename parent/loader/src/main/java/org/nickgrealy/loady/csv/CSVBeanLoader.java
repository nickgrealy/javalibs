/**
 *
 */
package org.nickgrealy.loady.csv;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.nickgrealy.conversion.reflect.BeanMetaModel;
import org.nickgrealy.conversion.reflect.BeanMetaModelUtil;
import org.nickgrealy.loady.AbstractBeanLoader;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author nickgrealy@gmail.com
 */
public class CSVBeanLoader extends AbstractBeanLoader {
	
	private static final FileFilter FILE_FILTER = new CSVFileFilter();

	@Override
	public FileFilter getFileFilter() {
		return FILE_FILTER;
	}

	/** {@inheritDocs} */
	public List<BeanMetaModel> load(File csvFile) {
		check("csvFile", csvFile).isNotNull();
		check("csvFile is readable",
				csvFile.exists() && csvFile.isFile() && csvFile.canRead())
				.isTrue();
		// setup reader
		FileReader fileReader = null;
		String[] tmp;
		String name = csvFile.getName();
		try {
			Class<?> clazz = Class
					.forName(name.substring(0, name.length() - 4));
			// get column headers...
			fileReader = new FileReader(csvFile);
			CSVReader csvReader = new CSVReader(fileReader, ',', '\"', 0);
			tmp = csvReader.readNext();
			check("columnHeaders", tmp).isNotNull();
			List<String> columnHeaders = Arrays.asList(tmp);
			// read in data...
			List<List<? extends Object>> readAll = new LinkedList<List<? extends Object>>();
			while ((tmp = csvReader.readNext()) != null) {
				readAll.add(Arrays.asList(tmp));
			}
			return Arrays.asList(BeanMetaModelUtil.build(clazz, columnHeaders,
					readAll));
		} catch (FileNotFoundException e) {
			throw new CSVException(e);
		} catch (IOException e) {
			throw new CSVException(e);
		} catch (ClassNotFoundException e) {
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
