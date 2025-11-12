public class SeriesV2<T> implements Series<T> {
    private LL<T> seriesData;
    private BST<String,T> seriesDataBST;

    public SeriesV2(String[] _rowNames, T[] _data) {
        if (_data == null) {
            throw new NullPointerException("Series(String[] _index, T[] _data): _data can't be null. " +
                    "Terminating the program");
        }
        this.seriesData = new LL<>();
        this.seriesDataBST = new BST<>();

        try {
            if (_rowNames == null) {
                throw new NullPointerException();
            }

            if (_rowNames.length != _data.length) {
                throw new IllegalArgumentException("Series(String[] _index, T[] _data): " +
                        "the length of _index and _data must be the same");
            }

            for (String name : _rowNames) {
                if (name == null || name.isEmpty()) {
                    throw new IllegalArgumentException("Series(String[] _index, T[] _data): " +
                            "_rowNames is not valid");
                }
            }
            for (int i = 0; i < _rowNames.length; i++) {
                seriesData.appendNode(_rowNames[i], _data[i]);
                seriesDataBST.addNode(_rowNames[i], _data[i]);
            }
        } catch (NullPointerException e) {
            for (int i = 0; i < _data.length; i++) {
                seriesData.appendNode(String.valueOf(i), _data[i]);
                seriesDataBST.addNode(String.valueOf(i), _data[i]);
            }
        }
    }
    /**
     * Returns a string representation of the Series object.
     */
    public String toString() {
        return seriesData.toString();
    }

    /**
     * Returns the length of the series object.
     */
    public int getLength() {
        return seriesData.getLength();
    }

    /**
     * Returns the row names of this Series object.
     */
    public String[] getRowNames() {
        return seriesData.getIndexArray();
    }

    /**
     * Returns the data of this Series object as strings.
     */
    public String[] getData() {
        return seriesData.getDataArray();
    }

    /**
     * Adds a new pair of rowName and data at the end of the Series object.
     *
     * @param rn the row name to be added
     * @param d the Integer data value to be added
     */
    public void append(String rn, T d) {
        if (rn == null) rn = String.valueOf(getLength());
        seriesData.appendNode(rn, d);
        seriesDataBST.addNode(rn, d);
    }
    public T loc(String rn) throws NullPointerException, IllegalArgumentException {
        if (rn == null) {
            throw new NullPointerException("loc(String rn): rn can't be null");
        }

        if (rn.isEmpty()) {
            throw new IllegalArgumentException("loc(String rn): rn can't be an empty string");
        }

        BST<String, T>.BSTNode node = seriesDataBST.searchNode(rn);
        if (node != null) {
            return node.getData();
        }
        return null;
    }
    public T[] loc(String[] rn) throws NullPointerException, IllegalArgumentException {
        if (rn == null) {
            throw new NullPointerException("loc(String[] rn): rn[] can't be null");
        }
        if (rn.length == 0) {
            throw new IllegalArgumentException("loc(String[] rn): rn[] can't be an empty array");
        }
        T[] result = (T[]) new Object[rn.length];
        for (int i = 0; i < rn.length; i++) {
            T val = loc(rn[i]);
            result[i] = val;
        }
        return result;
    }

    public T iloc(int ind) {
        try {
            if (ind < 0 || ind >= seriesData.getLength()) {
                throw new IndexOutOfBoundsException("the index " + ind + " is not valid.. returning null");
            }
            String[] names = getRowNames();
            return loc(names[ind]);
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean drop(String rn) throws NullPointerException, IllegalArgumentException {
        if (rn == null) {
            throw new NullPointerException("drop(String rn): rn can't be null");
        }

        if (rn.isEmpty()){
            throw new IllegalArgumentException("drop(String rn): rn can't be an empty String");
        }
        if (seriesData.searchNode(rn) == null) {
            return false;
        }
        seriesData.removeNode(rn);
        try {
            seriesDataBST.removeNode(rn);
        }
        catch (IllegalArgumentException e) {

        }
        return true;
    }
    public void fillNull(T value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("fillNull(T value): value can't be null");
        }

        for (String rn : getRowNames()) {
            if (loc(rn) == null) {
                seriesData.updateNode(rn, value);
                try {
                    seriesDataBST.updateNode(rn, value);
                } catch (IllegalArgumentException e) {

                }
            }
        }

    }
}
