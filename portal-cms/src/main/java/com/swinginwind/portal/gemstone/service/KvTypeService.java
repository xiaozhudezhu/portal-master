package com.swinginwind.portal.gemstone.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swinginwind.portal.common.service.CommonService;
import com.swinginwind.portal.gemstone.dao.KvTypeDao;
import com.swinginwind.portal.gemstone.entity.Kv;
import com.swinginwind.portal.gemstone.entity.KvType;

@Service
public class KvTypeService extends CommonService<KvType, String> {
	
	private Map<String, List<Kv>> kvListMap = new HashMap<String, List<Kv>>();
	private Map<String, String> kvNameMap = new HashMap<String, String>();

	@Autowired
	public void setDao(KvTypeDao dao) {
		super.setCommonDao(dao);
		initKvMap();
	}
	
	public void initKvMap() {
		List<KvType> types = this.getCommonDao().findAll();
		for(KvType type : types) {
			if(type.getChildren() != null && type.getChildren().size() > 0) {
				for(Kv kv : type.getChildren()) {
					String key = kv.getType() + "#" + kv.getTypeId();
					List<Kv> kvs = kvListMap.get(key);
					if(kvs == null) {
						kvs = new ArrayList<Kv>();
						kvListMap.put(key, kvs);
					}
					kvs.add(kv);
					kvNameMap.put(kv.getId(), kv.getName());
				}
			}
		}
	}
	
	public String getKvName(String id) {
		return kvNameMap.get(id);
	}
	
	public List<Kv> getKvList(String type, String typeId) {
		String typeStr = type;
		while(true) {
			String key = typeStr + "#" + typeId;
			if(kvListMap.containsKey(key)) {
				return kvListMap.get(key);
			}
			int index = typeStr.lastIndexOf("-");
			if(index < 0)
				break;
			else
				typeStr = typeStr.substring(0, typeStr.lastIndexOf("-"));
		}
		return new ArrayList<Kv>();
	}
	
}
