package com.ecard.products.constants;

import java.io.File;
import java.util.*;

import com.ecard.products.utils.ResourceLoad;

public class Constants {

	public static class Config{
		private static Properties config = null;
		/*static{
			config = ResourceLoad.load("cfg_dsf.properties");
		}*/
        /* root=D\:\\\\Root
        model_pay_fname=agentpay_model.txt
        model_deduct_fname=agentdeduct_model.txt
        identity_timeout=300*/
        public static final String DATA_ROOT = "/home/production/uploads/ismp";//"D:\\home\\production\\uploads\\ismp";
        //public static String DATA_ROOT = "D:"+File.separator+"home"+File.separator+"production"+File.separator+"uploads"+File.separator+"ismp";
		/*public static final int IDENTITY_TIMEOUT = Integer.parseInt(config.getProperty("identity_timeout"));
		public static final String PAY_MODEL_FILE = config.getProperty("model_pay_fname");
		public static final String DEDUCT_MODEL_FILE = config.getProperty("model_deduct_fname");*/
		
	}
    //计算手续费
     public static class Channel{

        public static String BATCH_CHANNEL = "batch";
        public static String BATCH_CHANNEL_TXT_TYPE = "TXT";
        public static String BATCH_CHANNEL_EXCEL_TYPE = "XLS";
        public static String SINGLE_CHANNEL = "single";
        public static String INTERFACE_CHANNEL = "interface";
        public static String TYPE = "Channel";

        public static class Status{
            public static String OPEN = "open";
            public static String CLOSED = "closed";
        }
    }
    /**
     * 服务端安全证书信息
     */
    public static class ServerCert{

        // 证书库名称
        public static String CERTIFICATE_STORE_NAME = "tomcat.keystore";
        // 证书名称
        public static String CERTIFICATE_NAME = "tomcat.cer";
        // 证书Alias
        public static String CERTIFICATE_ALIAS = "tomcat";
        // 证书库密码
        public static String CERTIFICATE_STORE_PASS = "pdepde";
        // 证书密码
        public static String CERTIFICATE_KEY_PASS = "pdepde";

    }

    public static class ServiceType{
        public static String COLLECT_SERVICE = "agentcoll";
        public static String PAY_SERVICE = "agentpay";
        public static String PAY_ONLINE = "online";
        public static String FEE_SERVICE = "fee";
        public static String FEERFD_SERVICE = "fee_rfd";
        public static String REFUND_SERVICE = "refund";
    }
	
	public static class Services{
		public static Map<String, String> sf = new HashMap<String, String>(); 
		static{
			sf.put("S", "05");
			sf.put("F", "06");
		}
	}

    public static class ServiceAlias{
		public static Map<String, String> sf = new HashMap<String, String>();
		static{
            sf.put("S", "agentcoll");
            sf.put("F", "agentpay");
		}
	}
	
	public static class Status{
		
		// 待商户审批
		public static final String ORDER_INIT = "0";
		
		// 商户审批通过
		public static final String ORDER_PASS = "1";
		
		// 商户审批拒绝
		public static final String ORDER_REFUSE = "2";

        //商户代确认
        public static final String ORDER_BOX = "3";
		
	}
	public static class AllowFType{
       private static Set fTypes=new HashSet();//"xls","txt","XLS","TXT");
       public static Set allowTypes(){
          if(fTypes.size()!=0){
             return fTypes;
          }else{
              fTypes.add("txt");
              fTypes.add("TXT");
              fTypes.add("xls");
              fTypes.add("XLS");
          }
          return fTypes;
       }
    }


	// 可将信息加入struts
	public static class Verify{
		// 
		public static final String FNAME_FORMAT_ERRMSG = "文件名格式错误";
		//
		public static final String FNAME_DATE_ERRMSG = "提交日期不匹配";
		//
		public static final String FNAME_EXIST_ERRMSG = "当日已有同名文件，请修改文件名重新提交";
		//
		public static final String FNAME_PAYSIGN_ERRMSG = "代付标志不正确";
		//
		public static final String FNAME_COLLECTSIGN_ERRMSG = "代收标志不正确";
		
