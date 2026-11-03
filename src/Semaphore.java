class Semaphore
{
    private int m_availablePermits;
    public Semaphore(int i)
    {
        m_availablePermits = i;
    }

    public synchronized void Acquire() throws InterruptedException
    {
        while(m_availablePermits <= 0)
        {
            wait();
        }
        m_availablePermits--;
    }

    public synchronized boolean TryAcquire()
    {
        if (m_availablePermits <= 0) return false;
        m_availablePermits--;
        return true;
    }

    public synchronized boolean TryAcquireIn(long seconds) throws InterruptedException
    {
        if (seconds < 0) throw new IllegalArgumentException("Time cannot be negative");

        long start = System.nanoTime();
        while (m_availablePermits <= 0)
        {
            long remainingTimeNANO = seconds * 1_000_000_000L - (System.nanoTime() - start);

            if (remainingTimeNANO <= 0) return false;
            
            wait(remainingTimeNANO / 1_000_000L, (int)(remainingTimeNANO % 1_000_000L));
        }
        m_availablePermits--;
        return true;
    }

    public synchronized void Release()
    {
        m_availablePermits++;
        notifyAll();
    }
}
