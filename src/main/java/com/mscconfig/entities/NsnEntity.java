package com.mscconfig.entities;

import com.mscconfig.commands.NsnCmd;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: win7srv
 * Date: 15.05.13
 * Time: 14:08
 * Сущность кот. наполняется через команды НСН через ССШ
 */
public interface NsnEntity {


	// заполнить поля сушности
	void fillFields(Map<String,Object> map);
	NsnCmd getNsnCommand();


}
