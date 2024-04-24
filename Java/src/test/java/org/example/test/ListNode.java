package org.example.test;

public class ListNode {
    //节点的值
    public int val;

    //下一个节点
    public ListNode next;

    public ListNode(){}

    public ListNode(int val){
        this.val = val;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}
