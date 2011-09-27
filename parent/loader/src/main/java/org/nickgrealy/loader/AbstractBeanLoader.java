/**
 *
 */
package org.nickgrealy.loader;

import static org.nickgrealy.commons.validate.RuntimeAssert.check;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nickgrealy.commons.reflect.BeanMetaModel;
import org.nickgrealy.commons.reflect.SmartBeanBuilder;

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
		List<BeanMetaModel> metaModelsList = new LinkedList<BeanMetaModel>();
		// load data from files...
		File[] files = folder.listFiles(filter);
		for (File file : files) {
			metaModelsList.addAll(load(file));
		}
		// build beans from the meta models...
		return new SmartBeanBuilder().buildBeans(metaModelsList);
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
