package com.zion.morphia.entity.embed;

import java.util.ArrayList;
import java.util.Date;

public class PersonEntity {

	private String title;

	private String introduce;

	private String preferName;

	private String firstName;

	private String middleName;

	private String lastName;

	private String gender;
	
	private String avatar;
	
	private Date dob;

	private ArrayList<String> experience;

	private ArrayList<String> certification;

	private ArrayList<String> award;

	private ArrayList<String> partnership;

	private ArrayList<String> client;

	private String story;

	public PersonEntity() {

	}

	public PersonEntity(String title, String introduce, String preferName,String firstName, String middleName, String lastName, String gender, String avatar, Date dob, ArrayList<String> experience, ArrayList<String> certification, ArrayList<String> award, ArrayList<String> partnership, ArrayList<String> client, String story) {
		this.title = title;
		this.introduce = introduce;
		this.preferName = preferName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.avatar = avatar;
		this.dob = dob;
		this.experience = experience;
		this.certification = certification;
		this.award = award;
		this.partnership = partnership;
		this.client = client;
		this.story = story;
	}

	public ArrayList<String> getExperience() {
		return experience;
	}

	public void setExperience(ArrayList<String> experience) {
		this.experience = experience;
	}

	public ArrayList<String> getCertification() {
		return certification;
	}

	public void setCertification(ArrayList<String> certification) {
		this.certification = certification;
	}

	public ArrayList<String> getAward() {
		return award;
	}

	public void setAward(ArrayList<String> award) {
		this.award = award;
	}

	public ArrayList<String> getPartnership() {
		return partnership;
	}

	public void setPartnership(ArrayList<String> partnership) {
		this.partnership = partnership;
	}

	public ArrayList<String> getClient() {
		return client;
	}

	public void setClient(ArrayList<String> client) {
		this.client = client;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getPreferName() {
		return preferName;
	}

	public void setPreferName(String preferName) {
		this.preferName = preferName;
	}
}
