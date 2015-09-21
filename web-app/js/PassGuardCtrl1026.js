function _$(v){
	return document.getElementById(v);
}
//获得控件输入框,如果没有安装提示用户下载
//ocx_id 控件ID号(必须设置)
//ocx_edittype 控件框模式.0:星号显示;1:明文显示(必须设置)
//ocx_tabindex tab键顺序(可选)
//ocx_onkeydown Enter事件响应函数(可选)
//ocx_class 控件框样式(可选)
function IntPassGuardCtrl(ocx_id,ocx_edittype,ocx_tabindex,ocx_onkeydown,ocx_class){
	if((navigator.platform =="Win32") || (navigator.platform =="Windows")){
		if(navigator.userAgent.indexOf("MSIE")>0){//IE控件
			document.write('<span id="'+ocx_id+'_pgc" style="display:none;">');
			var ocx_str='<OBJECT id="'+ocx_id+'"  classid="CLSID:3A2C8BC3-5B68-4AE5-81D6-6DC378708F3E" border="1" onkeydown="if(13==event.keyCode || 27==event.keyCode)'+ocx_onkeydown+';"  codebase="../ocx/PassGuardCtrl.cab#version=1,0,1,2"';
			if(ocx_tabindex!="") ocx_str+=' tabindex="'+ocx_tabindex+'"';
			if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
			else ocx_str+=' width="200" height="18"';
			ocx_str+='>';
			if(ocx_edittype!="") ocx_str+='<param name="edittype" value="'+ocx_edittype+'">';
			ocx_str+='</OBJECT>';
			document.write(ocx_str);
			document.write('</span>');
			if(GetPassGuardCtrlStatusIE()){
				_$(ocx_id+'_pgc').style.display="block";
			}else{
				document.write('<div id="'+ocx_id+'_down" class="'+ocx_class+' down" style="text-align:center;"><a href="../ocx/PassGuardSetupIE.msi">请点此安装控件</a></div>');
			 }		
		}else{//非IE控件
			document.write('<span id="'+ocx_id+'_pgc" >');
			var ocx_str='<embed  id="'+ocx_id+'" edittype="'+ocx_edittype+'" type="application/x-pass-guard"';	
			if(ocx_tabindex!="") ocx_str+=' tabindex="'+ocx_tabindex+'"';
			if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
			else ocx_str+=' width="200" height="18"';
			ocx_str+='>';
			document.write(ocx_str);			
			document.write('</span>');		
			if(!GetPassGuardCtrlStatusFF(ocx_id,"")){
				_$(ocx_id+'_pgc').style.display="none";
				document.write('<div id="'+ocx_id+'_down" class="'+ocx_class+' down" style="text-align:center;"><a href="../ocx/PassGuardSetupFF.msi">请点此安装控件</a></div>');
			}
		}
	}else if((navigator.platform=="Win64")){
		/*
		document.write('<span id="'+ocx_id+'_pgc" style="display:none;">');
		var ocx_str='<OBJECT id="'+ocx_id+'"  classid="CLSID:206F48A0-61BB-48C8-B54C-7700B7923CFD" border="1" onkeydown="if(13==event.keyCode || 27==event.keyCode)'+ocx_onkeydown+';"  codebase="./ocx/PassGuardX64.cab#version=1,0,0,1"';
		if(ocx_tabindex!="") ocx_str+=' tabindex="'+ocx_tabindex+'"';
		if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
		else ocx_str+=' width="200" height="18"';
		ocx_str+='>';
		if(ocx_edittype!="") ocx_str+='<param name="edittype" value="'+ocx_edittype+'">';
		ocx_str+='</OBJECT>';
		document.write(ocx_str);
		document.write('</span>');
		if(GetPassGuardCtrlStatusIE()){
			_$(ocx_id+'_pgc').style.display="block";
		}else{
			document.write('<div id="'+ocx_id+'_down" class="'+ocx_class+' down" style="text-align:center;"><a href="./ocx/PassGuardSetupIE64.msi">请点此安装控件</a></div>');   
		}
		*/
	}else if(navigator.userAgent.indexOf("Linux")>0){
		document.write('<span id="'+ocx_id+'_pgc" >');
		var ocx_str='<embed  id="'+ocx_id+'" edittype="'+ocx_edittype+'" type="application/x-pass-guard"';	
		if(ocx_tabindex!="") ocx_str+=' tabindex="'+ocx_tabindex+'"';
		if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
		else ocx_str+=' width="200" height="18"';
		ocx_str+='>';
		document.write(ocx_str);			
		document.write('</span>');		
		if(!GetPassGuardCtrlStatusFF(ocx_id,"")){
			_$(ocx_id+'_pgc').style.display="none";
			document.write('<div id="'+ocx_id+'_down" class="'+ocx_class+' down" style="text-align:center;"><a href="../ocx/PassGuardSetupFF.msi">请点此安装控件</a></div>');
		}		
	}else if(navigator.userAgent.indexOf("Macintosh")>0){ 
		document.write('<span id="'+ocx_id+'_pgc" >');
		var ocx_str='<embed  id="'+ocx_id+'" type="application/microdone-passguard-plugin" version="1.0.0.2"';	
		if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
		else ocx_str+=' width="200" height="18"';
		ocx_str+=' >';
		document.write(ocx_str);
		if(ocx_edittype!=""){
			var ocx_obj=_$(ocx_id);//控件ID号
			ocx_obj.input0=Number(ocx_edittype);
		}
		document.write('</span>');
		//alert("hh");
		var osocx=GetPassGuardCtrlStatusMacOS(ocx_id);
		
		if(osocx==0){
			_$(ocx_id+'_pgc').style.display="none";
			document.write('<div id="'+ocx_id+'_down" class="'+ocx_class+' down" style="text-align:center;"><a href="../ocx/PassGuardCtrl.dmg">请点此安装控件</a></div>');
		  
		}
		else if(osocx == 2){//update
          _$(ocx_id+'_pgc').style.display="none";
			document.write('<div id="'+ocx_id+'_down" class="'+ocx_class+' down" style="text-align:center;"><a href="../ocx/PassGuardCtrl.dmg">请点此升级控件</a></div>');
		  
		}
	}
}
//设置控件参数,如果没有安装提示用户下载
//ocx_id 控件ID号(必须设置)
//ocx_maxlength //控件框允许输入的最大字符数(可选)
//input1 //设置控件随机因子(可选)
//input2 //判断输入过程中的字符是否满足要求,正则表达式(可选)
//input3 //判断输入完成后字符是否满足要求,正则表达式(可选)
//ocx_return 非IE浏览器Enter键回调函数(可选)
function SetPassGuardCtrl(ocx_id,ocx_maxlength,input1,input2,input3){
	var ocx_obj=_$(ocx_id);//控件ID号	
	ShowPassGuardCtrl(ocx_id);
	if(GetPassGuardCtrlStatus(ocx_id,input1)){	
		if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){
		    if(ocx_maxlength!="") ocx_obj.maxlength=ocx_maxlength;
			if(navigator.userAgent.indexOf("MSIE")>0){//IE
				if(input1!="") ocx_obj.input1=input1;
				if(input2!="") ocx_obj.input2=input2;
				if(input3!="") ocx_obj.input3=input3;
		    }else{//非IE	
				ocx_obj.package = 0;
				if(input1!="") ocx_obj.input(1, input1);
				if(input2!="") ocx_obj.input(2, input2);
				if(input3!="") ocx_obj.input(3, input3);
				//if(ocx_return!="") ocx_obj.returnCallback = ocx_return;//回车键回调函数 			 	
			}
		}else if(navigator.userAgent.indexOf("Linux")>0){ 
			ocx_obj.package = 0;
		    ocx_obj.input(1, input1);
			if(input2!="") ocx_obj.input(2, input2);
			if(input3!="") ocx_obj.input(3, input3);			
		}else if(navigator.userAgent.indexOf("Macintosh")>0){ 
			ocx_obj.input1=input1;
		    if(input2!="") ocx_obj.input2=input2;
			if(input3!="") ocx_obj.input3=input3;
	        if(ocx_maxlength!="") ocx_obj.input4=ocx_maxlength;	
		}
	}
}

