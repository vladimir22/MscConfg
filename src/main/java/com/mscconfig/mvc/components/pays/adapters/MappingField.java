package com.mscconfig.mvc.components.pays.adapters;

/**
 * User: Vladimir
 * Date: 12.09.13
 * Time: 14:52
 * Please describe this stuff
 */
public class MappingField {
	String field;
	String mappingField;
	ConvertRule convertRule;

	public MappingField(String field, String mappinField, ConvertRule convertRule) {
		this.field = field;
		this.mappingField = mappinField;
		this.convertRule = convertRule;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMappingField() {
		return mappingField;
	}

	public void setMappingField(String mappingField) {
		this.mappingField = mappingField;
	}

	public ConvertRule getConvertRule() {
		return convertRule;
	}

	public void setConvertRule(ConvertRule convertRule) {
		this.convertRule = convertRule;
	}
}
