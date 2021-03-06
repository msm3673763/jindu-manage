package com.ucsmy.jindu.manage.config.serialNumber.getter;



import com.ucsmy.jindu.manage.config.serialNumber.util.SerialNumberMode;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * 默认序列号生成器，序列号的编号部分无序
 * Created by chenqilin on 2017/5/19.
 */
public class SerialNumberDefaultGenerator extends AbstractSerialNumberGenerator {

    public SerialNumberDefaultGenerator(String prefix, String dataFormat, String decimalFormat) {
        super(prefix, dataFormat, decimalFormat);
    }

    @Override
    public String getMode() {
        return SerialNumberMode.DEFAULT.toString();
    }

    @Override
    public String get(String prefix) {
        Date now = new Date();
        DecimalFormat decimalFormat = super.getDecimalFormat();
        String randomNumber = super.getNumberByUUID(decimalFormat.toPattern().length() - 1);
        return generateNumber(prefix, now, Long.parseLong(randomNumber));
    }

    @Override
    public String getAutoIncrement(String prefix, int length, int maxLength, long delta) {
        // 乱序模式没有自增，所以返回指定长度的UUID.hasCode
        return getNumberByUUID(length);
    }
}
