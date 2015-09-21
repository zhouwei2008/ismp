function decodeURL(str){

  var s0, i, j, s, ss, u, n, f;

  s0 = "";           // decoded str

  for (i = 0; i < str.length; i++){   // scan the source str

    s = str.charAt(i);

    if (s == "+"){s0 += " ";}     // "+" should be changed to SP

    else {

        if (s != "%"){s0 += s;}   // add an unescaped char

        else{           // escape sequence decoding

          u = 0;       // unicode of the character

          f = 1;       // escape flag, zero means end of this sequence

          while (true) {

            ss = "";   // local str to parse as int

                for (j = 0; j < 2; j++ ) { // get two maximum hex characters to parse

                  sss = str.charAt(++i);

                  if (((sss >= "0") && (sss <= "9")) || ((sss >= "a") && (sss <= "f")) || ((sss >= "A") && (sss <= "F"))) {

                    ss += sss;       // if hex, add the hex character

                  } else {--i; break;}   // not a hex char., exit the loop

                }

            n = parseInt(ss, 16);       // parse the hex str as byte

            if (n <= 0x7f){u = n; f = 1;}   // single byte format

            if ((n >= 0xc0) && (n <= 0xdf)){u = n & 0x1f; f = 2;}   // double byte format

            if ((n >= 0xe0) && (n <= 0xef)){u = n & 0x0f; f = 3;}   // triple byte format

            if ((n >= 0xf0) && (n <= 0xf7)){u = n & 0x07; f = 4;}   // quaternary byte format (extended)

            if ((n >= 0x80) && (n <= 0xbf)){u = (u << 6) + (n & 0x3f); --f;}   // not a first, shift and add 6 lower bits

            if (f <= 1){break;}         // end of the utf byte sequence

            if (str.charAt(i + 1) == "%"){ i++ ;}             // test for the next shift byte

            else {break;}             // abnormal, format error

          }

        s0 += String.fromCharCode(u);       // add the escaped character

        }

    }

  }

  return s0;

}


function encodeURL(str){

  var s0, i, s, u;

  s0 = "";           // encoded str

  for (i = 0; i < str.length; i++){   // scan the source

    s = str.charAt(i);

    u = str.charCodeAt(i);       // get unicode of the char

    if (s == " "){s0 += "+";}     // SP should be converted to "+"

    else {

        if ( u == 0x2a || u == 0x2d || u == 0x2e || u == 0x5f || ((u >= 0x30) && (u <= 0x39)) || ((u >= 0x41) && (u <= 0x5a)) || ((u >= 0x61) && (u <= 0x7a))){   // check for escape

          s0 = s0 + s;       // don't escape

        }

        else {               // escape

          if ((u >= 0x0) && (u <= 0x7f)){   // single byte format

            s = "0"+u.toString(16);

            s0 += "%"+ s.substr(s.length-2);

          }

          else if (u > 0x1fffff){   // quaternary byte format (extended)

            s0 += "%" + (oxf0 + ((u & 0x1c0000) >> 18)).toString(16);

            s0 += "%" + (0x80 + ((u & 0x3f000) >> 12)).toString(16);

            s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);

            s0 += "%" + (0x80 + (u & 0x3f)).toString(16);

          }

          else if (u > 0x7ff){     // triple byte format

            s0 += "%" + (0xe0 + ((u & 0xf000) >> 12)).toString(16);

            s0 += "%" + (0x80 + ((u & 0xfc0) >> 6)).toString(16);

            s0 += "%" + (0x80 + (u & 0x3f)).toString(16);

          }

          else {               // double byte format

            s0 += "%" + (0xc0 + ((u & 0x7c0) >> 6)).toString(16);

            s0 += "%" + (0x80 + (u & 0x3f)).toString(16);

          }

        }

    }

  }

  return s0;

}