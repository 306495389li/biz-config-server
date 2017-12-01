package com.flaginfo.lightning.common;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseService<E extends BaseEntity> {

    public void setJpaRepository(JpaRepository<E, Long> jpaRepository);

    public void setJpaSpecificationExecutor(JpaSpecificationExecutor<E> jpaSpecificationExecutor);

    public Iterable<E> getAll();

    public E get(Long pid);

    public E save(E entity) throws Exception;
    
    public List<E> saveList(List<E> entityList) throws Exception;

    public void delete(Long pid) throws Exception;

    public void delete(E entity) throws Exception;
    
    public void deleteAll() throws Exception;

    public Page<E> find(Map<String, Object> searchParams, Map<String, String> sortParams, int pageNumber, int pageSize);

    public Iterable<E> findAll(Map<String, Object> searchParams);
    
    /**
     * 用于调用其他服务的(查询操作)
     * @param serviceName
     * @param method
     * @param map
     * @return
     * @throws IOException
     */
    public ResultData get(String serviceName, String method, Map map) throws IOException;

    /**
     * 用于调用其他服务的(查询操作)
     * @param serviceName
     * @param method
     * @param map
     * @return
     * @throws IOException
     */
    public ResultData find(String serviceName, String method, Map map) throws IOException;

    /**
     * 用于调用其他服务的(查询操作)
     * @param serviceName
     * @param method
     * @param map
     * @return
     * @throws IOException
     */
    public ResultData findAll(String serviceName, String method, Map map) throws IOException;

    /**
     * 用于调用其他服务的(保存、修改操作)
     * @param serviceName
     * @param method
     * @param data
     * @return
     * @throws IOException
     */
    public ResultData save(String serviceName, String method, String data) throws IOException;

    /**
     * 用于调用其他服务的(删除操作)
     * @param serviceName
     * @param method
     * @param map
     * @return
     * @throws IOException
     */
    public ResultData delete(String serviceName, String method, Map map) throws IOException;

    /**
     * 用于调用其他服务的(批量更新操作)
     * @param serviceName
     * @param method
     * @param data
     * @return
     * @throws IOException
     */
    public ResultData doBatchSaveOrUpdateOrDelete(String serviceName, String method, String data) throws IOException;
    
}
