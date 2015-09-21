
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-3-14
 * Time: 下午6:17
 * To change this template use File | Settings | File Templates.
 */
def strPass=null
println strPass==~/(?=^.{6,}$)(?=(?:.*?\d){1})(?=.*[a-zA-Z])(?=(?:.*?[!@#$%*()_+^&}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%*()_+^&]*$/

def num="0.0a"
println num==~/^([0]|([1-9]\d*))\.(\d{0,2})$/

def szSrc = "106D057366E88731";
def ec=TripeDESCodec.encode(szSrc)
println ec
println TripeDESCodec.decode(ec)

