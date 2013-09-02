package com.mscconfig.commands;

import com.mscconfig.commands.exceptions.NsnCmdException;
import org.hibernate.metamodel.source.binder.Sortable;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Vladimir
 * Date: 22.05.13
 * Time: 9:32
 * NSN Command Instance contains:child NSN command,  itself command(String), values related to command
 */
public class NsnCmd implements Comparable<NsnCmd> {
	private String cmd;    //Ex:ZRQI:ROU:NAME=%VALUE%;  temporarily have :  exemmlmx -c "ZRQI:ROU:NAME=AIMSI;" -n "MSS-239663"
	private Map<String,Param> values = new LinkedHashMap<>(); // values Map
	private NsnCmd childCmd;   // преКоманда в значения которой (%value%) нужны для выполнения команды
	private String fullText;   // full command log(ssh-answer)

	public void addValue(String name, Param param) throws NsnCmdException {
		if (param==null)  throw new NsnCmdException("Parameter isNull !!!");
		values.put(name.toUpperCase(), param);
	}

	public NsnCmd() {
	}

	public NsnCmd(String cmd, NsnCmd childCmd) {
		this.cmd = cmd;
		this.childCmd = childCmd;
	}

	public String getCmd() {
		return cmd;
	}

	/**
	 * Replace Values of NsnCmd (%VALUES%)  by Real VALUES from child Command
	 * @return
	 * @throws NsnCmdException
	 */
	public String getCompletedCmd() throws NsnCmdException {
		if (cmd ==null) throw new NsnCmdException("Command for execution is NULL !");
		StringBuilder completedCmd = new StringBuilder(cmd);
		Pattern pattern = Pattern.compile("%.\\w+?%");
		Matcher matcher = pattern.matcher(completedCmd.toString());
		while(matcher.find()){
			String foundVal;
			foundVal = matcher.group().toUpperCase();
			//foundVal = foundVal.substring(1,foundVal.length()-1);  // убираем "%"
			Param param  = childCmd.getValues().get(foundVal.substring(1,foundVal.length()-1));  // берем значения из Пре комманды и подставляем в нашу комманду
			if (param==null) throw new NsnCmdException("Can't find in PreCmd Value= '"+foundVal+"'");


			completedCmd.replace(matcher.start(),matcher.end(),param.getValue().toUpperCase());
		}
		return completedCmd.toString();
	}

	/**
	 * Replace CustomParams ($CustomParameter$) by customValues
	 * @param customParamName
	 * @param customParamValue
	 * @throws NsnCmdException
	 * @deprecated   Temprorary method while not realize complete on js (ajax) side logic
	 */
	public void replaceCustomParams(String customParamName, String customParamValue) throws NsnCmdException {
		if (cmd ==null) throw new NsnCmdException("Command for execution is NULL !");
		Pattern pattern = Pattern.compile("@.\\w+?@");
		Matcher matcher = pattern.matcher(cmd);
		while(matcher.find()){
			String foundVal;
			foundVal = matcher.group().toUpperCase();
			if(foundVal.substring(1,foundVal.length()-1).equals(customParamName))
				cmd = cmd.replace(foundVal,customParamValue.toUpperCase());
		}
	}

	public void setCmd(String cmd) throws NsnCmdException {
		if (cmd.contains("%")) checkPreCmdParams(cmd);
		this.cmd = cmd;
	}
	// проверяем наличие в ПреКомманде нужных %значений%
	private void checkPreCmdParams(String cmd) throws NsnCmdException {
		if (childCmd ==null) throw new NsnCmdException("PreCommand isNull , cant past %Param%");
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
			cmd =cmd.getChildCmd();
			if(cmd!=null) startCmd = cmd;
		}
		return startCmd;
	}

	/**
	 * Returns full ordered List of Nsn commands required to perform
	 * @return
	 */
	public List<NsnCmd> getOrderedCmdList(){
		ArrayList<NsnCmd> cmdList = new ArrayList();
		cmdList.add(this);
		NsnCmd children =this.getChildCmd();
		while(children!=null){
			cmdList.add(children);
			children =children.getChildCmd();
		}
		Collections.sort(cmdList);
		return cmdList;
	}

	public Map<String, Param> getValues() {
		return values;
	}

	public void setValues(Map<String, Param> values) {
		this.values = values;
	}

	public NsnCmd getChildCmd() {
		return childCmd;
	}

	public void setChildCmd(NsnCmd childCmd) {
		this.childCmd = childCmd;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("NsnCmd{");
		sb.append("childCmd:").append(childCmd).append("\n");
		sb.append("COMMAND:\n").append(cmd).append("\n");
		sb.append("VALUES:\n");
		for (Map.Entry val : values.entrySet()){
			sb.append(val.getKey()).append(" = ").append(val.getValue()).append("\n");
		}
		return sb.toString();
	}

	public String getFullText() {
		StringBuffer allText = new StringBuffer();
		if(childCmd!=null) allText.append(childCmd.getFullText());
		allText.append(this.fullText).append('\n');

		return allText.toString();
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	@Override
	public int compareTo(NsnCmd o) {
		if (this.getChildCmd()==null) {
			if (o.getChildCmd()==null) return 0;
			else return -1;
		}   else return getChildCmd().compareTo(o.getChildCmd());
	}
}
