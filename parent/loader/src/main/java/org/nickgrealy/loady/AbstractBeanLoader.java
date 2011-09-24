/**
 *
 */
package org.nickgrealy.loady;

import static org.nickgrealy.commons.validation.RuntimeAssert.check;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.nickgrealy.commons.util.MapUtil;
import org.nickgrealy.commons.util.NotNullableMap;
import org.nickgrealy.conversion.reflect.BeanMetaModel;
import org.nickgrealy.conversion.reflect.SmartBeanBuilder;

/**
 * @author nickgrealy@gmail.com
 */
public abstract class AbstractBeanLoader {

	private static final int NOT_INSTANTIABLE_MODIFIER = Modifier.ABSTRACT
			| Modifier.INTERFACE;
	// if (class.getModfiiers & NOTINSTA... == 0) i.e. they dont' have the
	// abstract and interface modifiers...

	private static final MapUtil mapUtil = new MapUtil();

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
		Map<Class<?>, BeanMetaModel> metaModelsMap = mapUtil.mapByField(
				metaModelsList, "clazz",
				new NotNullableMap<Class<?>, BeanMetaModel>());
		SmartBeanBuilder<Object> builder = new SmartBeanBuilder<Object>();
		return builder.buildBeans(metaModelsMap);
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
