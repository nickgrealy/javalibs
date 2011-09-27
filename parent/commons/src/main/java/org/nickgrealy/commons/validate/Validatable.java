package org.nickgrealy.commons.validate;

public interface Validatable {

	/**
	 * Checks whether this object is valid. Throws an {@link AssertionException} if it isn't.
	 */
	void validate();
}
