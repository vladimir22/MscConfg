package com.mscconfig.commands;

import com.mscconfig.commands.exceptions.NsnCmdException;



import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: win7srv
 * Date: 22.05.13
 * Time: 9:32
 * Объекты класса хранят команды NSN, пре-команды NSN , паттерны для получения значений, получаемые значения
 */
public class NsnCmd {
	private String cmd;    //ZRQI:ROU:NAME=%VALUE%;  или пока  exemmlmx -c "ZRQI:ROU:NAME=AIMSI;" -n "MSS-239663"
	private Map<String,Param> values = new HashMap<>(); // значения
	private NsnCmd preCmd;   // преКоманда в значения которой (%value%) нужны для выполнения команды
	private NsnCmd parentCmd;

	public void addValue(String name, Param param) throws NsnCmdException {
		if (param==null)  throw new NsnCmdException("Parameter isNull !!!");
		values.put(name.toUpperCase(), param);
	}

	public NsnCmd() {
	}

	public NsnCmd(String cmd, NsnCmd preCmd) {
		this.cmd = cmd;
		this.preCmd = preCmd;
		if (preCmd!=null) preCmd.setParentCmd(this);
	}

	public String getCmd() {
		return cmd;
	}

	/**
	 * Выдает готовую (составленную) НСН команду (с замененными %параметраметрами%)
	 * @return
	 * @throws NsnCmdException
	 */
	public String getCompletedCmd() throws NsnCmdException {
		if (cmd ==null) return null;
		StringBuilder completedCmd = new StringBuilder(cmd);
		Pattern pattern = Pattern.compile("%.\\w+?%");
		Matcher matcher = pattern.matcher(completedCmd.toString());
		boolean found=false;
		while(matcher.find()){
			String foundVal;
			foundVal = matcher.group().toUpperCase();
			//foundVal = foundVal.substring(1,foundVal.length()-1);  // убираем "%"
			Param param  = preCmd.getValues().get(foundVal.substring(1,foundVal.length()-1));  // берем значения из Пре комманды и подставляем в нашу комманду
			if (param==null) throw new NsnCmdException("Can't find in PreCmd Value= '"+foundVal+"'");
			found=true;

			completedCmd.replace(matcher.start(),matcher.end(),param.getValue().toUpperCase());
		}
		return completedCmd.toString();
	}

	public void setCmd(String cmd) throws NsnCmdException {
		if (cmd.contains("%")) checkPreCmdParams(cmd);
		this.cmd = cmd;
	}
	// проверяем наличие в ПреКомманде нужных %значений%
	private void checkPreCmdParams(String cmd) throws NsnCmdException {
		if (preCmd ==null) throw new NsnCmdException("PreCommand isNull , cant past %Param%");
		Pattern pattern = Pattern.compile("%.\\w+?%");
		Matcher matcher = pattern.matcher(cmd);
		boolean found=false;
		while(matcher.find()){
			String foundVal = matcher.group().toUpperCase();
			foundVal = foundVal.substring(1,foundVal.length()-1);  // убираем "%"
			Param param  = values.get(foundVal);
			if (param==null) throw new NsnCmdException("Can't find in PreCmd Value= '"+foundVal+"'");
			found=true;
		}
		if (!found) throw new NsnCmdException("Command syntax error = '"+cmd+"'");
	}

	/**
	 * Выдает самую начальную(первую) команду на выполнение
	 * @return
	 */
	public NsnCmd getStartCmd(){
		NsnCmd cmd =this;
		NsnCmd startCmd = this;
		while (cmd!=null){
			cmd =cmd.getPreCmd();
			if(cmd!=null) startCmd = cmd;
		}
		return startCmd;
	}

	public Map<String, Param> getValues() {
		return values;
	}

	public void setValues(Map<String, Param> values) {
		this.values = values;
	}

	public NsnCmd getPreCmd() {
		return preCmd;
	}

	public void setPreCmd(NsnCmd preCmd) {
		this.preCmd = preCmd;
	}

	public NsnCmd getParentCmd() {
		return parentCmd;
	}

	public void setParentCmd(NsnCmd parentCmd) {
		this.parentCmd = parentCmd;
	}

	@Override
	public String toString() {
		String parentName="NULL";
		if (parentCmd!=null)  parentName = parentCmd.getCmd().toString();
		StringBuilder sb = new StringBuilder("NsnCmd{");
		sb.append("preCmd:").append(preCmd).append("\n");
		sb.append("COMMAND:\n").append(cmd).append("\n");
		sb.append("VALUES:\n");
		for (Map.Entry val : values.entrySet()){
			sb.append(val.getKey()).append(" = ").append(val.getValue()).append("\n");
		}
		sb.append("parentCmd=").append(parentName);
		return sb.toString();
	}
}
