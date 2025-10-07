package com.spark.dating.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class SessionRegistry {
    private final Map<String, Long> uuidToMno = new ConcurrentHashMap<>();
    private final Map<Long, String> mnoToUuid = new ConcurrentHashMap<>();

    public void addMapping(Long mno, String uuid) {
        uuidToMno.put(uuid, mno);
        mnoToUuid.put(mno, uuid);
    }

    public void removeByUuid(String uuid) {
        Long mno = uuidToMno.remove(uuid);
        if (mno != null) {
            mnoToUuid.remove(mno);
        }
    }

    public void removeByMno(Long mno) {
        String uuid = mnoToUuid.remove(mno);
        if (uuid != null) {
            uuidToMno.remove(uuid);
        }
    }

    public Long getMno(String uuid) {
        return uuidToMno.get(uuid);
    }

    public String getUuid(Long mno) {
        return mnoToUuid.get(mno);
    }

    public boolean isSessionActive(Long mno) {
    	System.err.println("=-=-=-=- " + mnoToUuid.isEmpty());
    	if(!mnoToUuid.isEmpty() && mnoToUuid.containsKey(mno)) {
    		String uuid = mnoToUuid.get(mno);
        	if(uuid != null) {
        		return true;
        	}
    	}
    	return false;
    }
}