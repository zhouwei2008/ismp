package role

import ismp.CmCustomer

/**
 * Created by IntelliJ IDEA.
 * User: Rex_xu
 * Date: 11-8-29
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
class Role {
    //所属系统
    String belongSys = "商户管理系统"

    //角色名称
    String roleName

    //创建时间
    Date createTime = new Date()

    //角色状态；normal - 正常， close - 关闭
    String roleStatus = 'normal'

    String model = '0'

    String customerId

    static constraints = {
        model(size:0..1000,nullable: true)
    }

    static mapping = {
        id(generator: 'org.hibernate.id.enhanced.SequenceStyleGenerator', params: [sequence_name: 'seq_role', initial_value: 1])
    }
    def static roleStatusMap = ['normal': '正常', 'close': '关闭']
}
