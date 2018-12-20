package com.bo.spring.model.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class AgentCustomer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String loginname;

	private String password;

	private String accountname;

	private Double credit;

	private String email;

	private String phone;

	private String qq;

	private String md5str;

	private Integer phonestatus;

	private Integer userstatus;

	private Integer emailstatus;

	private Date createtime;

	private Integer logintimes;

	private Date lastlogintime;

	private String agentcode;

	private Integer clicknum;

	private String registerip;

	private String lastloginip;

	private String area;

	private String lastarea;

	private String referwebsite;

	private String role;

	private String remark;

	private String agentwebsit;

	private String agenturl;

	private String agentmess;

	private String logintimeTmp;

	 
}