//获得控件输入框,如果没有安装提示用户下载
//ocx_id 控件ID号(必须设置)
//ocx_edittype 控件框模式.0:星号显示;1:明文显示(必须设置)
//ocx_tabindex tab键顺序(可选)
//ocx_onkeydown Enter事件响应函数(可选)
//ocx_class 控件框样式(可选)
function IntPassGuardCtrlAjax(ocx_id,ocx_edittype,ocx_tabindex,ocx_onkeydown,ocx_class){
	if((navigator.platform =="Win32") || (navigator.platform =="Windows")){
		if(navigator.userAgent.indexOf("MSIE")>0){//IE控件
			var ocx_str='<OBJECT id="'+ocx_id+'"  classid="CLSID:3A2C8BC3-5B68-4AE5-81D6-6DC378708F3E" border="1" onkeydown="if(13==event.keyCode || 27==event.keyCode)'+ocx_onkeydown+';"  codebase="../ocx/PassGuardCtrl.cab#version=1,0,1,2"';
			if(ocx_tabindex!="") ocx_str+=' tabindex="'+ocx_tabindex+'"';
			if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
			else ocx_str+=' width="200" height="18"';
			ocx_str+='>';
			if(ocx_edittype!="") ocx_str+='<param name="edittype" value="'+ocx_edittype+'">';
			ocx_str+='</OBJECT>';
			return ocx_str;
		}else{//非IE控件
			var ocx_str='<embed  id="'+ocx_id+'" edittype="'+ocx_edittype+'" type="application/x-pass-guard"';	
			if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
			else ocx_str+=' width="200" height="18"';
			if(ocx_tabindex!="") ocx_str+=' tabindex="'+ocx_tabindex+'"';
			ocx_str+='>';
			return ocx_str;
		}
	}else if(navigator.userAgent.indexOf("Macintosh")>0){ 
		var ocx_str='<embed  id="'+ocx_id+'" type="application/microdone-passguard-plugin" version="1.0.0.2"';	
		if(ocx_class!="") ocx_str+=' class="'+ocx_class+'"';
		else ocx_str+=' width="200" height="18"';
		ocx_str+=' >';
		return ocx_str;
	}
}
//判断IE控件是否安装，已安装返回true，未安装返回false
function GetPassGuardCtrlStatusIE(){
   try{
	  if((navigator.platform=="Win32") || (navigator.platform=="Windows")){	   
          var comActiveX = new ActiveXObject("PassGuardCtrl.PassGuard.1"); 
	  }else if((navigator.platform=="Win64")){ 
		  var comActiveX = new ActiveXObject("PassGuardX64.PassGuard.1");  
	  }      
   }catch(e){
	   //return true;
	   return false;
   }
   return true;
}
//判断非IE控件是否安装，已安装返回true，未安装返回false
//ocx_id 控件ID
//input1 随机数
function GetPassGuardCtrlStatusFF(ocx_id,input1){
	try{
 	  _$(ocx_id).input(1,input1);
 	 //var ocx_version=navigator.plugins['PassGuard'].version;
 	}catch(e){
 	   return  false;
	}
		return true;
}
//判断mac_os控件是否安装，0,未安装；1，已安装；2，需要升级
function GetPassGuardCtrlStatusMacOS(ocx_id){
    var ret = 0;//0,未安装；1，已安装；2，需要升级
	try
	{
 	    var comActiveX = window.navigator.plugins['PassGuard 1G'];
 	    var newversion=document.getElementById(ocx_id).getAttribute("version");
 	    
 	    if(comActiveX !=undefined)
 	    {    
 	     	var plugindescription = navigator.plugins["PassGuard 1G"].description;
 	    	 var curversion=plugindescription.substring(plugindescription.indexOf("version:") + 8, plugindescription.length);
 	     	if(newversion != undefined
 	     		&&curversion != undefined
 	     		&&plugindescription.indexOf("version:") != -1)
 	     	{
 	     	 		if(newversion > curversion )
 	     	 		{
 	        			ret = 2
 	     			}
 	     			else
 	     			{
 	        			ret = 1;
 	     			}
 	     	}
 	     	else if(newversion == undefined)
 	     	{
 	     		//alert("has no new version ");
 	     		ret = 1;
 	     	}
 	     	else if(plugindescription.indexOf("version:") == -1)
 	     	{
 	     		//alert("has new version");
 	     		ret = 2;
 	     	}
 	     	else
 	     	{
 	        	ret = 1;
 	     	}
 	    }
 	    
 	}catch(e)
 	{
 	   //return  false;
	}
	  
	 return ret;//comActiveX;
}

