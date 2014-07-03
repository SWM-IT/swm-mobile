package de.swm.mobile.kitchensink.client.showcase.forms.utils;

import java.util.Date;

public class Person {

	private String name;

	private String email;

	private Date birthdate;

	private String job;

	private String option;

	private EnumRenderer.ExampleEnum genOption;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", email=" + email + ", birthdate=" + birthdate + ", job=" + job
				+ ", option=" + option + ", genOption=" + genOption + "]";
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getOption() {
		return option;
	}

	public EnumRenderer.ExampleEnum getGenOption() {
		return genOption;
	}

	public void setGenOption(EnumRenderer.ExampleEnum genOption) {
		this.genOption = genOption;
	}

}
