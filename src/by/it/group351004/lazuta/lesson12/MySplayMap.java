package lesson12;

import java.util.*;

public class MySplayMap implements NavigableMap<Integer, String> {

    private class TreeNode {
        TreeNode left;
        TreeNode right;
        Integer key;
        String value;

        public TreeNode(TreeNode left,TreeNode right, Integer key, String value) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.value = value;
        }
    }

    private TreeNode _root = null;
    private int _size = 0;

    private TreeNode rotateRight(TreeNode node) {
        TreeNode leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;
        return leftChild;
    }

    private TreeNode rotateLeft(TreeNode node) {
        TreeNode rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;
        return rightChild;
    }

    private TreeNode splay(TreeNode node, Integer key) {
        if (node == null || node.key.equals(key)) {
            return node;
        }
        // left child
        if (key.compareTo(node.key) < 0) {
            if (node.left == null) {
                return node;
            }
            TreeNode leftChild = node.left;
            // zig-zig
            if (key.compareTo(leftChild.key) < 0) {
                leftChild.left = splay(leftChild.left, key);
                node = rotateRight(node);
            }
            // zig-zag
            else if (key.compareTo(leftChild.key) > 0) {
                leftChild.right = splay(leftChild.right, key);
                if (leftChild.right != null) {
                    node.left = rotateLeft(leftChild);
                }
            }
            return node.left != null ? rotateRight(node) : node;
        }
        // right child
        else {
            if (node.right == null) {
                return node;
            }
            TreeNode rightChild = node.right;
            // zig-zig
            if (key.compareTo(rightChild.key) > 0) {
                rightChild.right = splay(rightChild.right, key);
                node = rotateLeft(node);
            }
            // zig-zag
            else if (key.compareTo(rightChild.key) < 0) {
                rightChild.left = splay(rightChild.left, key);
                if (rightChild.left != null) {
                    node.right = rotateRight(rightChild);
                }
            }
            return node.right != null ? rotateLeft(node) : node;
        }
    }

    private TreeNode[] split(Integer key) {
        if (_root == null) {
            return new TreeNode[] {null, null};
        }
        _root = splay(_root, key);
        if (key.compareTo(_root.key) > 0) {
            TreeNode right = _root.right;
            _root.right = null;
            return new TreeNode[] {_root,right};
        }
        else {
            TreeNode left = _root.left;
            _root.left = null;
            return new TreeNode[] {left,_root};
        }
    }

    private TreeNode merge(TreeNode left, TreeNode right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }

        left = splay(left, Integer.MAX_VALUE);
        left.right = right;
        return left;
    }

    private void clear(TreeNode node) {
        if (node != null) {
            clear(node.left);
            clear(node.right);
            node.left = node.right = null;
        }
    }

    private String toString(TreeNode node) {
        if (node == null) {
            return "";
        }
        String result = toString(node.left);
        result += node.key + "=" + node.value + ", ";
        return result + toString(node.right);
    }

    private boolean containsValue(TreeNode node, Object value) {
        if (node == null) {
            return false;
        }
        if (node.value.equals(value)) {
            return true;
        }
        return containsValue(node.right, value) || containsValue(node.left, value);
    }

    private void headMap(TreeNode node, Integer key, MySplayMap map) {
        if (node == null) {
            return;
        }
        if (node.key.compareTo(key) < 0) {
            map.put(node.key,node.value);
            headMap(node.right, key, map);
        }
        headMap(node.left, key, map);
    }

    private void tailMap(TreeNode node, Integer key, MySplayMap map) {
        if (node == null) {
            return;
        }
        if (node.key.compareTo(key) >= 0) {
            map.put(node.key, node.value);
            tailMap(node.left, key, map);
        }
        tailMap(node.right, key, map);
    }

    public String toString() {
        if (_root == null) {
            return "{}";
        }
        String result = toString(_root);
        return "{" + result.substring(0, result.length() - 2) + "}";

    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public boolean isEmpty() {
        return _size == 0;
    }

    @Override
    public void clear() {
        clear(_root);
        _root = null;
        _size = 0;
    }

    @Override
    public String put(Integer key, String value) {
        if (_root == null) {
            _root = new TreeNode(null, null, key, value);
            _size++;
            return null;
        }
        TreeNode[] trees = split(key);
        if (trees[1] != null && trees[1].key.equals(key)) {
            String oldValue = trees[1].value;
            trees[1].value = value;
            _root = merge(trees[0],trees[1]);
            return oldValue;
        }
        TreeNode newNode = new TreeNode(null, null, key, value);
        _root = merge(merge(trees[0], newNode), trees[1]);
        _size++;
        return null;
    }

    @Override
    public String remove(Object o) {
        Integer key = (Integer)o;
        TreeNode[] trees = split(key);

        if (trees[1] == null || !trees[1].key.equals(key)) {
            _root = merge(trees[0],trees[1]);
            return null;
        }

        String oldValue = trees[1].value;
        _root = merge(trees[0],trees[1].right);
        _size--;
        return oldValue;
    }

    @Override
    public String get(Object o) {
        Integer key = (Integer)o;
        _root = splay(_root, key);
        return _root != null && _root.key.equals(key) ? _root.value : null;
    }

    @Override
    public boolean containsKey(Object o) {
        return get(o) != null;
    }

    @Override
    public boolean containsValue(Object o) {
        return containsValue(_root, o);
    }

    @Override
    public Integer firstKey() {
        if (_root == null) {
            return null;
        }
        TreeNode cur = _root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    @Override
    public Integer lastKey() {
        if (_root == null) {
            return null;
        }
        TreeNode cur = _root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer key) {
        MySplayMap map = new MySplayMap();
        headMap(_root, key, map);
        return map;
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer key) {
        MySplayMap map = new MySplayMap();
        tailMap(_root, key, map);
        return map;
    }

    @Override
    public Integer lowerKey(Integer key) {
        TreeNode cur = _root;
        TreeNode low = null;
        while (cur != null) {
            if (key.compareTo(cur.key) > 0) {
                low = cur;
                cur = cur.right;
            }
            else {
                cur = cur.left;
            }
        }
        return low != null ? low.key : null;
    }

    @Override
    public Integer floorKey(Integer key) {
        TreeNode cur = _root;
        TreeNode low = null;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                return cur.key;
            }
            else if (key.compareTo(cur.key) > 0) {
                low = cur;
                cur = cur.right;
            }
            else {
                cur = cur.left;
            }
        }
        return low != null ? low.key : null;
    }

    @Override
    public Integer ceilingKey(Integer key) {
        TreeNode cur = _root;
        TreeNode higher = null;
        while (cur != null) {
            if (key.compareTo(cur.key) == 0) {
                return cur.key;
            }
            if (key.compareTo(cur.key) < 0) {
                higher = cur;
                cur = cur.left;
            }
            else {
                cur = cur.right;
            }
        }
        return higher != null ? higher.key : null;
    }

    @Override
    public Integer higherKey(Integer key) {
        TreeNode cur = _root;
        TreeNode higher = null;
        while (cur != null) {
            if (key.compareTo(cur.key) < 0) {
                higher = cur;
                cur = cur.left;
            }
            else {
                cur = cur.right;
            }
        }
        return higher != null ? higher.key : null;
    }

    @Override
    public Entry<Integer, String> lowerEntry(Integer integer) {
        return null;
    }

    @Override
    public Entry<Integer, String> floorEntry(Integer integer) {
        return null;
    }

    @Override
    public Entry<Integer, String> ceilingEntry(Integer integer) {
        return null;
    }

    @Override
    public Entry<Integer, String> higherEntry(Integer integer) {
        return null;
    }

    @Override
    public Entry<Integer, String> firstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> lastEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollFirstEntry() {
        return null;
    }

    @Override
    public Entry<Integer, String> pollLastEntry() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> descendingMap() {
        return null;
    }

    @Override
    public NavigableSet<Integer> navigableKeySet() {
        return null;
    }

    @Override
    public NavigableSet<Integer> descendingKeySet() {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> subMap(Integer integer, boolean b, Integer k1, boolean b1) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> headMap(Integer integer, boolean b) {
        return null;
    }

    @Override
    public NavigableMap<Integer, String> tailMap(Integer integer, boolean b) {
        return null;
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer integer, Integer k1) {
        return null;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> map) {

    }

    @Override
    public Set<Integer> keySet() {
        return null;
    }

    @Override
    public Collection<String> values() {
        return null;
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        return null;
    }
}