//判断用户控件安装情况，引导用户安装
function ShowPassGuardCtrl(ocx_id){
	if(navigator.userAgent.indexOf("MSIE")>0){//IE
		if(GetPassGuardCtrlStatusIE()){
			try{
				_$(ocx_id+'_pgc').style.display="block";
				_$(ocx_id+'_down').style.display="none";
			}catch(e){
		    }
			
		}
	}
}
//判断控件是否安装，已安装返回true，未安装返回false
//参数ocx_id(控件ID),input1(32位随机数)
function GetPassGuardCtrlStatus(ocx_id,input1){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){
	   if(navigator.userAgent.indexOf("MSIE")>0){//IE
		  return GetPassGuardCtrlStatusIE();
	   }else{//非IE
		  return GetPassGuardCtrlStatusFF(ocx_id,input1);
	   }
	}else if(navigator.userAgent.indexOf("Macintosh")>0){ 
	   if(GetPassGuardCtrlStatusMacOS(ocx_id)==1) return true;
	   else return false;
	}
}

//非IE焦点切换回调函数
function callbackff(tab_id){
	_$(tab_id).focus();
}
//获得加密后的密文
function GetPassGuardCtrlKeyCode(ocx_id){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){//windows
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			return _$(ocx_id).output1;
		}else{//非IE
			if(_$(ocx_id).output(3)>0) return _$(ocx_id).output(7);
		}
	}else if(navigator.userAgent.indexOf("Macintosh")>0){
		return _$(ocx_id).get_output1();
	}
}

