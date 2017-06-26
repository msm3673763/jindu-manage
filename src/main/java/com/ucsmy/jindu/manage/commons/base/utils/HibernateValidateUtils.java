package com.ucsmy.jindu.manage.commons.base.utils;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
/***
 * 
 * @author ucs_hexuejun
 *
 */
public class HibernateValidateUtils {
	protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	public static String getErrors(Object obj) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);// 验证某个对象,，其实也可以只验证其中的某一个属性的

		Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
		while (iter.hasNext()) {
			return iter.next().getMessage();
		}
		return null;
	}

	public static String getErrors(Object... objs) {
		for(Object obj : objs) {
			String msg = getErrors(obj);
			if(msg != null || "".equals(msg)){
				return msg;
			}
		}
		return null;
	}

	/**
	 * 返回错误信息，忽略掉给定的msg
	 * @param obj
	 * @param ignoreMsgs 给定要忽略的消息
     * @return
     */
	public static String getErrors(Object obj, String[] ignoreMsgs) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(obj);// 验证某个对象,，其实也可以只验证其中的某一个属性的

		Iterator<ConstraintViolation<Object>> iter = constraintViolations.iterator();
		while (iter.hasNext()) {
			String msg = iter.next().getMessage();
			boolean flag = true;
			if(null != ignoreMsgs && ignoreMsgs.length > 0 ){  //存在需要忽略的信息
				for(String ignoreMsg : ignoreMsgs){
					if(msg.equals(ignoreMsg)){
						flag = false;
						break;
					}
				}
			}
			if(!flag){
				continue;
			}
			return msg;
		}
		return null;
	}

//	public static void main(String[] args) {
//		System.out.println(getErrors(new UserAccount(), new UserProfile()));
//	}
}
