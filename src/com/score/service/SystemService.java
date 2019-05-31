package com.score.service;

import java.util.List;

import com.score.bean.Clazz;
import com.score.bean.Grade;
import com.score.bean.SystemInfo;
import com.score.bean.User;
import com.score.dao.impl.BaseDaoImpl;
import com.score.dao.impl.SystemDaoImpl;
import com.score.dao.inter.BaseDaoInter;
import com.score.dao.inter.SystemDaoInter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 年级服务层
 * @author bojiangzhou
 *
 */
public class SystemService {
	
	SystemDaoInter dao = new SystemDaoImpl();
	
	/**
	 * 获取系统所有账号
	 * @return
	 */
	public String getAccountList(){
		//获取数据
		List<String> list = dao.getColumn("SELECT account FROM user", null);
		//json化
        String result = JSONArray.fromObject(list).toString();
        
        return result;
	}

	/**
	 * 登录验证
	 * @param user
	 * @return 
	 */
	public User getAdmin(User user) {
		User searchUser = (User) dao.getObject(User.class, 
				"SELECT * FROM user WHERE account=? AND password=? AND type=?", 
				new Object[]{user.getAccount(), user.getPassword(), user.getType()});
		
		return searchUser;
	}

	/**
	 * 修改用户密码
	 * @param user
	 */
	public void editPassword(User user) {
		dao.update("UPDATE user SET password=? WHERE account=?", 
				new Object[]{user.getPassword(),user.getAccount()});
	}
	
	/**
	 * 修改系统信息
	 * @param name 修改的名称
	 * @param value 值
	 * @return 返回修改后的系统信息对象
	 */
	public SystemInfo editSystemInfo(String name, String value) {
		//修改数据库
		dao.update("UPDATE system SET "+name+" = ?", new Object[]{value});
		//重新加载数据
		//获取系统初始化对象
    	SystemInfo sys = (SystemInfo) dao.getObject(SystemInfo.class, "SELECT * FROM system", null);
    	return sys;
	}
	
	
}
