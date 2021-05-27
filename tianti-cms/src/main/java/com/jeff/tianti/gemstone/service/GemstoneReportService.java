package com.jeff.tianti.gemstone.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.jeff.tianti.common.config.AppConfig;
import com.jeff.tianti.common.service.CommonService;
import com.jeff.tianti.gemstone.dao.GemstoneReportDao;
import com.jeff.tianti.gemstone.entity.GemstoneReport;
import com.jeff.tianti.gemstone.entity.GemstoneReportImage;

@Service
public class GemstoneReportService extends CommonService<GemstoneReport, String> {
	
	@Autowired
	private AppConfig appConfig;

	@Autowired
	public void setDao(GemstoneReportDao dao) {
		super.setCommonDao(dao);
	}

	public void generateFile(String reportId) {
		GemstoneReport report = this.get(reportId);
		if (report != null) {
			// 模板路径
			String templatePath = "D:/template1.pdf";
			// 生成的新文件路径
			String newPDFPath = "D:/export.pdf";

			PdfReader reader;
			FileOutputStream out;
			ByteArrayOutputStream bos;
			PdfStamper stamper;
			try {
				// BaseFont bf =
				// BaseFont.createFont("c://windows//fonts//simsun.ttc,1" ,
				// BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				// Font FontChinese = new Font(bf, 5, Font.NORMAL);
				out = new FileOutputStream(newPDFPath);// 输出流
				reader = new PdfReader(templatePath);// 读取pdf模板
				bos = new ByteArrayOutputStream();
				stamper = new PdfStamper(reader, bos);
				AcroFields form = stamper.getAcroFields();
				form.setField("no", report.getNo());
				form.setField("reportDate", report.getReportDate().toString());
				form.setField("object", report.getObject());
				form.setField("identification", report.getIdentification());
				form.setField("weight", report.getWeight() + " " + "cts");
				form.setField("cut", report.getCut());
				form.setField("shape", report.getShape());
				form.setField("measurements", report.getDimensionsLength() + "x" + report.getDimensionsWidth() + "x"
						+ report.getDimensionsHeight() + " mm");
				form.setField("color", report.getColor());
				form.setField("origin", report.getOrigin());
				form.setField("comments", report.getComments());
				form.setField("barcode_no", report.getNo());
				if(report.getImages() != null && report.getImages().size() > 0) {
					for(GemstoneReportImage image : report.getImages()) {
						String filePath = appConfig.getFileDir() + "/" + image.getFilePath();
						if(new File(filePath).exists()) {
							int pageNo = form.getFieldPositions("images").get(0).page;
							Rectangle signRect = form.getFieldPositions("images").get(0).position;
							float x = signRect.getLeft();
							float y = signRect.getBottom();
							// 根据路径读取图片
							Image image1 = Image.getInstance(filePath);
							// 获取图片页面
							PdfContentByte under = stamper.getOverContent(pageNo);
							// 图片大小自适应
							image1.scaleToFit(signRect.getWidth(), signRect.getHeight());
							// 添加图片
							image1.setAbsolutePosition(x, y);
							under.addImage(image1);
						}
						else {
							System.out.println("image not exists");
						}
					}
				}
				
				int pageNo = form.getFieldPositions("qrcode").get(0).page;
				Rectangle signRect = form.getFieldPositions("qrcode").get(0).position;
				float x = signRect.getLeft();
				float y = signRect.getBottom();
				
				String content = "http://www.baidu.com";//二维码内容
		        String filePath = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID().toString() + ".png";
		        File tempFile = new File(filePath);
		        this.createQRCode(content, (int) signRect.getWidth(), (int) signRect.getHeight(), BarcodeFormat.QR_CODE, tempFile);
		        // 根据路径读取图片
				Image image1 = Image.getInstance(filePath);
				// 获取图片页面
				PdfContentByte under = stamper.getOverContent(pageNo);
				// 图片大小自适应
				image1.scaleToFit(signRect.getWidth(), signRect.getHeight());
				// 添加图片
				image1.setAbsolutePosition(x, y);
				under.addImage(image1);
				tempFile.delete();
				
				int pageNo1 = form.getFieldPositions("barcode").get(0).page;
				Rectangle signRect1 = form.getFieldPositions("barcode").get(0).position;
				float x1 = signRect1.getLeft();
				float y1 = signRect1.getBottom();
		        String filePath1 = System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID().toString() + ".png";
		        File tempFile1 = new File(filePath1);
		        this.createQRCode(report.getNo(), (int) signRect1.getWidth(), (int) signRect1.getHeight(), BarcodeFormat.CODE_128, tempFile1);
		        // 根据路径读取图片
				Image image2 = Image.getInstance(filePath1);
				// 获取图片页面
				PdfContentByte under1 = stamper.getOverContent(pageNo1);
				// 图片大小自适应
				image2.scaleToFit(signRect1.getWidth(), signRect1.getHeight());
				// 添加图片
				image2.setAbsolutePosition(x1, y1);
				under1.addImage(image2);
				tempFile1.delete();
				
				stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
				stamper.close();
				Document doc = new Document();
				PdfCopy copy = new PdfCopy(doc, out);
				doc.open();
				PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
				copy.addPage(importPage);
				doc.close();
				reader.close();
				out.close();
				

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void createQRCode(String content, int width, int height, BarcodeFormat format, File file) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        //内容编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置二维码边的空度，非负数
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, width, height, hints);
        MatrixToImageWriter.writeToPath(bitMatrix, "png", file.toPath());// 输出原图片
	}
	
}