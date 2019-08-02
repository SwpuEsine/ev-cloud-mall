package com.ev.cloud.db.util;
 
import org.apache.commons.lang.RandomStringUtils;

import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订购业务唯一订单号实现
 * @author ouyangjun
 *
 */
public class IdGeneratorUtils {

	private final static long SEQUENCE_BIT = 12; //序列号占用的位数	//最大4096
	private final String txType;//交易类型
	private String machineId;     //机器标识
	private long sequence = 0L; //序列号
	private long lastStmp = -1L;//上一次时间戳

	private final static long MAX_SEQUENCE = -1L ^ (-1L << SEQUENCE_BIT);

	//默认微信支付
	private static IdGeneratorUtils defaultInstance=new IdGeneratorUtils("WXXX","1");

	/**
	 *  数据中心   机器id
	 * @param
	 * @param machineId
	 */
	private IdGeneratorUtils(String txType, String machineId) {
		if (txType==null||machineId==null){
			throw new InvalidParameterException("参数不能为空");
		}
		if (txType.length()<4){
			throw new InvalidParameterException("参数长度不能小于4");
		}
		if (machineId.length()!=1){
			throw new InvalidParameterException("机器码参数为1位");
		}

		this.txType = txType;
		this.machineId = machineId;
	}


	public static IdGeneratorUtils getDefaultInstance(){
		return defaultInstance;
	}
	/**
	 * 产生下一个ID
	 *
	 * @return
	 */
	public synchronized String nextId() {
		long currStmp = getNewstmp();
		if (currStmp < lastStmp) {
			throw new RuntimeException("出现异常");
		}

		if (currStmp == lastStmp) {
			//相同毫秒内，序列号自增
			sequence = (sequence + 1) & MAX_SEQUENCE;
			//同一毫秒的序列数已经达到最大
			if (sequence == 0L) {
				currStmp = getNextMill();
			}
		} else {
			//不同毫秒内，序列号置为0
			sequence = 0L;
		}

		lastStmp = currStmp;

		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSS");

		Date date=new Date();
		date.setTime(currStmp);

		StringBuilder sb=new StringBuilder(simpleDateFormat.format(date)).append(txType).append(getRandoms()).append(machineId).append(leftPad(sequence,4));

		return sb.toString();
	}



	public String getRandoms(){
		return RandomStringUtils.random(3,true,true);
	}
	/**
	 * 补码
	 * @param i
	 * @param n
	 * @return
	 */
	private String leftPad(long i,int n){
		String s = String.valueOf(i);
		StringBuilder sb=new StringBuilder();
		int c=n-s.length();
		c=c<0?0:c;
		for (int t=0;t<c;t++){
			sb.append("0");
		}
		return sb.append(s).toString();
	}



	private long getNextMill() {
		long mill = getNewstmp();
		while (mill <= lastStmp) {
			mill = getNewstmp();
		}
		return mill;
	}

	private long getNewstmp() {
		return System.currentTimeMillis();
	}

	
}