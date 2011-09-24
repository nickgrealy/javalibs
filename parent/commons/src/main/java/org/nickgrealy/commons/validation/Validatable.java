package org.nickgrealy.commons.validation;

public interface Validatable {

	/**
	 * Checks whether this object is valid. Throws an {@link AssertionException} if it isn't.
	 */
	void validate();
}
