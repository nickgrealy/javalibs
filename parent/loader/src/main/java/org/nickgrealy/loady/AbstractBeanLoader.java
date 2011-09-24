/**
 *
 */
package org.nickgrealy.loady;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nickgrealy.conversion.reflect.BeanMetaModel;
import org.nickgrealy.conversion.reflect.SmartBeanBuilder;

/**
 * @author nickgrealy@gmail.com
 */
public abstract class AbstractBeanLoader {

	public Map<Class<?>, List<?>> loadFolder(File folder) {
		return loadFolder(folder, getFileFilter());
	}

	/**
	 * Call {@link #load(File)} for each file in the given folder.
	 * 
	 * @param folder
	 * @param filter
	 * @return
	 */
	public Map<Class<?>, List<?>> loadFolder(File folder, FileFilter filter) {
		check("folder", folder).isNotNull();
		check("folder exists", folder.exists()).isTrue();
		check("folder is a directory and readable",
				folder.isDirectory() && folder.canRead()).isTrue();
		// load data from files...
		List<BeanMetaModel> metaModelsList = new LinkedList<BeanMetaModel>();
		File[] files = folder.listFiles(filter);
		for (File file : files) {
			metaModelsList.addAll(load(file));
		}
		// build beans from the meta models...
		SmartBeanBuilder builder = new SmartBeanBuilder();
		return builder.buildBeans(metaModelsList);
	}

	/**
	 * Build BeanMetaModel(s) from the given file.
	 * 
	 * @param file
	 * @return
	 */
	public abstract List<BeanMetaModel> load(File file);

	public abstract FileFilter getFileFilter();
}
