package com.example.security.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Entity
@Data
@Table(name = "tb_pay_record")
@DynamicUpdate
public class PayRecord {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "uid")
	private String uid;

	@Column(name = "pname")
	private String pname;
	
	@Column(name = "pamount")
	private long pamount;

	@Column(name = "reg_date")
	private Date reg_date;
	
	@Builder
	public PayRecord(int id, String uid, String pname, long pamount, Date reg_date) {
		this.id = id;
		this.uid = uid;
		this.pname = pname;
		this.pamount = pamount;
		this.reg_date = reg_date;
	}
}

//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@Getter
//@Entity
//@Data
//@Table(name = "tb_charge")
//@DynamicUpdate
//public class Charge {
//	
//	@Id
//	@Column(name = "id")
//	private int id;
//	
//	@Column(name = "name")
//	private String name;
//	
//	@Column(name = "amount")
//	private int amount;
//
//	@Builder
//	public Charge(int id, String name, int amount) {
//		this.id = id;
//		this.name = name;
//		this.amount = amount;
//	}
//}
