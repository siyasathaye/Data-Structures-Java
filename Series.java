public interface Series<T> {
    public String toString();
    public int getLength();
    public String[] getRowNames();
    public String[] getData();
    public void append(String rn, T d);
    public T loc(String rn) throws NullPointerException, IllegalArgumentException;
    public T[] loc(String[] rn) throws NullPointerException, IllegalArgumentException;
    public T iloc(int ind);
    public boolean drop(String rn) throws NullPointerException, IllegalArgumentException;
    public void fillNull(T value) throws IllegalArgumentException;
}