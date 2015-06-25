package com.cmb.surveypark.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.google.common.base.Throwables;
import com.google.common.hash.Hashing;

public class DataUtil {

	public static String md5(String src) {
		return Hashing.md5().hashBytes(src.getBytes()).toString();
	}
	
	public static Serializable deeplyCopy(Serializable src) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(src);
			oos.close();
			bos.close();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			Serializable copy = (Serializable) ois.readObject();
			ois.close();
			bis.close();
			
			return copy;
		} catch (Exception e) {
			Throwables.propagate(e);
		}
		return null;
	} 
	
	
	public static void main(String[] args) {
		System.out.println(md5("123456"));
	}
	
}
