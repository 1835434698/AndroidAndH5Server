package com.tang.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamOperator {
	/** 读取缓冲区大小. */
	private static final int READBUF_SIZE = 1024;

	/**
	 * 获取输入流的字节数组.
	 *
	 * @param is
	 *            待读取的输入流
	 * @return 输入流的字节数组
	 * @throws MSIOException
	 *             输入输出错误.
	 */
	public static byte[] getInputStreamBytes(InputStream is) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		copy(is, baos);
		close(baos);
		return baos.toByteArray();
	}
	
	/**
	 * 将输入流复制到输出流.
	 *
	 * @param is
	 *            待复制的输入流
	 * @param os
	 *            目标输出流
	 * @throws MSIOException
	 *             输入输出错误.
	 */
	public static void copy(InputStream is, OutputStream os) throws Exception{
		byte[] buf = new byte[READBUF_SIZE];
		int size;
		try{
			while ((size = is.read(buf)) != -1){
				os.write(buf, 0, size);
			}
		} catch (IOException e){
			
		}
	}

	/**
	 * 关闭流.
	 *
	 * @param stream
	 *            要关闭的流
	 */
	public static void close(Closeable stream){
		if (stream == null)
			return;
		try{
			stream.close();
		} catch (IOException e){
			// Nothing to do
		}
	}

}
