package com.powerhf.telecom.web.service;

import com.powerhf.telecom.web.bean.Calllog;

import java.util.List;
import java.util.Map;

public interface CalllogService {
    List<Calllog> queryDatas(Map<String, Object> paraMap);
}
