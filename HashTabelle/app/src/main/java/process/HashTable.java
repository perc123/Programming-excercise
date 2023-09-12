package process;

public class HashTable {

    public int HASH_TABLE_SIZE;
    private int size;
    private ValueEntry[] table;
    private int totalprimeSize;


    public HashTable(int ts)
    {

        size = 0;
        HASH_TABLE_SIZE = ts;
        table = new ValueEntry[HASH_TABLE_SIZE];

        // Iterating over the table
        for (int i = 0; i < HASH_TABLE_SIZE; i++)
            table[i] = null;
        totalprimeSize = getPrime();
    }

    // Check for the prime number
    public int getPrime()
    {
        // Iterating using for loop in reverse order
        for (int i = HASH_TABLE_SIZE - 1; i >= 1; i--) {

            // Initially assigning count to zero
            int cnt = 0;

            // Now, iterating from 2 up to the desired number to be checked by dividing it with all numbers in between [2 - table size]
            for (int j = 2; j * j <= i; j++)

                // If number is divisible means not a prime number
                if (i % j == 0)
                    cnt++;

            // if count holds 0 till last the number is not divisible
            if (cnt == 0)

                // It means it is a prime number so return the number
                return i;
        }
        return 3;
    }

    // To get number of key-value pairs
    public int getSize() { return size; }
    public boolean isEmpty() { return size == 0; }

    // To get value of a key
    public int getkey(String key)
    {
        int hash1 = myhash1(key);
        int hash2 = myhash2(key);

        while (table[hash1] != null
                && !table[hash1].key.equals(key)) {
            hash1 += hash2;
            hash1 %= HASH_TABLE_SIZE;
        }
        return table[hash1].value;
    }

    // To insert a key value pair
    public void insert(String key, int value)
    {
        // checking the size of table and comparing it with users input value
        if (size == HASH_TABLE_SIZE) {
            System.out.println("Table is full");
            return;
        }

        int hashing1 = myhash1(key);
        int hashing2 = myhash2(key);
        while (table[hashing1] != null) {
            hashing1 += hashing2;
            hashing1 %= HASH_TABLE_SIZE;
        }

        table[hashing1] = new ValueEntry(key, value);
        size++;
    }

    // To remove a key
    public void remove(String key)
    {
        int hash1 = myhash1(key);
        int hash2 = myhash2(key);
        while (table[hash1] != null
                && !table[hash1].key.equals(key)) {
            hash1 += hash2;
            hash1 %= HASH_TABLE_SIZE;
        }
        table[hash1] = null;
        size--;
    }

    // Function gives a hash value for a given string (linear probing)
    private int myhash1(String y)
    {
        int myhashVal1 = y.hashCode();
        myhashVal1 %= HASH_TABLE_SIZE;
        if (myhashVal1 < 0)
            myhashVal1 += HASH_TABLE_SIZE;
        return myhashVal1;
    }

    //  quadratic probing
    private int myhash2(String y)
    {
        int myhashVal2 = y.hashCode();
        myhashVal2 %= HASH_TABLE_SIZE;
        if (myhashVal2 < 0)
            myhashVal2 += HASH_TABLE_SIZE;
        return totalprimeSize - myhashVal2 % totalprimeSize;
    }
}