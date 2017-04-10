package com.dangdang.ae.base.convertor;

import java.util.List;

import com.dangdang.ae.base.AbstractMarkConvertor;
import com.dangdang.ae.base.AttributeExplainPrinter;

/**
 * 把解析结果转换成文本的转换器内置实现
 * 
 * @author zhangxiansheng
 *
 */
public class PlainMarkConvertor extends AbstractMarkConvertor{

	public PlainMarkConvertor(){
		super();
	}
	
	@Override
	protected void initKeyReplaceValue() {
		KEY_START = "";
		KEY_END = "";
		VALUE_START = "";
		VALUE_END = "";
		COMMENT_START = "";
		COMMENT_END = "";
		EMBRACE_START = "";
		EMBRACE_END = "";
	}

	@Override
	protected StringBuilder concatInfoArray(List<StringBuilder> infoArray) {
		StringBuilder plainTxt = new StringBuilder();

		for(StringBuilder info : infoArray){
			plainTxt.append(info.toString());
			plainTxt.append("\n");
		}
		
		return plainTxt;
	}

	@Override
	protected String replaceMarkKey(String html) {
		for(int i=0; i< markKeyArray.size(); i++){
			html = html.replaceAll(markKeyArray.get(i), markValueArray.get(i)).replaceAll(AttributeExplainPrinter.QUADRI_SPACE, "    ");
		}
		
		return html;
	}
}
