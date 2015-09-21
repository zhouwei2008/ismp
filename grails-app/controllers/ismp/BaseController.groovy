package ismp

abstract class BaseController {

    protected writeInfoPage ={msg,type='error'->
        render view:'/error',model: [type:type,msg:msg]
    }

}
