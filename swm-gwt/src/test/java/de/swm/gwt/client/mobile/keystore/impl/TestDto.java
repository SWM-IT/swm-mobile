package de.swm.gwt.client.mobile.keystore.impl;

import org.junit.Ignore;

import java.io.Serializable;

/**
 * Test DTO.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
@Ignore
public class TestDto implements Serializable {

	private long oid;
	private String value;

	public TestDto() {

	}

	public TestDto(long oid, String value) {
		this.oid = oid;
		this.value = value;
	}


	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TestDto)) {
			return false;
		}

		TestDto testDto = (TestDto) o;

		if (oid != testDto.oid) {
			return false;
		}
		if (value != null ? !value.equals(testDto.value) : testDto.value != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (oid ^ (oid >>> 32));
		result = 31 * result + (value != null ? value.hashCode() : 0);
		return result;
	}
}
