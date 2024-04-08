package org.example;

import org.example.test.ListNode;
import org.example.util.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppApplicationTest {

    private final long maxRequire = 10l;

    @Autowired
    private RedisUtils redisUtils;

    private static final String SCORE_RANK = "score_name";

    @Test
    public void transfer() {
        int[] nums = new int[]{4, 3, 4};
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.getOrDefault(nums[i], nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }
        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                majorityEntry = entry;
            }
        }
        System.out.println(majorityEntry.getKey());

    }

    private int[] prices = {7, 1, 5, 3, 6, 4};

    @Test
    public void maxProfit() {
        int ans = 0;
        int n = prices.length;
        for (int i = 1; i < n; i++) {
            ans += Math.max(0, prices[i] - prices[i - 1]);
            System.out.println(i);
        }
        System.out.println(ans);
    }


    @Test
    public void RandomSet() {
        //1.查找指定数字的下标
//        int[] nums = {-1, 0, 3, 5, 9, 12};
//        int search = search(nums, 2);
//        System.out.println(search);
        //2.打印一个二维数组
        generateMatrix(3);
        //3.
//        System.out.println(minSubArrayLen(4, new int[]{1,4,4}));

    }

    public int[][] generateMatrix(int n) {
        int l = 0, r = n - 1, t = 0, b = n - 1;
        int[][] mat = new int[n][n];
        int num = 1, tar = n * n;
        while (num <= tar) {
            for (int i = l; i <= r; i++) mat[t][i] = num++; // left to right.
            t++;
            for (int i = t; i <= b; i++) mat[i][r] = num++; // top to bottom.
            r--;
            for (int i = r; i >= l; i--) mat[b][i] = num++; // right to left.
            b--;
            for (int i = b; i >= t; i--) mat[i][l] = num++; // bottom to top.
            l++;
        }
        return mat;
    }


    public int minSubArrayLen(int target, int[] nums) {
        int len = nums.length;
        int left = 0, right = 0;
        int sum = 0;
        int res = Integer.MAX_VALUE;
        while (left <= right && left < len) {
            if (sum >= target) {
                res = Math.min(res, right - left);
                sum -= nums[left];
                left++;
                continue;
            }
            if (right < len) {
                sum += nums[right++];
                continue;
            }
            if (left != 0 && right - left > res) {
                sum += nums[left++];
                continue;
            }
            if (right <= len) {
                return res == Integer.MAX_VALUE ? 0 : res;
            }
        }
        return res;
    }


    public int search(int[] nums, int target) {
        int len = nums.length;
        int left = 0;
        int right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int removeElement(int[] nums, int val) {
        int len = nums.length;
        int cur = 0;
        for (int i = 1; i < len; i++) {
            if (nums[i] != val && cur != i) {
                int tar = nums[i];
                nums[i] = nums[cur];
                nums[cur] = tar;
                cur++;
            }
        }
        return cur;
    }


    @Test
    public void testRedis() {
//        redisTemplate.opsForValue().set(UUID.randomUUID().toString().replace("-",""), "k1");
        Jedis jedis = new Jedis("121.40.166.14", 6379);
        jedis.auth("redis123");
        String set = jedis.set("aa", "bb");
        System.out.println(set);


        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>("张三" + i, 1D + i);
            tuples.add(tuple);
        }
        System.out.println("循环时间:" + (System.currentTimeMillis() - start));
        Long num = redisUtils.zAdd(SCORE_RANK, tuples);
        System.out.println("批量新增时间:" + (System.currentTimeMillis() - start));
        System.out.println("受影响行数:" + num);
    }

    @Test
    public Boolean canJump() {
        int[] nums = {3, 2, 1, 0, 4};
        int n = nums.length;
        int most;
        for (int i = 0; i < n - 1; i++) {
            most = nums[i];
            if (most > 0 && i + most < n - 1 && most + nums[i + most] < n - 1) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }


    //--------链表
    @Test
    public void ListNodeTest() {
        //删除链表中指定的节点
        ListNode listNode = buildListNode(new int[]{1, 2, 6, 3, 4, 5, 6});
        ListNode res = removeElements4(listNode, 6);
        System.out.println(Arrays.toString(buildList(res)));
        //反转链表
        ListNode head = buildListNode(new int[]{1, 2, 3, 4, 5});
        reverseList(head);
    }

    public ListNode reverseList(ListNode head) {
        ListNode res = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = res;
            res = cur;
            cur = next;
        }
        return res;
    }


    public static ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        //添加虚拟头节点
        ListNode dummy = new ListNode(-1, head);
        ListNode pre = dummy;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return dummy.next;
    }

    public static ListNode removeElements2(ListNode head, int val) {
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return head;
        }

        ListNode pre = head;
        ListNode cur = head.next;
        while (cur != null) {
            if (cur.val == val) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }

    /**
     * 不添加虚拟节点and pre Node方式
     * 时间复杂度 O(n)
     * 空间复杂度 O(1)
     *
     * @param head
     * @param val
     * @return
     */
    public static ListNode removeElements3(ListNode head, int val) {
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return head;
        }
        //此时head 已经不等于val
        ListNode cur = head;
        while (cur != null) {
            while (cur.next != null && cur.next.val == val) {
                cur.next = cur.next.next;
            }
            cur = cur.next;
        }
        return head;
    }

    //递归
    public ListNode removeElements4(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        head.next = removeElements(head.next, val);
        return head.val == val ? head.next : head;
    }


    //给定一个数组，构建一个ListNode
    public static ListNode buildListNode(int[] nums) {
        // 创建一个哨兵节点（dummy head）
        ListNode dummy = new ListNode(0);
        // 当前节点指针初始化指向哨兵节点
        ListNode current = dummy;
        // 遍历数组元素
        for (int num : nums) {
            // 为当前元素创建一个新节点，将其设置为当前节点的下一个节点
            current.next = new ListNode(num);
            // 移动当前节点指针到刚创建的节点
            current = current.next;
        }
        // 由于最开始我们创建的是哨兵节点，链表的实际头节点是哨兵节点的下一个节点
        return dummy.next;
    }

    //给定一个ListNode, 构建一个数组
    public static int[] buildList(ListNode head) {
        List<Integer> tar = new ArrayList<>();
        // 遍历ListNode，直到遇到null节点
        while (head != null) {
            tar.add(head.val);
            head = head.next; // 移动head指针到下一个节点
        }
        // 创建一个与tar列表大小相同的数组
        int[] nums = new int[tar.size()];
        // 将tar列表中的值复制到数组中
        for (int i = 0; i < tar.size(); i++) {
            nums[i] = tar.get(i);
        }
        return nums;
    }


    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int len1 = nums1.length;
        int len2 = nums2.length;
        int[] res = new int[len1 + len2];
        int index = 0, index1 = 0, index2 = 0;
        while (index1 < len1 && index2 < len2) {
            int num1 = nums1[index1], num2 = nums2[index2];
            if (num1 == num2) {
                if (index == 0 || num1 != res[index - 1]) {
                    res[index++] = num1;
                }
                index1++;
                index2++;
            } else if (num1 < num2) {
                index1++;
            } else {
                index2++;
            }
        }
        return Arrays.copyOfRange(res, 0, index);
    }


    @Test
    public void TestMap() {
        System.out.println(isHappy(121));
    }


    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int tmp = getSum(n);
        while (!set.contains(tmp)) {
            if(set.size() == 0){
                set.add(tmp);
            }
            if (tmp == 1) {
                return true;
            }
            set.add(tmp);
            tmp = getSum(tmp);
        }
        return false;
    }

    public int getSum(int num) {
        int sum = 0;
        while (num != 0) {
            int remain = num % 10;//余数
            num = num / 10;
            sum += remain * remain;
        }
        return sum;
    }


}
