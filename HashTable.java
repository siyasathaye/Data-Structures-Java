public class HashTable<V> {
    private static final Object BRIDGE = new
            String("[BRIDGE]".toCharArray());
    private int size;
    private int capacity;
    private String[] keys;
    private V[] values;

    public HashTable() {
        this.capacity = 4;
        this.size = 0;
        this.keys = new String[capacity];
        this.values = (V[]) new Object[capacity];
    }
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("printing the hash table ...\n==================\n");
        for (int i = 0; i < capacity; i++) {
            output.append("index:\t").append(i).append(",\tkey:\t").append(keys[i]).append(",\tdata:\t").append(values[i]).append("\n");
        }
        return output.toString();
    }
    public int getSize() {
        return size;
    }
    public int getCapacity() {
        return capacity;
    }
    public String[] getKeyArray() {
        String[] result = new String[capacity];
        for (int i = 0; i < capacity; i++) {
            result[i] = keys[i];
        }
        return result;
    }
    public V[] getDataArray() {
        V[] result = (V[]) new Object[capacity];
        for (int i = 0; i < capacity; i++) {
            result[i] = values[i];
        }
        return result;
    }
    public String[] getValidKeys() {
        int count = 0;
        for (String key : keys) {
            if (key != null && key != BRIDGE) {
                count++;
            }
        }
        String[] result = new String[count];
        int index = 0;
        for (String key : keys) {
            if (key != null && key != BRIDGE) {
                result[index++] = key;
            }
        }
        return result;
    }
    public int getHashIndex(String k) {
        int hashValue = 0;
        for (int i = 0; i < k.length(); i++) {
            int letter = k.charAt(i) - 96;
            hashValue += (hashValue * 27 + letter);
        }
        return hashValue % this.getCapacity();
    }
    public V lookup(String k) throws NullPointerException {
        if (k == null) {
            throw new NullPointerException("lookup(String key): key is null");
        }
        int index = getHashIndex(k);
        int start = index;
        while (keys[index] != null) {
            if (keys[index] != BRIDGE && keys[index].equals(k)) {
                return values[index];
            }
            index = (index + 1) % capacity;
            if (index == start) {
                break;
            }
        }
        return null;
    }
    public int insert(String k, V v) throws NullPointerException{
        if (k == null) {
            throw new NullPointerException("insert(String k, V v): k is null");
        }
        if (v == null) {
            throw new NullPointerException("insert(String k, V v): v is null");
        }

        if ((double)(size + 1) / capacity >= 0.55) {
            sizeUp();
        }
        int index = getHashIndex(k);
        int start = index;
        int firstbridge = -1;
        while (keys[index] != null) {
            if (keys[index] != BRIDGE && keys[index].equals(k)) {
                values[index] = v;
                return index;
            }
            if (keys[index] == BRIDGE && firstbridge == -1) {
                firstbridge = index;
            }
            index = (index + 1) % capacity;
            if (index == start) {
                break;
            }
        }
        int insertIndex;
        if (firstbridge >= 0) {
            insertIndex = firstbridge;
        } else {
            insertIndex = index;
        }
        keys[insertIndex] = k;
        values[insertIndex] = v;
        size++;
        return insertIndex;
    }

    private void sizeUp() {
        int newCapacity = capacity * 2;
        String[] oldKeys = keys;
        V[] oldValues = values;
        keys = new String[newCapacity];
        values = (V[]) new Object[newCapacity];
        capacity = newCapacity;
        size = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != BRIDGE) {
                insert(oldKeys[i], oldValues[i]);
            }
        }
    }
    private void sizeDown() {
        if (capacity <= 4) return;
        int newCapacity = capacity / 2;
        String[] oldKeys = keys;
        V[] oldValues = values;
        keys = new String[newCapacity];
        values = (V[]) new Object[newCapacity];
        capacity = newCapacity;
        size = 0;
        for (int i = 0; i < oldKeys.length; i++) {
            if (oldKeys[i] != null && oldKeys[i] != BRIDGE) {
                insert(oldKeys[i], oldValues[i]);
            }
        }
    }
    public int delete(String k) {
        int index = getHashIndex(k);
        int start = index;
        while (keys[index] != null) {
            if (keys[index] != BRIDGE && keys[index].equals(k)) {
                keys[index] = (String) BRIDGE;
                values[index] = null;
                size--;
                if ((double)size / capacity <= 0.3) {
                    sizeDown();
                }
                return index;
            }
            index = (index + 1) % capacity;
            if (index == start) {
                break;
            }
        }
        return getHashIndex(k);
    }
}
