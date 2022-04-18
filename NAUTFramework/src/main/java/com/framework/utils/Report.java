package com.framework.utils;

import org.apache.logging.log4j.Logger;

public class Report {
	private static Logger log = LoggerHelper.getLogger(Report.class);

	public synchronized static void printOperation(String operation) {
		log.debug("\tThread-ID:" + Thread.currentThread().getId() + "Opeartion\t:\t" + operation);
	}

	public synchronized static void printKey(String key) {
		log.debug("\tThread-ID:" + Thread.currentThread().getId() + "Key\t:\t" + key);
	}

	public synchronized static void printValue(String value) {
		log.debug("\tThread-ID:" + Thread.currentThread().getId() + "Value\t:\t" + value);
	}

	public synchronized static void printValueType(String valueType) {
		log.debug("\tThread-ID:" + Thread.currentThread().getId() + "ValueType\t:\t" + valueType);
	}

	public synchronized static void printData(String data) {
		log.debug("\tThread-ID:" + Thread.currentThread().getId() + "Data\t:\t" + data);
	}

	public synchronized static void printStatus(String status) {
		log.debug("\tThread-ID:" + Thread.currentThread().getId() + "Status\t:\t" + status + "\n");
	}
}
