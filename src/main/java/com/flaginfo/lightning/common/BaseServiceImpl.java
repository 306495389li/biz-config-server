package com.flaginfo.lightning.common;

import java.util.Map;
import java.util.List;
import org.slf4j.Logger;
import java.io.IOException;
import java.util.ArrayList;
import net.sf.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.flaginfo.lightning.common.persistence.SearchFilter;
import org.springframework.transaction.annotation.Transactional;
import com.flaginfo.lightning.common.persistence.DynamicSpecifications;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

//import org.springframework.cache.annotation.CachePut;
//import org.springframework.cache.annotation.Cacheable;

public class BaseServiceImpl<E extends BaseEntity> implements BaseService<E> {
    final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
    
    private JpaRepository<E, Long> jpaRepository;

    private JpaSpecificationExecutor<E> jpaSpecificationExecutor;

    private JpaRepository<E, Long> getJpaRepository() {
        return jpaRepository;
    }

    @Override
    public void setJpaRepository(JpaRepository<E, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    private JpaSpecificationExecutor<E> getJpaSpecificationExecutor() {
        return jpaSpecificationExecutor;
    }

    @Override
    public void setJpaSpecificationExecutor(JpaSpecificationExecutor<E> jpaSpecificationExecutor) {
        this.jpaSpecificationExecutor = jpaSpecificationExecutor;
    }

    @Override
    public Iterable<E> getAll() {
        return this.getJpaRepository().findAll();
    }

    @Override
    // @Cacheable(value = "smile.auth",keyGenerator = "wiselyKeyGenerator")
    public E get(Long pid) {
        return this.getJpaRepository().findOne(pid);
    }

    @Override
    // @CachePut(value = "smile.auth", keyGenerator = "wiselyKeyGenerator")
    @Transactional
    public E save(E entity) throws Exception {
        return this.getJpaRepository().save(entity);
    }
    
    @Override
    @Transactional
    public List<E> saveList(List<E> entityList) throws Exception {
        return this.getJpaRepository().save(entityList);
    }

    @Override
    // @CacheEvict(value = "smile.auth", keyGenerator = "wiselyKeyGenerator")
    @Transactional
    public void delete(Long pid) throws Exception {
        this.getJpaRepository().delete(pid);
    }

    @Override
    public void delete(E entity) throws Exception {
        this.delete(entity.getPrimaryKey());
    }
    
    @Override
    public void deleteAll() throws Exception {
        this.getJpaRepository().deleteAll();
    }

    @Override
    public Page<E> find(Map<String, Object> searchParams, Map<String, String> sortParams, int pageNumber, int pageSize) {
        Pageable pageable;
        // 构建排序条件
        if (sortParams != null && !sortParams.equals(null) && !sortParams.isEmpty()) {
            List<Order> orders = new ArrayList<Order>();
            for (Map.Entry<String, String> m : sortParams.entrySet()) {
                Direction direction = Direction.ASC;
                if ("DESC".equalsIgnoreCase(m.getValue())) {
                    direction = Direction.DESC;
                }
                String property = m.getKey();
                Order order = new Order(direction, property);
                orders.add(order);
            }
            Sort sort = new Sort(orders);
            pageable = new PageRequest(pageNumber, pageSize, sort);
        } else {
            pageable = new PageRequest(pageNumber, pageSize);
        }

        // 构建查询条件
        Specification<E> spec = null;
        Map<String, SearchFilter> filters = null;
        if (searchParams != null && !searchParams.equals(null) && !searchParams.isEmpty()) {
            filters = SearchFilter.parse(searchParams);
            spec = DynamicSpecifications.bySearchFilter(filters.values());
        }

        return this.getJpaSpecificationExecutor().findAll(spec, pageable);
    }

    @Override
    public Iterable<E> findAll(Map<String, Object> searchParams) {
        // 构建查询条件
        Specification<E> spec = null;
        Map<String, SearchFilter> filters = null;
        if (searchParams != null && !searchParams.equals(null) && !searchParams.isEmpty()) {
            filters = SearchFilter.parse(searchParams);
            spec = DynamicSpecifications.bySearchFilter(filters.values());
        }
        return this.getJpaSpecificationExecutor().findAll(spec);
    }
    
    /**
     * ----------------------------------------------------------------------------------------------
     * 下面的操作，都是为了调用其他服务的
     * ----------------------------------------------------------------------------------------------
     */
    
    @Autowired
    private Environment env;
    
    @Autowired
    private RestTemplate restTemplate;
    
    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
    
    private String getServiceUrlByServiceName(String serviceName) {
        if (null == serviceName || serviceName.equals(null) || serviceName.isEmpty()) {
            return null;
        }
        return env.getProperty("httpServer." + serviceName + ".serviceUrl");
    }
    
    protected ResultData invokeRemoteUrl(String serviceName, String method, Object data) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        JSONObject jsonObj = JSONObject.fromObject(data);
        HttpEntity<String> formEntity = new HttpEntity<String>(jsonObj.toString(), headers);
        String url = getServiceUrlByServiceName(serviceName) + "/" + method;
        Object uriVariables = null;
        logger.info("invoike remote method url : = {}  para := {}", url, data);
        ResultData resultData = this.getRestTemplate().postForObject(url, formEntity, ResultData.class, uriVariables);
        logger.info("invoike remote method url : = {}  return := {}", url, JSONObject.fromObject(resultData).toString());
        return resultData;
    }

    protected ResultData invokeRemoteUrl(String serviceName, String method, String data) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("text/plain; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
        String url = getServiceUrlByServiceName(serviceName) + "/" + method;
        Object uriVariables = null;
        logger.info("invoike remote method url : = {}  para := {}", url, data);
        ResultData resultData = this.getRestTemplate().postForObject(url, formEntity, ResultData.class, uriVariables);
        logger.info("invoike remote method url : = {}  return := {}", url, resultData);
        return resultData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultData get(String serviceName, String method, Map map) throws IOException {
        return this.invokeRemoteUrl(serviceName, method, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultData find(String serviceName, String method, Map map) throws IOException {
        return this.invokeRemoteUrl(serviceName, method, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultData findAll(String serviceName, String method, Map map) throws IOException {
        return this.invokeRemoteUrl(serviceName, method, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultData save(String serviceName, String method, String data) throws IOException {
        return this.invokeRemoteUrl(serviceName, method, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultData delete(String serviceName, String method, Map map) throws IOException {
        return this.invokeRemoteUrl(serviceName, method, map);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultData doBatchSaveOrUpdateOrDelete(String serviceName, String method, String data) throws IOException {
        return this.invokeRemoteUrl(serviceName, method, data);
    }

}
