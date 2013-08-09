package com.mscconfig.entities;

import com.mscconfig.commands.NsnCmd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

/**
 * User: win7srv
 * Date: 14.05.13
 * Time: 12:01
 * Хранит данные по MGW
 */
@Entity
@Table(name = "mgw_data")
public class MgwData implements NsnEntity {
	public static final String NSN_COMMAND ="ZRQI:NAME=?";

	@Id
	@Column(name = "mgw_id")
	private Long mgw_id;
	@Column(name = "name")
	private String name;
	@Column(name = "ip")
	private String ip;

	public MgwData() {
	}


	@Override
	public void fillFields(Map<String, Object> map) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public NsnCmd getNsnCommand() {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public Long getMgw_id() {
		return mgw_id;
	}

	public void setMgw_id(Long mgw_id) {
		this.mgw_id = mgw_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


}
