package com.personal.demo.utils;

import com.personal.demo.conf.GsonFactory;
import com.personal.demo.consts.Separator;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SignUtils {



    //  私有构造，工具类不允许实例化
    private SignUtils() {
    }

    /**
     * 生成签名（完整对齐 PHP sign 方法）
     *
     * @param reqMap 业务参数 Map（不含系统参数）
     */
    public static String sign(Map<String, Object> reqMap,String appid,String appKey,long timestamp) {
        // 1. 自动探测层数并排序（不含系统参数）
        //排序
        Map<String, Object> sorted = sortAuto(reqMap);
        String json = httpBuildQuery(sorted, Separator.NONE);
        String signStr = json+"&appid="+appid+"&appkey="+appKey+"&v=2.0&ts="+timestamp;
        //md5签名
        return MD5Utils.getMD5(signStr);
    }

    /**
     * 自动探测 Map 最大嵌套深度，再按该深度排序
     * 无需手动指定层数，适配任意结构
     */
    public static Map<String, Object> sortAuto(Map<String, Object> args) {
        int depth = detectDepth(args);
        return sortByDepth(args, depth);
    }

    /**
     * 递归探测对象最大嵌套深度
     */
    public static int detectDepth(Object obj) {
        if (obj instanceof Map) {
            int max = 0;
            for (Object val : ((Map<?, ?>) obj).values()) {
                max = Math.max(max, detectDepth(val));
            }
            return max + 1;

        } else if (obj instanceof List) {
            int max = 0;
            for (Object item : (List<?>) obj) {
                max = Math.max(max, detectDepth(item));
            }
            // List 本身不算一层
            return max;

        } else {
            return 0;
        }
    }

    /**
     * 递归按 key ASCII 字典序升序排序，depth 控制最大层数
     * <p>
     * depth=3                  → 对齐 PHP 原版（ksort 3层）
     * depth=Integer.MAX_VALUE  → 完全递归（无限层）
     * depth=auto               → 使用 sortAuto() 自动探测
     *
     * @param args  待排序 Map
     * @param depth 最大排序层数
     * @return 排序后的新 TreeMap
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> sortByDepth(Map<String, Object> args, int depth) {
        if (depth <= 0) return args;

        Map<String, Object> sorted = new TreeMap<>(args);

        for (Map.Entry<String, Object> entry : sorted.entrySet()) {
            Object val = entry.getValue();

            if (val instanceof Map && !((Map<?, ?>) val).isEmpty()) {
                // 子 Map 递归，层数 -1
                entry.setValue(sortByDepth((Map<String, Object>) val, depth - 1));

            } else if (val instanceof List && !((List<?>) val).isEmpty()) {
                // List 内元素若是 Map，同样递归，层数 -1
                List<Object> sortedList = new ArrayList<>();
                for (Object item : (List<Object>) val) {
                    if (item instanceof Map && !((Map<?, ?>) item).isEmpty()) {
                        sortedList.add(sortByDepth((Map<String, Object>) item, depth - 1));
                    } else {
                        sortedList.add(item);
                    }
                }
                entry.setValue(sortedList);
            }
        }

        return sorted;
    }

    /**
     * 对应 PHP http_build_query
     * <p>
     * - null 值跳过
     * - 空数组跳过
     * - boolean: true→"1"，false→""
     * - 嵌套 Map：parent[key]=val
     * - 数组：parent[0]=val&parent[1]=val
     * - 编码：RFC 1738，空格→+
     *
     * @param params 参数 Map
     * @param prefix 前缀（顶层传 ""）
     * @return URL 键值对字符串
     */
    public static String httpBuildQuery(Map<String, Object> params, String prefix) {
        List<String> pairs = new ArrayList<>();
        buildPairs(params, prefix, pairs);
        return String.join(Separator.SPLIT, pairs);
    }

    @SuppressWarnings("unchecked")
    private static void buildPairs(Object obj, String prefix, List<String> pairs) {
        if (obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) obj;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = prefix.isEmpty()
                        ? entry.getKey()
                        : prefix + Separator.LEFT_BRACKET + entry.getKey() + Separator.RIGHT_BRACKET;
                buildPairs(entry.getValue(), key, pairs);
            }

        } else if (obj instanceof List) {
            List<Object> list = (List<Object>) obj;
            // 空数组跳过
            if (list.isEmpty()) return;
            for (int i = 0; i < list.size(); i++) {
                buildPairs(list.get(i), prefix + Separator.LEFT_BRACKET + i + Separator.RIGHT_BRACKET, pairs);
            }

        } else {
            // null 跳过
            if (obj == null) return;
            String val;
            if (obj instanceof Boolean) {
                val = ((Boolean) obj) ? "1" : "0";
            } else {
                val = obj.toString();
            }

            try {
                pairs.add(URLEncoder.encode(prefix, StandardCharsets.UTF_8)
                        + "="
                        + URLEncoder.encode(val, StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new RuntimeException("URL 编码失败，key=" + prefix, e);
            }
        }
    }

    public static void main(String[] args) {

//        // ---- 构造业务参数 ----
//        Map<String, Object> address = new LinkedHashMap<>();
//        address.put("province", "北京");
//        address.put("city", "北京市");
//        address.put("is_default", false);
//        address.put("street", "");      // 空字符串，参与构造
//
//        Map<String, Object> buyer = new LinkedHashMap<>();
//        buyer.put("phone", "13800138000");
//        buyer.put("name", "张三");
//        buyer.put("is_verified", true);
//        buyer.put("address", address);
//
//        Map<String, Object> item1 = new LinkedHashMap<>();
//        item1.put("unit_price", 49.90);
//        item1.put("sku_id", "SKU_001");
//        item1.put("name", "精品咖啡豆");
//        item1.put("in_stock", true);
//
//        Map<String, Object> item2 = new LinkedHashMap<>();
//        item2.put("unit_price", 50.00);
//        item2.put("sku_id", "SKU_002");
//        item2.put("name", "手冲滤纸");
//        item2.put("in_stock", false);
//
//        Map<String, Object> orderby = new LinkedHashMap<>();
//        orderby.put("balance", "desc");
//
//        Map<String, Object> params = new LinkedHashMap<>();
//        params.put("total_amount", 99.90);
//        params.put("order_id", "ORD20240601001");
//        params.put("is_vip", true);
//        params.put("is_delete", false);
//        params.put("remark", "");                          // 空字符串，参与
//        params.put("extra", null);                        // null，跳过
//        params.put("coupons", Arrays.asList("12345", "67890"));
//        params.put("tags", Collections.emptyList());     // 空数组，跳过
//        params.put("buyer", buyer);
//        params.put("items", Arrays.asList(item1, item2));
//        params.put("orderby", orderby);
//        params.put("grade", 1);
//        params.put("limit", 20);
//        params.put("offset", 0);
//
//        // ---- 1. 自动探测深度 ----
//        System.out.println("======== 自动探测深度 ========");
//        System.out.println("探测到最大深度：" + detectDepth(params));
//
//        // ---- 2. 排序后结构 ----
//        System.out.println("\n======== 排序后结构（JSON）========");
//        System.out.println(gson.toJson(sortAuto(params)));
//
//        // ---- 3. 追加系统参数后 http_build_query ----
//        Map<String, Object> sorted = sortAuto(params);
//        String query = httpBuildQuery(sorted, "");
//        System.out.println("\n======== http_build_query 结果 ========");
//        System.out.println(query.replace("&", "\n&"));
//
//        // ---- 4. 最终签名 ----
//        System.out.println("\n======== 最终签名（md5）========");
//        System.out.println(sign(params));
    }

}
