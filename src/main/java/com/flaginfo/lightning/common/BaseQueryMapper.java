package com.flaginfo.lightning.common;

import java.util.Map;
import java.util.List;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@SuppressWarnings("all")
public interface BaseQueryMapper {
	PageList query(Map conditions, PageBounds pageBounds);
	List query(Map conditions);
}