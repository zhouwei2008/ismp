package packagedata;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: wangzhao
 * Date: 12-2-13
 * Time: 下午3:12
 * 将订单数据构造成一个对象,传个接口解析数据
 */
public class Submitorders {
      public String srvCode; //业务类型 代付agentpay  代收agentcoll
      public String srvChan;   //渠道类型      批量batch ，单笔single ，     接口interface
      public String cmrNo;    //商户号
      public String msg;      //订单域 格式为 ,,,|,,,,|,,,

    @Override
    public String toString() {
        return "Submitorders{" +
                "srvCode='" + srvCode + '\'' +
                ", srvChan='" + srvChan + '\'' +
                ", cmrNo='" + cmrNo + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String getSrvCode() {
        return srvCode;
    }

    public void setSrvCode(String srvCode) {
        this.srvCode = srvCode;
    }

    public void setSrvChan(String srvChan) {
        this.srvChan = srvChan;
    }

    public void setCmrNo(String cmrNo) {
        this.cmrNo = cmrNo;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSrvChan() {
        return srvChan;
    }

    public String getCmrNo() {
        return cmrNo;
    }

    public String getMsg() {
        return msg;
    }
}
