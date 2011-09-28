/**
 *
 */
package org.nickgrealy.commons.util;

/**
 * Not pretty but does the job.
 *
 * @author nickgrealy@gmail.com
 */
public class SimpleStringBuilder {

    private StringBuilder sb = new StringBuilder();

    public <X> SimpleStringBuilder prepend(X object) {
        StringBuilder tmp = sb;
        sb = new StringBuilder().append(object).append(tmp);
        return this;
    }

    public <X> SimpleStringBuilder append(X object) {
        sb.append(object);
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
