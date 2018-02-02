package com.wys.ssm.base.util;

import com.ibatis.common.beans.Probe;
import com.ibatis.common.beans.ProbeFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({ "deprecation" })
public class CommonUtil {

	private static final Log log = LogFactory.getLog(CommonUtil.class);

	private static final Probe PROBE = ProbeFactory.getProbe();
	@SuppressWarnings("rawtypes")
	public static final Collection NULL_COLLECTION = new NullCollection();

	public static String getIp() {
		StringBuffer sb = new StringBuffer();
		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();

				sb.append("DisplayName:" + ni.getDisplayName() + ",");
				sb.append("Name:" + ni.getName() + ",");
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					sb.append("IP:" + ips.nextElement().getHostAddress());
				}
				sb.append("\r\n");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return sb.toString();
	}

	public static String moveLastSymbol(String source, String symbol) {
		if (null == source) {
			return null;
		}
		int flag = source.lastIndexOf(symbol);
		if (flag != -1) {
			source = source.substring(0, flag);
		}
		return source;
	}

	public static BufferedImage getHttpImage(String url) {

		URL urlcon = null;
		BufferedImage bi = null;
		try {
			urlcon = new URL(url);
			bi = ImageIO.read(urlcon);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}

	/**
	 * 将字符串按照编码写入文件
	 * 
	 * @param filePath
	 *            ：文件路径
	 * @param content
	 *            :写入的内容
	 * @param charset
	 *            ：文件编码
	 * @return
	 */
	public static boolean writeFile(String filePath, String fileName,
			String content, String charset) {
		FileOutputStream fops = null;
		OutputStreamWriter os = null;
		boolean sign = false;
		try {
			File file = new File(filePath);
			file.mkdirs();
			File newFile = new File(filePath + fileName);
			fops = new FileOutputStream(newFile);
			os = new OutputStreamWriter(fops, charset);
			os.write(content);
			sign = true;
		} catch (Exception e) {
			log.error("::", e);
			sign = false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fops != null) {
					fops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sign;
	}

	/**
	 * 将字符串按照编码写入文件
	 * 
	 * @param filePath
	 *            ：文件路径
	 * @param content
	 *            :写入的内容
	 * @param charset
	 *            ：文件编码
	 * @return
	 */
	public static boolean writeFile(String filePath, String fileName,
			long lastModified, String content, String charset) {
		FileOutputStream fops = null;
		OutputStreamWriter os = null;
		boolean sign = false;
		try {
			File path = new File(filePath);
			path.mkdirs();

			File _file = new File(filePath + fileName);
			fops = new FileOutputStream(_file);
			os = new OutputStreamWriter(fops, charset);
			os.write(content);
			_file.setLastModified(lastModified);
			sign = true;
		} catch (Exception e) {
			log.error("::", e);
			sign = false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fops != null) {
					fops.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sign;
	}

	/**
	 * 读入字符文件内容
	 * 
	 * @param filePath
	 *            ：文件路径
	 * @return
	 */
	public static String readFile(String filePath, String charset) {
		StringBuilder sb = new StringBuilder("");
		FileInputStream fips = null;
		InputStreamReader in = null;
		try {
			fips = new FileInputStream(filePath);
			in = new InputStreamReader(fips, charset);
			char[] buffer = new char[4 * 1024];
			int len = 0;
			while ((len = in.read(buffer)) != -1) {
				sb.append(buffer, 0, len);
			}
			in.close();
			fips.close();
		} catch (Exception e) {
			log.error("::", e);
			return "";
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (fips != null) {
					fips.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 有效邮件正则表达式
	 */
	private static Pattern emailPattern = Pattern
			.compile("[_a-zA-Z0-9.\\-]+@([_a-zA-Z0-9]+\\.)+[a-zA-Z0-9]{2,3}");

	/**
	 * 手机号正则表达式
	 */
	private static Pattern mobilePattern = Pattern
			.compile("(13[0-9]|15[0|1|3|5|6|8|9])\\d{8}");

	/**
	 * 数字政策表达式
	 */
	private static Pattern numberPattern = Pattern
			.compile("[0-9]{1,}[.][0-9]{1,}|[0-9]{1,}");

	private static Pattern positiveNumPattern = Pattern
			.compile("[1-9][0-9]{0,}");

	public static String[] domains = { ".com.cn", ".net.cn", ".cn", ".com",
			".net" /** ,".org",".edu",".mil",".gov" */
	};

	/**
	 * 判断是否有效邮件
	 * 
	 * @param email
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		Matcher m = emailPattern.matcher(email);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否有效手机号
	 * 
	 * @param email
	 */
	public static boolean isValidMobile(String mobile) {

		Matcher m = mobilePattern.matcher(mobile);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 给定字符传是否是数字
	 * 
	 * @return
	 */
	public static boolean isNumber(String targetStr) {
		Matcher m = numberPattern.matcher(targetStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 给定字符串是否正整数
	 * 
	 * @param targetStr
	 * @return
	 */
	public static boolean isPositiveNumber(String targetStr) {
		Matcher m = positiveNumPattern.matcher(targetStr);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 导出xsl文件
	 * 
	 * @param filePath
	 *            ：文件路径（包括名称）
	 * @return
	 */
	public static FileInputStream doExportXslFile(HSSFWorkbook wb,
			String filePath) {

		FileOutputStream fileOut = null;
		FileInputStream fileIn = null;

		try {
			fileOut = new FileOutputStream(filePath);
			wb.write(fileOut);
			fileIn = new FileInputStream(filePath);

			return fileIn;
		} catch (IOException e) {
			log.error("::", e);
			return null;
		} finally {
			if (fileOut != null) {
				try {
					fileOut.close();
				} catch (IOException e) {

				}
				fileOut = null;
			}
		}
	}

	/**
	 * 创建excel表格的列
	 * 
	 * @param row
	 * @param cellNum
	 * @param value
	 */
	public static void createCell(HSSFRow row, int cellNum, String value) {
		if (row != null) {
			row.createCell((short) cellNum).setCellValue(
					new HSSFRichTextString(getNotNullStr(value)));
		}
	}

	/**
	 * 创建Double类型 excel表格的列
	 * 
	 * @param row
	 * @param cellNum
	 * @param value
	 */
	public static void createCostomCell(HSSFRow row, int cellNum, Object value,
			HSSFCellStyle cellStyle) {
		if (row != null) {
			HSSFCell cell = row.createCell((short) cellNum);
			cell.setCellStyle(cellStyle);
			if (value.getClass().equals(Double.class)) {
				cell.setCellValue((Double) value);
				cellStyle
						.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			} else if (value.getClass().equals(Float.class)) {
				cell.setCellValue((Float) value);
				cellStyle
						.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
			} else if (value.getClass().equals(Integer.class)) {
				cell.setCellValue((Integer) value);
				cellStyle
						.setDataFormat(HSSFDataFormat.getBuiltinFormat("###0"));
			} else if (value.getClass().equals(Long.class)) {
				cell.setCellValue((Long) value);
				cellStyle.setDataFormat(HSSFDataFormat
						.getBuiltinFormat("#,##0"));
			} else if (value.getClass().equals(BigDecimal.class)) {
				cell.setCellValue(Double.valueOf(value.toString()));
				cellStyle.setDataFormat(HSSFDataFormat
						.getBuiltinFormat("0.00%"));
			} else {
				cell.setCellValue(new HSSFRichTextString(getNotNullStr(value)));
			}
			cell.setCellStyle(cellStyle);
		}
	}

	/**
	 * 根据单元格的类型，取值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			return getNumericValue(cell);
		case HSSFCell.CELL_TYPE_STRING:
			return getStringValue(cell);
		default:
			return "";
		}
	}

	private static String getNumericValue(HSSFCell cell) {
		return String.valueOf(new Double(cell.getNumericCellValue()));
	}

	private static String getStringValue(HSSFCell cell) {
		return cell.getRichStringCellValue().getString();
	}

	/**
	 * 去掉所有空格
	 * 
	 * @param targetStr
	 */
	public static String ignoreSpaces(String str) {

		StringBuffer sb = new StringBuffer("");
		String[] temp = str.split(" ");
		for (int i = 0; i < temp.length; i++) {
			sb.append(temp[i]);
		}
		return sb.toString();
	}

	/**
	 * 是否空字符串
	 * 
	 * @param targetStr
	 */
	public static boolean isEmpty(String targetStr) {
		if (targetStr == null || "".equals(targetStr.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 去掉字符串前后的空字符，包括全角的
	 * 
	 * @return
	 */
	public static String trim(String str, String Flag) {

		if (str == null || str.equals("")) {
			return str;
		} else {
			str = "" + str;
			if (Flag.equals("l") || Flag.equals("L")) {// 去掉首空格
				String RegularExp = "^[\u00a0|\u0020]+";
				return str.replaceAll(RegularExp, "");
			} else if (Flag.equals("r") || Flag.equals("R")) { // 去掉尾空格
				String RegularExp = "[\u00a0|\u0020]+$";
				return str.replaceAll(RegularExp, "");
			} else { // 去掉首和尾空格
				String RegularExp = "^[\u00a0|\u0020]+|[\u00a0|\u0020]+$";
				return str.replaceAll(RegularExp, "");
			}
		}
	}

	/**
	 * 字符串左侧补零
	 * 
	 * @param srcStr
	 * @param length
	 *            需要
	 * @return
	 */
	public static String leftFillZero(String srcStr, int length) {
		if (StringUtils.isEmpty(srcStr)) {
			return srcStr;
		}
		String result = srcStr;
		for (int i = 0; i < length - srcStr.length(); i++) {
			result = "0" + result;
		}
		return result;
	}

	/**
	 * 精确除法 d1 / d2
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 *            保留小数个数
	 * @return double
	 * @author yuanyuan1
	 */
	public static double divide(double d1, double d2, int scale) {
		double result = 0.0;
		if (d2 != 0) {
			BigDecimal b1 = new BigDecimal(d1);
			BigDecimal b2 = new BigDecimal(d2);

			BigDecimal b3 = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP); // 计算结果

			result = b3.doubleValue();
		}

		return result;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 *            待转日期
	 * @param ds
	 *            日期格式
	 * @return
	 * @author yuanyuan1
	 */
	public static String dateToString(Date date, String ds) {
		String dateStr = null;
		if (date != null && ds != null) {
			SimpleDateFormat sf = new SimpleDateFormat(ds);
			dateStr = sf.format(date);
		}
		return dateStr;
	}

	/**
	 * 字符转日期
	 * 
	 * @param date
	 * @param ds
	 * @return
	 * @author yuanyuan1
	 */
	public static Date stringToDate(String dateStr, String ds) {
		Date date = null;
		SimpleDateFormat sf = new SimpleDateFormat(ds);
		try {
			date = sf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 计算两个Date类型的日期相差的天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @author jinxingshan
	 */
	public static int DateGap(Date beginDate, Date endDate) {
		long time = endDate.getTime() - beginDate.getTime();
		int days = (int) (time / (24 * 60 * 60 * 1000)) + 1;

		return days;
	}

	/**
	 * 计算某个日期所在的月的天数
	 * 
	 * @param countDate
	 * @return
	 * @author jinxingshan
	 */
	public static int countDaysOfMonth(Date countDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(countDate);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		Date beginDate = cal.getTime();
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date endDate = cal.getTime();
		int days = DateGap(beginDate, endDate);
		return days;
	}

	/**
	 * 将从excel里得到的日期数值转换成Date 这个方法用于从excel得到的日期类型是个从1900-1-1号算起到今天的数字
	 * 要转换成java里的Date的情况
	 * 
	 * @param days
	 * @return
	 */
	public static Date excelDateToDate(long days) {
		Calendar cal = Calendar.getInstance();
		cal.set(1900, 0, 1, 0, 0, 0);
		// 因为Excel是从1算起，而且会错误的认为1900为闰年，所以要减2天
		cal.add(Calendar.DAY_OF_YEAR, 40544 - 2);

		return cal.getTime();
	}

	public static String getNotNullStr(Object obj) {

		return (obj != null) ? obj.toString() : "";
	}

	@SuppressWarnings("unchecked")
	public static final <T> Collection<T> nullCollection() {
		return (List<T>) NULL_COLLECTION;
	}

	/**
	 * @author wumc
	 * 
	 *         <pre>
	 * 批量获取map中的值
	 * </pre>
	 *
	 * @param <K>
	 * @param <V>
	 * @param keys
	 * @param source
	 * @return
	 */
	public static <K, V> List<V> getAllFormMap(List<K> keys, Map<K, V> source) {
		List<V> values = Collections.emptyList();
		if (keys != null && !keys.isEmpty() && source != null
				&& !source.isEmpty()) {
			values = new ArrayList<V>();
			for (K k : keys) {
				values.add(source.get(k));
			}
		}
		return values;
	}

	/**
	 * @author wumc
	 * 
	 *         <pre>
	 * 从List中获取valueProp组成一个新的list
	 * </pre>
	 *
	 * @param <E>
	 * @param list
	 * @param valueProp
	 * @return
	 */
	public static <E, K> List<K> getValueList(List<E> list, String valueProp) {
		List<K> valueList = Collections.emptyList();
		if (CollectionUtils.isNotEmpty(list)) {
			list.removeAll(nullCollection());
			valueList = new ArrayList<K>(list.size());
			for (E e : list) {
				@SuppressWarnings("unchecked")
				K value = (K) PROBE.getObject(e, valueProp);
				valueList.add(value);
			}
		}
		return valueList;
	}

	/**
	 * @author wumc
	 * 
	 *         <pre>
	 * 用list生成一个map,keyProp 为指定的key,valueProp 为指定是value
	 * </pre>
	 *
	 * @param <K>
	 * @param <V>
	 * @param <E>
	 * @param list
	 * @param keyProp
	 * @param valueProp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V, E> ConcurrentHashMap<K, V> listforMap(List<E> list, String keyProp,
			String valueProp) {
        ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();
		if (CollectionUtils.isNotEmpty(list)) {
			list.removeAll(nullCollection());
			for (E object : list) {
				K key = (K) PROBE.getObject(object, keyProp);
				Object value = null;
				if (StringUtils.isEmpty(valueProp)) {
					value = object;
				} else {
					value = PROBE.getObject(object, valueProp);
				}
				map.put(key, (V) value);
			}
		}
		return map;
	}

	/**
	 * @author wumc
	 * 
	 *         <pre>
	 * list 生成一个Map<K,List<V>>
	 * </pre>
	 *
	 * @param <K>
	 * @param <V>
	 * @param <E>
	 * @param list
	 * @param keyProp
	 * @param valueProp
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V, E> Map<K, List<V>> listforListMap(List<E> list,
			String keyProp, String valueProp) {
		Map<K, List<V>> map = Collections.emptyMap();
		if (CollectionUtils.isNotEmpty(list)) {
			list.removeAll(nullCollection());
			map = new HashMap<K, List<V>>(list.size());
			V value = null;
			for (E object : list) {
				K key = (K) PROBE.getObject(object, keyProp);
				if (StringUtils.isEmpty(valueProp)) {
					value = (V) object;
				} else {
					value = (V) PROBE.getObject(object, valueProp);
				}
				List<V> values = map.get(key);
				if (values == null) {
					values = new ArrayList<V>();
				}
				values.add(value);
				map.put(key, values);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		String picUrl = "http://xxx.img.com/xxx/xxxxxx.jpg";
		System.out.println(changePicSize(picUrl, 40));
		System.out.println(changePicSize(picUrl, 360));
		System.out.println(changePicSize(picUrl, 450));
	}

	/**
	 * Description ： 根据尺寸改变图片链接<br>
	 * 
	 * wumc
	 * 
	 * @param picUrl
	 *            图片原始网络链接
	 * @param picSize
	 *            图片需要修改的尺寸大小
	 * @return
	 * @since
	 *
	 */
	public static String changePicSize(String picUrl, int picSize) {
		if (picUrl == null) {
			return picUrl;
		}
		StringBuilder builder = new StringBuilder(picUrl);
		int lastIndexOf = builder.lastIndexOf(".");
		if (lastIndexOf < 0) {
			return picUrl;
		}
		return builder.insert(lastIndexOf, "_" + picSize + "x" + picSize)
				.toString();
	}
	
    /**
     * 计算两个Date类型的日期相差的分钟数
     * 
     * @param beginDate
     * @param endDate
     * @return
     * @author wumc
     */
    public static int DateDiffierMin(Date beginDate, Date endDate) {
        long time = endDate.getTime() - beginDate.getTime();
        int hours = (int) (time / (60 * 1000)) + 1;
        
        return hours;
    }
    
    /**
     * @author wumc
     * 
     *         <pre>
     * 用list生成一个map,keyProp 为指定的key,valueProp 为指定是value
     * </pre>
     *
     * @param <K>
     * @param <V>
     * @param <E>
     * @param list
     * @param keyProp
     * @param valueProp
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <K, V, E> Map<K, V> listforMapNotNull(List<E> list, String keyProp, String valueProp) {
        Map<K, V> map = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(list)) {
            list.removeAll(nullCollection());
            map = new HashMap<K, V>(list.size());
            for (E object : list) {
                K key = (K) PROBE.getObject(object, keyProp);
                Object value = null;
                if (StringUtils.isEmpty(valueProp)) {
                    value = object;
                } else {
                    value = PROBE.getObject(object, valueProp);
                }
                if (value != null)
                    map.put(key, (V) value);
            }
        }
        return map;
    }

	public static boolean stringInArray(String str, String...arrs){
		List<String> array = arrs==null||arrs.length<=0?null: Arrays.asList(arrs);
		for(String s : array){
			if(s.equals(str)){
				return true;
			}
		}
		return false;
	}


}
