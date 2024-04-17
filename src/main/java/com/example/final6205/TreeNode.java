package com.example.final6205;

class TreeNode<K, V> {
    K key;
    V value;
    TreeNode<K, V> parent;
    TreeNode<K, V> left;
    TreeNode<K, V> right;
    boolean red;

    TreeNode(K key, V value, TreeNode<K, V> parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
        this.left = null;
        this.right = null;
        this.red = true; // 新节点默认为红色
    }
    TreeNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
        this.red = true; // 新节点默认为红色
    }
}
