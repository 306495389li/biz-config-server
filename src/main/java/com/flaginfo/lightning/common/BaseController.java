package com.flaginfo.lightning.common;

import java.util.Map;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import java.util.HashMap;
import java.util.Iterator;
import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import net.sf.json.util.JSONUtils;
import org.springframework.data.domain.Page;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.flaginfo.lightning.common.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("all")
public abstract class BaseController<E extends BaseEntity> {

	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected BaseService<E> baseService;

	protected Class<E> entityClass;

	public abstract void setBaseService(BaseService<E> baseService);

	public BaseService<E> getBaseService() {
		return baseService;
	}

	public BaseController() {
		try {
			entityClass = GenericUtil.getActualClass(this.getClass(), 0);
		} catch (Exception e) {
			logger.error("error",e);
			e.getStackTrace();
		}
	}

	/**
	 * 根据主键查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "get", method = RequestMethod.POST)
	@ResponseBody
	public ResultData get(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		logger.info("involved method:=get  &&  request para : body:={}", body);
		JSONObject requestObject = JSONObject.fromObject(body);
		String id = requestObject.getString("id");
		E entity = null;
		if (!("".equals(id) || id == null || id == "null")) {
			entity = this.getBaseService().get(new Long(id));
			ResultData resultData = new ResultData(entity);
			logger.info("involved method:=get  &&  return  ResultData {}", JSONObject.fromObject(resultData));
			return resultData;
		} else {
			logger.warn("involved method:=get  &&  warn message {}", "id can not be null");
			return new ResultData(new Exception("id can not be null"));
		}
	}

	/**
	 * 条件查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "find", method = RequestMethod.POST)
	public ResultData find(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		logger.info("involved method:=find  &&  request para : body:={}", body);
		JSONObject requestObject = JSONObject.fromObject(body);
		requestObject.remove("tmp");

		Map<String, String> sortParams = new HashMap<String, String>();
		String sortField = requestObject.getString("sortField");// 排序字段
		String sortOrder = requestObject.getString("sortOrder");// 排序类型
		if (!(sortField.isEmpty() && sortOrder.isEmpty())) {
			sortParams.put(sortField, sortOrder);// 排序（前台页面点击title）
		}

		Map<String, Object> searchParams = new HashMap<String, Object>();
		Iterator it = requestObject.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
			String value = requestObject.getString(key);
			// 查询
			if (!("sortField".equals(key) || "sortOrder".equals(key) || "pageSize".equals(key)|| "pageIndex".equals(key))) {
				searchParams.put(key, value);
			}
		}
		try {
			int pageIndex = requestObject.getInt("pageIndex");
			int pageSize = requestObject.getInt("pageSize");
			Page<E> data = baseService.find(searchParams, sortParams, pageIndex, pageSize);
			logger.info("involved method:=find  &&  return  ResultData {}", JSONArray.fromObject(data));
			return new ResultData(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("find method excute error {}",body,e);
			return new ResultData(e);
		}
	}

	/**
	 * 条件查询 无分页，无排序
	 * 
	 * @return
	 */
	@RequestMapping(value = "findAll", method = RequestMethod.POST)
	public ResultData findAll(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		logger.info("involved method:=findAll  &&  request para : body:={}", body);
		Map<String, Object> searchParams = new HashMap<String, Object>();
		JSONObject queryObject = JSONObject.fromObject(body);
		queryObject.remove("tmp");
		Iterator it = queryObject.keys();

		while (it.hasNext()) {
			String key = (String) it.next();
			String value = queryObject.getString(key);
			if (!("sortField".equals(key) || "sortOrder".equals(key) || "pageSize".equals(key)
					|| "pageIndex".equals(key))) {// 查询
				searchParams.put(key, value);
			}
		}
		try {
			List<E> data = (List<E>) baseService.findAll(searchParams);
			logger.info("involved method:=findAll  &&  return  ResultData {}", JSONArray.fromObject(data));
			return new ResultData(data);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("findAll method excute error {}",body,e);
			return new ResultData(e);
		}
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public ResultData save(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		logger.info("involved method:=save  &&  request para : body:={}", body);
		JSONObject jsonObject = JSONObject.fromObject(body);
		E entity = null;
		String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd", "yyyy-MM-dd't'HH:mm:ss" }; // 格式化日期
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats,(Date) null));
		
