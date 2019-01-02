package com.powerhf.telecom.web.service.impl;

import com.powerhf.telecom.web.bean.Calllog;
import com.powerhf.telecom.web.dao.CalllogDao;
import com.powerhf.telecom.web.service.CalllogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CalllogServiceImpl implements CalllogService {
    @Autowired
    private CalllogDao calllogDao;

    @Override
    public List<Calllog> queryDatas(Map<String, Object> paraMap) {
        return calllogDao.queryDatas(paraMap);
    }
}
