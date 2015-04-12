package au.com.unico.model;

import java.util.Date;

public class ParametersResponse {

	private int parameterValue;
	
	private Date parameterDate;

	public int getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(int parameterValue) {
		this.parameterValue = parameterValue;
	}

	public Date getParameterDate() {
		return parameterDate;
	}

	public void setParameterDate(Date parameterDate) {
		this.parameterDate = parameterDate;
	}
}
