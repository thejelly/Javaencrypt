package com.example.final6205;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree<K extends Comparable<K>, V> {
    private TreeNode<K, V> root;

    public void put(K key, V value) {
        TreeNode<K, V> newNode = new TreeNode<>(key, value, null);
        if (root == null) {
            root = newNode;
            root.red = false;  // 根节点必须是黑色
        } else {
            insertRecursive(root, newNode);
            fixAfterInsertion(newNode);
        }
    }

    private void insertRecursive(TreeNode<K, V> current, TreeNode<K, V> newNode) {
        int compare = newNode.key.compareTo(current.key);
        if (compare < 0) {
            if (current.left == null) {
                current.left = newNode;
                newNode.parent = current;
            } else {
                insertRecursive(current.left, newNode);
            }
        } else if (compare > 0) {
            if (current.right == null) {
                current.right = newNode;
                newNode.parent = current;
            } else {
                insertRecursive(current.right, newNode);
            }
        }
    }

    private void fixAfterInsertion(TreeNode<K, V> node) {
        while (node != null && node != root && node.parent.red) {
            // 父节点是祖父节点的左子节点的情况
            if (parentOf(node) == leftOf(parentOf(parentOf(node)))) {
                TreeNode<K, V> y = rightOf(parentOf(parentOf(node))); // 叔叔节点
                if (colorOf(y) == true) { // 叔叔节点是红色
                    setColor(parentOf(node), false); // 父节点着色为黑
                    setColor(y, false); // 叔叔节点着色为黑
                    setColor(parentOf(parentOf(node)), true); // 祖父节点着色为红
                    node = parentOf(parentOf(node)); // 将当前节点指向祖父节点
                } else {
                    if (node == rightOf(parentOf(node))) { // 当前节点是父节点的右子节点
                        node = parentOf(node);
                        rotateLeft(node); // 左旋父节点
                    }
                    setColor(parentOf(node), false); // 父节点着色为黑
                    setColor(parentOf(parentOf(node)), true); // 祖父节点着色为红
                    rotateRight(parentOf(parentOf(node))); // 右旋祖父节点
                }
            } else { // 父节点是祖父节点的右子节点的情况
                TreeNode<K, V> y = leftOf(parentOf(parentOf(node))); // 叔叔节点
                if (colorOf(y) == true) { // 叔叔节点是红色
                    setColor(parentOf(node), false); // 父节点着色为黑
                    setColor(y, false); // 叔叔节点着色为黑
                    setColor(parentOf(parentOf(node)), true); // 祖父节点着色为红
                    node = parentOf(parentOf(node)); // 将当前节点指向祖父节点
                } else {
                    if (node == leftOf(parentOf(node))) { // 当前节点是父节点的左子节点
                        node = parentOf(node);
                        rotateRight(node); // 右旋父节点
                    }
                    setColor(parentOf(node), false); // 父节点着色为黑
                    setColor(parentOf(parentOf(node)), true); // 祖父节点着色为红
                    rotateLeft(parentOf(parentOf(node))); // 左旋祖父节点
                }
            }
        }
        root.red = false; // 根节点总是黑色
    }


    private void rotateLeft(TreeNode<K, V> node) {
        if (node != null) {
            TreeNode<K, V> r = node.right;
            node.right = r.left;
            if (r.left != null) r.left.parent = node;
            r.parent = node.parent;
            if (node.parent == null) root = r;
            else if (node.parent.left == node) node.parent.left = r;
            else node.parent.right = r;
            r.left = node;
            node.parent = r;
        }
    }

    private void rotateRight(TreeNode<K, V> node) {
        if (node != null) {
            TreeNode<K, V> l = node.left;
            node.left = l.right;
            if (l.right != null) l.right.parent = node;
            l.parent = node.parent;
            if (node.parent == null) root = l;
            else if (node.parent.right == node) node.parent.right = l;
            else node.parent.left = l;
            l.right = node;
            node.parent = l;
        }
    }

    private boolean colorOf(TreeNode<K, V> node) {
        return node == null ? false : node.red;
    }

    private TreeNode<K, V> parentOf(TreeNode<K, V> node) {
        return node == null ? null : node.parent;
    }

    private TreeNode<K, V> leftOf(TreeNode<K, V> node) {
        return node == null ? null : node.left;
    }

    private TreeNode<K, V> rightOf(TreeNode<K, V> node) {
        return node == null ? null : node.right;
    }

    private void setColor(TreeNode<K, V> node, boolean red) {
        if (node != null) node.red = red;
    }



    public List<TreeNode<K, V>> inorderTraversal() {
        List<TreeNode<K, V>> entries = new ArrayList<>();
        inorder(root, entries);
        return entries;
    }

    private void inorder(TreeNode<K, V> node, List<TreeNode<K, V>> entries) {
        if (node != null) {
            inorder(node.left, entries);  // Visit left subtree
            entries.add(new TreeNode<>(node.key, node.value));  // Visit node
            inorder(node.right, entries);  // Visit right subtree
        }

        // 更多方法，如获取值等
    }
}
