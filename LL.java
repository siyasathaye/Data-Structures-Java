import java.util.Objects;

public class LL<T>{
    private LLNode head;
    private LLNode tail;
    private int length;

    public class LLNode {
        private String index;
        private T data;
        private LLNode next;

        public LLNode() {
            this.index = null;
            this.data = null;
            this.next = null;
        }

        public LLNode(String _index, T _data) {
            this.index = _index;
            this.data = _data;
            this.next = null;

        }
        public String getIndex() {
            return this.index;
        }
        public T getData() {
            return this.data;
        }
        public void setData(T d) {
            this.data = d;
        }
    }
    public LL () {
        this.length = 0;
        head = new LLNode();
        tail = new LLNode();
        head.next = tail;
    }
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("print the series ...\n");
        output.append("==================\n");
        LLNode current = head;
        while (current != null) {
            output.append(current.index).append("\t: ").append(current.data).append("\n");
            current = current.next;
        }
        return output.toString();
    }
    public int getLength() {
        return length;
    }
    public String[] getDataArray() {
        String[] output_array = new String[length];
        LLNode current = head.next;
        int i = 0;
        while (current != tail) {
            output_array[i] = String.valueOf(current.data);
            current = current.next;
            i++;
        }
        return output_array;
    }
    public String[] getIndexArray() {
        String[] output_array = new String[length];
        LLNode current = head.next;
        int i = 0;
        while (current != tail) {
            output_array[i] = current.index;
            current = current.next;
            i++;
        }
        return output_array;
    }
    public void appendNode(String _index, T _data) {
        if (_index == null || _index.isEmpty()) {
            _index = String.valueOf(length);
        }
        LLNode newNode = new LLNode(_index, _data);
        LLNode current = head;
        while (current.next != tail) {
            current = current.next;
        }
        newNode.next = tail;
        current.next = newNode;
        length++;
    }
    public LLNode searchNode(String _index) {
        LLNode current = head.next;
        while (current != tail) {
            if (Objects.equals(current.index, _index)){
                return current;
            }
            current = current.next;
        }
        return null;
    }
    public void removeNode(String _index) throws IllegalArgumentException{
        LLNode previous = head;
        LLNode current = head.next;
        while (current != tail) {
            if (Objects.equals(current.index, _index)) {
                previous.next = current.next;
                length--;
                return;
            }
            previous = current;
            current = current.next;
        }
        throw new IllegalArgumentException("removeNode(String _index): No node with an index " + _index +
                " in the list");
    }
    public void updateNode(String _index, T value) throws IllegalArgumentException{
        LLNode current = head.next;
        while (current != tail) {
            if (Objects.equals(current.index, _index)){
                current.data = value;
                return;
            }
            current = current.next;
        }
        throw new IllegalArgumentException("updateNode(String _index, T value): No node with an index " + _index +
                " in the list");
    }

}

