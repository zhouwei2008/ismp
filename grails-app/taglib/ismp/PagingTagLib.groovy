package ismp

class PagingTagLib {
    def pageNavi = {attrs->
        def offset = params.int('offset') ?: 0
        def max = params.int('max')?:10
        def total = attrs.int('total') ?: 0
        int curpage = (offset / max) + 1
        int totalpages = Math.round(Math.ceil(total / max))

        if(totalpages > 1)
        {
            def html="""<div class=\"pagination\"><span>共 ${totalpages} 页 （${total}条记录）"""
            out << html
            if(curpage == 1){

                out <<"<span>第${curpage}页</span>\n"
                out << "<span><a href=\"javascript:pageJS(${curpage+1});\">下一页</a></span>\n"
                out << "<span><a href=\"javascript:pageJS(${totalpages});\">最后一页</a></span>\n"
            } else if(curpage == totalpages){
                out << "<span><a href=\"javascript:pageJS(1);\">首页</a></span>\n"
                out << "<span><a href=\"javascript:pageJS(${curpage-1});\">上一页</a></span>\n"
                out <<"<span>第${curpage}页</span>\n"
            }else{
                out << "<span><a href=\"javascript:pageJS(1);\">首页</a>\n</span>"
                out << "<span><a href=\"javascript:pageJS(${curpage-1});\">上一页</a></span>\n"
                out <<"<span>第${curpage}页</span>\n"
                out << "<span><a href=\"javascript:pageJS(${curpage+1});\" >下一页</a></span>\n"
                out << "<span><a href=\"javascript:pageJS(${totalpages});\" >最后一页</a></span>\n"
            }
            html = """
                    <span>跳转到第
                    <input type="hidden" name="offset" id="offset" value="">
                    <select id="page" name="page" onchange="turnpage(this.value)">
          """
            out << html
            for(int i=1;i<=totalpages;i++)
            {
                if(i==curpage)  out << "<option value='${i}' selected>${i}</option>\n"
                else out << "<option value='${i}'>${i}</option>\n"
            }
            html="""</select>页</span><span>显示<select name="max" id="max" value="${max}" onchange="turnpage(this.value)">"""
             out << html
            for(int j=10;j<=50;j=j+10)
            {
                if(j==max)  out << "<option value='${j}' selected>${j}</option>\n"
                else out << "<option value='${j}'>${j}</option>\n"
            }
               html="""</select>条</span></div>"""
             out << html
	    }else{
            def html = """<div class="pagination">
                    <span>共${totalpages}页 （${total}条记录）</span>
                    <span><a href="#">首页</a></span>
                    <span><a href="#">上一页</a></span>
                    <span>第 ${curpage} 页</span>
                    <span><a href="#">下一页</a></span>
                    <span><a href="#">最后一页</a></span>
                    <span>跳转到第
                    <input type="hidden" name="offset" id="offset" value="">
                    <input type="hidden" name="max" id="max" value="${max}">
                        <select id="page" name="page">
                          <option>1</option>
                        </select>
                    </span>
                    <span>页</span></div>"""
            out << html
        }
    }
}