		entity = (E) jsonObject.toBean(jsonObject, getEntityClass());
		jsonObject.remove("tmp");
		try {
			entity = (E) JSONObject.toBean(jsonObject, entityClass);
			entity = baseService.save(entity);
			logger.info("involved method:=save  && excute success ");
			return new ResultData(entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save method excute error {}",body,e);
			return new ResultData(e);
		}
	}

	/**
	 * 批量保存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doBatchSave", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResultData doBatchSave(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		logger.info("involved method:=doBatchSave  && request para : body:={}", body);
		JSONObject jsonTmp = new JSONObject();
		Result result = null;
		E entity = null;
		Integer num = 0, successnum = 0;
		Map<String, Object> dataMap = new HashMap<String, Object>();
		JSONArray jsonArray = JSONArray.fromObject(body);
		JSONArray resultDataJson = new JSONArray();
		if (jsonArray.size() > 0 && jsonArray != null) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				logger.info("JSONObject[{}] := {}  ", i , jsonObject);
				String[] dateFormats = new String[] { "yyyy-MM-dd HH:mm:ss","yyyy-MM-dd", "yyyy-MM-dd't'HH:mm:ss" }; // 格式化日期
				JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats,(Date) null));
				entity = (E) jsonObject.toBean(jsonObject, getEntityClass());
				String _state = jsonObject.getString("_state");
				try {
					if ("added".equals(_state)) {
						// 新增
						beforeDoSave(request, entity);
						entity = baseService.save(entity);
						afterDoSave(request, entity);
						result = new Result(true, "success");
						result.setData(entity);
						successnum += 1;
					} else if ("modified".equals(_state)) {
						// 修改
						beforeDoUpdate(request, entity);
						this.getBaseService().save(entity);
						afterDoUpdate(request, entity);
						result = new Result(true, "success");
						successnum += 1;
					} else if ("removed".equals(_state)) {
						// 删除
						this.getBaseService().delete(entity);
						result = new Result(true, "success");
						result.setData(entity);
						successnum += 1;
					} else {
						result = new Result(false, "error");
						num += 1;
					}
				} catch (Exception e) {
					result = new Result(false, "error" + e.getMessage());
					num += 1;
					logger.error("save method excute error {}",jsonObject,e);
					e.printStackTrace();
				} 
				resultDataJson.add(result);
			}
			dataMap.put("errorNumber", num);// 失败条数
			dataMap.put("successNumber", successnum);// 成功条数
			dataMap.put("result", resultDataJson);// 失败原因
			jsonTmp = JSONObject.fromObject(dataMap);
		}
		return new ResultData(jsonTmp.toString());
	}

	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public ResultData delete(HttpServletRequest request, HttpServletResponse response, @RequestBody String body) {
		logger.info("involved method:=delete  && request para : body:={}", body);
		JSONObject requestObject = JSONObject.fromObject(body);
		String id = requestObject.getString("id");
		E entity = null;
		if (!("".equals(id) || id == null || id == "null")) {
			entity = this.getBaseService().get(new Long(id));
		} else {
			return new ResultData(new NullPointerException("id can not be null"));
		}
		try {
			this.getBaseService().delete(new Long(id));
			logger.info("involved method:=save  && excute success ");
			return new ResultData();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete method excute error {}",body,e);
			return new ResultData(e);
		}
	}

	/**
	 * 调用保存方法之前进行的方法调用
	 * 
	 * @param request
	 * @param entity
	 *            对应实体信息
	 * @throws Exception
	 */
	protected void beforeDoSave(HttpServletRequest request, E entity) throws Exception {

	}

	/**
	 * 电泳保存方法之后进行的方法调用
	 * 
	 * @param request
	 * @param entity
	 *            对应实体信息
	 * @throws Exception
	 */
	protected void afterDoSave(HttpServletRequest request, E entity) throws Exception {

	}

	/**
	 * 调用更新操作之前进行的操作
	 * 
	 * @param request
	 * @param entity
	 * @throws Exception
	 */
	protected void beforeDoUpdate(HttpServletRequest request, E entity) throws Exception {

	}

	/**
	 * 调用更新操作之后进行的操作
	 * 
	 * @param request
	 * @param entity
	 * @throws Exception
	 */
	protected void afterDoUpdate(HttpServletRequest request, E entity) throws Exception {

	}

	public Class<E> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
}
