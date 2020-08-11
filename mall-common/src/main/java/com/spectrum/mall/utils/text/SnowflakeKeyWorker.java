package com.spectrum.mall.utils.text;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnowflakeKeyWorker {
    private static final Logger log = LoggerFactory.getLogger(SnowflakeKeyWorker.class);
    private static final long TWEPOCH = 12888349746579L;
    private static final long WORKERIDBITS = 5L;
    private static final long DATACENTERIDBITS = 5L;
    private static final long SEQUENCEBITS = 12L;
    private static final long WORKERIDSHIFT = 12L;
    private static final long DATACENTERIDSHIFT = 17L;
    private static final long TIMESTAMPLEFTSHIFT = 22L;
    private static final long SEQUENCEMASK = 4095L;
    private static long lastTimestamp = -1L;
    private long sequence = 0L;
    private long workerId = 1L;
    private static long workerMask = 31L;
    private long processId = 1L;
    private static long processMask = 31L;
    private static SnowflakeKeyWorker keyWorker = null;

    public static synchronized String nextId() {
        return String.valueOf(keyWorker.getNextId());
    }

    public static synchronized long nextLongId() {
        return keyWorker.getNextId();
    }

    private SnowflakeKeyWorker() {
        this.workerId = this.getMachineNum();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        log.info("00==" + runtimeMXBean.getName().split("@")[0]);
        this.processId = Long.valueOf(runtimeMXBean.getName().split("@")[0]);
        this.workerId &= workerMask;
        this.processId &= processMask;
        log.info(this.workerId + "===" + this.processId);
    }

    private synchronized long getNextId() {
        long timestamp = this.timeGen();
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception var4) {
                log.error("时间戳小于上次时间戳", var4);
            }
        }

        if (lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 4095L;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }

        lastTimestamp = timestamp;
        return timestamp - 12888349746579L << 22 | this.processId << 17 | this.workerId << 12 | this.sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = this.timeGen(); timestamp <= lastTimestamp; timestamp = this.timeGen()) {
        }

        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    private long getMachineNum() {
        StringBuilder sb = new StringBuilder();
        Enumeration e = null;

        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException var6) {
            log.error("获取机器编码异常", var6);
        }

        while(e.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface)e.nextElement();
            sb.append(ni.toString());
        }

        long machinePiece = (long)sb.toString().hashCode();
        return machinePiece;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10000; ++i) {
            System.out.println(nextId());

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var3) {
                log.error("exception", var3);
            }
        }

    }

    private static String long2BinaryStr(Long l) {
        return StringUtils.leftPad(Long.toBinaryString(l), 64, '0');
    }

    static {
        keyWorker = new SnowflakeKeyWorker();
    }
}
