var action;

function pageJS(page)
{
    document.getElementById("page").value = page;
 
	var offset = document.getElementById("offset");
	var max = parseInt(document.getElementById("max").value);
	offset.value=(page-1)*max;
    search(false);
}

function pagePlusJS(pages)
{
    var page = document.getElementById("page").value;
    
    if(isNaN(page) || page < 1 || page > pages || page.indexOf(".") == 1)
    {
        flag = false;
        
        alert("page error");
    }
    else
    {
		var offset = document.getElementById("offset");
		var max = parseInt(document.getElementById("max").value);
		offset.value=(pages-1)*max;
        search(false);
    }
}


function turnpage(totalpages)
{
	var txt_page=document.getElementById('page');
    var v_page=txt_page.value;
	if(!/^\d+$/.test(v_page)||v_page<1||v_page>totalpages)
	{
		alert('错误的页码！');
		txt_page.focus();
	}else{
		var pageIndex = v_page>0?v_page:1;
		pagePlusJS(pageIndex);
	}
}