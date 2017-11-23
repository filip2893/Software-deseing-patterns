/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterator;

import composite.Mjesto;
import java.util.ArrayList;
import java.util.List;
import java.lang.IndexOutOfBoundsException;

/**
 *
 * @author Filip
 */
public class Mjesta {

    private Mjesto[] m_data;

    public Mjesta(int size) {
        this.m_data = new Mjesto[size];
    }

    public class Iterator {

        private Mjesta m_collection;
        private int mj_next;
        private Mjesto m_current;

        public Iterator(Mjesta mjesta) {
            m_collection = mjesta;
        }

        public void first() {
            mj_next = 0;
            m_current = m_collection.m_data[0];
            next();
        }

        public void next() {
            try {                
                m_current = m_collection.m_data[mj_next];
                mj_next++;
            } catch (IndexOutOfBoundsException e) {
                m_current = null;
            }
        }

        public boolean is_done() {
            return m_current == null;
        }

        public Mjesto current_item() {
            return m_current;
        }
    }

    public void add(Mjesto mjesto, int i) {
        m_data[i] = mjesto;
    }
    
    public void remove(int elem) {
        int size = this.m_data.length - 1; 
        if (size == 0) {
            return;
        }
        Mjesto[] nova = new Mjesto[size]; 
        int j = 0;
        for (int i = 0; i < this.m_data.length; i++) {
            if (i != elem) {
                nova[j] = this.m_data[i];
                j++;
            }
        }
        this.m_data = new Mjesto[size];
        this.m_data = nova;
    }
    
    public int length(){
      return  m_data.length; 
    }

    public Iterator create_iterator() {
        return new Iterator(this);
    }
}
