

package io.renren.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.util.Assert;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * 日期处理
 * 
 * @author Mark sunlightcs@gmail.com
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @return  返回yyyy-MM-dd格式日期
     */
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 日期格式化 日期格式为：yyyy-MM-dd
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回yyyy-MM-dd格式日期
     */
    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }

    /**
     * 日期解析
     * @param date  日期
     * @param pattern  格式，如：DateUtils.DATE_TIME_PATTERN
     * @return  返回Date
     */
    public static Date parse(String date, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转换成日期
     * @param strDate 日期字符串
     * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
     */
    public static Date stringToDate(String strDate, String pattern) {
        if (StringUtils.isBlank(strDate)){
            return null;
        }

        DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
        return fmt.parseLocalDateTime(strDate).toDate();
    }

    /**
     * 根据周数，获取开始日期、结束日期
     * @param week  周期  0本周，-1上周，-2上上周，1下周，2下下周
     * @return  返回date[0]开始日期、date[1]结束日期
     */
    public static Date[] getWeekStartAndEnd(int week) {
        DateTime dateTime = new DateTime();
        LocalDate date = new LocalDate(dateTime.plusWeeks(week));

        date = date.dayOfWeek().withMinimumValue();
        Date beginDate = date.toDate();
        Date endDate = date.plusDays(6).toDate();
        return new Date[]{beginDate, endDate};
    }

    /**
     * 对日期的【秒】进行加/减
     *
     * @param date 日期
     * @param seconds 秒数，负数为减
     * @return 加/减几秒后的日期
     */
    public static Date addDateSeconds(Date date, int seconds) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusSeconds(seconds).toDate();
    }

    /**
     * 对日期的【分钟】进行加/减
     *
     * @param date 日期
     * @param minutes 分钟数，负数为减
     * @return 加/减几分钟后的日期
     */
    public static Date addDateMinutes(Date date, int minutes) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMinutes(minutes).toDate();
    }

    /**
     * 对日期的【小时】进行加/减
     *
     * @param date 日期
     * @param hours 小时数，负数为减
     * @return 加/减几小时后的日期
     */
    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    /**
     * 对日期的【天】进行加/减
     *
     * @param date 日期
     * @param days 天数，负数为减
     * @return 加/减几天后的日期
     */
    public static Date addDateDays(Date date, int days) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(days).toDate();
    }

    /**
     * 对日期的【周】进行加/减
     *
     * @param date 日期
     * @param weeks 周数，负数为减
     * @return 加/减几周后的日期
     */
    public static Date addDateWeeks(Date date, int weeks) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(weeks).toDate();
    }

    /**
     * 对日期的【月】进行加/减
     *
     * @param date 日期
     * @param months 月数，负数为减
     * @return 加/减几月后的日期
     */
    public static Date addDateMonths(Date date, int months) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusMonths(months).toDate();
    }

    /**
     * 对日期的【年】进行加/减
     *
     * @param date 日期
     * @param years 年数，负数为减
     * @return 加/减几年后的日期
     */
    public static Date addDateYears(Date date, int years) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusYears(years).toDate();
    }
    /**
     * 判断2个日期的间隔年数
     */
    public static int differYears(Date date1, Date date2) {
        return (int) (Math.abs(date2.getTime() - date1.getTime()) / 1000 / (3600 * 24 * 365));
    }


    /**
     * 判断2个日期的间隔天数（非自然日）
     */
    public static int differDays(Date date1, Date date2) {
        return (int) (Math.abs(date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 判断2个时间的间隔秒数
     */
    public static int differSeconds(Date date1, Date date2) {
        return (int) Math.ceil((date2.getTime() - date1.getTime()) / 1000f);
    }

    /**
     * 判断2个时间的间隔分钟数
     */
    public static int differMinutes(Date date1, Date date2) {
        return (int) Math.ceil((date2.getTime() - date1.getTime()) / 1000f / 60f);
    }

    /**
     * 判断2个日期的间隔的天数（自然日）
     */
    public static int differNaturalDays(Date date1, Date date2) {
        return (int) (Math.abs(getZeroPointDate(date2).getTime() - getZeroPointDate(date1).getTime()) / (1000 * 3600 * 24));
    }





    /**
     * 根据日期获取年份
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 根据日期获取日期字符串
     */
    public static String getDateStr(String regx, Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat(regx);
        return format.format(date);
    }

    /**
     * 判断当前小时是否在[startHour, endHour]之间 闭区间，24小时制
     */
    public static boolean isHourBetween(int startHour, int endHour) {
        return isHourBetween(new Date(),startHour,endHour);
    }

    /**
     * 判断当前小时是否在[startHour, endHour]之间 闭区间，24小时制
     */
    public static boolean isHourBetween(Date date,int startHour, int endHour) {
        Assert.isTrue(startHour < endHour, "startHour must less than endHour!");
        Assert.isTrue(startHour >= 0 && startHour <= 23, "startHour must in [0,23]!");
        Assert.isTrue(endHour <= 23, "endHour must in [0,23]!");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int nowHour = c.get(Calendar.HOUR_OF_DAY);
        return nowHour >= startHour && nowHour <= endHour;
    }

    /**
     * 获取当前日期本周的第几天
     */
    public static int getDayNumOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int day = c.get(Calendar.DAY_OF_WEEK) - 1;
        //西方周日为一周的第一天，所以当取得的天数为1时是星期天
        if (day == 0) {
            day = 7;
        }
        return day;
    }

    /**
     * 判断两个日期是否在同一周
     * “2004-12-25”是星期六，也就是说它是2004年中第52周的星期六，
     * 那么“2004-12-26”到底是2004年的第几周哪，java中经测试取得的它的Week值是1，
     * 那么也就是说它被看作2005年的第一周了，这个处理是比较好的。
     * 可以用来判断“2004-12-26”和“2005-1-1”是同一周。
     */
    public static boolean isSameWeek(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setFirstDayOfWeek(Calendar.MONDAY);//西方周日为一周的第一天，咱得将周一设为一周第一天
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.setTime(d1);
        cal2.setTime(d2);
        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if (subYear == 0)// subYear==0,说明是同一年
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == 1 && cal2.get(Calendar.MONTH) == 11) //subYear==1,说明cal比cal2大一年;java的一月用"0"标识，那么12月用"11"
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        } else if (subYear == -1 && cal1.get(Calendar.MONTH) == 11)//subYear==-1,说明cal比cal2小一年
        {
            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
                return true;
        }
        return false;
    }

    /**
     * 由出生日期获得年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAgeByBirth(Date birthDay) throws IllegalArgumentException {
        Calendar c = Calendar.getInstance();
        if (c.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is after Now.It's unbelievable!");
        }
        int yearNow = c.get(Calendar.YEAR);
        int monthNow = c.get(Calendar.MONTH);
        int dayOfMonthNow = c.get(Calendar.DAY_OF_MONTH);
        c.setTime(birthDay);

        int yearBirth = c.get(Calendar.YEAR);
        int monthBirth = c.get(Calendar.MONTH);
        int dayOfMonthBirth = c.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 根据年龄反推生日
     */
    public static Date getBirthByAge(Integer age) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, -age);
        return c.getTime();
    }
    /**
     * 获取当前日期
     */
    public static String getNow() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
        String nowStr = dateFormat.format(now);
        return nowStr;
    }

    /**
     * 判断两个日期是否为同一天
     */
    public static boolean isSameDay(Date time1, Date time2) {
        Calendar date1 = Calendar.getInstance();
        date1.setTime(time1);
        Calendar date2 = Calendar.getInstance();
        date2.setTime(time2);

        boolean isSameYear = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 判断两个时间是否为同一小时
     */
    public static boolean isSameHour(Date time1, Date time2) {
        Calendar date1 = Calendar.getInstance();
        date1.setTime(time1);
        Calendar date2 = Calendar.getInstance();
        date2.setTime(time2);

        boolean isSameYear = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
        boolean isSameHour = isSameDate && date1.get(Calendar.HOUR_OF_DAY) == date2.get(Calendar.HOUR_OF_DAY);

        return isSameHour;
    }

    /**
     * 判断指定日期是否是今天
     */
    public static boolean isToday(Date time) {
        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();
        date2.setTime(time);

        boolean isSameYear = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 获取当天的下一天
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);// +1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当天的下一年
     */
    public static Date getNextYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, +1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取当天的前一天
     */
    public static Date getPrevDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);// +1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取指定时间的下一个小时
     */
    public static Date getNextHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, +1);// +1 今天的时间加一小时
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取指定时间的上一个小时
     */
    public static Date getPrevHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);// 后退一个小时
        date = calendar.getTime();
        return date;
    }


    /**
     * 获取当前时间的整点小时
     */
    public static Date getNowWholeHour() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取今天与当前同时刻的整点日期
     */
    public static Date getJTDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当前系统时间的指定某小时的整点日期
     */
    public static Date getJTDateByHour(int hour) {
        return getJTDateByHour(new Date(), hour);
    }

    /**
     * 获取指定日期当天的指定某小时的整点日期
     */
    public static Date getJTDateByHour(Date dateTime, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取昨天与当前同时刻的整点日期
     */
    public static Date getZTDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当前时间的昨天指定某小时的整点日期
     */
    public static Date getZTDateByHour(int hour) {
        return getZTDateByHour(new Date(), hour);
    }

    /**
     * 获取指定日期的昨天指定某小时的整点日期
     */
    public static Date getZTDateByHour(Date dateTime, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取前天与当前同时刻的整点日期
     */
    public static Date getQTDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -2);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    /**
     * 获取当前时间前天指定某小时的整点日期
     */
    public static Date getQTDateByHour(int hour) {
        return getQTDateByHour(new Date(), hour);
    }

    /**
     * 获取指定时间的前天指定某小时的整点日期
     */
    public static Date getQTDateByHour(Date dateTime, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.add(Calendar.DAY_OF_MONTH, -2);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的上一个小时
     */
    public static Date getPrevWholeHour() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }


    /**
     * 根据字符串获取对应的Date对象
     *
     * @throws ParseException
     */
    public static Date getDateByStr(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取指定日期的指定小时整点
     * hour:0-23
     */
    public static Date getWholeHourDate(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当天的日期不带时分秒
     *
     * @throws ParseException
     */
    public static Date getToday() {
        return getZeroPointDate(new Date());
    }

    /**
     * 获取当前小时(24小时制)
     */
    public static int getNowHour() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期的零点时刻
     */
    public static Date getZeroPointDate(Date date) {
        return getWholeHourDate(date, 0);
    }

//	public static void main(String[] args) throws ParseException {
////		Calendar c = Calendar.getInstance();
////		c.set(Calendar.HOUR_OF_DAY,0);
////		c.add(Calendar.HOUR_OF_DAY, -1);
////		c.set(Calendar.MINUTE, 0);
////		c.set(Calendar.SECOND, 0);
////		c.set(Calendar.MILLISECOND, 0);
////		System.out.println(getDateStr("yyyy-MM-dd HH:mm:ss", c.getTime()));
//		//System.out.println(getNowHour());
//	}

    /**
     * 获取明天凌晨的时间点
     */
    public static Date getMTZeroPointDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取今天天凌晨的时间点
     */
    public static Date getJTZeroPointDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取昨天凌晨的时间点
     */
    public static Date getZTZeroPointDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getZeroPointDate(Date dateTime, int addDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateTime);
        c.add(Calendar.DAY_OF_MONTH, addDay);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期的昨天凌晨的时间点
     */
    public static Date getZTZeroPointDate(Date dateTime) {
        if (dateTime == null) {
            return null;
        }
        return getZeroPointDate(dateTime, -1);
    }

    /**
     * 获取最近的时间字符串
     *
     * @param d
     * @return
     */
    public static String getFriendlytime(Date d) {
        long delta = (new Date().getTime() - d.getTime()) / 1000;
        if (delta / (60 * 60 * 24 * 365) > 0) return delta / (60 * 60 * 24 * 365) + "年前";
        if (delta / (60 * 60 * 24 * 30) > 0) return delta / (60 * 60 * 24 * 30) + "个月前";
        if (delta / (60 * 60 * 24 * 7) > 0) return delta / (60 * 60 * 24 * 7) + "周前";
        if (delta / (60 * 60 * 24) > 0) return delta / (60 * 60 * 24) + "天前";
        if (delta / (60 * 60) > 0) return delta / (60 * 60) + "小时前";
        if (delta / (60) > 0) return delta / (60) + "分钟前";
        return "刚刚";
    }

    /**
     * 获取指定日期的今天凌晨的时间点
     */
    public static Date getJTZeroPointDate(Date dateTime) {
        return getZeroPointDate(dateTime, 0);
    }

    /**
     * 获取指定日期的明天凌晨的时间点
     */
    public static Date getMTZeroPointDate(Date dateTime) {
        return getZeroPointDate(dateTime, 1);
    }

    /**
     * 获取当前时间的上一整点小时
     */
    public static Date getPrevHourByNow() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, -1);// 后退一个小时
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当前整点小时
     */
    public static Date getNowHourByNow() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定年月日小时的时间
     */
    public static Date getDateByYearAndMonAndDayAndHour(int year, int month, int day, int hour) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期前addDay天的hour整点小时的日期
     */
    public static Date getBeforeDate(Date date, int addDay, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, addDay);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期 前addYear年 前addDay天 前addHour小时
     */
    public static Date getRangeDate(Date date, int addYear, int addDay, int addHour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, addYear);
        c.add(Calendar.DAY_OF_MONTH, addDay);
        c.add(Calendar.HOUR_OF_DAY, addHour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期的当前整点小时时刻
     */
    public static Date getTimelyHour(Date date) {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定日期范围内的日期集合
     */
    public static List<Date> getDateListInclude(Date from, Date to) {
        List<Date> dates = new ArrayList<>();
        if (from != null && to != null && (from.getTime() <= to.getTime())) {
            Date nextday = from;
            int num = 0;
            while (nextday.getTime() <= to.getTime()) {
                num++;
                dates.add(nextday);
                nextday = getNextDay(nextday);
                if (num >= 60) {
                    break;
                }
            }
        }
        return dates;
    }

    /**
     * 获取指定日期范围内的日期集合(前闭后开)
     */
    public static List<Date> getDateListNotEqTo(Date from, Date to) {
        List<Date> dates = new ArrayList<>();
        if (from != null && to != null && (from.getTime() < to.getTime())) {
            Date nextday = from;
            int num = 0;
            while (nextday.getTime() < to.getTime()) {
                num++;
                dates.add(nextday);
                nextday = getNextDay(nextday);
                if (num >= 60) {
                    break;
                }
            }
        }
        return dates;
    }

    /**
     * 获取指定日期的指定小时整点的时间
     */
    public static Date getDateByHour(Date date, int hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 将指定日期按10分钟分割成数组
     */
    public static List<Date> splitDayBy10min(Date date) {
        List<Date> dates = new ArrayList<>();
        if (date != null) {
            Date next = getNext10Min(date);
            while (next.getTime() <= getNextDay(date).getTime()) {
                dates.add(next);
                next = getNext10Min(next);
            }
        }
        return dates;
    }

    /**
     * 获取指定日期的下10分钟的时刻
     */
    public static Date getNext10Min(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 10);// 增加10分钟
        date = calendar.getTime();
        return date;
    }


    public static void main(String[] args) throws ParseException {
//		System.out.println(getDateStr("yyyy-MM-dd HH:mm:ss",DateUtil.getDateByYearAndMonAndDayAndHour(2016,10,11,2)));
//		System.out.println(getDateStr("yyyy-MM-dd HH:mm:ss",DateUtil.getBeforeDate(new Date(),-12,1)));
//		System.out.println(DateUtil.getZeroPointDate(new Date(), -2));
//		System.out.println(DateUtil.getZeroPointDate(new Date(), -1));
//		System.out.println(getTimelyHour(new Date()));
//		System.out.println(splitDayBy10min(getJTZeroPointDate()));
//        System.out.println(getDistanceTime("2019-11-7 10:29:00","2019-11-8 10:29:00"));
        System.out.println(isBelongPeriodTime("08:00","13:00"));

    }

    /**
     * 获取昨天的日期
     */
    public static Date getYesterday(Date date) {
        return getBeforeDate(date, -1, 0);
    }

    /**
     * 获取指定日期的小时（24小时制）
     */
    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定日期的天数
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期的月份
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static Date getAfterYear(Date date, int addYear) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, addYear);// +1今天的时间加一天
        date = c.getTime();
        return date;
    }

    public static Date getThisMonthDay(int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getAfterDayAndHour(Date date, int addDay, int addHour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, addDay);
        c.add(Calendar.HOUR_OF_DAY, addHour);
        return c.getTime();
    }

    public static Date getAfterDay(Date date, int addDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, addDay);
        return c.getTime();
    }

    public static Date getAfterHour(Date date, int addHour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY, addHour);
        return c.getTime();
    }

    public static Date getAfterMin(Date date, int addMin) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, addMin);
        return c.getTime();
    }

    public static Date getAfterSecond(Date date, int addSecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, addSecond);
        return c.getTime();
    }

    /**
     * 格式化发布时间
     *
     * @param d
     * @return
     */
    public static String formatNewTime(Date d) {
        if (d == null) {
            return "";
        }
        //和当前时间的差值
        long delta = (new Date().getTime() - d.getTime()) / 1000;
        int diffDay = (int) (delta / (60 * 60 * 24));
        if (diffDay > 0) {
            diffDay = diffDay > 7 ? 7 : diffDay;
            return diffDay + "天前";
        }
        int diffHour = (int) (delta / (60 * 60));
        if (diffHour > 0) {
            diffHour = diffHour > 23 ? 23 : diffHour;
            return diffHour + "小时前";
        }
        int diffMin = (int) (delta / 60);
        if (diffMin > 0) {
            diffMin = diffMin > 59 ? 59 : diffMin;
            return diffMin + "分钟前";
        }
        return "刚刚";
    }

    public static long minuteBetween(Date date1, Date date2) {
        try {
            long start = date1.getTime() / 1000;
            long end = date2.getTime() / 1000;
            return Math.abs((end - start) / 60);
        } catch (Exception e) {
            return 0;
        }

    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    public static Date minusDays(int days){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTimeToDate(localDateTime.minusDays(days));
    }

    public static Date minusDays(Date date, int days){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusDays(days));
    }

    public static Date plusDays(Date date, int days){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusDays(days));
    }

    public static Date plusDays(int days){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTimeToDate(localDateTime.plusDays(days));
    }

    public static Date minusHours(int hours){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTimeToDate(localDateTime.minusHours(hours));
    }

    public static Date minusHours(Date date, int hours){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.minusHours(hours));
    }

    public static Date plusHours(Date date, int hours){
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        return localDateTimeToDate(localDateTime.plusHours(hours));
    }

    public static Date plusHours(int hours){
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTimeToDate(localDateTime.plusHours(hours));
    }


    //审核时间
    public static boolean inReviewTime(){
        LocalDateTime now = LocalDateTime.now();
        int dayOfWeek = now.getDayOfWeek().getValue();
        int hour = now.getHour();
        return dayOfWeek >=1 && dayOfWeek <=5 && (hour >= 8 && hour < 21);
    }
    /**
     * 两个时间相差距离多少小时
     * @param one 时间参数 1    开始时间
     * @param two 时间参数 2     结束时间
     */
    public static Long getDistanceTime(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        return day*24 + hour;
    }
    /**
     * 两个时间相差距离多少分钟
     * @param one 时间参数 1    开始时间
     * @param two  时间参数 2     结束时间
     */
    public static Long getDistanceMin(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        return day*24*60 + hour*60+min;
    }
    /**
     * 两个时间相差距离多少秒
     * @param one 时间参数 1    开始时间
     * @param two  时间参数 2     结束时间
     */
    public static Long getDistanceSec(Date one, Date two) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff ;
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        return day*24*60*60 + hour*60*60+min*60+sec;
    }
    /**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
    public static boolean isNow(String date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        return date.equals(nowDay);
    }
    /**
     * 格式化时间为Date
     */
    public static Date formateDate(String date) throws ParseException {
        //当前时间
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取今天的日期
        Date parse = formatter.parse(date);
        return parse;
    }
    /**
     * 格式化时间为Date
     */
    public static String formateDatetoString(Date date) throws ParseException {
        //当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取今天的日期
        String format = formatter.format(date);
        return format;
    }
    /**
     * 获取指定日期的年月日
     */
    public static String getDatetoDate(Date date) throws ParseException {
        //当前时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String format = formatter.format(date);
        return format;
    }
    /**
     * 获取指定多少秒以后的时间
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
    /**
     * 在日期date上增加amount天 。
     *
     * @param date   处理的日期，非null
     * @param amount 要加的天数(正数)/要减去的天数(负数)
     */
    public static java.time.LocalDate  dealDays(Date date, int amount) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + amount);
        java.time.LocalDate localDate = now.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    /**
     * 获取今天
     * @return
     */
    public static String getToday_STR() {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        return nowDay;
    }
    /**
     * 判断当前时间距离第二天是否大于4小时 如果大于4小时 则返回4小时的秒数
     * 否则返回到第二天0点的秒数
     * @return
     */
    public static int getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Long l = (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        return l.intValue();
    }

    public static boolean isBeforeNow(Date oldTime) {
        Date nowdate=new Date();
        boolean flag = oldTime.before(nowdate);
        return flag;
    }
    public static boolean isBeforeNowA(Date createTime,Date now) {
        boolean flag = createTime.before(now);
        return flag;
    }
    public static boolean greatThan(Date date, Date other){
        return date.getTime() > other.getTime();
    }

    public static Date utcToLocal(String utcTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate;
    }

    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date localDate= null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis=localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar= Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate=new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    /**
     * 判断两个日期间是否超过的年数
     * @param time1
     * @param time2
     * @param numYear
     * @return
     */
    public static Boolean DateCompare(Date time1,Date time2,int numYear) {
        Date time3 = add(time1, Calendar.YEAR,numYear);
        if(time3.getTime()<time2.getTime()){
            return true;
        }
        return false;
    }
    /**
     * 时间加减
     * @param date
     * @param calendarField ：Calendar.YEAR/ Calendar.MONTH /Calendar.DAY
     * @param amount
     * @return
     */
    public static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            return null;
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    /*
     *  判断当前时间是否在设置的dark mode时间段内
     *  @param date1: 开始时间（hh:mm）
     *  @param date2: 结束时间（hh:mm）
     */
    public static boolean isBelongPeriodTime(String date1, String date2){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date(System.currentTimeMillis());
        Date startTimeDate;
        Date endTimeDate;
        Calendar date = Calendar.getInstance();
        Calendar begin = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        try{
            date.setTime(df.parse(df.format(currentTime)));
            startTimeDate = df.parse(date1);
            endTimeDate = df.parse(date2);
            begin.setTime(startTimeDate);
            end.setTime(endTimeDate);
            if (endTimeDate.getHours() < startTimeDate.getHours()) {
                if (date.after(begin) || date.before(end)) {

                    return true;
                }else {
                    return false;
                }
            }else if(endTimeDate.getHours() == startTimeDate.getHours()){
                if (endTimeDate.getMinutes() < startTimeDate.getMinutes()) {
                    if (date.after(begin) || date.before(end)) {
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }catch(ParseException e){
            e.printStackTrace();
        }
        //这里是时间段的起止都在同一天的情况，只需要判断当前时间是否在这个时间段内即可
        if (date.after(begin) && date.before(end)) {
            return true;
        }else {
            return false;
        }
    }
}
