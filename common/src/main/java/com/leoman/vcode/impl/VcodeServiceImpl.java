package com.leoman.vcode.impl;

import com.leoman.vcode.VcodeService;
import com.leoman.vcode.kx.SendSMSDemo;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/6/13.
 */
@Service
public class VcodeServiceImpl implements VcodeService {

    private static final String KEY = "CACHE_CODE_KEY";
    private Cache cache;

    @Override
    public Boolean sendKX(String code, String... mobiles) {
        Boolean flag = SendSMSDemo.sendKX(code, mobiles);
        putCache(code, mobiles);
        return flag;
    }

    @Override
    public Boolean sendYP(String code, String... mobiles) {
        return null;
    }

    public void putCache(String value, String... keys) {
        cache = CacheManager.getInstance().getCache("com.leoman");
        for (String key : keys) {
            cache.put(new Element(getKey(key), value));
        }
    }

    @Override
    public Boolean validate(String mobile, String code) {
        Boolean flag;
        String _code = get(mobile);
        if (StringUtils.isBlank(_code) || StringUtils.isBlank(code)) {
            return Boolean.FALSE;
        }
        if (!_code.equals(code)) {
            return Boolean.FALSE;
        }
        flag = Boolean.TRUE;
        remove(mobile);
        return flag;
    }

    public String get(String key) {
        if (isExist(key)) {
            return cache.get(getKey(key)).getObjectValue().toString();
        } else {
            return null;
        }
    }

    protected String getKey(String key) {
        return KEY + ">" + key;
    }

    public boolean isExist(String key) {
        return cache.isKeyInCache(getKey(key));
    }

    public void remove(String key) {
        cache.remove(getKey(key));
    }
}
