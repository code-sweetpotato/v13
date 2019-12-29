package work;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
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

    @Select("<script>" +
            "select * from (select ar.name as \"custName\",ar.mobile as \"mobile\",to_char(ar.date_appoint," +
            "'yyyy.MM.dd') as\"appointDate\" from csp_appoint_register ar where not exists(select s.appoint_no\n" +
            "from csp_el_city_channel_info s where s.appoint_no = ar.appoint_no) ar.um = #{map.salesManCode}\n" +
            "<if test = \"map.mainLoanCode != null\">" +
            "and ar.main_loan_code = #{mainLoanCode}" +
            "</if>)" +
            "and ar.agent_dept_no in\n" +
            "<foreach collection=\"map.deptCode\" item=\"item\" index=\"index\" open =\"(\" close=\")\" separator=\",\">" +
            "#{item}" +
            "</foreach> ) tab2 where 1=1\n" +
            "<choose>" +
            "<when test=\"'desc'.toString() == map.orderByTime\">" +
            "order by tab2.\"appointDate\" desc,tab2.\"appointNo\"" +
            "<otherwise>" +
            "order by tab2.\"appoimtDate\" asc,tab2.\"appointNo\"" +
            "</otherwise>" +
            "</choose>" +
            "</script>")
    List<Map<String,Object>> queryLifeMap(@Param("map") Map<String,Object> map);

}
