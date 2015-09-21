package model

/**
 * Created by IntelliJ IDEA.
 * User: MengLH
 * Date: 11-8-30
 * Time: 下午6:51
 * To change this template use File | Settings | File Templates.
 */
class Model {

    /**
     * 功能操作名
     */
    String modelName

    /**
     * 功能操作标识（路径）
     */
    String modelPath

    /**
     * 当前树中的位置
     */
    String point

    /**
     * 当前树中，所属父辈的位置
     */
    String parentPoint
    /**
     * r 读操作
     * u 写操作
     * ru 全部操作
     * null || "" 无权限
     */
    String operate

    /**
     * 功能所处层级
     */
    String modelLevel

    Integer modelindex

    String serviceCode

    static mapping = {
        id generator: 'sequence', params: [sequence: 'seq_model']
    }

    static constraints = {

        modelName(maxSize:255,blank: false)
        modelPath(maxSize:255,blank: false)
        point(maxSize:255,blank: false)
        parentPoint(maxSize:255,blank: false)
        operate(nullable: true)
        modelLevel(nullable: true)
        modelindex(nullable: true)
        serviceCode(nullable: true)
    }

//    def static defModelUrlMap = ['index/account','captcha/index','login/login','login/logout','login/queryWelcomeMsg','login/authenticate']
//    def static modelUrlMap
//    def static modelMap
}
