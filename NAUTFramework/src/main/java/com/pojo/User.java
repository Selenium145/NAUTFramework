package com.pojo;

import lombok.Data;

@Data
public class User {
	
	public String name;
    public String job;
    
    @Override
	public String toString() {
		return "User [name=" + name + ", job=" + job + "]";
	}
}
