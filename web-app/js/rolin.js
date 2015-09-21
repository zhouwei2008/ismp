/**
 * Created by IntelliJ IDEA.
 * User: zhaoshuang
 * Date: 12-12-5
 * Time: 下午4:27
 * To change this template use File | Settings | File Templates.
 */

//抽屉
window.onload = function() {
rolinTab("rolin")
}
function rolinTab(obj) {
var list = $(obj).getElementsByTagName("ol");
var state = {show:false,hidden:false,showObj:false};

for (var i=0; i<list.length; i++) {
var tmp = new rolinItem(list[i],state);
if (i == 0) tmp.pShow();
}
}

function rolinItem(obj,state) {
var speed = 0.0666;
var range = 1;
var interval;
var tarH;
var tar = this;
var head = getFirstChild(obj);
var content = getNextChild(head);
var isOpen = false;
this.pHidden = function() {
if (isOpen) hidden();
}
this.pShow = show;

var baseH = content.offsetHeight;
content.style.display = "none";
var isOpen = false;

head.onmouseover = function() {
this.css= "rolinList "

}

head.onmouseout = mouseout;

head.onclick = function() {
this.css= "rolinList "

if (!state.show && !state.hidden) {
if (!isOpen) {
head.onmouseout = null;
show();
} else {
hidden();
}

}
}
function mouseout() {
this.css= "rolinList "
}
function show() {
head.style.borderBottom = "1px solid #DADADA";
state.show = true;
if (state.openObj && state.openObj != tar ) {
state.openObj.pHidden();
}
content.style.height = "0px";
content.style.display = "block";
content.style.overflow = "hidden";
state.openObj = tar;
tarH = baseH;

interval = setInterval(move,10);
}
function showS() {
isOpen = true;
state.show = false;
}

function hidden() {
state.hidden = true;
tarH = 0;
interval = setInterval(move,10);
}

function hiddenS() {
head.style.borderBottom = "none";
head.onmouseout = mouseout;
head.onmouseout();
content.style.display = "none";
isOpen = false;
state.hidden = false;
}

function move() {
var dist = (tarH - content.style.height.pxToNum())*speed;
if (Math.abs(dist) < 1) dist = dist > 0 ? 1: -1;
content.style.height = (content.style.height.pxToNum() + dist) + "px";
if (Math.abs(content.style.height.pxToNum() - tarH) <= range ) {
clearInterval(interval);
content.style.height = tarH + "px";
if (tarH != 0) {
showS()
} else {
hiddenS();
}
}
}

}
var $ = function($) {return document.getElementById($)};
String.prototype.pxToNum = function() {return Number(this.replace("px",""))}
function getFirstChild(obj) {
var result = obj.firstChild;
while (!result.tagName) {
result = result.nextSibling;
}
return result;
}

function getNextChild(obj) {
var result = obj.nextSibling;
while (!result.tagName) {
result = result.nextSibling;
}
return result;
}
