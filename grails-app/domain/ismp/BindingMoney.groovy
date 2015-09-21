package ismp

class BindingMoney extends RoyaltyBinding {
    //单笔限额
    Double amount
    //每日限额
    Double totalAmount

    static constraints = {

    }

    static mapping = {
        table 'cm_binding_money'
    }
}