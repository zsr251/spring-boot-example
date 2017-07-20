/*
 *The code is written by 51jiecai.com.
 *All rights reserved.
 */
package com.jc.constant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 *
 *@author kenny
 * Created on 2014年11月27日
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultModel<T extends Serializable> implements Serializable {

	public static final String RESULT_SUCCESS = "100";//成功
	public static final String RESULT_ERROR = "200";//失败
    public static final String RESULT_TIMES_LIMIT = "201";//交易密码输出次数超过3次

	public static final String RESULT_NOT_MODIFIED = "304";//请求内容未发生变化
	public static final String RESULT_WARN = "300";//警告
	public static final String RESULT_AUTH_INVALID = "400";//登陆无效
	public static final String RESULT_REAL_NAME_INVALID = "401";//未实名认证
	public static final String RESULT_NOPERMISSION = "500";//无权限访问
	public static final String RESULT_NOPERMISSION_LIMIT = "501";//访问频繁
	public static final String RESULT_NOT_SUPPORTED = "600";//不支持，场景/接口不再使用，典型的针对app接口，一段时间废弃后，可以通过返回该响应码
	public static final String RESULT_JIECAI_ERROR = "700";	//推送到今日出错

	private static final long serialVersionUID = 1L;

	private String code;

	private String msg;

	private T data;
	public ResultModel(){

	}
	public ResultModel(String c, String m){
		this.code = c;
		this.msg = m;
	}
	
	@JsonProperty("c")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@JsonProperty("m")
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@JsonProperty("d")
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
