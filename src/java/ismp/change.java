package ismp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
//����˼·��
//�����ֽ��зּ����?����Ϊ4
//�Էּ����ÿ���ֱ��?�����õ��ַ�����
//�磺123456=12|3456
//�ڶ�����12=Ҽʰ�� + ����
//��һ����3456 =��ǧ������ʰ½ + ����
public final class change{
    private static final String NUM = "零壹贰叁肆伍陆柒捌玖";
    private static final String UNIT = "仟佰拾个";
    private static final String GRADEUNIT = "仟万亿兆";
    private static final String DOTUNIT = "角分厘";
    private static final int GRADE = 4;
    private static final NumberFormat nf = new DecimalFormat("#0.###");

    public static String toBigAmt(double amount) {
        nf.setMinimumFractionDigits(3);//С������Ĳ���
        String amt = nf.format(amount);//��double���͵����ʽ����ת�����ַ�,ʵ���Ͻ�������������
        System.out.println(amt);
        String dotPart = ""; //ȡС��λ
        String intPart = ""; //ȡ����λ
        int dotPos;

        if ((dotPos = amt.indexOf('.')) != -1) {
            intPart = amt.substring(0, dotPos);
            dotPart = amt.substring(dotPos + 1);
        } else {
            intPart = amt;
        }
        if (intPart.length() > 16)
            throw new InternalError("The amount is too big.");
        String intBig = intToBig(intPart);
        String dotBig = dotToBig(dotPart);
        //���´��������޸ģ������������Ĵ�������
        if ((dotBig.length() == 0) && (intBig.length() != 0)) {
            return intBig + "元整";
        } else if ((dotBig.length() == 0) && (intBig.length() == 0)) {
            return intBig + "零元";
        } else if ((dotBig.length() != 0) && (intBig.length() != 0)) {
            return intBig + "元" + dotBig;
        } else {
            return dotBig;
        }
    }
    //�������?�Ǽ��ּ���
    private static String dotToBig(String dotPart) {
        //�õ�ת����Ĵ�д��С��֣�
        String strRet = "";
        for (int i = 0; i < dotPart.length() && i < 3; i++) {
            int num;
            if ((num = Integer.parseInt(dotPart.substring(i, i + 1))) != 0)
                strRet += NUM.substring(num, num + 1)
                        + DOTUNIT.substring(i, i + 1);
        }
        return strRet;
    }
    //�����������Ԫ
    private static String intToBig(String intPart) {
        //�õ�ת����Ĵ�д������֣�
        int grade; //����
        String result = "";
        String strTmp = "";
        //�õ�������
        grade = intPart.length() / GRADE;
        //����γ���
        if (intPart.length() % GRADE != 0)
            grade += 1;
        //��ÿ�����ִ��?�ȴ�����߼���Ȼ���ٴ���ͼ���
        for (int i = grade; i >= 1; i--) {
            strTmp = getNowGradeVal(intPart, i);//ȡ�õ�ǰ��������
            result += getSubUnit(strTmp);//ת����д
            result = dropZero(result);//���� ȥ���������
            //�Ӽ��ε�λ
            if (i > 1) //ĩλ���ӵ�λ
                //��λ����������
                //ע�⣺����4�����ʱ��Ҫ���⴦��
                if(getSubUnit(strTmp).equals("零零零零")){
                    result = result+"零";
                }else{
                 result += GRADEUNIT.substring(i - 1, i);
                }
        }
        return result;
    }
    //�õ���ǰ���εĴ�
    private static String getNowGradeVal(String strVal, int grade) {       
        String rst;
        if (strVal.length() <= grade * GRADE)
            rst = strVal.substring(0, strVal.length() - (grade - 1) * GRADE);
        else
            rst = strVal.substring(strVal.length() - grade * GRADE, strVal
                    .length()
                    - (grade - 1) * GRADE);
        return rst;
    }
    //��ֵת��
    private static String getSubUnit(String strVal) {       
        String rst = "";
        for (int i = 0; i < strVal.length(); i++) {
            String s = strVal.substring(i, i + 1);
            int num = Integer.parseInt(s);
            if (num == 0) {
                //���㡱�����⴦��
                if (i != strVal.length()) //ת������ĩλ����Ϊ��
                    rst += "零";
            } 
            else {
                //��Ҽʰ�������⴦��
                rst += NUM.substring(num, num + 1);
                //׷�ӵ�λ
                if (i != strVal.length() - 1)//��λ���ӵ�λ
                    rst += UNIT.substring(i + 4 - strVal.length(), i + 4
                            - strVal.length() + 1);
            }
        }
        return rst;
    }
    //ȥ�����̵ġ��㡱
    private static String dropZero(String strVal) {     
        String strRst;
        String strBefore; //ǰһλ���ַ�
        String strNow; //����λ���ַ�
        strBefore = strVal.substring(0, 1);
        strRst = strBefore;
        for (int i = 1; i < strVal.length(); i++) {
            strNow = strVal.substring(i, i + 1);
            if (strNow.equals("零") && strBefore.equals("零"))
             ;//ͬʱΪ��
            else
                strRst += strNow;
            strBefore = strNow;
        }
        //ĩλȥ��
        if (strRst.substring(strRst.length() - 1, strRst.length()).equals("零"))
            strRst = strRst.substring(0, strRst.length() - 1);
        return strRst;
    }
    public static void main(String[] args) {       
     System.out.println(change.toBigAmt(0.090D));    
     System.out.println(change.toBigAmt(10.090D));   
     System.out.println(change.toBigAmt(9.090D));    
     System.out.println(change.toBigAmt(9.290D));    
     System.out.println(change.toBigAmt(5.90D));
        System.out.println(change.toBigAmt(10052345.00D));
        System.out.println(change.toBigAmt(0.00D));
        System.out.println(change.toBigAmt(0.60D));
        System.out.println(change.toBigAmt(00.60D));
        System.out.println(change.toBigAmt(150.2101D));
        System.out.println(change.toBigAmt(15400089666.234D));
        System.out.println(change.toBigAmt(15400089666.2347D));
        System.out.println(change.toBigAmt(1111222233334444.2347D));
        System.out.println(change.toBigAmt(111222233334444.2347D));
        System.out.println(change.toBigAmt(11222233334444.2347D));
        System.out.println(change.toBigAmt(1222233334444.2347D));
        System.out.println(change.toBigAmt(222233334444.2347D));
        System.out.println(change.toBigAmt(22200004444.2347D));
        System.out.println(change.toBigAmt(10004.2347D));
        System.out.println(change.toBigAmt(22200000004.2347D));
        System.out.println(change.toBigAmt(10400.0047D));
        System.out.println(change.toBigAmt(1000000000000.23477777777777D));     
        System.out.println(change.toBigAmt(100100100.090D));
    }
}
