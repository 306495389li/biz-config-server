package com.flaginfo.lightning.devtools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
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
public class GenerateJavaFile {
    
	public static void main1(String[] args) {
		String moduleName = "auth"; //模块名
		String packageName = "com.flaginfo.lightning.auth";// 包路径
		String tableName = "ad_user";
		
		
		String className =  GenerateJavaFile.toUpperCamelString(tableName);//"";// 类名称
		String springName = GenerateJavaFile.toLowercamelString(tableName);//"";//  类名称的首字母小写
		String entityNameCN = "用户";// 类描述
		String author = "admin";// 作者
		demo(moduleName,packageName,className,springName,author,tableName,entityNameCN);
	}
	public static void demo(String moduleName,String packageName,String className,String springName,String author,String tableName,String entityNameCN) {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("moduleName", moduleName);// 包路径
		root.put("packageName", packageName);// 包路径
		root.put("className", className);// 类名称CM_CHANNEL
		root.put("springName", springName);// 类名称的首字母小写
		root.put("author", author);// 作者
		root.put("tableName", tableName);// 表名称
		root.put("entityNameCN", entityNameCN);// 类描述
		root.put("date", new Date().toString());// 创建时间
		root.put("contextPath", "${pageContext.request.contextPath}");// 创建时间
		String workDir = (String) System.getProperties().get("user.dir");
		try {
			entity(workDir, root,tableName);
			dao(workDir, root);
			service(workDir, root);
			serviceImpl(workDir, root);
			controller(workDir, root);
			
//			webController("/Users/yangqing/Documents/workspace2/web/smile-auth-web",root);
//			listPage("/Users/yangqing/Documents/workspace2/web/smile-auth-web",root,tableName); 
//			editPage("/Users/yangqing/Documents/workspace2/web/smile-auth-web",root,tableName); 
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("file generate success!");
	}

	public static void listPage(String workDir, Map<String, Object> input,String tableName)
			throws Exception {
		String fileName = workDir + "/src/main/webapp/WEB-INF/views/"
				+ input.get("packageName").toString().substring(13).replaceAll("\\.", "/")
				+"/"
				+ input.get("springName").toString()
				+ "/list.jsp";
		MysqlTableEntity tableEntity = TableProcess.getTableInf(tableName);
		java.util.List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();//有序
		input.put("properties", properties);
		java.util.List<MysqlColumnEntity> tableColumns =tableEntity.getColumns();
		for(int i=0;i<tableColumns.size();i++){
			Map<String, Object> tableEntityInfo= new HashMap<String, Object>(); 
			tableEntityInfo.put("name", tableColumns.get(i).getFiledName());//set get 列名称
			tableEntityInfo.put("type", tableColumns.get(i).getJavatype());//java类型
			tableEntityInfo.put("comment", tableColumns.get(i).getComm());//中文注释
			tableEntityInfo.put("columnName",tableColumns.get(i).getColumnName().toLowerCase());//注释列名称 
			tableEntityInfo.put("columnKey",tableColumns.get(i).getColumnKey());//注释列名称 
			tableEntityInfo.put("pri",tableColumns.get(i).isPri());//注释列名称 
			String isnull = tableColumns.get(i).getNullable();
			if("YES".equals(isnull)){
				isnull = "true";
			}else{
				isnull = "false";
			}
			tableEntityInfo.put("nullable",isnull);//是否为空
			tableEntityInfo.put("length",tableColumns.get(i).getCharMaxLength()+"");//长度
			properties.add(tableEntityInfo);
		}
		buildFile("templete/list.ftl", fileName, input);
	}
	
	public static void editPage(String workDir, Map<String, Object> input,String tableName)
			throws Exception {
		String fileName = workDir + "/src/main/webapp/WEB-INF/views/"
				+ input.get("packageName").toString().substring(13).replaceAll("\\.", "/")
				+"/"
				+ input.get("springName").toString()
				+ "/edit.jsp";
		MysqlTableEntity tableEntity = TableProcess.getTableInf(tableName);
		java.util.List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();//有序
		input.put("properties", properties);
		java.util.List<MysqlColumnEntity> tableColumns =tableEntity.getColumns();
		for(int i=0;i<tableColumns.size();i++){
			Map<String, Object> tableEntityInfo= new HashMap<String, Object>(); 
			tableEntityInfo.put("name", tableColumns.get(i).getFiledName());//set get 列名称
			tableEntityInfo.put("type", tableColumns.get(i).getJavatype());//java类型
			tableEntityInfo.put("comment", tableColumns.get(i).getComm());//中文注释
			tableEntityInfo.put("columnName",tableColumns.get(i).getColumnName().toLowerCase());//注释列名称 
			tableEntityInfo.put("columnKey",tableColumns.get(i).getColumnKey());//注释列名称 
			tableEntityInfo.put("pri",tableColumns.get(i).isPri());//注释列名称 
			String isnull = tableColumns.get(i).getNullable();
			if("YES".equals(isnull)){
				isnull = "true";
			}else{
				isnull = "false";
			}
			tableEntityInfo.put("nullable",isnull);//是否为空
			tableEntityInfo.put("length",tableColumns.get(i).getCharMaxLength()+"");//长度
			properties.add(tableEntityInfo);
		}
		buildFile("templete/edit.ftl", fileName, input);
	}
	public static void entity(String workDir, Map<String, Object> input,String tableName)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/entity/" + input.get("className").toString() + ".java";
		MysqlTableEntity tableEntity = TableProcess.getTableInf(tableName);
		java.util.List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();//有序
		input.put("properties", properties);
		java.util.List<MysqlColumnEntity> tableColumns =tableEntity.getColumns();
		for(int i=0;i<tableColumns.size();i++){
			Map<String, Object> tableEntityInfo= new HashMap<String, Object>(); 
			tableEntityInfo.put("name", tableColumns.get(i).getFiledName());//set get 列名称
			tableEntityInfo.put("type", tableColumns.get(i).getJavatype());//java类型
			tableEntityInfo.put("comment", tableColumns.get(i).getComm());//中文注释
			tableEntityInfo.put("columnName",tableColumns.get(i).getColumnName().toLowerCase());//注释列名称 
			tableEntityInfo.put("columnKey",tableColumns.get(i).getColumnKey());//注释列名称 
			tableEntityInfo.put("pri",tableColumns.get(i).isPri());//注释列名称 
			String isnull = tableColumns.get(i).getNullable();
			if("YES".equals(isnull)){
				isnull = "true";
			}else{
				isnull = "false";
			}
			tableEntityInfo.put("nullable",isnull);//是否为空
			tableEntityInfo.put("length",tableColumns.get(i).getCharMaxLength()+"");//长度
			properties.add(tableEntityInfo);
		}
		buildFile("templete/entity.ftl", fileName, input,true);
	}

