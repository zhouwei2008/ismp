package boss

class BoNews {
    static mapping = {
        id generator: 'sequence', params: [sequence: 'seq_bo_news']
      }
      //系统编号
      String sysId = 'RONGPAY'
      //接收对象
      String reciever
      //栏目
      String msgColumn
      //内容
      String content
      //创建日期
      Date dateCreated
      //发布人
      BoOperator publisher

      static constraints = {
        reciever(nullable: true)
      }

      def static columnLs = ['吉高', '商户接入', '生活服务']

}