		//
		public static final String FNAME_NOBIZORG_ERRMSG = "不能获取商户信息";
		//
		public static final String FNAME_NOEQUALSBIZORG_ERRMSG = "没有权限上传其它商户文件";
		//
		public static final String FNAME_VERSION_ERRMSG = "提交的文件版本号不符合";
		//
		public static final String FNAME_PAYSERVICE_ERRMSG = "没有开通代付服务，请联系管理员网络";
		//
		public static final String FNAME_COLLECTSERVICE_ERRMSG = "没有开通代收服务，请联系管理员网络";
		
		
		// 
		public static String DETAIL_COMPLETE_ERRMSG(final int count){
			return "文件内容格式错误，数据不是12项！ 请检查第" + count +  "行记录!";
		}
		//
		public static String DETAIL_NULLNUM_ERRMSG(final int count){
			return "划账记录序号为空! 请检查第" + count + "行记录!";
		}
		// 
		public static String DETAIL_FORMAT_ERRMSG(final int count){
			return "划账记录序号格式错误! 请检查第" + count + "行记录!";
		}
		// 
		public static String DETAIL_REPEAT_ERRMSG(final int count){
			return "划账记录序号重复! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_NULLACCOUNT_ERRMSG(final int count){
			return "账户号不能为空! 请检查第" + count + "行记录!";
		}
		// 
		public static String DETAIL_ACCOUNT_ERRMSG(final int count){
			return "账户号格式错误或带非数字字符! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_NULLACCNAME_ERRMSG(final int count){
			return "账户名不能为空! 请检查第" + count + "行记录!";
		}
        //
		public static String DETAIL_ACCNAMEOVER_ERRMSG(final int count){
			return "账户名超长! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_NULLBRANCH_ERRMSG(final int count){
			return "分行不能为空! 请检查第" + count + "行记录!";
		}
        //
		public static String DETAIL_BRANCHOVER_ERRMSG(final int count){
			return "分行信息超长! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_NULLSUBBRANCH_ERRMSG(final int count){
			return "支行不能为空! 请检查第" + count + "行记录!";
		}
        //
		public static String DETAIL_SUBBRANCHOVER_ERRMSG(final int count){
			return "支行信息超长! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_NULLBANKNAME_ERRMSG(final int count){
			return "银行名称不能为空! 请检查第" + count + "行记录!";
		}
        //
		public static String DETAIL_BANKNAMEOVER_ERRMSG(final int count){
			return "银行名称超长! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_NULLACCTYPE_ERRMSG(final int count){
			return "账户类型不能为空! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_FORMATACCTYPE_ERRMSG(final int count){
			return "账户类型格式错误! 请检查第" + count + "行记录!";
		}
		public static String DETAIL_NULLAMOUNT_ERRMSG(final int count){
			return "金额不能为空! 请检查第" + count + "行记录!";
		}
		//
		public static String DETAIL_AMOUNT_ERRMSG(final int count){
			return "金额格式出错! 请检查第" + count + "行记录！";
		}
        //
		public static String DETAIL_NULLINFO_ERRMSG(final int count){
			return "银行所在省市信息不能为空！ 请检查第" + count + "行记录！";
		}
        //
		public static String DETAIL_FORMATINFO_ERRMSG(final int count){
			return "银行所在省市信息格式错误，需要一个分号分隔! 请检查第" + count + "行记录！";
		}
		//
		public static String DETAIL_OVERINFO_ERRMSG(final int count){
			return "银行所在省市信息过长! 请检查第" + count + "行记录！";
		}
        //
		public static String DETAIL_AMOUNTOVER_ERRMSG(final int count){
			return "金额超限额! 请检查第" + count + "行记录！";
		}

          //
		public static String DETAIL_NULLUSERCODE_ERRMSG(final int count){
			return "用户协议号不能为空！ 请检查第" + count + "行记录！";
		}
          //
		public static String DETAIL_FORMATUSERCODE_ERRMSG(final int count){
			return "用户协议号格式错误！ 请检查第" + count + "行记录！";
		}
         //
		public static String DETAIL_NULLINFO2_ERRMSG(final int count){
			return "用途信息不能为空！ 请检查第" + count + "行记录！";
		}
        //
		public static String DETAIL_OVERINFO2_ERRMSG(final int count){
			return "用途信息超出! 请检查第" + count + "行记录！";
		}

        //长度
		public static String DETAIL_ACCOUNTLEN_ERRMSG(final int count){
			return "账户号长度错误! 请检查第" + count + "行记录!";
		}


        // TOTAL Count Size
        public static String TITLE_NOCOUNT_ERRMSG = "文件内容没有记录数，请检查";
		// TOTAL Count
		public static String TITLE_TOTALCOUNT_ERRMSG = "文件内容格式错误，数据不是3项！ 请检查文件标题记录！";
		// TOTAL Count
		public static String TITLE_COUNT_ERRMSG = "文件内容格式错误，汇总数量格式错误或与明细数量不符， 请检查文件标题记录！";
		// TOTAL AMOUNT
		public static String TITLE_AMOUNT_ERRMSG = "文件内容格式错误，汇总金额格式错误或与明细金额总数不符，请检查文件标题记录！";
		// TOTAL AMOUNT
		public static String TITLE_BIZTYPE_ERRMSG = "文件内容格式错误，业务类型不能为空，请检查文件标题记录！";
        // 业务类型格式错误
        public static String TITLE_BIZTYPEFORMAT_ERRMSG = "文件内容格式错误，业务类型格式错误，请检查文件标题记录！";
		 //
		public static String DETAIL_DAYCOUNTOVER_ERRMSG = "提交的交易数量超出当日限额， 请联系管理员!";

        public static String DETAIL_DAYAMOUNTOVER_ERRMSG = "提交的交易金额超出当日限额， 请联系管理员!";

