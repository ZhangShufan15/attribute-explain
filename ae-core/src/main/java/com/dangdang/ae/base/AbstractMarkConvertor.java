package com.dangdang.ae.base;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析结果拼接转换的抽象类。
 * 
 * @author zhangxiansheng
 *
 */
public abstract class AbstractMarkConvertor implements IMarkConvertor {
	protected String QUADRI_SPACE = "";
	protected String KEY_START = "";
	protected String KEY_END = "";
	protected String VALUE_START = "";
	protected String VALUE_END = "";
	protected String COMMENT_START = "";
	protected String COMMENT_END = "";
	protected String EMBRACE_START = "";
	protected String EMBRACE_END = "";
	protected List<String> markValueArray = new ArrayList<String>();
	protected List<String> markKeyArray = new ArrayList<>();
	
	public AbstractMarkConvertor(){
		initKeyReplaceValue();
		initKeyArray();
	}
	
	private void initKeyArray(){
		markValueArray.add(QUADRI_SPACE);
		markValueArray.add(KEY_START);
		markValueArray.add(KEY_END);
		markValueArray.add(VALUE_START);
		markValueArray.add(VALUE_END);
		markValueArray.add(COMMENT_START);
		markValueArray.add(COMMENT_END);
		markValueArray.add(EMBRACE_START);
		markValueArray.add(EMBRACE_END);
		
		markKeyArray.add(AttributeExplainPrinter.QUADRI_SPACE);
		markKeyArray.add(AttributeExplainPrinter.KEY_START_KEY);
		markKeyArray.add(AttributeExplainPrinter.KEY_END_KEY);
		markKeyArray.add(AttributeExplainPrinter.VALUE_START_KEY);
		markKeyArray.add(AttributeExplainPrinter.VALUE_END_KEY);
		markKeyArray.add(AttributeExplainPrinter.COMMENT_START_KEY);
		markKeyArray.add(AttributeExplainPrinter.COMMENT_END_KEY);
		markKeyArray.add(AttributeExplainPrinter.EMBRACE_START_KEY);
		markKeyArray.add(AttributeExplainPrinter.EMBRACE_END_KEY);
	}
	
	@Override
	public String convert(List<StringBuffer> infoArray) {
		StringBuffer info =  concatInfoArray(infoArray);
		
		return replaceMarkKey(info.toString());
	}

	
	protected abstract void initKeyReplaceValue();
	
	protected abstract StringBuffer concatInfoArray(List<StringBuffer> infoArray);
	
	protected abstract String replaceMarkKey(String html);
}
