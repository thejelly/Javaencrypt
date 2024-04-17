package com.example.final6205;

import java.util.List;

public class SimpleDeque<E> {
    private Node<E> head; // Front node of the deque
    private Node<E> tail; // Rear node of the deque
    private int size; // Number of elements in the deque

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(E data) {
            this.data = data;
        }
    }

    public SimpleDeque() {
        head = null;
        tail = null;
        size = 0;
    }
    public SimpleDeque(List<E> list) {
        this(); // Calls the default constructor to initialize head, tail, and size
        for (E item : list) {
            addLast(item); // Add each element to the end of the deque
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (isEmpty()) {
            head = tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }
        E data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        } else {
            head.prev = null;
        }
        size--;
        return data;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }
        E data = tail.data;
        tail = tail.prev;
        if (tail == null) {
            head = null;
        } else {
            tail.next = null;
        }
        size--;
        return data;
    }

    public E peekFirst() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    public E peekLast() {
        if (isEmpty()) {
            return null;
        }
        return tail.data;
    }
}
