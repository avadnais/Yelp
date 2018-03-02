class BusinessHashtable {
    static final int INITIAL_CAP = (1024);

    static final class Node {

        final String name;
        YelpProfile profile;
        int hash;
        Node next;


        /**
         * @param name
         * @param profile
         * @param hash
         * @param next
         */
        Node(String name, YelpProfile profile, int hash, Node next) {
            this.name = name;
            this.profile = profile;
            this.hash = hash;
            this.next = next;
        }
    }
    int count = 0;
    int resizes = 0;
    Node[] table = new Node[INITIAL_CAP];

    YelpProfile get(String name) {
        int h = name.hashCode();
        int i = h & (this.table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (h == e.name.hashCode() && e.name.equals(name))
                return e.profile;
        }
        return null;
    }

    void put(String name, YelpProfile p) {
        int h = name.hashCode();
        int i = h & (this.table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (name.equals(e.name)) {
                e.profile = p;
                count++;
                return;
            }
        }
        Node q = new Node(name, p, p.hashCode(), table[i]);
        table[i] = q;
        if(++count > 3 * table.length / 4){
            resize();
        }

    }

    private void resize() {
        Node[] newtable = new Node[table.length * 2];
        for (int i = 0; i < table.length; i++) {
            Node list = table[i];
            while (list != null) {
                Node next = list.next;
                int h = list.name.hashCode();
                int j = h & (newtable.length - 1);
                list.next = newtable[j];
                newtable[j] = list;
                list = next;
            }
        }
        table = newtable;
    }

    void printAll() {
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                e.profile.display();
            }
        }
    }

    void printAllNames(){
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                System.out.println(e.profile.name);
            }
        }
    }
}


