package com.italia.marxmind.bris.reader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeGenerator {
	
	public static void main(String[] args) throws WriterException, IOException{
		
		String qrCodeText = "As an example, if you were to create a QR Code that was only to contain numerical data, then you would have the ability to encode up to 7089 digits." + 
				"If you were to create a QR Code that would store alphanumeric data, then the maximum number of characters you could store would reduce to 4296. " + 
				"There are other types of QR Code that are less common but that you should be aware of, and these each have their own limitations. For example Micro QR Codes can store a lot less data (up to 35 numerical characters). iQR Codes on the other hand have much greater capacity (up to 40,000 numerical characters)." +
				"This table details the maximum amount";
		String filePath = "C:\\bls\\img\\qrcode\\qrcode.png";
		int size = 200;
		String fileType = "png";
		File qrFile = new File(filePath);
		createQRImage(qrFile, qrCodeText, size, fileType);
		System.out.println("DONE");
		
	}
	
	@SuppressWarnings("unchecked")
	private static void createQRImage(File qrFile, String qrCodeText, int size, String fileType)
			throws WriterException, IOException {
		
		Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
		int matrixWidth = byteMatrix.getWidth();
		BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
		image.createGraphics();
		
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, matrixWidth, matrixWidth);
		graphics.setColor(Color.BLUE);
		
		for (int i = 0; i < matrixWidth; i++) {
			for (int j = 0; j < matrixWidth; j++) {
				if (byteMatrix.get(i, j)) {
					graphics.fillRect(i, j, 1, 1);
				}
			}
		}
		int y = size/2;
		graphics.setColor(Color.WHITE);
		//graphics.drawString(qrCodeText, 5, y);
		ImageIO.write(image, fileType, qrFile);
	
	}
	
}
