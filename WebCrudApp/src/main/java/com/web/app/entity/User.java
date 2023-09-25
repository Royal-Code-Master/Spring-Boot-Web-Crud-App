package com.web.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="user")

public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", length=5, nullable = false)
	private long id;
	
	
	@Column(name="fname", length=50, nullable = false)
	private String fname;
	
	@Column(name="lname", length=50, nullable = false)
	private String lname;
	
	@Column(name="email", length=50, nullable = false)
	private String email;
	
	@Column(name="pwd", nullable = false)
	private String pwd;
	
	@Column(name="age", length=3, nullable = false)
	private int age;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public User(long id, String fname, String lname, String email, String pwd, int age) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pwd = pwd;
		this.age = age;
	}

	public User() {
		super();
		
	}

	
	
}