		// 签名不能为空
		public static final String TITLE_NULLSIGN_ERRMSG = "签名不能为空，请检查。";
		// 签名失败
		public static final String TITLE_SIGN_ERRMSG = "签名失败，请检查。";
		
		
		// 系统异常
		public static final String SYS_CHECK_ERRMSG = "系统检测时发现异常，请联系管理员。";
		// 未知异常
		public static final String NONAME_CHECK_ERRMSG = "未知错误，请联系海汇支付.";
	}
	
	/**
	 * 业务类型集合
	 * @deprecated 
	 * @author tkZhu
	 * 暂时没有意义
	 */
	public static class BizType{
		// 代付业务类型
		public static final Map<String, String> bizPayTypes = new HashMap<String, String>();
		// 待收业务类型
		public static final Map<String, String> bizCollectTypes = new HashMap<String, String>();
	}
	
	public static class FILEState{
		// 
		public static final String SUFFIX_TEMP = ".TEMP";
		// 
		public static final String SUFFIX_NORMAL = "";
		// 
		public static final String SUFFIX_DONE = ".DONE";
		// 
		public static final String SUFFIX_FAIL = ".FAIL";
        //
        public static final String SUFFIX_EXCEPTION = ".EXCEPTION";
	}
	
	public static class ClientResult{
		public static final String SUCCESS = "成功";
		public static final String FAIL = "失败";
	}
	
	/**
	 * 
	 * @author tkZhu
	 *
	 */
	public static class LogicServices{
		public static final String ILogService = "ILogService";	//
		public static final String IBizUserService = "IBizUserService";	//
		public static final String IAgentPayService = "IAgentPayService";//
	}
	
	public static class Log{
		public static final String[] OPERATE_TYPE = {"添加操作","更改操作","删除操作","查询操作","登录操作","分析操作","审核通过操作","审核拒绝操作"};
		public static final String[] STATUS = {"操作正常完成","操作无法完成","无权操作","登录密码错误","登录用户名错误","非法操作","未登录操作"};
		public static final Integer OPERATE_TYPE_INSERT = 0;
		public static final Integer OPERATE_TYPE_UPDATE = 1;
		public static final Integer OPERATE_TYPE_DELETE = 2;
		public static final Integer OPERATE_TYPE_SELECT = 3;
		public static final Integer OPERATE_TYPE_LOGIN = 4;
		public static final Integer OPERATE_TYPE_ANALYZE = 5;
		public static final Integer OPERATE_TYPE_CHECKPASS = 6;
		public static final Integer OPERATE_TYPE_CHECKREFUSE = 7;
		
		public static final Integer STATUS_SUCCESS = 0;
		public static final Integer STATUS_FAIL = 1;
		public static final Integer STATUS_NORIGHT = 2;
		public static final Integer STATUS_WRONGPASS = 3;
		public static final Integer STATUS_WRONGLOGINID = 4;
		public static final Integer STATUS_ILLEGAL = 5;
		public static final Integer STATUS_NOLOGIN = 6;
		
	}
	
	public static final String SESSION_LAST_IP_LABEL = "SESSION_LAST_IP";
	public static final int ERR_TIME_LONG = -3000;
	public static final String SESSION_USER_LABEL = "bizUser";
	public static final String SESSION_USER_BEAN = "sessionUserBean";
	public static final String SESSION_LOG = "bizOperLog";
	
	public static class UPLOAD{
		public static final String MAX_UPLOAD_SIZE = "maxUploadSize";
		
		// 请求数据的size超出了规定的大小
		public static final String BASESIZELIMIT_EXCEEDED_EXCEPTION = "baseSizeLimitExceededException";
		// 请求文件的size超出了规定的大小
		public static final String BASEFILESIZELIMIT_EXCEEDED_EXCEPTION = "baseFileSizeLimitExceededException";
		// 文件传输出现错误,例如磁盘空间不足等.
		public static final String BASEIOFILE_UPLOAD_EXCEPTION = "baseIOFileUploadException";
		//
		public static final String BASEINVALID_CONTENTTYPE_EXCEPTION = "baseInvalidContentTypeException";
		//
		public static final String FILE_UPLOAD_EXCEPTION = "FileUploadException";
	}
	
	public static class SQL{
		
		public static final String USER_SELECT_FOR_LOGIN = "FROM BizUser bu WHERE bu.userCode = ? AND bu.userPwd = ?";
		public static final String USER_SELECT_BY_USERCODE = "FROM BizUser bu WHERE bu.userCode = ?";
	
		
	}
	public static class FileNum{
         public static final Integer NUMBER=50000;
    }
	public static final String[] EXCEPT_URLS = new String[]{
		"index.jsp",
		"index_login.jsp",
		"building.jsp",
		"Click.jsp",
		"fullScreen.jsp",
		"left.jsp",
		"subframe.jsp",
		"success.jsp",
		"systemframe.jsp",
		"top.jsp",
		"welcome.jsp",
		"systemAdmin.jsp",
		"systemPaths.jsp",
		"AuthUserAction.login",
		"nologinError.jsp",
		"privilegeError.jsp",
		"systemError.jsp",
		"auth.jsp",
		"login"
	};
	
	// Testing
	public static void main(String[] args) {
		System.out.println(Config.DATA_ROOT);
	}
}
