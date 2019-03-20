package info.niteshjha.config;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatter implements Formatter<Date> {

    final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String print(Date object, Locale locale) {
        return new SimpleDateFormat(defaultDateFormat).format(object);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        return DateUtils.parseDate(text, defaultDateFormat);
    }
}