package com.powerhf.telecom.web.dao;

import com.powerhf.telecom.web.bean.Calllog;

import java.util.List;
import java.util.Map;

public interface CalllogDao {
    List<Calllog> queryDatas(Map<String, Object> paramMap);
}
