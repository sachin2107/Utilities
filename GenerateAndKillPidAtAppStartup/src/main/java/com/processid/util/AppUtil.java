package com.processid.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Collection;

public class AppUtil {

	private static String serverName;

	private static String pid;

	private static String pidFilePath;

	private static String serviceWithPID;

	static {
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		String[] name = runtimeMXBean.getName().split("@");
		pid = name[0];
		serverName = name[1];

		String processDirectory = ""; // System.getenv("PROCESS_DIR");
		String serviceName = ""; // System.getenv("SERVICE_NAME");
		String serviceType = "na"; // System.getenv("SERVICE_TYPE");
		if (isNotEmpty(processDirectory) && isNotEmpty(serviceName)) {
			pidFilePath = processDirectory + "/" + serviceName + "-pid.txt";
			serviceWithPID = serviceName + ":" + pid;
		}

	}

	public static boolean isEmpty(Object obj) {
		return !isNotEmpty(obj);
	}

	public static boolean isNotEmpty(Object obj) {
		boolean flag = true;
		if (null == obj) {
			flag = false;
		} else {
			if (obj instanceof String) {
				String objString = (String) obj;
				if (objString.trim().length() == 0) {
					flag = false;
				}
			}
			if (obj instanceof Collection<?>) {
				Collection<?> objList = (Collection<?>) obj;
				if (objList.isEmpty()) {
					flag = false;
				}
			}
		}
		return flag;
	}

	public static String getServerName() {
		return serverName;
	}

	public static void setServerName(String serverName) {
		AppUtil.serverName = serverName;
	}

	public static String getPid() {
		return pid;
	}

	public static void setPid(String pid) {
		AppUtil.pid = pid;
	}

	public static String getPidFilePath() {
		return pidFilePath;
	}

	public static void setPidFilePath(String pidFilePath) {
		AppUtil.pidFilePath = pidFilePath;
	}

	public static String getServiceWithPID() {
		return serviceWithPID;
	}

	public static void setServiceWithPID(String serviceWithPID) {
		AppUtil.serviceWithPID = serviceWithPID;
	}

}
