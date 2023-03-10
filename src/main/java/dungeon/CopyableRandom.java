package dungeon;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class CopyableRandom extends Random {
    private final static long multiplier = 0x5DEECE66DL;
    private final static long addend = 0xBL;
    private final static long mask = (1L << 48) - 1;
    private static volatile long seedUniquifier = 8682522807148012L;
    private final AtomicLong innerSeed = new AtomicLong(0L);

    public CopyableRandom() {
        this(++seedUniquifier + System.nanoTime());
    }

    public CopyableRandom(long innerSeed) {
        this.innerSeed.set((innerSeed ^ multiplier) & mask);
    }

    /* copy of superclasses code, as you can seed the seed changes */
    @Override
    protected int next(int bits) {
        long oldseed, nextseed;
        AtomicLong seed_ = this.innerSeed;
        do {
            oldseed = seed_.get();
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!seed_.compareAndSet(oldseed, nextseed));
        return (int) (nextseed >>> (48 - bits));
    }

    /* necessary to prevent changes to seed that are made in constructor */
    public CopyableRandom copy() {
        return new CopyableRandom((innerSeed.get() ^ multiplier) & mask);
    }
}