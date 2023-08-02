package com.gdxsoft.web.dao;

import java.util.*;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** SYS_MESSAGE_INFO_FACEBACK
 * @author gdx date: Wed Aug 02 2023 09:11:02 GMT+0800 (中国标准时间) */
public class SysMessageInfoFacebackDao extends ClassDaoBase < SysMessageInfoFaceback > implements IClassDao < SysMessageInfoFaceback > {
    public final static String TABLE_NAME = "SYS_MESSAGE_INFO_FACEBACK";
    public final static String[] KEY_LIST = {
        "MESSAGE_ID" /* 消息编号, int, length:10, null:false, pk:true */ ,
        "FBK_ADM_ID" /* 用户编号, int, length:10, null:false, pk:true */
    };
    public final static String[] FIELD_LIST = {
        "MESSAGE_ID" /* 消息编号, int, length:10, null:false, pk:true */ ,
        "FBK_ADM_ID" /* 用户编号, int, length:10, null:false, pk:true */ ,
        "FBK_TIME" /* 回复时间, datetime, length:23, null:true, pk:false */ ,
        "FBK_IP" /* ip, varchar, length:40, null:true, pk:false */ ,
        "FBK_AGENT" /* 浏览器代理, varchar, length:2000, null:true, pk:false */ ,
        "FBK_UNID" /* unid, char, length:36, null:true, pk:false */ ,
        "FBK_SHOW_TIME" /* 打开时间, datetime, length:23, null:true, pk:false */ ,
        "FBK_SHOW_IP" /* 打开IP, varchar, length:40, null:true, pk:false */ ,
        "FBK_SHOW_AGENT" /* 打开代理, varchar, length:2000, null:true, pk:false */ ,
        "FBK_SHOW_REFFER" /* 跟踪的浏览器来源参考, varchar, length:8000, null:true, pk:false */
    };
    public SysMessageInfoFacebackDao() {
        // 设置数据库连接配置名称，在 ewa_conf.xml中定义
        // super.setConfigName("globaltravel");
        super.setInstanceClass(SysMessageInfoFaceback.class);
        super.setTableName(TABLE_NAME);
        super.setFields(FIELD_LIST);
        super.setKeyFields(KEY_LIST);
    }
    /**
     * 生成一条记录
     * @param para  表SYS_MESSAGE_INFO_FACEBACK的映射类
     * @return true/false
     */
    public boolean newRecord(SysMessageInfoFaceback para) {
        Map < String, Boolean > updateFields = super.createAllUpdateFields(FIELD_LIST);
        return this.newRecord(para, updateFields);
    }
    /**
     * 生成一条记录
     * @param para 表SYS_MESSAGE_INFO_FACEBACK的映射类
     * @param updateFields 变化的字段Map
     * @return true/false
     */
    public boolean newRecord(SysMessageInfoFaceback para, Map < String, Boolean > updateFields) {
        String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
        if (sql == null) { //没有可更新数据
            return false;
        }
        RequestValue rv = this.createRequestValue(para);
        return super.executeUpdate(sql, rv);
    }
    /**
     * 更新一条记录，全字段
     *@param para 表SYS_MESSAGE_INFO_FACEBACK的映射类
     *@return 是否成功 
     */
    public boolean updateRecord(SysMessageInfoFaceback para) {

        Map < String, Boolean > updateFields = super.createAllUpdateFields(FIELD_LIST);
        return updateRecord(para, updateFields);
    }
    /**
     * 更新一条记录，根据类的字段变化
     * 
     * @param para
     *            表SYS_MESSAGE_INFO_FACEBACK的映射类
     * @param updateFields
     *            变化的字段Map
     * @return
     */
    public boolean updateRecord(SysMessageInfoFaceback para, Map < String, Boolean > updateFields) {
        // 没定义主键的话不能更新
        if (KEY_LIST.length == 0) {
            return false;
        }
        String sql = super.sqlUpdateChanged(TABLE_NAME, KEY_LIST, updateFields);
        if (sql == null) { //没有可更新数据
            return false;
        }
        RequestValue rv = this.createRequestValue(para);
        return super.executeUpdate(sql, rv);
    }
    /**
     * 根据主键返回一条记录
     *@param paraMessageId 消息编号
     *@param paraFbkAdmId 用户编号
     *@return 记录类(SysMessageInfoFaceback)
     */
    public SysMessageInfoFaceback getRecord(Integer paraMessageId, Integer paraFbkAdmId) {
        RequestValue rv = new RequestValue();
        rv.addValue("MESSAGE_ID", paraMessageId, "Integer", 10);
        rv.addValue("FBK_ADM_ID", paraFbkAdmId, "Integer", 10);
        String sql = super.getSqlSelect();
        ArrayList < SysMessageInfoFaceback > al = super.executeQuery(sql, rv, new SysMessageInfoFaceback(), FIELD_LIST);
        if (al.size() > 0) {
            SysMessageInfoFaceback o = al.get(0);
            al.clear();
            return o;
        } else {
            return null;
        }
    }
    /**
     * 根据主键删除一条记录
     *@param paraMessageId 消息编号
     *@param paraFbkAdmId 用户编号
     *@return 是否成功
     */
    public boolean deleteRecord(Integer paraMessageId, Integer paraFbkAdmId) {
        RequestValue rv = new RequestValue();
        rv.addValue("MESSAGE_ID", paraMessageId, "Integer", 10);
        rv.addValue("FBK_ADM_ID", paraFbkAdmId, "Integer", 10);
        return super.executeUpdate(super.createDeleteSql(), rv);
    }
}