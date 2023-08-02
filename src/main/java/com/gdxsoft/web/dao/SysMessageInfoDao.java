package com.gdxsoft.web.dao;

import java.util.*;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.datasource.IClassDao;
import com.gdxsoft.easyweb.datasource.ClassDaoBase;
/** SYS_MESSAGE_INFO
 * @author gdx date: Wed Aug 02 2023 09:12:28 GMT+0800 (中国标准时间) */
public class SysMessageInfoDao extends ClassDaoBase < SysMessageInfo > implements IClassDao < SysMessageInfo > {
    public final static String TABLE_NAME = "SYS_MESSAGE_INFO";
    public final static String[] KEY_LIST = {
        "MESSAGE_ID" /* 编号, int identity, length:10, null:false, pk:true */
    };
    public final static String[] FIELD_LIST = {
        "MESSAGE_ID" /* 编号, int identity, length:10, null:false, pk:true */ ,
        "FROM_SUP_ID" /* 来自供应商, int, length:10, null:true, pk:false */ ,
        "FROM_USR_ID" /* 来自用户, int, length:10, null:true, pk:false */ ,
        "FROM_NAME" /* FROM_NAME, nvarchar, length:50, null:true, pk:false */ ,
        "TARGET_USR_ID" /* 目标用户, int, length:10, null:true, pk:false */ ,
        "TARGET_SUP_ID" /* 目标供应商, int, length:10, null:true, pk:false */ ,
        "TARGET_NAME" /* TARGET_NAME, nvarchar, length:50, null:true, pk:false */ ,
        "FROM_EMAIL" /* 来源地址, varchar, length:200, null:true, pk:false */ ,
        "TARGET_EMAIL" /* 目标地址, varchar, length:2000, null:true, pk:false */ ,
        "TARGET_TYPE" /* 目标类型, varchar, length:20, null:true, pk:false */ ,
        "MESSAGE_TITLE" /* 标题, nvarchar, length:500, null:true, pk:false */ ,
        "MESSAGE_CONTENT" /* 内容, nvarchar, length:2147483647, null:true, pk:false */ ,
        "MESSAGE_STATUS" /* 状态, varchar, length:20, null:true, pk:false */ ,
        "CREATE_DATE" /* 创建时间, datetime, length:23, null:true, pk:false */ ,
        "SEND_DATE" /* 发送时间, datetime, length:23, null:true, pk:false */ ,
        "MODULE_TYPE" /* 模块类型, varchar, length:20, null:true, pk:false */ ,
        "IS_READ" /* IS_READ, nvarchar, length:20, null:true, pk:false */ ,
        "REF_ID" /* REF_ID, varchar, length:51, null:true, pk:false */ ,
        "REF_TABLE" /* REF_TABLE, varchar, length:200, null:true, pk:false */ ,
        "CAL_SDATE" /* CAL_SDATE, datetime, length:23, null:true, pk:false */ ,
        "CAL_EDATE" /* CAL_EDATE, datetime, length:23, null:true, pk:false */ ,
        "MAIL_TYPE" /* MAIL_TYPE, varchar, length:20, null:true, pk:false */ ,
        "ATTS" /* ATTS, ntext, length:1073741823, null:true, pk:false */ ,
        "MESSSAGE_LOG" /* MESSSAGE_LOG, nvarchar, length:2000, null:true, pk:false */ ,
        "FACEBACK_URL" /* FACEBACK_URL, varchar, length:1000, null:true, pk:false */ ,
        "REPLAY_TO_EMAIL" /* REPLAY_TO_EMAIL, varchar, length:200, null:true, pk:false */ ,
        "REPLAY_TO_NAME" /* REPLAY_TO_NAME, nvarchar, length:50, null:true, pk:false */ ,
        "SINGLE_TO_EMAIL" /* SINGLE_TO_EMAIL, varchar, length:200, null:true, pk:false */ ,
        "SINGLE_TO_NAME" /* SINGLE_TO_NAME, nvarchar, length:50, null:true, pk:false */ ,
        "SMTP_CFG" /* SMTP_CFG, varchar, length:1000, null:true, pk:false */ ,
        "MQ_MSG_ID" /* MQ_MSG_ID, char, length:32, null:true, pk:false */ ,
        "MQ_MSG" /* MQ_MSG, nvarchar, length:2147483647, null:true, pk:false */ ,
        "CC_EMAILS" /* CC_EMAILS, nvarchar, length:2147483647, null:true, pk:false */ ,
        "CC_NAMES" /* CC_NAMES, nvarchar, length:2147483647, null:true, pk:false */ ,
        "BCC_EMAILS" /* BCC_EMAILS, nvarchar, length:2147483647, null:true, pk:false */ ,
        "BCC_NAMES" /* BCC_NAMES, nvarchar, length:2147483647, null:true, pk:false */ ,
        "MESSAGE_MD5" /* MESSAGE_MD5, varchar, length:32, null:true, pk:false */
    };
    public SysMessageInfoDao() {
        // 设置数据库连接配置名称，在 ewa_conf.xml中定义
        // super.setConfigName("globaltravel");
        super.setInstanceClass(SysMessageInfo.class);
        super.setTableName(TABLE_NAME);
        super.setFields(FIELD_LIST);
        super.setKeyFields(KEY_LIST);
        // 自增字段 
        super.setAutoKey("MESSAGE_ID");
    }
    /**
     * 生成一条记录
     * @param para  表SYS_MESSAGE_INFO的映射类
     * @return true/false
     */
    public boolean newRecord(SysMessageInfo para) {
        Map < String, Boolean > updateFields = super.createAllUpdateFields(FIELD_LIST);
        return this.newRecord(para, updateFields);
    }
    /**
     * 生成一条记录
     * @param para 表SYS_MESSAGE_INFO的映射类
     * @param updateFields 变化的字段Map
     * @return true/false
     */
    public boolean newRecord(SysMessageInfo para, Map < String, Boolean > updateFields) {
        String sql = super.sqlInsertChanged(TABLE_NAME, updateFields, para);
        if (sql == null) { //没有可更新数据
            return false;
        }
        RequestValue rv = this.createRequestValue(para);
        int autoKey = super.executeUpdateAutoIncrement(sql, rv);
        if (autoKey > 0) {
            para.setMessageId(autoKey);
            return true;
        } else {
            return false;
        }
    }
    /**
     * 更新一条记录，全字段
     *@param para 表SYS_MESSAGE_INFO的映射类
     *@return 是否成功 
     */
    public boolean updateRecord(SysMessageInfo para) {

        Map < String, Boolean > updateFields = super.createAllUpdateFields(FIELD_LIST);
        return updateRecord(para, updateFields);
    }
    /**
     * 更新一条记录，根据类的字段变化
     * 
     * @param para
     *            表SYS_MESSAGE_INFO的映射类
     * @param updateFields
     *            变化的字段Map
     * @return
     */
    public boolean updateRecord(SysMessageInfo para, Map < String, Boolean > updateFields) {
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
     *@param paraMessageId 编号
     *@return 记录类(SysMessageInfo)
     */
    public SysMessageInfo getRecord(Integer paraMessageId) {
        RequestValue rv = new RequestValue();
        rv.addValue("MESSAGE_ID", paraMessageId, "Integer", 10);
        String sql = super.getSqlSelect();
        ArrayList < SysMessageInfo > al = super.executeQuery(sql, rv, new SysMessageInfo(), FIELD_LIST);
        if (al.size() > 0) {
            SysMessageInfo o = al.get(0);
            al.clear();
            return o;
        } else {
            return null;
        }
    }
    /**
     * 根据主键删除一条记录
     *@param paraMessageId 编号
     *@return 是否成功
     */
    public boolean deleteRecord(Integer paraMessageId) {
        RequestValue rv = new RequestValue();
        rv.addValue("MESSAGE_ID", paraMessageId, "Integer", 10);
        return super.executeUpdate(super.createDeleteSql(), rv);
    }
}