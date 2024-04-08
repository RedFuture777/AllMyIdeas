package org.example.test;

/**
 * @description: 链表使用
 * @author: muqingfeng
 * @date: 2024/4/5 16:25
 */
public class ListNodeTest {
    public static void main(String[] args) {
        //删除链表中指定的节点
        ListNode listNode = buildListNode(new int[]{1, 2, 6, 3, 4, 5, 6});
        ListNode listNode1 = removeElements(listNode, 6);

    }
    public static ListNode removeElements(ListNode head, int val) {
        if(head == null){
            return head;
        }
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy;
        ListNode cur = head;
        while(cur != null){
            if(cur.val == val){
                pre.next = cur.next;
            }else{
                pre = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }


    //给定一个数组，构建一个ListNode
    public static ListNode buildListNode(int[] nums){
        ListNode tar = new ListNode();
        int index = 0;
        while(index < nums.length){
            tar.val = index;
            tar.next = new ListNode();
            tar = tar.next;
        }
        return tar;
    }
}
