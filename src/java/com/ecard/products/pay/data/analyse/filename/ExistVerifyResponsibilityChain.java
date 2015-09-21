package com.ecard.products.pay.data.analyse.filename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ecard.products.pay.data.analyse.ResponsibilityChain;
import com.ecard.products.constants.Constants;

/**
 * 校验重复发送
 * 
 * @author tkZhu
 *
 */
public class ExistVerifyResponsibilityChain extends ResponsibilityChain {

	public ExistVerifyResponsibilityChain(ResponsibilityChain next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String processSelf(String r) {
		// TODO Auto-generated method stub
        //SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        System.out.println("analy name is " + r);
		String rex = "\\d{8}$";
		Pattern p = Pattern.compile(rex);
		Matcher m = p.matcher(r.split("_")[1]);
		if(m.find()){
			StringBuffer path = new StringBuffer();
			path.append(Constants.Config.DATA_ROOT)
				.append(File.separator)
				//.append(m.group())
                //.append(df.format(new Date()))
                .append(r.split("_")[1].substring(3))
				.append(File.separator)
				.append(r + Constants.FILEState.SUFFIX_DONE);	// Need Update
            System.out.println("AAA bbb =========== " + path.toString());
			if(new File(path.toString()).exists()){
				return Constants.Verify.FNAME_EXIST_ERRMSG;
			}
		}
		return null;
	}

}
