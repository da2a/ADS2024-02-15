package by.it.group310901.maydanyuk.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;


class Node<E>{
    Node<E> next;//Ссылка на следующий узел.
    Node<E> prev;//предыдущий
    E value;//значение узла
    public Node(E e){
        value=e;
        prev=null;
        next=null;
    }
}//реализует двустороннюю связную очередь, используя узлы Node
public class MyLinkedList<E> implements Deque<E> {

    Node<E> elems = new Node<>(null);//начальный узел
    Node<E> head = elems;//голова списка
    Node<E> tail = elems;//конец
    int size = 0;//размер списка

//Создает строку, представляющую элементы списка в квадратных скобках и разделенных запятыми
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        String inter = "";
        Node<E> temp = head;
        while (temp.prev != null) {
            temp = temp.prev;
            str.append(inter).append(temp.value);
            inter = ", ";
        }
        str.append("]");
        return str.toString();

    }

    @Override
    public void addFirst(E e) {
        head.value = e;
        //Создает новый узел temp, который становится новой головой списка
        Node<E> temp = new Node<>(null);
        //Устанавливает ссылки prev и next для нового узла
        temp.prev = head;
        head.next = temp;
        head = temp;
        size++;
    }
//Добавляет элемент в конец списка
    @Override
    public void addLast(E e) {
        Node<E> temp = new Node<>(e);
        tail.prev = temp;
        temp.next = tail;
        tail = temp;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }
//Удаляет и возвращает первый элемент из начала списка
    @Override
    public E pollFirst() {
        head = head.prev;
        head.next.prev = null;
        head.next = null;
        size--;
        return head.value;
    }
//последний
    @Override
    public E pollLast() {
        E value = tail.value;
        tail = tail.next;
        tail.prev.next = null;
        tail.prev = null;
        size--;
        return value;
    }

    @Override
    public E getFirst() {
        return head.prev.value;
    }

    @Override
    public E getLast() {
        return tail.value;
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean add(E e) {
        Node<E> temp = new Node<>(e);
        temp.next = tail;
        tail.prev = temp;
        tail = temp;
        size++;
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        head = head.prev;
        head.next.prev = null;
        head.next = null;
        size--;
        return head.value;
    }

    @Override
    public E element() {
        return head.prev.value;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

//удаления узла.
    private E theRemover(Node<E> elem) {
        Node<E> swap = elem.next;
        swap.prev = elem.prev;
        if (elem.prev == null) {
            elem.next = null;
        } else {
            elem.prev.next = swap;
        }
        size--;
        return elem.value;
    }
//Удаляет элемент по индексу
    public E remove(int i) {
        Node<E> temp = head.prev;
        for (int j = 0; j < i; j++) {
            temp = temp.prev;
        }
        return theRemover(temp);
    }
//Удаляет первый найденный элемент, равный заданному объекту
    @Override
    public boolean remove(Object o) {
        Node<E> temp = head.prev;
        while (temp.prev != null && !(temp.value.equals(o))) {
            temp = temp.prev;
        }
        if (temp.value.equals(o)) {
            theRemover(temp);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Iterator<E> descendingIterator() {
        return null;
    }
}