//获得输入长度
function GetPassGuardCtrlLength(ocx_id){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){//windows
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			return _$(ocx_id).output3;
		}else{//非IE
			return _$(ocx_id).output(3);
		}
	}else if(navigator.userAgent.indexOf("Macintosh")>0){//mac
		return _$(ocx_id).get_output3();
	}
}

//获得输入字符哈希值
function GetPassGuardCtrlHash(ocx_id){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){//windows
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			return _$(ocx_id).output2;
		}else{//非IE
			if(_$(ocx_id).output(3)>0) return _$(ocx_id).output(2);
		}
	}else if(navigator.userAgent.indexOf("Macintosh")>0){//mac
		if(_$(ocx_id).get_output3()>0) return _$(ocx_id).get_output2();
	}	
}

//判读输入字符是否满足要求
function GetPassGuardCtrlValid(ocx_id){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){//windows
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			if(GetPassGuardCtrlKeyCode(ocx_id)) return _$(ocx_id).output5;
		}else{//非IE
		    try{
			     if(_$(ocx_id).output(3)>0){
			    	  return _$(ocx_id).output(5);
			     }
		     }catch(e){
		          return  0;
		    }
		}
	}else if(navigator.userAgent.indexOf("Macintosh")>0){//mac
	    try{
    	  var ocx_id_tmp =_$(ocx_id).get_output1();
    	  return _$(ocx_id).get_output5();
	     }catch(e){
	          return  0;
	    }
	}
}

