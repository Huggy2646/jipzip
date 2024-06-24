package com.jipzip.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ModifyPw {

	String id;
	String pw;
//	String pkey;
	String modifypw;

	public ModifyPw(String id, String pw, String modifypw) {
		super();
		this.id = id;
		this.pw = pw;
		this.modifypw = modifypw;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getModifypw() {
		return modifypw;
	}

	public void setModifypw(String modifypw) {
		this.modifypw = modifypw;
	}

	@Override
	public String toString() {
		return "ModifyPw [id=" + id + ", pw=" + pw + ", modifypw=" + modifypw + "]";
	}

}
