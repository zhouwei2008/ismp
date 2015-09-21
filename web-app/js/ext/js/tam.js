
	// Set the clock's font face:
	var myfont_face = "Verdana";

	// Set the clock's font size (in point):
	var myfont_size = "8";

	// Set the clock's font color:
	var myfont_color = "#111111";
	
	// Set the clock's background color:
	var myback_color = "";

	// Set the text to display before the clock:
	var mypre_text = " ";

	// Set the width of the clock (in pixels):
	var mywidth = 350;

	// Display the time in 24 or 12 hour time?
	// 0 = 24, 1 = 12
	var my12_hour = 1;

	// How often do you want the clock updated?
	// 0 = Never, 1 = Every Second, 2 = Every Minute
	// If you pick 0 or 2, the seconds will not be displayed
	var myupdate = 1;

	// Display the date?
	// 0 = No, 1 = Yes
	var DisplayDate = 1;

  // 工作秒数
  var workTotalSeconds=0;
  var workSeconds=0;
  var workMinute=0;
  var workHour=0;
  var workDay=0;
  
/////////////// END CONFIGURATION /////////////////////////
///////////////////////////////////////////////////////////

// Browser detect code
        var ie4=document.all
        var ns4=document.layers
        var ns6=document.getElementById&&!document.all

// Global varibale definitions:

	var dn = "";
	var mn = "th";
	var old = "";

// The following arrays contain data which is used in the clock's
// date function. Feel free to change values for Days and Months
// if needed (if you wanted abbreviated names for example).
	var DaysOfWeek = new Array(7);
		DaysOfWeek[0] = "星期日";
		DaysOfWeek[1] = "星期一";
		DaysOfWeek[2] = "星期二";
		DaysOfWeek[3] = "星期三";
		DaysOfWeek[4] = "星期四";
		DaysOfWeek[5] = "星期五";
		DaysOfWeek[6] = "星期六 ";

	var MonthsOfYear = new Array(12);
		MonthsOfYear[0] = "1月";
		MonthsOfYear[1] = "2月";
		MonthsOfYear[2] = "3月";
		MonthsOfYear[3] = "4月";
		MonthsOfYear[4] = "5月";
		MonthsOfYear[5] = "6月";
		MonthsOfYear[6] = "7月";
		MonthsOfYear[7] = "8月";
		MonthsOfYear[8] = "9月";
		MonthsOfYear[9] = "10月";
		MonthsOfYear[10] = "11月";
		MonthsOfYear[11] = "12月";

// This array controls how often the clock is updated,
// based on your selection in the configuration.
	var ClockUpdate = new Array(3);
		ClockUpdate[0] = 0;
		ClockUpdate[1] = 1000;
		ClockUpdate[2] = 60000;

// For Version 4+ browsers, write the appropriate HTML to the
// page for the clock, otherwise, attempt to write a static
// date to the page.
	if (ie4||ns6) 
	{ 
		document.write('<span id="LiveClockIE" style="width:'+mywidth+'px; background-color:'+myback_color+'"></span>'); 
	}
	else if (document.layers) 
	{ 
		document.write('<ilayer bgColor="'+myback_color+'" id="ClockPosNS" visibility="hide"><layer width="' + 
		   mywidth + '" id="LiveClockNS"></layer></ilayer>'); 
	}
	else 
	{ 
		 old = "true"; 
		 show_clock(); 
	}

// The main part of the script:
function show_clock() 
{
		if (old == "die") 
		  return;
	
	  //show clock in NS 4
		if (ns4)
       document.ClockPosNS.visibility="show"
	  
	  // Get all our date variables:
		var Digital = new Date();
		var day = Digital.getDay();
		var mday = Digital.getDate();
		var month = Digital.getMonth();
		var hours = Digital.getHours();
		var year = Digital.getFullYear();

		var minutes = Digital.getMinutes();
		var seconds = Digital.getSeconds();
    
    workTotalSeconds ++;
    
    workSeconds ++;
    if ( workSeconds > 59 )
    {
    	 workMinute++;
    	 workSeconds=1;
    }
    
    if ( workMinute > 59 )
    {
    	 workHour++;
    	 workMinute=1;
    }
    
    if ( workHour > 23 )
    {
    	 workDay++;
    	 workHour=0;
    }
    
	// Fix the "mn" variable if needed:
		if (mday == 1) { mn = "st"; }
		else if (mday == 2) { mn = "nd"; }
		else if (mday == 3) { mn = "rd"; }
		else if (mday == 21) { mn = "st"; }
		else if (mday == 22) { mn = "nd"; }
		else if (mday == 23) { mn = "rd"; }
		else if (mday == 31) { mn = "st"; }

		if (minutes <= 9) { minutes = "0"+minutes; }
		if (seconds <= 9) { seconds = "0"+seconds; }

		myclock = '';
		myclock += '<font style="color:'+myfont_color+'; font-family:'+myfont_face+'; font-size:'+myfont_size+'pt;">';
		myclock += mypre_text;
		myclock += hours+':'+minutes;
		if ((myupdate < 2) || (myupdate == 0)) 
		{ 
			myclock += ':'+seconds; 
		}
		myclock += ' '+dn;
		if (DisplayDate) 
		{ 
			  myclock = '<font style="color:' + myfont_color + '; font-family:' + myfont_face + 
			    '; font-size:' + myfont_size + 'pt;">' + year + '年' + 
			    MonthsOfYear[month] + mday + '号 ' + DaysOfWeek[day] + ' ' + myclock; 
		}

		myclock += ' 已在网:';
		
		if ( workDay>0)
		   myclock += workDay + '天';

		if ( workDay>0 || workHour>0)
		   myclock += workHour + '小时';

		if ( workDay>0 || workHour>0 || workMinute>0)
		   myclock += workMinute + '分钟';

     myclock += workSeconds + '秒';
    // myclock += ' 总共'+ workTotalSeconds + '秒';
		   
		myclock += '</font>';

		if (old == "true") {
			document.write(myclock);
			old = "die";
			return;
		}

	// Write the clock to the layer:
		if (ns4) {
			clockpos = document.ClockPosNS;
			liveclock = clockpos.document.LiveClockNS;
			liveclock.document.write(myclock);
			liveclock.document.close();
		} else if (ie4) {
			LiveClockIE.innerHTML = myclock;
		} else if (ns6){
			document.getElementById("LiveClockIE").innerHTML = myclock;
                }            

	if (myupdate != 0) { setTimeout("show_clock()",ClockUpdate[myupdate]); }
}
show_clock();