package cose.lexian.order;

import cn.itcast.commons.CommonUtils;
import cose.lexian.order.domain.OrderItem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class testJson {

    @Test
    public void test() {
        String json = "{'u_id':'123121564321312', 's_id':'13254351', 'goods':[{'g_id':'123153153', 'g_num':'2'}, " +
                "{'g_id':'12312123', 'g_num':'2'}]}";
        JSONObject jsonObject = JSONObject.fromObject(json);
        System.out.println(jsonObject);

        String u_id = jsonObject.getString("u_id");
        System.out.println(u_id);
        String s_id = jsonObject.getString("s_id");
        System.out.println(s_id);
        String goods = jsonObject.getString("goods");
        System.out.println(goods);
        JSONArray jsonArray = JSONArray.fromObject(goods);
        List<OrderItem> orderItemList = new ArrayList<OrderItem>();
        for (int i=0; i<jsonArray.size(); i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOi_id(CommonUtils.uuid());
            orderItem.setOi_count(Integer.parseInt((String) jsonArray.getJSONObject(i).get("g_num")));
            orderItemList.add(orderItem);
        }
        System.out.println(orderItemList);
    }
}
