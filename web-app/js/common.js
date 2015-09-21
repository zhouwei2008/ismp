/**
 * Created by IntelliJ IDEA.
 * User: zhaoshuang
 * Date: 12-9-25
 * Time: 上午9:37
 * To change this template use File | Settings | File Templates.
 */

// 隔行换色
$(function(){
	$(".tlb1 tr:even").addClass("demoBg");
})
$(document).ready(function(){
  $("#serch").click(function(){
  if ($("#serch").text() == "更多条件") {
    $("#serch").text("简单查询");
  } else {
    $("#serch").text("更多条件");
  }
  $("#serchmore").toggle();
  });
});