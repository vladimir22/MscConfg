package com.mscconfig.temp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Пример для рефлексии и аннотации
 */
public class ReflectEntity {

	public static void main2(String[] args) throws NoSuchFieldException, IllegalAccessException {
		DataBean bean = new DataBean();
		System.out.println(bean);
		bean.name ="QUQU";
		System.out.println(bean);

		Field field = bean.getClass().getDeclaredField("name");
		field.setAccessible(true);
		field.set(bean, "BLABLA");
		System.out.println(bean);
		DataBean bean2 = new DataBean();
		System.out.println(bean2);

		EnityCmd enityCmd = bean.getClass().getAnnotation(EnityCmd.class);
		System.out.println(enityCmd.command());
		DataField dataField;
		for (Field f : bean.getClass().getDeclaredFields()) {
			if ((dataField = f.getAnnotation(DataField.class)) != null) {
				System.out.println(f.getName()+" = "+dataField.regex());
				f.set(bean, "BLABLA!!!"+dataField.regex());
			}
		}
		System.out.println(bean);
	}
}
@EnityCmd(command = "SUPER PUPER")
class DataBean {
	@DataField(regex = "MEGA REGEX")
	public String name;

	@DataField
	public String data;

	public String description;

	@Override
	public String toString() {
		return "DataBean{" +
				"name='" + name + '\'' +
				", data='" + data + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface DataField {
	String regex() default "defRegex";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface EnityCmd {
	String command() default "empty";
}
