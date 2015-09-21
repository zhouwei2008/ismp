package com.ecard.products.utils;

import java.io.File;
import java.io.IOException;

import com.ecard.products.constants.Constants;

public class FileUtils {
		
	public static boolean makeNewDir(String dirPath){
		File fPath = new File(dirPath);
		if(fPath.exists()){
			return false; 
		}
		return fPath.mkdir();
	}
	
	public static boolean makeFile(String filePath) throws IOException{
		File fPath = new File(filePath);
		if(fPath.exists()){
			fPath.delete();
		}
		return fPath.createNewFile();
	}
	
	public static boolean renameFile(String filePath, String suffix){
		File fPath = new File(filePath);
		// 目标文件前缀
		String prefix = filePath.indexOf(".") == -1 ? filePath : filePath.substring(0, filePath.indexOf("."));
		// 目标文件后缀
		suffix = suffix == null?"":suffix;
		File dest = new File(prefix + (suffix == null?"":suffix));
		return fPath.renameTo(dest);
	}

    public static boolean removeFile(String fName){
        File f = new File(fName);
        if(f.exists()){
            return f.delete();
        }
        return true;
    }
	
	public static String make2done(String filePath) {
		// TODO Auto-generated method stub

		File srcF = new File(filePath);
        System.out.println("make2done is " + srcF);
		if(!srcF.exists()){
            System.out.println("make2done is not make .");
			return srcF.getAbsolutePath();
		}
		File tarFile = new File(filePath + Constants.FILEState.SUFFIX_DONE);
		srcF.renameTo(tarFile);
        System.out.println("make2done is exec & " + tarFile.getAbsolutePath());
		return tarFile.getAbsolutePath();
	}
	
	public static String make2error(String filePath) {
		// TODO Auto-generated method stub
		File srcF = new File(filePath);
		if(!srcF.exists()){
			return srcF.getAbsolutePath();
		}
		File destF = new File(filePath.replaceFirst(Constants.FILEState.SUFFIX_TEMP, Constants.FILEState.SUFFIX_FAIL));
		if(destF.exists()){
			destF.delete();
		}
		srcF.renameTo(destF);
		return destF.getAbsolutePath();
	}
	
	public static void main(String[] args) {
		String f = "D:\\root\\client\\100000000001_S0020110607_00001.txt";
		File fn = new File(f);
		System.out.println(fn.getAbsolutePath());
	}
	
}
