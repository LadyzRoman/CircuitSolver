package org.roman.model.math;

public class RationalFraction
{
    private long numerator;
    private long denumerator;


    public final static  RationalFraction ONE = new RationalFraction(1);
    public final static RationalFraction NULL = new RationalFraction();

    public RationalFraction(long numerator, long denumerator)
    {
        this.numerator = numerator;
        this.denumerator = denumerator;
        reduce();
    }

    public RationalFraction()
    {
        numerator = 0;
        denumerator = 1;
    }

    public RationalFraction(int numerator)
    {
        this.numerator = numerator;
        denumerator = 1;
    }

    public RationalFraction add(RationalFraction addition)
    {
        long commonDenum = Util.lcm(denumerator, addition.denumerator);
        long commonNum = numerator * (commonDenum / denumerator)
                + addition.numerator * (commonDenum / addition.denumerator);

        RationalFraction rf = new RationalFraction(commonNum, commonDenum);
        rf.reduce();
        return rf;
    }

    public RationalFraction sub(RationalFraction sub)
    {
        long commonDenum = Util.lcm(denumerator, sub.denumerator);
        long commonNum = numerator * (commonDenum / denumerator)
                - sub.numerator * (commonDenum / sub.denumerator);

        RationalFraction rf = new RationalFraction(commonNum, commonDenum);
        rf.reduce();
        return rf;
    }

    public RationalFraction mul(RationalFraction mul)
    {
        long commonDenum = denumerator * mul.denumerator;
        long commonNum = numerator * mul.numerator;

        RationalFraction rf = new RationalFraction(commonNum, commonDenum);
        rf.reduce();
        return rf;
    }

    public RationalFraction div(RationalFraction divider)
    {
        long commonDenum = denumerator * divider.numerator;
        long commonNum = numerator * divider.denumerator;

        RationalFraction rf = new RationalFraction(commonNum, commonDenum);
        rf.reduce();
        return rf;
    }

    private void reduce()
    {
        long gcd = Util.gcd(Math.abs(numerator), denumerator);
        numerator /= gcd;
        denumerator /= gcd;
    }

    @Override
    public String toString()
    {
        return String.format("%d / %d" , numerator, denumerator);
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RationalFraction that = (RationalFraction) o;
        that.reduce();
        reduce();

        if (numerator != that.numerator) return false;
        return denumerator == that.denumerator;
    }

    @Override
    public int hashCode()
    {
        reduce();
        int result = (int) (numerator ^ (numerator >>> 32));
        result = 31 * result + (int) (denumerator ^ (denumerator >>> 32));
        return result;
    }
}
