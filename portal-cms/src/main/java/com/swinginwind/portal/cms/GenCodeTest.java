package com.swinginwind.portal.cms;

import java.io.IOException;

import com.swinginwind.portal.cms.entity.Article;
import com.swinginwind.portal.cms.entity.ColumnInfo;
import com.swinginwind.portal.common.util.GenCodeUtil;

public class GenCodeTest {
	
	public static void main(String[] args) throws IOException{
		//基本包目录（不用到entity那一层级）
		String s = "com.jeff.tianti.cms";
		//作者
		String writer = "xujianfang";
		//Demo为Entity类（放上自己新增的实体类即可）
		GenCodeTest.autoGenAllCode(ColumnInfo.class,s,writer);
		GenCodeTest.autoGenAllCode(Article.class,s,writer);
	}
	
	/**
	 * 组装所有生成类
	 * @param c
	 * @param commonPackage
	 * @param writer
	 * @throws IOException
	 */
	public static void autoGenAllCode(Class c,String commonPackage,String writer) throws IOException{
		GenCodeUtil.createQueryDTO(c, commonPackage, writer);
//		GenCodeUtil.createFrontQueryDTO(c, commonPackage, writer);
		GenCodeUtil.createDaoCustomInterface(c,commonPackage,writer);
		GenCodeUtil.createDaoInterface(c,commonPackage,writer);
		GenCodeUtil.createDaoClass(c,commonPackage,writer);
		GenCodeUtil.createServiceClass(c,commonPackage,writer);
	}

}