	public static void dao(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/dao/" + input.get("className").toString() + "Dao.java";
		buildFile("templete/dao.ftl", fileName, input);
	}

	public static void daoImpl(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/dao/impl/" + input.get("className").toString()
				+ "DaoImpl.java";
		buildFile("templete/daoImpl.ftl", fileName, input);
	}

	public static void service(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/service/" + input.get("className").toString()
				+ "Service.java";
		buildFile("templete/service.ftl", fileName, input);
	}

	public static void serviceImpl(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/service/impl/" + input.get("className").toString()
				+ "ServiceImpl.java";
		buildFile("templete/serviceImpl.ftl", fileName, input);
	}

	public static void controller(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/controller/" + input.get("className").toString()
				+ "Controller.java";
		buildFile("templete/controller.ftl", fileName, input);
	}
	
	public static void webController(String workDir, Map<String, Object> input)
			throws Exception {
		String fileName = workDir + "/src/main/java/"
				+ input.get("packageName").toString().replaceAll("\\.", "/")
				+ "/controller/" + input.get("className").toString()
				+ "Controller.java";
		buildFile("templete/webController.ftl", fileName, input);
	}
	

	/**
	 * 
	 * @param templateName
	 * @param fileName
	 * @param root
	 * @param forceUpdate 是否强制覆盖
	 */
	private static void buildFile(String templateName, String fileName, Map root,Boolean forceUpdate) {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setClassForTemplateLoading(GenerateJavaFile.class, "/");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		Template template;
		try {
			template = freemarkerCfg.getTemplate(templateName);
			template.setEncoding("UTF-8");
			
			File file = new File(fileName);
			System.out.println("---------------------");
			
			if(file.exists()){
				if(forceUpdate){
					File bakFile = new File( fileName+(new Date()).getTime());
					file.renameTo(bakFile);
					
					File file2 = new File(fileName);
					file2.createNewFile();
					Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file2), "UTF-8"));
					template.process(root, out);
					
				}else{
					return;
				}
			}else{
				file.getParentFile().mkdirs();
				file.createNewFile();
				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
				template.process(root, out);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void buildFile(String templateName, String fileName, Map root) {
		GenerateJavaFile.buildFile(templateName, fileName, root,false);
	}
	
	
	private static String toUpperCamelString(String s){
		String[] t = s.split("_");
		String r = new String();
		for(int i = 0;i<t.length;i++){
			r+= t[i].substring(0, 1).toUpperCase()+t[i].substring(1).toLowerCase();
		}
		return r;
	}
	
	private static String toLowercamelString(String s){
		String[] t = s.split("_");
		String r = new String();
		for(int i = 0;i<t.length;i++){
			if(i==0){
				r+= t[i].toLowerCase();
				
			}else{
				r+= t[i].substring(0, 1).toUpperCase()+t[i].substring(1).toLowerCase();
			}
		}
		return r;
	}
	
}
