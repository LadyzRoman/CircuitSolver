package org.roman.model.math;

public class Util
{
    public static long gcd(long a, long b)
    {
        while (a != 0 && b != 0)
        {
            if (a > b)
                a %= b;
            else
                b %= a;
        }

        return b + a;
    }


    public static long lcm(long a, long b)
    {
        return Math.abs(a * b) / gcd(a, b);
    }
}
