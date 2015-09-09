package com.md.idgenerator.server.common.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author zhiwei.wen
 * 2015年7月15日
 */
public class BaseObject implements Serializable {

	private static final long serialVersionUID = 2744190391613295958L;

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	/**
	 * <p>hashCode.</p>
	 *
	 * @return a int.
	 */
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
