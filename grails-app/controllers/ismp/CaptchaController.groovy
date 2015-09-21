package ismp

import java.awt.image.BufferedImage
import nl.captcha.Captcha
import nl.captcha.backgrounds.*
import nl.captcha.servlet.CaptchaServletUtil
import nl.captcha.text.producer.TextProducer
import java.awt.Color

class CaptchaController {
    def final WIDTH = 120
    def final HEIGHT = 50

    def index = {
        def val = "";
        Random random = new Random();
        for(i in 1..4){
            def charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
//                def choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (65 + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }

        def captcha = new Captcha.Builder(WIDTH, HEIGHT)
        .addText({
           return val
        } as TextProducer)
        .addNoise()
        .build()

        session.captcha = captcha
//        CaptchaServletUtil.writeImage(response, captcha.image)
        CaptchaServletUtil.writeImage(response, Image.createImage(WIDTH, HEIGHT, val))
    }

    //远程验证验证码
    def imageCaptcha = {
        if (session.captcha?.isCorrect(params.captcha.toUpperCase())) {
            render("true")
        } else {
            render("false")
        }
    }
}
