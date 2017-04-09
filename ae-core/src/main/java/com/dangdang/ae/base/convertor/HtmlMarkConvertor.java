package com.dangdang.ae.base.convertor;

import java.util.List;

import com.dangdang.ae.base.AbstractMarkConvertor;

/**
 * 把解析结果转换成html的转换器内置实现
 * 
 * @author zhangxiansheng
 *
 */
public class HtmlMarkConvertor extends AbstractMarkConvertor {
	
	public HtmlMarkConvertor(){
		super();
	}


	@Override
	protected void initKeyReplaceValue() {
		QUADRI_SPACE = "&nbsp&nbsp&nbsp&nbsp";
		KEY_START = "<span class='explain_key'>";
		KEY_END = "</span>";
		VALUE_START = "<span class='explain_value'>";
		VALUE_END = "</span>";
		COMMENT_START = "<span class='explain_comment'>";
		COMMENT_END = "</span>";
		EMBRACE_START = "<span class='explain_embrace'>";
		EMBRACE_END = "</span>";
	}

	@Override
	protected StringBuffer concatInfoArray(List<StringBuffer> infoArray) {
		StringBuffer html = new StringBuffer();
		html.append("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en'>");
		html.append("<head>");
		html.append("<meta http-equiv='Content-Type' content='text/html;charset=UTF-8' />");
		html.append("<title></title>");
		html.append("<style ='text/css'>");
		html.append(".explain_key{    color: #116;}");
		html.append(".explain_value{    color: #4e9a06;}");
		html.append(".explain_embrace{    color: #c11;font-weight: bold;}");
		html.append("</style>");
		html.append("</head>");
		html.append("<body>");
		html.append("<div class='content' style='border: 1px solid #ccc;    font-size: 12px;'>");

		for(StringBuffer info : infoArray){
			html.append(info.toString());
			html.append("</br>");
		}
		
		html.append("</div>");
		html.append("</body>");
		html.append("</html>");
		
		return html;
	}

	@Override
	protected String replaceMarkKey(String html) {
		for(int i=0; i< markKeyArray.size(); i++){
			html = html.replaceAll(markKeyArray.get(i), markValueArray.get(i));
		}
		
		return html;
	}
}
