package com.example.final6205;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Sorter {

    /**
     * 对给定的文件列表按大小进行快速排序（降序）。
     *
     * @param list 要排序的文件列表
     */
    public static void quickSortFilesBySize(List<Map.Entry<String, Long>> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private static void quickSort(List<Map.Entry<String, Long>> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);

            quickSort(list, low, pi - 1);
            quickSort(list, pi + 1, high);
        }
    }

    private static int partition(List<Map.Entry<String, Long>> list, int low, int high) {
        long pivot = list.get(high).getValue();
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (list.get(j).getValue() > pivot) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}