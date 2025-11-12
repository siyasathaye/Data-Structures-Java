public class DataFrame {
    private HashTable<SeriesV2<Object>> tabularData;
    private int numRows;
    private int numCols;

    public DataFrame() {
        tabularData = new HashTable<>();
        numRows = 0;
        numCols = 0;
    }
    public DataFrame(String _k, SeriesV2<Object> _series) {
        tabularData = new HashTable<>();
        numRows = 0;
        numCols = 0;
        tabularData.insert(_k, _series);
        numRows = _series.getLength();
        numCols = 1;
    }
    public SeriesV2<Object> colLoc(String k) {
        return tabularData.lookup(k);
    }
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("printing the dataframe ...\n==================\n");
        for (String col : tabularData.getValidKeys()) {
            output.append("[colName:\t").append(col).append("]\n");
            output.append(tabularData.lookup(col).toString()).append("\n");
        }
        return output.toString();
    }
    public int getNumRows() {
        return numRows;
    }
    public int getNumCols() {
        return numCols;
    }
    public String[] getColNames() {
        String[] keys = tabularData.getValidKeys();
        String[] result = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            result[i] = keys[i];
        }
        return result;
    }
    public void addColumn(String k, SeriesV2<Object> s) throws IllegalArgumentException{
        if (s.getLength() != numRows && numCols > 0)
            throw new IllegalArgumentException("addColumn(String k, SeriesV2<Object> s): the length of s does not match the dataframe's # of rows");
        tabularData.insert(k, s);
        if (numCols == 0) numRows = s.getLength();
        numCols++;
    }
    public void removeColumn(String k) {
        tabularData.delete(k);
        numCols--;
        if (numCols == 0)
            numRows = 0;
    }
}
