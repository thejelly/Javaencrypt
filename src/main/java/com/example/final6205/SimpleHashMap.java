package com.example.final6205;

import java.util.ArrayList;
import java.util.List;

public class SimpleHashMap<K extends Comparable<K>, V> {
    private RedBlackTree<K, V>[] table;
    private static final int INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public SimpleHashMap() {
        table = new RedBlackTree[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            table[i] = new RedBlackTree<>();
        }
    }

    private int hash(K key) {
        int hashCode = key.hashCode();
        return (hashCode ^ (hashCode >>> 16)) & (INITIAL_CAPACITY - 1);
    }

    public void put(K key, V value) {
        int index = hash(key);
        table[index].put(key, value);
    }

    public V get(K key) {
        int index = hash(key);
        // 实现查找逻辑，返回找到的值或 null
        return null;  // Placeholder
    }

    public List<TreeNode<K, V>> midTraversal() {
        List<TreeNode<K, V>> allNodes = new ArrayList<>();
        for (RedBlackTree<K, V> tree : table) {
            if (tree != null) {
                allNodes.addAll(tree.inorderTraversal());
            }
        }
        return allNodes;
    }
}
