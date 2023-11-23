package org.nature.net.interceptor;

import org.nature.net.HttpPackage;

import java.util.Comparator;

public class InterceptorHolder {

    private Node head;
    private Node last;

    public void add(Node node) {
        if (head == null) {
            head = node;
            last = head;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }
    }

    public HttpPackage doIntercept(HttpPackage httpPackage, boolean isReversion) {
        if (isReversion)
            return doInterceptReversion(last, httpPackage);
        return doInterceptOrder(head, httpPackage);
    }


    public HttpPackage doInterceptOrder(Node node, HttpPackage httpPackage) {
        return node == null ? httpPackage : this.doInterceptOrder(node.next, node.item.intercept(httpPackage));
    }

    public HttpPackage doInterceptReversion(Node node, HttpPackage httpPackage) {
        return node == null ? httpPackage : this.doInterceptReversion(node.prev, node.item.intercept(httpPackage));
    }



    public void putInterceptor(Interceptor interceptor) {
        orderInsert(new Node(interceptor), (p1, p2) -> p2.item.getPriority() - p1.item.getPriority());
    }

    public void orderInsert(Node node, Comparator<Node> comparator) {
        if (head == null) {
            head = node;
            last = head;
            return;
        }

        Node tempLast = null;
        Node tempCurrent  = head;;

        while (tempCurrent != null) {
            if (comparator.compare(node, tempCurrent) > 0) {
                node.next = tempCurrent;
                tempCurrent.prev = node;
                if (tempLast != null) {
                    tempLast.next = node;
                    node.prev = tempLast;
                } else {
                    head = node;
                }
                return;
            }
            tempLast = tempCurrent;
            tempCurrent = tempCurrent.next;
        }
        //优先级最低挂在尾巴上
        tempLast.next = node;
        node.prev = tempLast;
    }

    protected static class Node {
        Interceptor item;
        Node next;
        Node prev;
        Node(){}

        Node(Interceptor item) {
            this.item = item;
        }
    }
}
