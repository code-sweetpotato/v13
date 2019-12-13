package work;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

public interface OracleStudy {


    @Select("select decode(a.name_list_tag,'GBD','Y','GBDC','Y','GBDL','Y','N') \"isGBD\"," +
            "decode(c.cust_name,null,'',substr(c.cust_name,0,length(c.cust_name)-1||'*')) \"custName\"," +
            "replace(c.id_no,substr(c.id_no,length(c.id_no)-11,8),'********') \"custIdNo\"," +
            "to_char(c.date_first_reg,'YYYY-MM-dd') \"drivingRegistrationDate\"," +
            "(select b.city_name from base_bte_l_region_info b where c.city_code = b.city_code) \"insureCity\"" +
            "from appl_admin a,appl_gbd_car_info c where a.apply_no = c.apply_no and a.apply_no=#{applyNo}")
    public Map<String,Object> getGBDCarInfo(Map<String,Object> map);

    @Update("<script> BEGIN" +
            "UPDATE CSP_OLOAN_APPLY_SWITCH a set a.date_update = SYSDATE\n" +
            "<if test \"map.mergeSwitch != null\">,a.MERGE_SWITCH = #{map.mergeSwitch}</if>\n" +
            "where a.ACCOUNT_ID = #{map.accountId};" +
            "IF SQL%NOTFOUND THEN" +
            "INSERT into CSP_OLOAN_APPLY_SWITCH\n" +
            "(APPOINT_NO,ACCOUNT_ID)" +
            "VALUES\n" +
            "(#{map.appointNo},#{accountId});" +
            "END IF;" +
            "END;")
    public Map<String,Object> updateSwitch(Map<String,Object> map);

}
