package com.flaginfo.lightning.devtools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 通过设定参数值，生成对应的action dao entity service 对应的java类
 * 修改demo()方法内部的root.put()的value值，运行即可
 * 
 * @author gaoyihang
 *
 */
public class GenerateMapperFile {
	public static void main1(String[] args) {
		String packageName = "com.flaginfo.smile.pms";// 包路径
		String className = "XXX";// 类名称
		String author = "admin";// 作者
		String entityNameCN = "查询可生成采购合同的采购申请明细";// 类描述
		demo(packageName,className,author,entityNameCN);
	}
	public static void demo(String packageName,String className,String author,String entityNameCN) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("packageName", packageName);// 包路径
		root.put("className", className);// 类名称
		root.put("author", author);// 作者
		root.put("entityNameCN", entityNameCN);// 类描述
		root.put("date", new Date().toString());// 创建时间
		String workDir = (String) System.getProperties().get("user.dir");
		try {
			queryMapper(workDir, root);
			queryMapperXml(workDir, root);
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("file generate success!");
	}

	public static void queryMapper(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/mapper/" + input.get("className").toString()+"Mapper" + ".java";
		File myFile = new File(fileName);
		myFile.getParentFile().mkdirs();
		myFile.createNewFile();
		buildFile("templete/queryMapper.ftl", fileName, input);
	}

	public static void queryMapperXml(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/resources/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/mapper/" + input.get("className").toString() + "Mapper.xml";
		File myFile = new File(fileName);
		myFile.getParentFile().mkdirs();
		myFile.createNewFile();
		buildFile("templete/queryMapperXml.ftl", fileName, input);
	}
	/**
	 * 生成文件
	 * 
	 * @param templateName
	 *            模板文件
	 * @param fileName
	 *            生成文件
	 * @param root
	 *            参数
	 */
	private static void buildFile(String templateName, String fileName, Map root) {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setClassForTemplateLoading(GenerateMapperFile.class, "/");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		Template template;
		try {
			template = freemarkerCfg.getTemplate(templateName);
			template.setEncoding("UTF-8");
			File htmlFile = new File(fileName);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			template.process(root, out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
