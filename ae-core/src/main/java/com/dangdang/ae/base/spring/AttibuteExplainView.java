package com.dangdang.ae.base.spring;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import com.dangdang.ae.base.AttributeExplainPrinter;
import com.dangdang.ae.base.convertor.HtmlMarkConvertor;

/**
 * 采用属性解析器解析对象属性以及属性注解信息的spring视图类。
 * 使用时配合org.springframework.web.servlet.view.ContentNegotiatingViewResolver使用。
 * 
 * @author zhangxiansheng
 *
 */
public class AttibuteExplainView extends AbstractView {

	public static final String DEFAULT_CONTENT_TYPE = "application/explain";
	
	//指定解析的MediaType类型
	public AttibuteExplainView(){
		setContentType(DEFAULT_CONTENT_TYPE);
	}
	
	@Override
	//执行Model对象解析
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html");
		
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			if (!(entry.getValue() instanceof BindingResult)) {
				Object returnModel = entry.getValue();
				AttributeExplainPrinter printer = new AttributeExplainPrinter(new HtmlMarkConvertor());
				String html = printer.getExplainInfo(returnModel);
				response.setContentLength(html.getBytes().length);
				response.getOutputStream().write(html.getBytes());
				
				//这里仅解析model中第一个不是BindingResult的结果，也就是说仅解析了一个Model对象
				//一般来说我们向前端返回的也是一个对象，所以这样是没有问题的
				break;
			}
		}

	}

}
