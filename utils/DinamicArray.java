package utils;

public class DinamicArray {
    private static final int CHUNK=10;
    private int number;
    private Object[] array;
    
    public DinamicArray(int initialcap) {
        if (initialcap<=0) throw new IllegalArgumentException("El tamaño mínimo es 1");
        array=new Object[initialcap];
        number=0;
    }

    public int length() { return number; }
    
    private void resize() {
        if (number==array.length) {
            Object[] newarray=new Object[array.length+CHUNK];
            System.arraycopy(array, 0, newarray, 0, number);
            array=newarray;
        }
    }
    
    public boolean add(Object obj) {
        resize();
        array[number]=obj;
        number++;
        return true;
    }
    
    public Object get(int idx) throws IndexOutOfBoundsException {
        if (idx<0 || idx>=number) throw new IndexOutOfBoundsException("Índice fuera de rango");
        return array[idx];
    }
    
    public Integer position(Object obj) {
        for(int i=0;i<number;i++) {
            if (array[i].equals(obj)) return i;
        }
        return null;
    }
    
    public Object remove(int idx) throws IndexOutOfBoundsException {
        if (idx<0 || idx>=number) throw new IndexOutOfBoundsException("Índice fuera de rango");
        Object old=array[idx];
        System.arraycopy(array,idx+1, array, idx, number-(idx+1));
        number--;
        return old;
    }
    
    public Object[] toArray() {
        Object[] newarray=new Object[number];
        System.arraycopy(array, 0, newarray, 0, number);
        return newarray;
    }
}