//判读输入字符强度
function GetPassGuardCtrlStrength(ocx_id){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){//windows
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			var l=_$(ocx_id).output3;
			var n=_$(ocx_id).output4;
		}else{//非IE
			var l=_$(ocx_id).output(3);
			var n=_$(ocx_id).output(4);
		}
	}else if(navigator.userAgent.indexOf("Macintosh")>0){//mac
		var l=_$(ocx_id).get_output3();
		var n=_$(ocx_id).get_output4();
	} 
	//弱：全数字、或全字母
	if(n==1 || l<6){
		return 0;
	}
	//中：字母和数字组合，且大于等于6位
	if(n==2 && l>=6){
		return 1;
	}	
	//强：字母、数字、特殊符号组合，且大于等于8位
	if(n==3 && l>=8){
		return 2;
	}
	//中：其他情况
	return 1;	
}

//清除控件框内容
function PassGuardCtrlClear(ocx_id){
	_$(ocx_id).ClearSeCtrl();
}
//网卡信息
function GetPassGuardCtrlNetwork(ocx_id){
	if((navigator.platform=="Win32") || (navigator.platform=="Windows")){
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
		    return _$(ocx_id).GetIPMacList();
			//return _$(ocx_id).GetNicPhAddr(0);
		}else{//非IE
			if(_$(ocx_id).output(3)>0) return _$(ocx_id).output(9);
		}
	}else{
		return "";
	}		
}
//硬盘序列号
function GetPassGuardCtrlDisk(ocx_id){
	if((navigator.platform=="Win32") || (navigator.platform=="Windows")){	
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			return _$(ocx_id).GetNicPhAddr(1);
		}else{//非IE
			if(_$(ocx_id).output(3)>0) return _$(ocx_id).output(11);
		}
	}else{
		return "";
	}	
}
//CPU列号
function GetPassGuardCtrlCpu(ocx_id){
	if((navigator.platform=="Win32") || (navigator.platform=="Windows")){	
		if(navigator.userAgent.indexOf("MSIE")>0){//IE
			return _$(ocx_id).GetNicPhAddr(2);
		}else{//非IE
			if(_$(ocx_id).output(3)>0) return _$(ocx_id).output(10);
		}
	}else{
		return "";
	}	
}
//tab键切换
  function notifycallback(arg)
{
//   var strtext = 'function key value is ' + arg + ' \n';

   if(arg == 0x09)
   {
    //alert(arg);
    //var f = swfobject.getObjectById('embed1'); 
    callbackff();
   // document.getElementById('_ocx_password').focus(); 
   }
   else if(arg == 0x0d)
   {
  		mySubmit();
   }
   
}
//获得控件版本
function GetVersion(ocx_id){
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){
	  if(navigator.userAgent.indexOf("MSIE")>0){//IE
		  var ocx_version="";
	  }else{
		  var ocx_version=navigator.plugins['PassGuard'].version;
	  }
	}else if(navigator.userAgent.indexOf("Macintosh")>0){ 
	  var ocx_version=document.getElementById(ocx_id).getAttribute("version");
	}
	return ocx_version;
}
//重新设置随机因子
function ResetPassGuardCtrl(ocx_id,input1){
	var ocx_obj=_$(ocx_id);//控件ID号 
	if((navigator.platform =="Win32") || (navigator.platform =="Win64") || (navigator.platform =="Windows")){//windows
	     if(navigator.userAgent.indexOf("MSIE")>0){//IE
	       try{
	         ocx_obj.input1=input1;   
	       }catch(e){}
	    }else{//非IE
	       //try{
	          ocx_obj.input(1, input1);   
	       //}catch(e){}                                     
	     }
	}else if(navigator.userAgent.indexOf("Macintosh")>0){//mac
		ocx_obj.input1=input1;
	}
}
