package com.dangdang.ae.base;

import java.util.List;

/**
 * 将解析结果转换成某种展现形式的转换器接口
 * 
 * @author zhangxiansheng
 *
 */
public interface IMarkConvertor {
	String convert(List<StringBuffer> infoArray);
}
