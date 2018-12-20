package com.bo.spring.model.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameUsers implements Serializable {
	
	private static final long serialVersionUID = 1L;

    private Integer id;
    
    private String loginname;

    private String password;

    private Double credit;

}