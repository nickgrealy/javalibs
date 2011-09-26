package org.nickgrealy.conversion.reflect;

/**
 * @author nickgrealy@gmail.com
 */
public interface Processor<X> {

    void preProcess(X param);

    void postProcess(X param);
}
