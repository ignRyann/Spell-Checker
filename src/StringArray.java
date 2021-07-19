public class StringArray {

    private String[] data;
    private int size;

    public StringArray(){
        data = new String[100];
        size = 0;
    }

    public StringArray(StringArray a){
        data = new String[a.size()];
        System.arraycopy(a.convertToArray(), 0, data, 0, a.size());
        size = a.size();
    }

    public String[] convertToArray(){
        String[] data2 = new String[size];
        System.arraycopy(data, 0, data2, 0, size);
        return data2;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public String get(int index){
        if (index < size){
            return data[index];
        }else{
            return null;
        }
    }

    public void set(int index, String s){
        if (index < size){
            data[index] = s;
        }
    }

    public void add(String s){
        adjustSize();
        data[size] = s;
        size += 1;
    }

    public void insert(int index, String s){
        adjustSize();
        for (int i = size; i != index - 1; i--){
            data[i + 1] = data[i];
        }
        data[index] = s;
        size += 1;
    }

    public void remove(int index){
        if (index < size){
            if (size - index >= 0) System.arraycopy(data, index + 1, data, index, size - index);
        }
        size -= 1;
    }

    public boolean contains(String s){
        for (String s1 : data){
            if (s.equalsIgnoreCase(s1)){
                return true;
            }
        }
        return false;
    }

    public boolean containsMatchingCase(String s){
        for (String s1 : data){
            if (s.equals(s1)){
                return true;
            }
        }
        return false;
    }

    public int indexOf(String s){
        for (int i = 0; i < size; i++){
            if (data[i].equalsIgnoreCase(s)){
                return i;
            }
        }
        return -1;
    }

    public int indexOfMatchingCase(String s){
        for (int i = 0; i < size; i++){
            if (data[i].equals(s)){
                return i;
            }
        }
        return -1;
    }

    private void adjustSize(){
        if (size == data.length){
            int newSize = (int) (1.1 * size);
            String[] newData = new String[newSize];
            System.arraycopy(data, 0, newData, 0, size);
            data = newData;
        }
    }

}
