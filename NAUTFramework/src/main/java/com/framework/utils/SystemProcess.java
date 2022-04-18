package com.framework.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemProcess {
	public static synchronized void killProcess(String processName) throws IOException {
		Process p = Runtime.getRuntime().exec("tasklist");
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;

		while ((line = reader.readLine()) != null) {
			if (line.contains(processName)) {
				Runtime.getRuntime().exec("taskkill /F /IM" + processName);
			}
		}
	}
}
