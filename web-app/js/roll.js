/**
 * Created by IntelliJ IDEA.
 * User: zhaoshuang
 * Date: 12-10-11
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */

// 无缝滚动
var speed=60
var news=document.getElementById("news");
var news2=document.getElementById("news2");
var news1=document.getElementById("news1");
news2.innerHTML=news1.innerHTML

function Marquee() {
    if(news2.offsetTop-news.scrollTop<=0)
      news.scrollTop-=news1.offsetHeight
    else{
        news.scrollTop++
        if(news.scrollTop>=news1.offsetHeight){
            //当news1刚好超出滚动区域news的时候，就立马让news1又退回到滚动区域内
            news.scrollTop=0;
        }
    }
}
var MyMar=setInterval(Marquee,speed);
news.onmouseover=function() {
    clearInterval(MyMar);
}
news.onmouseout=function() {
    MyMar=setInterval(Marquee,speed);
}