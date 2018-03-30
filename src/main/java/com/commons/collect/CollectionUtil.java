package com.commons.collect;

import com.commons.util.Valid;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * 封装一些集合相关度工具类
 * <p/>
 * Collection<--List<--Vector
 * Collection<--List<--ArrayList
 * Collection<--List<--LinkedList
 * Collection<--Set<--HashSet
 * Collection<--Set<--HashSet<--LinkedHashSet
 * Collection<--Set<--SortedSet<--TreeSet
 * Map<--SortedMap<--TreeMap
 * Map<--HashMap
 */
@Slf4j
public class CollectionUtil {

    /**
     * 去重
     */
    public static <T> List<T> removeDuplicate(List<T> list) {

        if (!Valid.valid(list)) {
            log.error("list is empty or is null");
            return list;
        }

        HashSet<T> set = Sets.newHashSet(list);

        return Lists.newArrayList(set);
    }


    /**
     * 使用指定的Filter过滤集合
     */
    public static <T> List<T> filter(List<T> list, ListFilter<T> filter) {
        if (!Valid.valid(list)) {
            log.error("list is empty or is null");
            return Lists.newArrayListWithCapacity(0);
        }

        List<T> result = Lists.newArrayListWithCapacity(list.size());

        for (T t : list) {
            if (filter.filter(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T> Set<T> filter(Set<T> set, SetFilter<T> filter) {
        if (Valid.valid(set)) {
            log.error("list is empty or is null");
            return set;
        }

        Set<T> result = Sets.newHashSetWithExpectedSize(set.size());
        for (T t : set) {
            if (filter.filter(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T> Queue filter(Queue<T> queue, QueueFilter<T> filter) {
        if (Valid.valid(queue)) {
            log.error("queue is empty or is null");
            return Lists.newLinkedList();
        }
        Queue<T> result = Lists.newLinkedList();
        for (T t : queue) {
            if (filter.filter(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <K, V> Map filter(Map<K, V> map, MapFilter<Map.Entry<K, V>> filter) {
        if (Valid.valid(map)) {
            log.error("map is empty or is null");
            return Maps.newHashMapWithExpectedSize(0);
        }

        Map<K, V> result = Maps.newHashMapWithExpectedSize(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (filter.filter(entry)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * 求俩个集合的交集
     */
    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        if (Valid.valid(list1, list2)) {
            Set<T> set = Sets.newHashSet(list1) ;
            set.retainAll(list2);
            return Lists.newArrayList(set);
        }
        return Lists.newArrayListWithCapacity(0);
    }

    public static <T> Set<T> intersection(Set<T> set1, Set<T> set2) {
        if (Valid.valid(set1, set2)) {
            List<T> list = Lists.newArrayList(set1);
            list.retainAll(set2);
            return Sets.newHashSet(list);
        }
        return Sets.newHashSetWithExpectedSize(0);
    }

    public static <T> Queue<T> intersection(Queue<T> queue1, Queue<T> queue2) {
        if (Valid.valid(queue1, queue2)) {
            Set<T> set = Sets.newHashSet(queue1);
            set.retainAll(queue2);
            return Lists.newLinkedList(set) ;
        }
        return Lists.newLinkedList();
    }

    /**
     * Map集合的交集只提供键的交集
     *
     * @param map1
     * @param map2
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> intersection(Map<K, V> map1, Map<K, V> map2) {
        Map<K, V> map = Maps.newHashMapWithExpectedSize(map1.size());
        if (Valid.valid(map1, map2)) {
            Set<K> setkey1 = Sets.newHashSet(map1.keySet());
            Set<K> setkey2 = Sets.newHashSet(map2.keySet());
            setkey1.retainAll(setkey2);
            for (K k : setkey1) {
                map.put(k, map1.get(k));
            }
        }
        return map;
    }

    ///////////////////////////////////////////////////////////////////////////////

    /**
     * 求俩个集合的并集
     */
    public static <T> List<T> unicon(List<T> list1, List<T> list2) {
        if (null == list1){
            list1 = Lists.newArrayListWithCapacity(0) ;
        }
        if (null == list2){
            list2 = Lists.newArrayListWithCapacity(0) ;
        }
        List<T> list = Lists.newArrayListWithCapacity(list1.size() + list2.size());
        list.addAll(list1);
        list.addAll(list2);
        return list;
    }

    public static <T> Set<T> unicon(Set<T> set1, Set<T> set2) {
        if (null == set1){
            set1= Sets.newHashSetWithExpectedSize(0) ;
        }
        if (null == set2){
            set2 = Sets.newHashSetWithExpectedSize(0) ;
        }

        Set<T> set = set1;
        set.addAll(set2);
        return set;
    }

    public static <T> Queue<T> unicon(Queue<T> queue1, Queue<T> queue2) {
        Queue<T> queue = Lists.newLinkedList() ;
        queue.addAll(queue1);
        queue.addAll(queue2);
        return queue;
    }

    public static <K, V> Map<K, V> unicon(Map<K, V> map1, Map<K, V> map2) {
        if (null == map1){
            map1 = Maps.newHashMapWithExpectedSize(0);
        }
        if (null == map2){
            map2 = Maps.newHashMapWithExpectedSize(0) ;
        }
        Map<K, V> map = Maps.newHashMapWithExpectedSize(map1.size()+map2.size());
        map.putAll(map1);
        map.putAll(map2);
        return map;
    }

    ///////////////////////////////////////////////////////////////////////////////

    /**
     * 求俩个集合的差集
     */
    public static <T> List<T> subtract(List<T> list1, List<T> list2) {
        if (null == list1){
            list1 = Lists.newArrayListWithCapacity(0);
        }
        if (null == list2){
            list2 = Lists.newArrayListWithCapacity(0);
        }
        List<T> list = Lists.newArrayListWithCapacity(list1.size()+list2.size());
        if (Valid.valid(list1)) {
            list.addAll(list1);
            list.removeAll(list2);
        }
        return list;
    }

    public static <T> Set<T> subtract(Set<T> set1, Set<T> set2) {
        if (null == set1){
            set1 = Sets.newHashSetWithExpectedSize(0);
        }
        if (null == set2){
            set2 = Sets.newHashSetWithExpectedSize(0);
        }
        Set<T> set = Sets.newHashSetWithExpectedSize(set1.size()+set2.size());
        if (Valid.valid(set1)) {
            set.addAll(set1);
            set.removeAll(set2);
        }
        return set;
    }

    public static <T> Queue<T> subtract(Queue<T> queue1, Queue<T> queue2) {
        Queue<T> queue = Lists.newLinkedList() ;
        if (Valid.valid(queue1)) {
            queue.addAll(queue1);
            queue.removeAll(queue2);
        }
        return queue;
    }

    public static <K, V> Map<K, V> subtract(Map<K, V> map1, Map<K, V> map2) {
        if (null == map1){
            map1 = Maps.newHashMapWithExpectedSize(0);
        }
        if (null == map2){
            map2 = Maps.newHashMapWithExpectedSize(0) ;
        }
        Map<K, V> map = Maps.newHashMapWithExpectedSize(map1.size()+map2.size());
        if (Valid.valid(map1, map2)) {
            Set<K> setkey1 = Sets.newHashSet(map1.keySet());
            Set<K> setkey2 = Sets.newHashSet(map2.keySet());
            for (K k : setkey2) {
                setkey1.remove(k);
            }
            for (K k : setkey1) {
                map.put(k, map1.get(k));
            }
        }
        return map;

    }

    /**
     * 将List以separator链接并以字符串的形式返回
     */
    public static <T> String join(List<T> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T aList : list) {
            sb.append(aList.toString()).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * 将queue以separator链接并以字符串的形式返回
     *
     * @param queue
     * @param separator
     * @param <T>
     * @return
     */
    public static <T> String join(Queue<T> queue, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T t : queue) {
            sb.append(t.toString()).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }

    /**
     * 将set以separator链接并以字符串的形式返回
     *
     * @param set
     * @param separator
     * @param <T>
     * @return
     */
    public static <T> String join(Set<T> set, String separator) {
        StringBuilder sb = new StringBuilder();
        for (T t : set) {
            sb.append(t.toString()).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }

    /**
     * 将Map以separator链接并以字符串的形式返回
     *
     * @return
     */
    public static <K,V> String join(Map<K,V> map, String separator, String separator1) {
        if(map == null || map.size() == 0){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(String.valueOf(entry.getKey())).append(separator1)
                    .append(String.valueOf(entry.getValue())).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }

    /**
     * 将map的key以separator链接并以字符串的形式返回
     *
     * @param map
     * @param separator
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> String keyJoin(Map<K, V> map, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(String.valueOf(entry.getKey())).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }

    /**
     * 将map的value以separator链接并以字符串的形式返回
     *
     * @param map
     * @param separator
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> String valueJoin(Map<K, V> map, String separator) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<K, V> entry : map.entrySet()) {
            sb.append(String.valueOf(entry.getValue())).append(separator);
        }
        return sb.toString().substring(0, sb.toString().length() - separator.length());
    }
}
