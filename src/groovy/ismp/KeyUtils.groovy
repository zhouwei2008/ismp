package ismp

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-3-10
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
static class KeyUtils {
    //获得随机key,length 为长度
    static getRandKey(int length)
    {
        def random = new Random()
        def chars = []
        ['a'..'g','0'..'9'].each{chars += it}
        //println chars
        def password = (1..length).collect{ chars[random.nextInt(chars.size())] }.join()
    }

    static getRandNumberKey(int length)
    {
        def random = new Random()
        def chars = []
        ['0'..'9'].each{chars += it}
        //println chars
        def password = (1..length).collect{ chars[random.nextInt(chars.size())] }.join()
    }

    static boolean checkPassStrength(String strPass)
    {
        //检查密码强度(密码长度必须6-20位，且必须是数字、字母和特殊字符组合而成)
//        if(strPass==null||strPass.length()<6) return false
//        return true
        if (strPass==~/(?=^.{8,20}$)(?=(?:.*?\d){1})(?=.*[a-zA-Z])(?=(?:.*?[^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/}{:;?.]){1})(?!.*\s)[0-9a-zA-Z^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/]*$/){return true}
        else if (strPass==~/(?=^.{8,20}$)(?=(?:.*?\d){1})(?=.*)(?=(?:.*?[!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/}{:;?.]){1})(?!.*\s)[0-9!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/]*$/){return true}
        else if (strPass==~/(?=^.{8,20}$)(?=(?:.*){1})(?=.*[a-zA-Z])(?=(?:.*?[!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/}{:;?.]){1})(?!.*\s)[a-zA-Z!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/]*$/){return true}
        else if (strPass==~/(?=^.{8,20}$)(?=(?:.*?\d){1})(?=.*[a-zA-Z])(?=(?:.*?[!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/]*$/){return true}
        else {return false}
//        return strPass==~/(?=^.{6,}$)(?=(?:.*?\d){1})(?=.*[a-zA-Z])(?=(?:.*?[!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/}{:;?.]){1})(?!.*\s)[0-9a-zA-Z!@#$%*()_+^&:"\<\>\?\{\}\|\[\]\\;\'\,\.\/]*$/
    }

    static boolean checkWords(String strPass)
    {
        return strPass==~/^[a-zA-Z0-9\u4E00-\u9FA5]+$/
    }
}
