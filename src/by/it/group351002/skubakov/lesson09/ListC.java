package by.it.group351002.skubakov.lesson09;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListC<E> implements List<E> {

    //Создайте аналог списка БЕЗ использования других классов СТАНДАРТНОЙ БИБЛИОТЕКИ
//a
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Обязательные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    static final int defSize = 8;

    E[] my_list;
    int size = 0;

    //constructor (creating the List)
    public ListC() {
        this(defSize);
    }
    public ListC(int size)
    {
        my_list = (E[]) new Object[size];
    }

    @Override
    public String toString() {

        StringBuilder sb= new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++)
        {
            sb.append(my_list[i]);
            if (i < size - 1)
            {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean add(E e) {

        if (size>= my_list.length)
        {
            E[] new_list = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < my_list.length; i++)
            {
                new_list[i] = my_list[i];
            }
            my_list = new_list;
        }
        my_list[size] = e;
        size++;
        return true;
    }

    @Override
    public E remove(int index) {

        if (index < 0 || index >= size)
        {
            return null;
        }

        E _rItem = my_list[index];

        for (int i = index; i < size - 1; i++)
        {
            my_list[i] = my_list[i + 1];
        }

        size--;

        return _rItem;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size)
        {
            return;
        }

        if (size>= my_list.length)
        {
            E[] _cList = (E[]) new Object[my_list.length * 2];
            for (int i = 0; i < index; i++)
            {
                _cList[i] = my_list[i];
            }

            for (int i = index+1; i <= size; i++) {
                _cList[i] = my_list[i - 1];
            }

            my_list = _cList;
        }
        else {
            for (int i = size; i > index; i--) {
                my_list[i] = my_list[i - 1];
            }
        }

        my_list[index] = element;
        size++;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++)
        {
            if (o.equals(my_list[i])) {

                for (int j = i; j < size-1; j++) {
                    my_list[j] = my_list[j + 1];
                }
                size--;

                return true;
            }
        }

        return false;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index > size)
        {
            return null;
        }

        E _setItem = my_list[index];
        my_list[index] = element;
        return _setItem;
    }


    @Override
    public boolean isEmpty() {
        return size==0;
    }


    @Override
    public void clear() {
        my_list = (E[]) new Object[defSize];
        size = 0;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++)
        {
            if (o.equals(my_list[i]))
            {
                return i;
            }
        }

        return -1;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size)
        {
            return null;
        }

        return my_list[index];
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(my_list[i]))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--)
        {
            if (o.equals(my_list[i]))
            {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean containsAll(Collection<?> c) {

        for (Object item : c)
        {
            if (!contains(item))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) return false;
        for (E item : c)
        {
            if(!add(item))
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) return false;
        for (Object item : c)
        {

            add(index, (E) item);
            index++;

        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {

        boolean removed = false;

        for (int i = 0; i < size; i++)
        {
            if (c.contains(my_list[i]))
            {
                remove(i);
                i--;
                removed = true;
            }
        }

        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {

        boolean retained = false;

        for (int i = 0; i < size; ++i)
        {
            if (!c.contains(my_list[i]))
            {
                remove(i);
                i--;
                retained = true;
            }
        }

        return retained;
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    //////               Опциональные к реализации методы             ///////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ////////        Эти методы имплементировать необязательно    ////////////
    ////////        но они будут нужны для корректной отладки    ////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    @Override
    public Iterator<E> iterator() {
        return null;
    }

}
