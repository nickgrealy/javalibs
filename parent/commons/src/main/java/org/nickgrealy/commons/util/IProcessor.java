package org.nickgrealy.commons.util;

/**
 * @author nickgrealy@gmail.com
 */
public interface IProcessor<X> {

    void preProcess(X param);

    void postProcess(X param);
}
