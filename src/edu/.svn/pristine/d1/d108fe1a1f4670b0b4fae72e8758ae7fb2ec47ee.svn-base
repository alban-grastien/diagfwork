package edu.supercom.util;

import java.util.List;

/**
 * An implementation of Mersenne twister.  
 * http://en.wikipedia.org/w/index.php?title=Mersenne_twister&oldid=434152524
 *
 * @author Alban Grastien
 */
public class Mersenne implements PseudoRandom {
    private final int[] _state;

    private int _index = 0;

    private final static int TWOPOWER32 = (int)Math.pow(2,32);

    public Mersenne(List<Integer> list) {
        _state = new int[list.size()];
        for (int i=0 ; i<_state.length ; i++) {
            _state[i] = list.get(i);
        }
    }

    public Mersenne(int seed) {
        final int STATE_SIZE = 624;
        _state = new int[STATE_SIZE];
        _state[0] = seed;
        for (int i=1 ; i<STATE_SIZE ; i++) {
            _state[i] = (1812433253 * (_state[i-1] ^ (_state[i-1] >> 30)) + i) & (TWOPOWER32-1);
        }
    }

    public void generateNumbers() {
        for (int i=0 ; i<_state.length ; i++) {
            int y = ((_state[i] & TWOPOWER32)==0?0:1)
                    + (_state[(i+1) % _state.length] & (TWOPOWER32-1));
            _state[i] = _state[(i+397) % _state.length]  ^ (y >> 1);
            if (y % 2 == 0) {
                _state[i] = _state[i] ^ 0x9908b0df;
            }
        }
    }

    public int extractNumber() {
        if (_index == 0) {
            generateNumbers();
        }

        int y = _state[_index];
        y = y ^ (y >> 11);
        y = y ^ ((y<<7) & 0x9d2c5680);
        y = y ^ ((y<<15) & 0xefc60000);
        y = y ^ (y >> 18);

        _index = (_index+1) % _state.length;
        return y;
    }

    @Override
    public int rand(int n) {
        final int nb = extractNumber();
        return nb % n;
    }
}
