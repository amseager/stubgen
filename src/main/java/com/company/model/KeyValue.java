package com.company.model;


import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class KeyValue implements Serializable {
	static final long serialVersionUID = 1L;

	private String mKey;
	private String mValue;

	public KeyValue() {
		//empty
	}

	public KeyValue(String pKey, String pValue) {
		mKey = pKey;
		mValue = pValue;
	}

	public String getKey() {
		return mKey;
	}

	public void setKey(String pKey) {
		mKey = pKey;
	}

	public String getValue() {
		return mValue;
	}

	public void setValue(String pValue) {
		mValue = pValue;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("mKey", mKey)
				.append("mValue", mValue)
				.toString();
	}


	public String toJsonString() {
		return "{\"key\":\"" + mKey + "\", \"value\":\"" + mValue + "\"}";
	}
}
