package com.ecard.products.utils.security;

import java.util.HashMap;
import java.util.Map;

public class Test {

	// 100000000001,S,00,20110616,00001,4,20000,12345
	// 商户号,标记,批量版本号,日期,当日批次号,总数量,总金额,业务编码
	public static void main(String[] args) {
		
		String key = "zxcasdqwe123zxcasdqwe123qweasdzx";
		Map sPara = new HashMap();
		sPara.put("batchBizid", "100000000001");	// 	商户号
		sPara.put("batchType", "S");		// 	标记
		sPara.put("batchVersion", "00");	// 	批量版本号
		sPara.put("batchDate", "20110616");		//	日期
		sPara.put("batchCurrnum", "00001");	//	批次号
		sPara.put("batchCount", "4");	//	总数量
		sPara.put("batchAmount", "20000");	//	总金额
		sPara.put("batchBiztype", "12345");	//	业务编码
		
		Map sParaNew = EasypayFunction.ParaFilter(sPara); //除去数组中的空值和签名参数
		String mysign = EasypayFunction.BuildMysign(sParaNew, key, "GBK"); //生成签名结果
		System.out.println(mysign);
	}
	
}
