package com.ecard.products.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	private static final String regex = "^[0-9]{4}_\\d+$";
	
	public static void main(String[] args) {
		String s = "0000000001_S0220090729";
		System.out.println(s);
	}
	
	public static void main1(String[] args) {
		
		logger.info("Test ...");
		File rootFile = new File("D:\\Root");
		File [] dirArrays = rootFile.listFiles();
		List<File> fs = new ArrayList<File>();
		Collections.sort(Arrays.asList(dirArrays), new Comparator<File>() {
			public int compare(File f1, File f2) {
				return f2.getName().compareTo(f1.getName());
			};
		}
		);
		
		for (File dir  : dirArrays) {
			File [] childs = dir.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.getName().matches(regex);
				}
			});
			fs.addAll(Arrays.asList(childs));
		}
		for (File file : fs) {
			System.out.println("F : " + file.getAbsolutePath());
		}
	}
}
