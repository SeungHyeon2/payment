package com.example.security.persistence.model;

import java.io.Serializable;
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
@Table(name = "tb_charge_record")
@DynamicUpdate
public class ChargeRecord {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "uid")
	private String uid;
	
	@Column(name = "camount")
	private long camount;

	@Column(name = "reg_date")
	private Date reg_date;
	
	@Builder
	public ChargeRecord(int id, String uid, long camount, Date reg_date) {
		this.id = id;
		this.uid = uid;
		this.camount = camount;
		this.reg_date = reg_date;
	}
}